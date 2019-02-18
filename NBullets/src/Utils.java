import java.util.Random;
public class Utils {

  public Utils() {
  }
  
  // Return a new random ship
  public Ship newRandShip() {
    return new Ship(new MyPosn(this.randVelocity(this.randBool()), 0));
  }
  
  // Return either a 4 or -4 randomly
  public int randVelocity(boolean randBool) {
    if(randBool) {
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
