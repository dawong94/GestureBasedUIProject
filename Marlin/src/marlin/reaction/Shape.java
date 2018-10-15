package marlin.reaction;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import marlin.graphicslib.G;
import marlin.graphicslib.I;
import marlin.graphicslib.UC;
import marlin.reaction.Ink.Norm;
import marlin.reaction.Shape.Prototype.List;

public class Shape implements Serializable {

  public String name;
  public List prototypes = new List();

  public Shape(String name) {
    this.name = name;
  }

  public static HashMap<String, Shape> DB = loadDB();
  public static Shape DOT = DB.get("DOT");
  public static Collection<Shape> LIST = DB.values();

  public static HashMap<String, Shape> loadDB() {
    HashMap<String, Shape> res = new HashMap<>();
    res.put("DOT", new Shape("DOT"));
//    res.put("1", new Shape("1"));
    try {
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(UC.shapeDBFileName));
      res = (HashMap<String, Shape>) ois.readObject();
      System.out.println("Loaded Shape DB.");
    } catch (Exception e) {
      System.out.println("Failed load Shape DB.");
      System.out.println(e);
    }
    return res;
  }

  public static void saveDB() {
    try {
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(UC.shapeDBFileName));
      oos.writeObject(DB);
    } catch (Exception e) { System.out.println(e); }
  }

  public static Shape recognize(Ink ink) { // can return null
    if (ink.vs.size.x < UC.dotThreshold && ink.vs.size.y < UC.dotThreshold) {
      return DOT;
    }
    Shape bestMatched = null;
    int bestSoFar = UC.noMatchDist;
    for (Shape s : LIST) {
      int d = s.prototypes.bestDist(ink.norm);
      if (d < bestSoFar) {
        bestSoFar = d;
        bestMatched = s;
      }
    }
    return bestMatched;
  }

  public static class Prototype extends Ink.Norm implements Serializable {

    public int nBlends = 1;

    public void blend(Norm norm) {
      for (int i = 0; i < N; i++) {
        points[i].blend(norm.points[i], nBlends);
      }
      nBlends++;
    }

    public static class List extends ArrayList<Prototype> implements I.Show, Serializable {

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
