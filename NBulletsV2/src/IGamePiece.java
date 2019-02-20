import tester.Tester;
import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.worldcanvas.WorldCanvas;
import java.awt.Color;
import java.util.Random;

public interface IGamePiece {
  // Moves this IGamePiece to the desired position
  IGamePiece move();

  // Determines if this IGamePiece is off screen
  boolean isOffscreen();

  // Returns true if two IGamePieces are touching
  boolean areTouching(IGamePiece other);

  // Returns true if this IGamePiece is touching a Ship
  boolean areTouchingShip(Ship other);

  // Returns true if this IGamePiece is touching a Bullet
  boolean areTouchingBullet(Bullet other);

  // Determines if two IGamePieces are colliding
  boolean areColliding(IGamePiece other);

  // Returns true if this IGamePiece and is colliding with a ship
  boolean areCollidingShip(Ship other);

  // Returns true if this IGamePiece and is colliding with a bullet
  boolean areCollidingBullet(Bullet other);

  // Place the image on a given scene
  WorldScene place(WorldScene scene);

  WorldImage draw();

  // Returns true if this IGamePiece is the same as that IGamePiece
  boolean sameIGamePiece(IGamePiece other);

  // Returns true if this bullet is the same as that bullet
  boolean sameBullet(Bullet other);

  // Returns true if this Ship is the same as that Ship
  boolean sameShip(Ship other);

  // Returns true if this Ship is in the given ILoGamePiece
  boolean containsShip(ILoGamePiece other);

  // Returns true if this Bullet is in the given ILoGamePiece
  boolean containsBullet(ILoGamePiece other);

  // Returns true if it is not a bullet
  boolean isNotBullet();

}

class Ship implements IGamePiece {
  MyPosn position;
  MyPosn velocity;

  Ship(MyPosn position, MyPosn velocity) {
    this.velocity = velocity;
    this.position = position;
  }

  // Moves this Ship to the desired position
  public IGamePiece move() {
    return new Ship(this.position.addPosn(this.velocity), this.velocity);
  }

  // Determines if this Ship is off screen
  public boolean isOffscreen() {
    return this.position.isOffscreen(500, 300);
  }

  // Returns true if this Ship is colliding with an IGamePiece
  public boolean areColliding(IGamePiece other) {
    return other.areCollidingShip(this);
  }

  // Returns false because Ships can't collide with Ships
  public boolean areCollidingShip(Ship other) {
    return false;
  }

  // Returns true if this Ship collides with a Bullet
  public boolean areCollidingBullet(Bullet other) {
    return this.areTouching(other);
  }

  // Returns true if this Ship touches an IGamePiece
  public boolean areTouching(IGamePiece other) {
    return other.areTouchingShip(this);
  }

  // Returns true if this Ship touches another Ship
  public boolean areTouchingShip(Ship other) {
    return this.position.distance(other.position) <= 20;
  }

  // Returns true if this Ship touches a Bullet
  public boolean areTouchingBullet(Bullet other) {
    return this.position.distance(other.position) <= (other.size + 10);
  }

  // Draw a ship
  public WorldImage draw() {
    return new CircleImage(10, OutlineMode.SOLID, Color.CYAN);
  }

  // place this ship on the given world scene
  public WorldScene place(WorldScene scene) {
    return scene.placeImageXY(this.draw(), this.position.x, this.position.y);
  }

  public boolean sameIGamePiece(IGamePiece other) {
    return other.sameShip(this);
  }

  public boolean sameBullet(Bullet other) {
    return false;
  }

  public boolean sameShip(Ship other) {
    return this.position.x == other.position.x && this.position.y == other.position.y
        && this.velocity.x == other.velocity.x && this.velocity.y == other.velocity.y;
  }

  public boolean containsShip(ILoGamePiece other) {
    return other.containsIGamePiece(this);
  }

  public boolean containsBullet(ILoGamePiece other) {
    return false;
  }

// returns true because not a bullet
  public boolean isNotBullet() {
    return true;
  }

}

class Bullet implements IGamePiece {
  MyPosn position;
  MyPosn velocity;
  int size;
  int generation;

  Bullet(MyPosn position, MyPosn velocity, int size, int generation) {
    this.position = position;
    this.velocity = velocity;
    this.size = size;
    this.generation = generation;
  }

  // Moves this Bullet to the desired position
  public IGamePiece move() {
    return new Bullet(this.position.addPosn(this.velocity), this.velocity, this.size,
        this.generation);
  }

  // Determines if this Bullet is off screen
  public boolean isOffscreen() {
    return this.position.isOffscreen(500, 300);
  }

  // Returns true if this Bullet collides with an IGamePiece
  public boolean areColliding(IGamePiece other) {
    return other.areCollidingBullet(this);
  }

  // Returns true if this Bullet collides with a Ship
  public boolean areCollidingShip(Ship other) {
    return this.areTouching(other);
  }

  // Returns false because a Bullet can't collide with a Bullet
  public boolean areCollidingBullet(Bullet other) {
    return false;
  }

  // Returns true if this Bullet touches an IGamePiece
  public boolean areTouching(IGamePiece other) {
    return other.areTouchingBullet(this);
  }

  // Returns true if this Bullet is touching a Ship
  public boolean areTouchingShip(Ship other) {
    return this.position.distance(other.position) <= this.size + 10;
  }

  // Returns true if this Bullet is touching a Bullet
  public boolean areTouchingBullet(Bullet other) {
    return this.position.distance(other.position) <= this.size + other.size;
  }

  // Draw this bullet
  public WorldImage draw() {
    return new CircleImage(size, OutlineMode.SOLID, Color.PINK);
  }

  // Place the bullet on the given world scene
  public WorldScene place(WorldScene scene) {
    return scene.placeImageXY(this.draw(), this.position.x, this.position.y);
  }

  public boolean sameIGamePiece(IGamePiece other) {
    return other.sameBullet(this);
  }

  public boolean sameBullet(Bullet other) {
    return this.position.x == other.position.x && this.position.y == other.position.y
        && this.velocity.x == other.velocity.x && this.velocity.y == other.velocity.y
        && this.generation == other.generation && this.size == other.size;
  }

  public boolean sameShip(Ship other) {
    return false;
  }

  public boolean containsShip(ILoGamePiece other) {
    return false;
  }

  public boolean containsBullet(ILoGamePiece other) {
    return other.containsIGamePiece(this);
  }

  Bullet incrementSize() {
    if (this.size >= 10) {
      return new Bullet(this.position, this.velocity, this.size, this.generation);
    }
    else {
      return new Bullet(this.position, this.velocity, this.size * 2, this.generation);
    }
  }

  Bullet setVelocity(double angle) {
    angle = Math.toRadians(angle);
    // System.out.println("8.0*Math.cos(angle) : " +
    // Double.toString(8.0*Math.cos(angle)));
    // System.out.println("-8.0*Math.sin(angle) : " +
    // Double.toString(-8.0*Math.sin(angle)));

    return new Bullet(this.position, new MyPosn((int) Math.round(8.0 * Math.cos(angle)),
        (int) Math.round(-8.0 * Math.sin(angle))), this.size, this.generation);
  }

  // Returns false because it is a bullet
  public boolean isNotBullet() {
    return false;
  }

}