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

  public static void drawCircle(Graphics g, int x, int y, int r) {
    g.drawOval(x - r, y - r, r + r, r + r);
  }

  public static class V {

    public static Transform T = new Transform();
    public int x, y;

    public V(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public V(V v) {
      this.x = v.x;
      this.y = v.y;
    }


    public void add(V v) {
      this.x += v.x;
      this.y += v.y;
    }

    public void set(V v) {
      this.x = v.x;
      this.y = v.y;
    }

    public void set(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int tx() {
      return this.x * T.newScale / T.oldScale + T.dx;
    }

    public int ty() {
      return this.y * T.newScale / T.oldScale + T.dy;
    }

    public static class Transform {

      public int dx, dy, oldScale, newScale;

      public void setScale(int oldW, int oldH, int newW, int newH) {
        oldScale = oldW < oldH ? oldH : oldW;
        newScale = newW < newH ? newH : newW;
      }

      public int trans(int oldX, int oldW, int newX, int newW) {
        return (-oldX - oldW / 2) * newW / oldW + newX + newW / 2;
      }

      public void set(VS from, VS to) {
        setScale(from.size.x, from.size.y, to.size.x, to.size.y);
        dx = trans(from.loc.x, from.size.x, to.loc.x, to.size.x);
        dy = trans(from.loc.y, from.size.y, to.loc.y, to.size.y);
      }

      public void set(BBox from, VS to) {
        setScale(from.h.size(), from.v.size(), to.size.x, to.size.y);
        dx = trans(from.h.lo, from.h.size(), to.loc.x, to.size.x);
        dy = trans(from.v.lo, from.v.size(), to.loc.y, to.size.y);
      }
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

    public int lowX() {
      return loc.x;
    }

    public int hiX() {
      return loc.x + size.x;
    }

    public int midX() {
      return loc.x + size.x / 2;
    }

    public int lowY() {
      return loc.y;
    }

    public int hiY() {
      return loc.y + size.y;
    }

    public int midY() {
      return loc.y + size.y / 2;
    }
  }

  public static class LoHi {

    int lo;
    int hi;

    public LoHi(int min, int max) {
      this.lo = min;
      this.hi = max;
    }

    public void set(int v) {
      lo = v;
      hi = v;
    }

    public void add(int v) {
      lo = v < lo ? v : lo;
      hi = v > hi ? v : hi;
    }

    public int size() {
      return lo < hi ? hi - lo : 1;
    }
  }

  public static class BBox {

    LoHi h;
    LoHi v;

    public BBox() {
      h = new LoHi(0, 0);
      v = new LoHi(0, 0);
    }

    public void set(int x, int y) {
      h.set(x);
      v.set(y);
    }

    public void add(int x, int y) {
      h.add(x);
      v.add(y);
    }

    public void add(V p) {
      h.add(p.x);
      v.add(p.y);
    }

    public VS getNewVS() {
      return new VS(h.lo, v.lo, h.size(), v.size());
    }

    public void draw(Graphics g) {
      g.drawRect(h.lo, v.lo, h.size(), v.size());
    }
  }

  /*
  此类定义了线条图形的基本属性和方法。所谓线条，即把屏幕中每一个点的坐标保存起来，然后每两点画线连接。
   */
  public static class PL {

    public V[] points;

    public PL(int count) {
      points = new V[count];
      for (int i = 0; i < count; i++) {
        points[i] = new V(0, 0);
      }
    }

    public int size() {
      return points.length;
    }

    public void drawN(Graphics g, int n) {
      for (int i = 1; i < n; i++) {
        g.drawLine(points[i - 1].x, points[i - 1].y, points[i].x, points[i].y);
      }
      drawNdots(g, n);
    }

    public void drawNdots(Graphics g, int n) {
      for (int i = 0; i < n; i++) {
        drawCircle(g, points[i].x, points[i].y, 4);
      }
    }

    public void draw(Graphics g) {
      drawN(g, size());
    }

    public void transform() {
      for (int i = 0; i < points.length; i++) {
        points[i].set(points[i].tx(), points[i].ty());
      }
    }

  }
}
