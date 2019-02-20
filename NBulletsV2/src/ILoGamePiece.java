import javalib.funworld.WorldScene;

interface ILoGamePiece {
  // Moves all the IGamePieces to the desired position
  ILoGamePiece moveAll();

  // Removes GamePieces that are off screen
  ILoGamePiece removeOffscreen();

  // Returns true if any in this list are colliding with the given gamepiece
  boolean areCollidingWith(IGamePiece other);

  // Returns list of bullets that have collided
  ILoGamePiece getCollisions();

  // Returns list of bullets that have collided
  ILoGamePiece getCollisionsAcc(ILoGamePiece collisionsSoFar);

  // Removes ships that have been hit
  ILoGamePiece updateLoGamePiece(ILoGamePiece collisions);

  // Place this list of GamePieces on the given scene
  WorldScene placeAll(WorldScene scene);

  // Get length of list
  int len();

  // Returns true if this list contains that IGamePiece
  boolean containsIGamePiece(IGamePiece other);

  // Adds the exploded bullets to this ILoGamePiece
  ILoGamePiece addBullets(Bullet bullet, int index);

  // Returns true if there are no bullets left
  boolean noBulletsLeft();
}

// Represents a list of GamePieces
class ConsLoGamePiece implements ILoGamePiece {
  IGamePiece first;
  ILoGamePiece rest;

  ConsLoGamePiece(IGamePiece first, ILoGamePiece rest) {
    this.first = first;
    this.rest = rest;
  }

  // Moves all the ConsLoGamePieces to the desired position
  public ILoGamePiece moveAll() {
    return new ConsLoGamePiece(this.first.move(), this.rest.moveAll());
  }

  // Removes all the GamePieces that are offscreen
  public ILoGamePiece removeOffscreen() {
    if (this.first.isOffscreen()) {
      return this.rest.removeOffscreen();
    }
    else {
      return new ConsLoGamePiece(this.first, this.rest.removeOffscreen());
    }
  }

  // Returns a list of collided items
  public ILoGamePiece getCollisions() {
    return this.getCollisionsAcc(new MtLoGamePiece());
  }

  // Accumulator: Returns a list of collided items
  public ILoGamePiece getCollisionsAcc(ILoGamePiece collisionsSoFar) {
    if (this.rest.areCollidingWith(this.first) || collisionsSoFar.areCollidingWith(this.first)) {
      return this.rest.getCollisionsAcc(new ConsLoGamePiece(this.first, collisionsSoFar));
    }
    else {
      return this.rest.getCollisionsAcc(collisionsSoFar);
    }
  }

  // Returns the updated list of items to display
  public ILoGamePiece updateLoGamePiece(ILoGamePiece collisions) {
    if (this.first.containsShip(collisions)) {
      return this.rest.updateLoGamePiece(collisions);
    }
    else if (this.first.containsBullet(collisions)) {
      return this.rest.updateLoGamePiece(collisions).addBullets((Bullet) this.first, 0);
    }
    else {
      return new ConsLoGamePiece(this.first, this.rest.updateLoGamePiece(collisions));
    }
  }

  // Returns true if any of the GamePieces in this list are colliding with the
  // given piece
  public boolean areCollidingWith(IGamePiece other) {
    return this.first.areColliding(other) || this.rest.areCollidingWith(other);
  }

  // Places all the GamePieces in the list onto the given scene
  public WorldScene placeAll(WorldScene scene) {
    return this.first.place(this.rest.placeAll(scene));
  }

  // Returns the length of this list
  public int len() {
    return 1 + this.rest.len();
  }

  // Returns true if this list contains the given IGamePiece
  public boolean containsIGamePiece(IGamePiece other) {
    return (this.first.sameIGamePiece(other)) || this.rest.containsIGamePiece(other);
  }

  // Adds exploded bullet to this list
  public ILoGamePiece addBullets(Bullet bullet, int index) {
    if (index <= bullet.generation + 1) {
      return new ConsLoGamePiece(
          new Bullet(bullet.position, bullet.velocity, bullet.size, bullet.generation + 1)
              .incrementSize().setVelocity(index * (360.0 / (double) (bullet.generation + 2))),
          this.addBullets(bullet, index + 1));
    }
    else {
      return this;
    }

  }

  // Returns true if there are no bullets left
  public boolean noBulletsLeft() {
    return this.first.isNotBullet() && this.rest.noBulletsLeft();
  }
}

class MtLoGamePiece implements ILoGamePiece {

  // Returns the empty list because there are nor items to move
  public ILoGamePiece moveAll() {
    return this;
  }

  // Returns the empty list because there are no items to remove
  public ILoGamePiece removeOffscreen() {
    return this;
  }

  // Returns false because there are no pieces to collide with
  public boolean areCollidingWith(IGamePiece other) {
    return false;
  }

  // Returns the empty list because there are no collisions
  public ILoGamePiece getCollisions() {
    return this;
  }

  // Returns the empty list because there are no updates to be made
  public ILoGamePiece updateLoGamePiece(ILoGamePiece collisions) {
    return this;
  }

  // Returns the current scene because there is nothing to place
  public WorldScene placeAll(WorldScene scene) {
    return scene;
  }

  // Returns 0 as length of the empty list
  public int len() {
    return 0;
  }

  // Returns false because an empty list cannot contain anything
  public boolean containsIGamePiece(IGamePiece other) {
    return false;
  }

  // Adds bullets to the empty list of game pieces
  public ILoGamePiece addBullets(Bullet bullet, int index) {
    return new ConsLoGamePiece(
        new Bullet(bullet.position, bullet.velocity, bullet.size, bullet.generation + 1)
            .incrementSize()
            .setVelocity((double) index * (360.0 / (double) (bullet.generation + 2))),
        this).addBullets(bullet, index + 1);
  }

  // Return the collisionsSoFar - the base case
  public ILoGamePiece getCollisionsAcc(ILoGamePiece collisionsSoFar) {
    return collisionsSoFar;
  }

  // Returns true because there are no bullets left
  public boolean noBulletsLeft() {
    return true;
  }
}