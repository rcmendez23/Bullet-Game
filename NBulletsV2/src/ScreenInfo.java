import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.worldcanvas.WorldCanvas;
import java.awt.Color;

public class ScreenInfo {

  int bulletsLeft;
  int score;

  public ScreenInfo(int bulletsLeft, int score) {
    this.bulletsLeft = bulletsLeft;
    this.score = score;
  }

  public WorldImage draw() {
    return new AboveImage(
        new TextImage("Bullets Left : " + Integer.toString(bulletsLeft), 13, Color.BLACK),
        new TextImage("Score : " + Integer.toString(score), 13, Color.BLACK));
  }

}
