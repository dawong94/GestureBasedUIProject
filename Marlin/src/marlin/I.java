package marlin;

public interface I {

  interface Area {

    boolean hit(int x, int y);
    void pressed(int x, int y);
    void dragged(int x, int y);
    void released(int x, int y);
  }
}
