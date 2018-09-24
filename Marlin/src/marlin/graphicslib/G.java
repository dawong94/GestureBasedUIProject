package marlin.graphicslib;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * A helper class with some static variables and functions.
 */
public class G {

  public static Random RND = new Random();

  /**
   * Get a random integer in the range [0, max).
   */
  public static int rnd(int max) {
    return RND.nextInt(max);
  }

  public static Color rndColor() {
    return new Color(rnd(256), rnd(256), rnd(256));
  }

  public static void fillBackground(Graphics g, Color c) {
    g.setColor(c);
    g.fillRect(0, 0, 3000, 3000);
  }

  public static class V {

    public int x, y;

    public V(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  /**
   * A static class represents an area which top-left coordinate saved in loc, width and height
   * saved in size.
   */
  public static class VS {

    public V loc, size;

    public VS(int x, int y, int w, int h) {
      loc = new V(x, y);
      size = new V(w, h);
    }

    public void fill(Graphics g, Color c) {
      g.setColor(c);
      g.fillRect(loc.x, loc.y, size.x, size.y);
    }

    /**
     * Check if the point user hit locates in the VS area.
     */
    public boolean hit(int x, int y) {
      return loc.x <= x && loc.y <= y && loc.x + size.x >= x && loc.y + size.y >= y;
    }

    /**
     * Adjust the size (width and height) of the VS.
     */
    public void resize(int x, int y) {
      size.x = x;
      size.y = y;
    }
  }

  public static class LoH {

  }

  public static class BBox {

  }

  public static class PL {

  }
}