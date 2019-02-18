import tester.Tester;
import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.worldcanvas.WorldCanvas;
import java.awt.Color;

class MyPosn extends Posn {
  // Standard constructor
  MyPosn(int x, int y) {
    super(x, y);
  }

  // constructor to convert a Posn to a MyPosn
  MyPosn(Posn p) {
    this(p.x, p.y);
  }

  // Adds two Posns together
  MyPosn addPosn(Posn other) {
    return new MyPosn(this.x + other.x, this.y + other.y);
  }

  // Determines if a Posn is off screen
  boolean isOffscreen(int width, int height) {
    return this.x < 0 || this.y < 0 || this.x > width || this.y > height;
  }

  double distance(MyPosn other) {
    return Math.hypot(other.x - this.x, other.y - this.y);
  }

}