package marlin.graphicslib;

import java.awt.Graphics;
import marlin.reaction.Gesture;

public interface I {

  interface Area {

    boolean hit(int x, int y);

    void dn(int x, int y);

    void drag(int x, int y);

    void up(int x, int y);
  }

  interface Show {

    void show(Graphics g);
  }

  interface Act {
    void act(Gesture gesture);
  }

  interface React extends Act {
    int bid(Gesture gesture);
  }
}
