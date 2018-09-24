package marlin.sandbox;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import marlin.graphicslib.G;
import marlin.graphicslib.G.VS;
import marlin.graphicslib.Window;

public class Squares extends Window {

  public static VS theVS = new VS(100, 100, 200, 300);
  public static Color theColor = G.rndColor();
  public static Square theSquare = new Square(200, 320);
  public static Square.SquareList theList = new Square.SquareList();
  public static boolean dragging = false;
  public static int distanceX, distanceY;

  public Squares() {
    super("square", 1000, 800);
  }

  public void paintComponent(Graphics g) {
    G.fillBackground(g,
        Color.WHITE); // Make sure every time the paintComponent be called, the panel resumes to white and only displays new images. It solves my problem in Windows system.
//    g.setColor(theColor);
//    g.fillRect(100,100,200,300);

//    theVS.fill(g, theColor); // Draw a square with color of theColor, instead of the two statements above

//    theSquare.draw(g); // Draw a square with random color, instead of the statement above
    theList.draw(g);
  }

  public void mousePressed(MouseEvent me) {
//    if (theSquare.hit(me.getX(), me.getY())) { // This if statement changes random color of theSquare
//      theSquare.c = G.rndColor();
//      repaint();
//    }
//    theSquare = new Square(me.getX(), me.getY());
    int x = me.getX();
    int y = me.getY();
    theSquare = theList.hit(x, y); // Get the top square in the list that coordinate (x, y) is inside the square
    if (theSquare == null) {
      dragging = false;
      theList.add(new Square(me.getX(), me.getY())); // If no square satisfied the requirement above, draw a new square here
    } else {
      dragging = true;
      distanceX = x - theSquare.loc.x;
      distanceY = y - theSquare.loc.y;
    }
//    theList.add(new Square(me.getX(), me.getY()));
    repaint();
  }

  public void mouseDragged(MouseEvent me) {
    int x = me.getX();
    int y = me.getY();
    if (dragging) {
      theSquare.loc.x = x - distanceX; // These two distance variable are my resolution of how to make sure the square moving from its original position when drag it
      theSquare.loc.y = y - distanceY;
    } else {
      Square s = theList.get(theList.size() - 1);
      int w = Math.abs(x - s.loc.x);
      int h = Math.abs(y - s.loc.y);
      s.resize(w, h);
    }
//    Square s = theList.get(theList.size() - 1);
//    int w = Math.abs(x - s.loc.x);
//    int h = Math.abs(y - s.loc.y);
//    s.resize(w, h);
    repaint();
  }

  public static class Square extends G.VS {

    public Color c = G.rndColor();

    /**
     * A constructor that create a square shape with top-left coordinate (x, y) and fixed weight
     * 100, height 100.
     */
    public Square(int x, int y) {
//      super(x, y, 100, 100);
      super(x, y, 0, 0);
    }

    public void draw(Graphics g) {
      this.fill(g, c);
    }

    public static class SquareList extends ArrayList<Square> {

      public void draw(Graphics g) {
        for (Square s : this) {
          s.draw(g);
        }
      }

      public Square hit(int x, int y) {
        Square result = null;
//        for (Square s : this) {
//          if (s.hit(x, y)) {
//            result = s;
//          }
//        }
        for (int i = this.size() - 1; i >= 0; i--) { // I think for-loop from backward is more efficient
          if (this.get(i).hit(x, y)) {
            result = this.get(i);
            break;
          }
        }
        return result;
      }
    }
  }
}
