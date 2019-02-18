import tester.Tester;
import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.worldcanvas.WorldCanvas;
import java.util.Random;

public class GameWorld extends World{
  int numBullets;
  int bulletsLeft;
  int score;
  ILoGamePiece pieces;
  Random rand;
  int clock;
  
  public GameWorld(int numBullets, int bulletsLeft, int score, ILoGamePiece pieces, Random rand, int clock) {
    this.numBullets = numBullets;
    this.bulletsLeft = bulletsLeft;
    this.score = score;
    this.pieces = pieces;
    this.rand = rand;
    this.clock = clock;
  }  
  
  public GameWorld(int numBullets) {
    this(numBullets, numBullets, 0, new MtLoGamePiece(), new Random(), 0);
  }

  // Draw the scene
  public WorldScene makeScene() {
    return this.pieces.placeAll(new WorldScene(500, 300)); 
  }
  
  // On every tick
  public GameWorld onTick() {
    this.clock += 1;
    return this.spawnShips(clock % 28).onKeyEvent(" ").moveAll();
  }
  
  // space bar press: release bullet
  public GameWorld onKeyEvent(String key) {
    if(key == " ") {
      return spawnBullet();
    }
    else {
      return this;
    }
  }
  
  // Create a bullet, decrement bulletsLeft
  public GameWorld spawnBullet() {
    return new GameWorld(numBullets, bulletsLeft - 1, score, new ConsLoGamePiece(new Bullet(new MyPosn(250, 300), new MyPosn(0, -8), 2, 0) ,pieces), rand, clock);
  }
  
  // Spawns ships every 28 ticks
  public GameWorld spawnShips(int count) {
    if(count == 0) {
      return this.addShips(rand.nextInt(2) + 1);
    }
    else {
      return this;
    }
  }
  
// Add numShips ships to the list of pieces 
public GameWorld addShips(int numShips) {
  if (numShips == 0) {
    return this;
  }
  else {
    return new GameWorld(numBullets, bulletsLeft, score, new ConsLoGamePiece(new Utils().newRandShip(), pieces), rand, clock).addShips(numShips - 1);
  }
}

  /*
  // Collision handler
  public GameWorld handleCollision() {
    
  }
  */
  public GameWorld moveAll() {
    return new GameWorld(numBullets, bulletsLeft, score, pieces.moveAll(), rand, clock);
  }

}