package marlin.sandbox;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import marlin.graphicslib.G;
import marlin.graphicslib.UC;
import marlin.graphicslib.Window;
import marlin.reaction.Gesture;
import marlin.reaction.Ink;
import marlin.reaction.Layer;
import marlin.reaction.Mass;
import marlin.reaction.Reaction;

public class SimpleReaction extends Window {

  static {
    new Layer("BACK");
    new Layer("FORE");
  }

  public SimpleReaction() {
    super("SimpleReaction", UC.screenWidth, UC.screenHeight);
    Reaction.initialReactions.addReaction(new Reaction("SW-SW") {
      @Override
      public void act(Gesture gesture) {
        new Box(gesture.vs);
      }

      @Override
      public int bid(Gesture gesture) {
        return 0;
      }
    });
  }

  @Override
  public void paintComponent(Graphics g) {
    G.fillBackground(g, Color.WHITE);
    g.setColor(Color.BLUE);
    Ink.BUFFER.show(g);
    Layer.ALL.show(g);
  }

  @Override
  public void mousePressed(MouseEvent me) {
    Gesture.AREA.dn(me.getX(), me.getY());
  }

  @Override
  public void mouseDragged(MouseEvent me) {
    Gesture.AREA.drag(me.getX(), me.getY());
  }

  @Override
  public void mouseReleased(MouseEvent me) {
    Gesture.AREA.up(me.getX(), me.getY());
  }

  public static class Box extends Mass {

    public G.VS vs;
    public Color c = G.rndColor();

    public Box(G.VS vs) {
      super("BACK");
      this.vs = vs;
    }

    @Override
    public void show(Graphics g) {
      vs.fill(g, c);
    }
  }
}
