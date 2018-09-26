package marlin.sandbox;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import marlin.UC;
import marlin.graphicslib.G;
import marlin.graphicslib.Window;
import marlin.reaction.Ink;
import marlin.reaction.Ink.inkList;

public class PaintInk extends Window {

  public static Ink.inkList inkList = new inkList();

  public PaintInk() {
    super("PaintInk", UC.screenWidth, UC.screenHeight);
  }

  @Override
  public void paintComponent(Graphics g) {
    G.fillBackground(g, Color.WHITE);
    g.setColor(Color.RED);
    g.fillRect(100, 100, 100, 100);
    inkList.show(g);
    Ink.BUFFER.show(g);
  }

  @Override
  public void mousePressed(MouseEvent me) {
    Ink.BUFFER.pressed(me.getX(), me.getY());
    repaint();
  }

  @Override
  public void mouseDragged(MouseEvent me) {
    Ink.BUFFER.dragged(me.getX(), me.getY());
    repaint();
  }

  @Override
  public void mouseReleased(MouseEvent me) {
    inkList.add(new Ink());
    repaint();
  }
}
