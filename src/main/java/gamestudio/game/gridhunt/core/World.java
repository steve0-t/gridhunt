package gamestudio.game.gridhunt.core;

import java.util.ArrayDeque;
import java.util.Queue;
import gamestudio.game.gridhunt.core.actor.Player;
import gamestudio.game.gridhunt.core.weapons.Hands;

public class World {
  public World() {
    this(Difficulty.EASY);
  }

  public World(Difficulty difficulty) {
    maxActors = 1000;
    totalScore = 0;

    levels = new Container<>(new Level[15]);
    messageBus = new ArrayDeque<>(50);

    gameName = "Gridhunt";
    this.difficulty = difficulty;

    reset();
  }

  public void update() {
    Scene scene = levels.getActive().getActiveScene();
    SceneResult res = scene.update();

    if (res.playerDied) gameState = GameState.FAILED;

    for (HintType h : res.hints) messageBus.add(h.getValue());

    totalScore += res.scoreDelta;
    // System.out.println("total score: " + totalScore);

    if (scene.getEnemyCount() <= 0) gameState = GameState.WON;
  }

  public void reset() {
    levels.reset();
    difficulty = Difficulty.EASY;
    gameState = GameState.PLAYING;
    levels.initElement(0, new Level(maxActors));
    getActiveLevel().setDifficulty(difficulty);
    totalScore = 0;
  }

  public void generateDefault() {
    if (levels.getActive() == null) levels.initElement(0, new Level(maxActors));
    Scene scene = levels.getActive().getActiveScene();
    scene.generateRandomMap(difficulty);
    scene.resetActors();

    Player player = new Player('*', new Hands(5));
    scene.setActivePlayer(player);
    scene.addPlayer(player, 0, 0);

    levels.getActive().setDifficulty(difficulty);
    levels.getActive().populateScene();
  }

  // public void copyFrom(World other) {
  //   this.difficulty = other.difficulty;
  //   this.gameState = other.gameState;
  //   this.levels = other.levels;
  //   this.messageBus = other.messageBus;
  //   this.totalScore = other.totalScore;
  // }

  public Container<Level> getLevels() {
    return levels;
  }

  public void nextLevel() {
    levels.initElement(levels.getActiveIndex() + 1, new Level(maxActors));
  }

  public String getGameName() {
    return gameName;
  }

  public Difficulty getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
    generateDefault();
  }

  public Level getActiveLevel() {
    return levels.getActive();
  }

  public GameState getGameState() {
    return gameState;
  }

  public void setGameState(GameState newState) {
    gameState = newState;
  }

  public int getMessageBusSize() {
    return messageBus.size();
  }

  public String[] pollHints() {
    String[] ret = messageBus.toArray(new String[0]);
    messageBus.clear();
    return ret;
  }

  public int getScore() {
    return totalScore;
  }

  private final int maxActors;
  private final String gameName;
  private int totalScore;

  private Queue<String> messageBus;

  private Container<Level> levels;

  private Difficulty difficulty;
  private GameState gameState;
}
