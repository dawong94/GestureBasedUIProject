package marlin.reaction;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import marlin.graphicslib.G;
import marlin.graphicslib.I;
import marlin.graphicslib.UC;
import marlin.reaction.Ink.Norm;
import marlin.reaction.Shape.Prototype.PrototypeList;

public class Shape {

  public String name;
  public Prototype.PrototypeList prototypes = new Prototype.PrototypeList();

  public Shape(String name) {
    this.name = name;
  }

  public static class Prototype extends Ink.Norm {

    public int nBlends = 1;

    public void blend(Norm norm) {
      for (int i = 0; i < N; i++) {
        points[i].blend(norm.points[i], nBlends);
      }
      nBlends++;
    }

    public static class PrototypeList extends ArrayList<Prototype> implements I.Show {

      public static Prototype bestMatch;

      public int bestDist(Norm norm) {
        bestMatch = null;
        int bestSoFar = UC.noMatchDist;
        for (Prototype p : this) {
          int d = p.dist(norm);
          if (d < bestSoFar) {
            bestSoFar = d;
            bestMatch = p;
          }
        }
        return bestSoFar;
      }

      private static int m = 10, w = 60;
      private static G.VS showBox = new G.VS(m, m, w, w);

      @Override
      public void show(Graphics g) {
        g.setColor(Color.ORANGE);
        for (int i = 0; i < this.size(); i++) {
          int x = (i + 1) * m + i * w;
          showBox.set(x, m, w, w);
          Prototype p = this.get(i);
          p.drawAt(g, showBox);
          g.drawString("" + p.nBlends, x, m);
        }
      }
    }
  }
}
