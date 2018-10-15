package marlin.graphicslib;

import java.awt.Color;

public class UC {

  public static final int screenHeight = 800;
  public static final int screenWidth = 1000;
  public static final int inkBufferMax = 800;
  public static final int normSampleSize = 25;
  public static final int normCoordMax = 500;
  public static Color inkColor = Color.BLUE;
  public static final int noMatchDist = 500000; // Based on normSampleSize of 200 and normCoordMax of 500
  public static final int dotThreshold = 6;
  public static final String shapeDBFileName = "C:/Users/LiC/Documents/java/GestureBasedUIProject/Marlin/ShapeDB.bin";
}
