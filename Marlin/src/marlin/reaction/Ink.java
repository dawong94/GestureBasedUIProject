package marlin.reaction;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import marlin.I;
import marlin.UC;
import marlin.graphicslib.G;

public class Ink extends G.PL implements I.Show {

  public static Buffer BUFFER = new Buffer();

  public Ink() {
    super(BUFFER.n);
    for (int i = 0; i < BUFFER.n; i++) {
      this.points[i].set(BUFFER.points[i]);
    }
  }

  @Override
  public void show(Graphics g) {
    draw(g);
  }

  public static class inkList extends ArrayList<Ink> implements I.Show {

    @Override
    public void show(Graphics g) {
      for (Ink ink : this) {
        ink.show(g);
      }
    }
  }

  public static class Buffer extends G.PL implements I.Show, I.Area {

    public static final int MAX = UC.inkBufferMax;
    public int n = 0;

    private Buffer() {
      super(MAX);
    }

    public void add(int x, int y) {
      if (n < MAX) {
        points[n++].set(x, y);
      }
    }

    public void clear() {
      n = 0;
    }

    @Override
    public boolean hit(int x, int y) {
      return true;
    }

    @Override
    public void pressed(int x, int y) {
      clear();
      add(x, y);
    }

    @Override
    public void dragged(int x, int y) {
      add(x, y);
    }

    @Override
    public void released(int x, int y) {

    }

    @Override
    public void show(Graphics g) {
      g.setColor(Color.RED);
      drawN(g, n);
    }
  }
}
