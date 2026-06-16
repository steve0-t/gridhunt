package gamestudio.server.controller;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;
import gamestudio.entity.SaveData;
import gamestudio.game.gridhunt.consoleui.RenderMode;
import gamestudio.game.gridhunt.core.Difficulty;
import gamestudio.game.gridhunt.core.Direction;
import gamestudio.game.gridhunt.core.GameMap;
import gamestudio.game.gridhunt.core.Scene;
import gamestudio.game.gridhunt.core.World;
import gamestudio.game.gridhunt.core.actor.Player;
import gamestudio.service.SaveService;

@Controller
@RequestMapping("/gridhunt")
@SessionAttributes("gameName")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GridhuntController {
  @GetMapping
  public String gridhunt(Model model, HttpSession session) {
    if (world != null && world.getActiveLevel().getActiveScene() != null && gameMap != null) {
      world.update();
      prepareModel(model);
    } else newGame(Difficulty.EASY, session, model);

    String username = (String) session.getAttribute("username");
    model.addAttribute("isLogged", username != null);
    model.addAttribute("username", username);
    return "gridhunt";
  }

  @GetMapping("/replay")
  public String replayGame(HttpSession session, Model model) {
    System.out.println("Replay called");
    world.reset();
    activeScene = world.getActiveLevel().getActiveScene();
    gameMap = activeScene.getGameMap();
    prepareModel(model);

    return "gridhunt :: gameFragment";
  }

  @GetMapping("/new/{difficulty}")
  public String newGame(@PathVariable Difficulty difficulty, HttpSession session, Model model) {
    System.out.println("Called new game!");
    world.reset();
    world.setDifficulty(difficulty);
    world.generateDefault();
    activeScene = world.getActiveLevel().getActiveScene();
    gameMap = activeScene.getGameMap();

    prepareModel(model);

    return "gridhunt :: gameFragment";
  }

  @GetMapping("/toggleView")
  public String toggleView(Model model) {
    System.out.println("View toggled");
    renderMode = renderMode == RenderMode.NORMAL ? RenderMode.REVEALED : RenderMode.NORMAL;
    prepareModel(model);
    return "gridhunt :: gridFragment";
  }

  @GetMapping("/move/{direction}")
  public String move(@PathVariable Direction direction, Model model) {
    // for (Direction dir : Direction.values()) if (dir != direction) return "gridhunt";
    System.out.println("move: " + direction);
    delegateMovement(direction);
    world.update();
    prepareModel(model);
    return "gridhunt :: gameFragment";
  }

  // @GetMapping("/genbat")
  // public String genBat(HttpSession session, Model model) {
  //   world.getActiveLevel().getActiveScene().addActor(new Bat('B'));
  //   prepareModel(model);
  //   return "gridhunt :: gameFragment";
  // }

  @GetMapping("/next")
  public String nextLevel(HttpSession session, Model model) {
    System.out.println("Creating next level...");

    world.nextLevel();
    world.generateDefault();
    activeScene = world.getActiveLevel().getActiveScene();
    gameMap = activeScene.getGameMap();

    System.out.println("Active level index: " + world.getLevels().getActiveIndex());
    prepareModel(model);
    return "gridhunt :: gameFragment";
  }

  @GetMapping("/attack/{direction}")
  public String attack(@PathVariable Direction direction, Model model) {
    // for (Direction dir : Direction.values()) if (dir != direction) return "gridhunt";
    System.out.println("attack: " + direction);
    delegateAttack(direction);
    world.update();
    prepareModel(model);
    return "gridhunt :: gameFragment";
  }

  @GetMapping("/reset")
  public String resetGame(HttpSession session, Model model) {
    renderMode = RenderMode.NORMAL;
    newGame(Difficulty.EASY, session, model);
    return "redirect:/";
  }

  @GetMapping("/save")
  public String saveGame(HttpSession session, Model model) {
    System.out.println("Saving game...");
    saveService.saveGame(renderMode, world, world.getActiveLevel().getActiveScene(), gameMap);

    prepareModel(model);
    return "gridhunt";
  }

  @GetMapping("/load")
  public String loadGame(Model model) {
    System.out.println("Loading game...");
    SaveData data = saveService.loadGame();

    if (data != null) {
      renderMode = data.getRenderMode();
      activeScene = data.getActiveScene();
      gameMap = data.getGameMap();
      world = data.getWorld();

      prepareModel(model);
    }

    return "gridhunt";
  }

  private void delegateMovement(Direction dir) {
    if (world != null && world.getActiveLevel().getActiveScene() != null && gameMap != null) {
      Player p = world.getLevels().getActive().getActiveScene().getActivePlayer();
      p.requestMovement(dir);
      System.out.println("delegated movement");
    }
  }

  private void delegateAttack(Direction dir) {
    if (world != null && world.getActiveLevel().getActiveScene() != null && gameMap != null) {
      Player actor = world.getLevels().getActive().getActiveScene().getActivePlayer();
      if (actor == null) return;
      actor.shoot(dir);
      System.out.println("delegated attack");
    }
  }

  private void prepareModel(Model model) {
    model.addAttribute("gameName", world.getGameName());
    model.addAttribute("gameAvailable", gameMap != null);
    model.addAttribute("gameStatus", world.getGameState().toString());
    model.addAttribute("hints", world.pollHints());
    model.addAttribute("gameMap", world.getActiveLevel().getActiveGameMap());
    model.addAttribute("renderMode", renderMode);
    model.addAttribute("score", world.getScore());
    model.addAttribute(
        "gameWon", world.getLevels().getActiveIndex() == world.getLevels().getMaxElements());

    ArrayList<Difficulty> difficulties = new ArrayList<Difficulty>();
    Collections.addAll(difficulties, Difficulty.values());
    model.addAttribute("diffs", difficulties);
  }

  private RenderMode renderMode = RenderMode.NORMAL;
  private World world = new World();
  private Scene activeScene;
  private GameMap gameMap;

  @Autowired private SaveService saveService;
}
