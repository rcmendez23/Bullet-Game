import tester.Tester;

public class ExamplesGamePiece {
  IGamePiece ship1 = new Ship(new MyPosn(0, 0), new MyPosn(4, 0));
  IGamePiece ship2 = new Ship(new MyPosn(500, 40), new MyPosn(-4, 0));
  IGamePiece ship3 = new Ship(new MyPosn(250, 150), new MyPosn(8, 0));
  IGamePiece ship4 = new Ship(new MyPosn(499, 30), new MyPosn(4, 0));
  IGamePiece ship5 = new Ship(new MyPosn(501, 30), new MyPosn(4, 0));

  IGamePiece bullet1 = new Bullet(new MyPosn(250, 300), new MyPosn(0, 8), 2, 0);
  IGamePiece bullet2 = new Bullet(new MyPosn(250, 3), new MyPosn(0, 8), 4, 0);
  IGamePiece bullet3 = new Bullet(new MyPosn(2, 2), new MyPosn(0, 8), 8, 0);
  IGamePiece bullet4 = new Bullet(new MyPosn(255, 400), new MyPosn(2, 16), 16, 0);
  IGamePiece bullet5 = new Bullet(new MyPosn(255, 400), new MyPosn(2, 16), 16, 2);
  IGamePiece bullet6 = new Bullet(new MyPosn(250, 150), new MyPosn(0, 8), 2, 0);
  IGamePiece bullet7 = new Bullet(new MyPosn(2, 301), new MyPosn(0, 8), 2, 0);

  ILoGamePiece mt = new MtLoGamePiece();
  ILoGamePiece list1 = new ConsLoGamePiece(this.ship1, this.mt);
  ILoGamePiece list2 = new ConsLoGamePiece(this.bullet1, this.list1);
  ILoGamePiece list3 = new ConsLoGamePiece(this.ship2, this.list2);
  ILoGamePiece list4 = new ConsLoGamePiece(this.bullet2, this.list3);
  ILoGamePiece list5 = new ConsLoGamePiece(this.bullet6, new ConsLoGamePiece(this.ship3, this.mt));
  ILoGamePiece list6 = new ConsLoGamePiece(this.bullet7, new ConsLoGamePiece(this.ship5, this.mt));
  ILoGamePiece collisionList3 = new ConsLoGamePiece(this.ship2,
      new ConsLoGamePiece(this.bullet1, this.mt)); // Ship2 and bullet1 collided

  boolean testPosns(Tester t) {
    return t.checkExpect(new MyPosn(0, 0).addPosn(new MyPosn(1, 1)), new MyPosn(1, 1))
        && t.checkExpect(new MyPosn(1, 3).addPosn(new MyPosn(-2, 5)), new MyPosn(-1, 8))
        && t.checkExpect(new MyPosn(0, 0).isOffscreen(500, 300), false)
        && t.checkExpect(new MyPosn(-1, 30).isOffscreen(500, 300), true)
        && t.checkExpect(new MyPosn(30, -1).isOffscreen(500, 300), true)
        && t.checkExpect(new MyPosn(501, 20).isOffscreen(500, 300), true)
        && t.checkExpect(new MyPosn(20, 301).isOffscreen(500, 300), true)
        && t.checkExpect(new MyPosn(0, 0).distance(new MyPosn(1, 1)), Math.sqrt(2))
        && t.checkInexact(new MyPosn(30, 40).distance(new MyPosn(20, 50)), 14.142, 0.01);
  }

  boolean testGamePosns(Tester t) {
    return t.checkExpect(this.ship1.move(), new Ship(new MyPosn(4, 0), new MyPosn(4, 0)))
        && t.checkExpect(this.ship2.move(), new Ship(new MyPosn(496, 40), new MyPosn(-4, 0)))
        && t.checkExpect(this.bullet1.move(),
            new Bullet(new MyPosn(250, 308), new MyPosn(0, 8), 2, 0))
        && t.checkExpect(this.bullet2.move(),
            new Bullet(new MyPosn(250, 11), new MyPosn(0, 8), 4, 0))
        && t.checkExpect(this.bullet3.move(), new Bullet(new MyPosn(2, 10), new MyPosn(0, 8), 8, 0))
        && t.checkExpect(this.bullet4.move(),
            new Bullet(new MyPosn(257, 416), new MyPosn(2, 16), 16, 0))
        && t.checkExpect(this.ship1.isOffscreen(), false)
        && t.checkExpect(new Bullet(new MyPosn(-1, 30), new MyPosn(0, 0), 2, 0).isOffscreen(), true)
        && t.checkExpect(new Ship(new MyPosn(30, -1), new MyPosn(0, 0)).isOffscreen(), true)
        && t.checkExpect(new Ship(new MyPosn(501, 20), new MyPosn(0, 0)).isOffscreen(), true)
        && t.checkExpect(new Ship(new MyPosn(20, 301), new MyPosn(0, 0)).isOffscreen(), true)
        && t.checkExpect(this.mt.moveAll(), this.mt)
        && t.checkExpect(this.mt.removeOffscreen(), this.mt)
        && t.checkExpect(this.list1.moveAll(), new ConsLoGamePiece(this.ship1.move(), this.mt))
        && t.checkExpect(this.list2.moveAll(),
            new ConsLoGamePiece(this.bullet1.move(),
                new ConsLoGamePiece(this.ship1.move(), this.mt)))
        && t.checkExpect(this.list3.moveAll(),
            new ConsLoGamePiece(this.ship2.move(),
                new ConsLoGamePiece(this.bullet1.move(),
                    new ConsLoGamePiece(this.ship1.move(), this.mt))))
        && t.checkExpect(this.list4.moveAll(),
            new ConsLoGamePiece(this.bullet2.move(),
                new ConsLoGamePiece(this.ship2.move(),
                    new ConsLoGamePiece(this.bullet1.move(),
                        new ConsLoGamePiece(this.ship1.move(), this.mt)))))
        && t.checkExpect(
            new ConsLoGamePiece(new Ship(new MyPosn(30, -1), new MyPosn(0, 0)), this.mt)
                .removeOffscreen(),
            this.mt)
        && t.checkExpect(this.list4.removeOffscreen(), this.list4)
        && t.checkExpect(list6.removeOffscreen(), this.mt);
  }

  boolean testSameIGamePiece(Tester t) {
    return t.checkExpect(ship2.sameIGamePiece(ship2), true)
        && t.checkExpect(ship2.sameIGamePiece(bullet1), false);
  }

  boolean testContains(Tester t) {
    return t.checkExpect(list3.containsIGamePiece(ship1), true)
        && t.checkExpect(list3.containsIGamePiece(ship4), false)
        && t.checkExpect(list3.containsIGamePiece(bullet4), false)
        && t.checkExpect(list3.containsIGamePiece(bullet1), true);
  }

  boolean testAddBullets(Tester t) {
    return t.checkExpect(mt.addBullets((Bullet) bullet1, 0),
        new ConsLoGamePiece(new Bullet(new MyPosn(250, 300), new MyPosn(-8, 0), 4, 1),
            new ConsLoGamePiece(new Bullet(new MyPosn(250, 300), new MyPosn(8, 0), 4, 1), this.mt)))
        && t.checkExpect(
            new ConsLoGamePiece(this.bullet2, this.mt)
                .addBullets(new Bullet(new MyPosn(250, 150), new MyPosn(-8, 0), 4, 1), 0),
            new ConsLoGamePiece(new Bullet(new MyPosn(250, 150), new MyPosn(8, 0), 8, 2),
                new ConsLoGamePiece(new Bullet(new MyPosn(250, 150), new MyPosn(-4, -7), 8, 2),
                    new ConsLoGamePiece(new Bullet(new MyPosn(250, 150), new MyPosn(-4, 7), 8, 2),
                        new ConsLoGamePiece(this.bullet2, this.mt)))));
  }

  boolean testCollisions(Tester t) {
    return t.checkExpect(mt.updateLoGamePiece(list2), mt)
        && t.checkExpect(bullet1.areColliding(ship1), false)
        && t.checkExpect(bullet6.areColliding(ship3), true)
        && t.checkExpect(bullet1.areColliding(bullet1), false)
        && t.checkExpect(ship1.areColliding(ship1), false)
        && t.checkExpect(ship1.areColliding(ship2), false)
        && t.checkExpect(list5.getCollisions(),
            new ConsLoGamePiece(new Ship(new MyPosn(250, 150), new MyPosn(8, 0)),
                new ConsLoGamePiece(new Bullet(new MyPosn(250, 150), new MyPosn(0, 8), 2, 0),
                    this.mt)))
        && t.checkExpect(list3.updateLoGamePiece(this.collisionList3),
            new ConsLoGamePiece(new Bullet(new MyPosn(250, 300), new MyPosn(8, 0), 4, 1),
                new ConsLoGamePiece(new Bullet(new MyPosn(250, 300), new MyPosn(-8, 0), 4, 1),
                    new ConsLoGamePiece(new Ship(new MyPosn(0, 0), new MyPosn(4, 0)), this.mt))));
  }

  boolean testLen(Tester t) {
    return t.checkExpect(this.mt.len(), 0) && t.checkExpect(this.collisionList3.len(), 2)
        && t.checkExpect(this.list2.len(), 2);
  }

  /*
   * TODO: Fix UpdateLoGamePiece test containsIGamePiece
   * 
   */

  boolean testWorld(Tester t) {
    GameWorld w = new GameWorld(20);
    int worldWidth = 500;
    int worldHeight = 300;
    double tickRate = .0357;
    return w.bigBang(worldWidth, worldHeight, tickRate);
  }
}