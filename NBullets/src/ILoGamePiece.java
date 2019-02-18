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

  // Removes ships that have been hit
  ILoGamePiece updateLoGamePiece(ILoGamePiece collisions);

  // Place this list of GamePieces on the given scene
  WorldScene placeAll(WorldScene scene);
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
    if(this.rest.areCollidingWith(this.first)) {
      return new ConsLoGamePiece(this.first, this.rest.getCollisions());
    }
    else {
      return this.rest.getCollisions();
    }
  }
  
  // Returns the updated list of items to display
  public ILoGamePiece updateLoGamePiece(ILoGamePiece collisions) {
    return this.rest; //  sameGamePiece()
  }
  
  // Returns true if any of the GamePieces in this list are colliding with the given piece
  public boolean areCollidingWith(IGamePiece other) {
    return this.first.areColliding(other) || this.rest.areCollidingWith(other);
  }

  // Places all the GamePieces in the list onto the given scene
  public WorldScene placeAll(WorldScene scene) {
    return this.first.place(this.rest.placeAll(scene));
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

  // Updates the list of GamePieces in the current world
  public ILoGamePiece updateLoGamePiece(ILoGamePiece collisions) {
    // TODO Auto-generated method stub
    return null;
  }

  // Returns the current scene because there is nothing to place
  public WorldScene placeAll(WorldScene scene) {
    return scene;
  }

}

/*
 * interface ILoShip { }
 * 
 * class ConsLoShip implements ILoShip { Ship first; ILoShip rest;
 * ConsLoShip(Ship first, ILoShip rest) { this.first = first; this.rest = rest;
 * }
 * 
 * class MtLoShip implements ILoShip { MtLoShip(){}
 * 
 * }
 * 
 * interface ILoBullet {}
 * 
 * class ConsLoBullet implements ILoBullet { Bullet first; ILoBullet rest;
 * ConsLoBullet(Bullet first, ILoBullet rest) { this.first = first; this.rest =
 * rest; } }
 * 
 * class MtLoBullet implements ILoBullet { MtLoBullet(){}; }
 */