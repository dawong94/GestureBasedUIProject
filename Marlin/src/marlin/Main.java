package marlin;

import java.awt.EventQueue;
import marlin.graphicslib.Window;
import marlin.sandbox.PaintInk;
import marlin.sandbox.ShapeTrainer;
import marlin.sandbox.SimpleReaction;
import marlin.sandbox.Squares;

public class Main {

  public static void main(String[] args) {

//    Window.PANEL = new Squares();
//    Window.PANEL = new PaintInk();
//    Window.PANEL = new ShapeTrainer();
    Window.PANEL = new SimpleReaction();
    Window.PANEL.launch();
  }
}
