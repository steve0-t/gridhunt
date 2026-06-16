package gamestudio.game.gridhunt.consoleui;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import gamestudio.game.gridhunt.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import gamestudio.entity.Comment;
import gamestudio.entity.Rating;
import gamestudio.entity.Score;
import sk.tuke.gamestudio.game.gridhunt.core.*;
import gamestudio.game.gridhunt.core.tile.Tile;
import gamestudio.service.CommentService;
import gamestudio.service.CommentServiceJDBC;
import gamestudio.service.RatingService;
import gamestudio.service.RatingServiceJDBC;
import gamestudio.service.ScoreService;
import gamestudio.service.ScoreServiceJDBC;

public class ConsoleUI {
  public ConsoleUI(World world) {
    this.world = world;
    inputScanner = new Scanner(System.in);
    renderMode = RenderMode.NORMAL;
  }

  public void play() {
    clearConsole();
    askForName();
    askForDifficulty();
    printRules();
    gameMap = world.getLevels().getActive().getActiveGameMap();
    world.update();
    do {
      render();
      getInput();
      world.update();
    } while (world.getGameState() == GameState.PLAYING);

    printMap();

    switch (world.getGameState()) {
      case WON -> System.out.println("\nYou won!");
      case QUIT -> System.out.println("\nGame over");
      case FAILED -> System.out.println("\nYou lost");
      default -> {}
    }

    saveScore();

    if (askForReplay()) {
      world.setGameState(GameState.PLAYING);
      play();
    }

    askForRating();
    askForComment();
  }

  private void clearConsole() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  private void render() {
    System.out.println();
    pollMessages();
    printGameScore();
    printMap();
  }

  private void askForName() {
    System.out.println();
    do {
      System.out.print("Enter player name (max 60 characters): ");
      playerName = inputScanner.nextLine();
    } while (playerName.length() > 60);
  }

  private void saveScore() {
    scoreService2.addScore(
        new Score(world.getGameName(), playerName, world.getScore(), new Date()));
    // scoreService.addScore(new Score(world.getGameName(), playerName, world.getScore(), new
    // Date()));
  }

  private void printGameScore() {
    System.out.println("Current score " + world.getScore());
  }

  private void askForDifficulty() {
    System.out.print(
        """
            Choose difficulty (type number)
            1. EASY (default)
            2. MEDIUM
            3. HARD
        """);
    String input = inputScanner.nextLine();
    // System.out.println(input);
    switch (input) {
      case "1" -> world.setDifficulty(Difficulty.EASY);
      case "2" -> world.setDifficulty(Difficulty.MEDIUM);
      case "3" -> world.setDifficulty(Difficulty.HARD);
      default -> askForDifficulty();
    }
  }

  private boolean askForReplay() {
    System.out.print("Would you like to play again (yes/no)? ");
    String input = inputScanner.nextLine().trim().toLowerCase();
    return input.equals("yes") || input.equals("y");
  }

  private void getInput() {
    System.out.print("Enter command: ");
    String input = inputScanner.nextLine().trim().toLowerCase();
    switch (input) {
      case "show scores", "ss" -> showTopScores();
      case "show comments", "sc" -> showComments();
      case "show rating", "sr" -> showRating();
      case "show average rating", "sar" -> showAverageRating();
      case "mn", "mu" -> delegateAttack(Direction.NORTH);
      case "ms", "md" -> delegateAttack(Direction.SOUTH);
      case "me", "mr" -> delegateAttack(Direction.EAST);
      case "mw", "ml" -> delegateAttack(Direction.WEST);
      case "v", "reveal" -> toggleReveal();
      case "h", "usage" -> printHelp();
      case "q", "quit" -> world.setGameState(GameState.QUIT);
      case "l", "left" -> delegateMovement(Direction.WEST);
      case "r", "right" -> delegateMovement(Direction.EAST);
      case "u", "up" -> delegateMovement(Direction.NORTH);
      case "d", "down" -> delegateMovement(Direction.SOUTH);
      default -> getInput();
    }
  }

  private void toggleReveal() {
    renderMode = renderMode == RenderMode.REVEALED ? RenderMode.NORMAL : RenderMode.REVEALED;
  }

  private void delegateAttack(Direction dir) {
    Armed actor = world.getLevels().getActive().getActiveScene().getActivePlayer();
    if (actor == null) return;
    actor.shoot(dir);
  }

  private void delegateMovement(Direction dir) {
    world.getLevels().getActive().getActiveScene().getActivePlayer().requestMovement(dir);
  }

  private void printRules() {
    System.out.println("Press Q to quit or type 'quit'");
    System.out.println("Press H to show usage or type 'usage'");
    System.out.println("Press V to reveal holes or type 'reveal'");
    System.out.println("Type left/right/up/down to move (or l/r/u/d)");
    System.out.println("Type m followed by direction to shoot in that direction");
    System.out.println("e.g. ms -> shoots south and md -> shoots south (down) as well");
    System.out.println("Type 'sc' or 'show comments' to show comments");
    System.out.println("Type 'sr' or 'show rating' to show rating");
    System.out.println("Type 'sar' or 'show average rating' to show average rating");
    System.out.println("Type 'ss' or 'show scores' to show top scores");
    System.out.print("Press enter to continue...");
    inputScanner.nextLine();
  }

  private void pollMessages() {
    String[] messages = world.pollHints();
    if (messages != null) {
      System.out.println("Hints:");
      for (String m : messages) {
        System.out.println(m);
      }
    }
  }

  private void printHelp() {
    System.out.println();
    printRules();
    System.out.println();
    System.out.print("Press enter to continue ");
    inputScanner.nextLine();
  }

  private void printMap() {
    System.out.println();
    System.out.print("  ");
    for (int i = 0; i < gameMap.getWidth(); i++) System.out.printf("%c ", 'A' + i);
    System.out.println();

    for (int y = 0; y < gameMap.getHeight(); y++) {
      System.out.printf("%c", 'A' + y);
      for (int x = 0; x < gameMap.getWidth(); x++) renderTile(gameMap.getTile(x, y));
      System.out.println();
    }
  }

  private void renderTile(Tile tile) {
    boolean visible = tile.isExplored() || renderMode == RenderMode.REVEALED;
    System.out.print(' ');
    if (!visible) {
      System.out.print('#');
      return;
    }
    char res = tile.getTileChar();
    if (tile.isOccupied()) res = tile.getLastActor().getAnimation();
    System.out.print(res);
  }

  private void showRating() {
    int rating = ratingService.getRating(world.getGameName(), playerName);
    System.out.println();
    System.out.printf("%s's rating: %d\n", playerName, rating);
  }

  private void showAverageRating() {
    int rating = ratingService.getAverageRating(world.getGameName());
    System.out.printf("Average rating for game %s: %d", world.getGameName(), rating);
  }

  private void showComments() {
    List<Comment> ret = commentService2.getComments(world.getGameName());
    System.out.println("Comments:");
    for (Comment c : ret) {
      System.out.println(c.getPlayer() + ": " + c.getComment());
    }
  }

  private void showTopScores() {
    List<Score> topScores = scoreService2.getTopScores(world.getGameName());
    if (topScores != null) {
      System.out.println();
      System.out.println("Top scores:");
      for (Score s : topScores) System.out.printf("%s: %d\n", s.getPlayer(), s.getPoints());
    }
  }

  private void askForRating() {
    System.out.print("Would you like to rate this game? (1-5) ");
    String input = inputScanner.nextLine();
    if (!input.isBlank()) {
      try {
        int rating = Integer.parseInt(input);
        ratingService2.setRating(new Rating(world.getGameName(), playerName, rating, new Date()));
        // ratingService.setRating(new Rating(world.getGameName(), playerName, rating, new Date()));
      } catch (Exception ignored) {
      }
    }
  }

  private void askForComment() {
    System.out.print("Would you like to leave a comment? ");
    String input = inputScanner.nextLine();
    if (!input.isBlank())
      commentService2.addComment(new Comment(playerName, world.getGameName(), input, new Date()));
    // commentService.addComment(new Comment(playerName, world.getGameName(), input, new Date()));
  }

  @Autowired private ScoreService scoreService2;
  @Autowired private RatingService ratingService2;
  @Autowired private CommentService commentService2;

  private final RatingService ratingService = new RatingServiceJDBC();
  private final CommentService commentService = new CommentServiceJDBC();
  private final ScoreService scoreService = new ScoreServiceJDBC();

  private RenderMode renderMode;
  private String playerName;
  private final World world;
  private GameMap gameMap;
  private final Scanner inputScanner;
}
