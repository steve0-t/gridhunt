package gamestudio.game.gridhunt;

import gamestudio.game.gridhunt.consoleui.ConsoleUI;
import gamestudio.game.gridhunt.core.World;

public class Main {
  public static void main(String[] args) {
    World world = new World();
    ConsoleUI cli = new ConsoleUI(world);

    cli.play();
  }
}
