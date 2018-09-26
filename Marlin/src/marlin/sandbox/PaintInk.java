package marlin.sandbox;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import marlin.graphicslib.UC;
import marlin.graphicslib.G;
import marlin.graphicslib.Window;
import marlin.reaction.Ink;

public class PaintInk extends Window {

  public static Ink.inkList inkList = new Ink.inkList();

  public PaintInk() {
    super("PaintInk", UC.screenWidth, UC.screenHeight);
  }

  @Override
  public void paintComponent(Graphics g) {
    G.fillBackground(g, Color.WHITE);
    g.setColor(Color.RED);
    /*
    此处绘制的过去已经画好并保存在intList的图形。
     */
    inkList.show(g);
    /*
    此处绘制的是目前正在画（鼠标释放前）并实时保存在Buffer对象里的图形。
     */
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

  /*
  鼠标释放时，将当前Buffer的图形作为一个新的Int对象并保存到intList中。
   */
  @Override
  public void mouseReleased(MouseEvent me) {
    inkList.add(new Ink());
    repaint();
  }
}
