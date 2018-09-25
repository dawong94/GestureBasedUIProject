package marlin;

import marlin.graphicslib.Window;
import marlin.sandbox.Squares;

public class Main {

  public static void main(String[] args) {
    System.out.println("hello");
    Window.PANEL = new Squares();
    Window.PANEL.launch();
  }
}
