import java.util.Random;

public class Utils {

  public Utils() {
  }

  // Return a new random ship
  public Ship newRandShip() {
    int vel = this.randVelocity(this.randBool());
    int posX;
    int posY;
    // Set X position based on velocity
    if (vel < 0) {
      posX = 500;
    }
    else {
      posX = 0;
    }

    // Set random y position
    int randY = (int) (300 * Math.random());
    if (randY > 260) {
      posY = 260;
    }
    else if (randY < 40) {
      posY = 40;
    }
    else {
      posY = randY;
    }
    return new Ship(new MyPosn(posX, posY), new MyPosn(vel, 0));
  }

  // Return either a 4 or -4 randomly
  public int randVelocity(boolean randBool) {
    if (randBool) {
      return 4;
    }
    else {
      return -4;
    }
  }

  // Return a random boolean
  public boolean randBool() {
    return new Random().nextBoolean();
  }

}
