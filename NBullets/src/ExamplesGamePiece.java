import tester.Tester;

public class ExamplesGamePiece {
  IGamePiece ship1 = new Ship(new MyPosn(0,0), new MyPosn(4,0));
  IGamePiece ship2 = new Ship(new MyPosn(500,40), new MyPosn(-4, 0));
  IGamePiece ship3 = new Ship(new MyPosn(250,150), new MyPosn(8,0));
  IGamePiece ship4 = new Ship(new MyPosn(499,30), new MyPosn(4,0));
  IGamePiece bullet1 = new Bullet(new MyPosn(250, 300), new MyPosn(0,8), 2, 0);
  IGamePiece bullet2 = new Bullet(new MyPosn(250, 3), new MyPosn(0,8), 4, 0);
  IGamePiece bullet3 = new Bullet(new MyPosn(2, 2), new MyPosn(0,8), 8, 0);
  IGamePiece bullet4 = new Bullet(new MyPosn(255, 400), new MyPosn(2,16), 16, 0);
  
  ILoGamePiece mt = new MtLoGamePiece();
  ILoGamePiece list1 = new ConsLoGamePiece(this.ship1, this.mt);
  ILoGamePiece list2 = new ConsLoGamePiece(this.bullet1, this.list1);
  ILoGamePiece list3 = new ConsLoGamePiece(this.ship2, this.list2);
  ILoGamePiece list4 = new ConsLoGamePiece(this.bullet2, this.list3);
  
  boolean testPosns(Tester t) {
    return t.checkExpect(new MyPosn(0,0).addPosn(new MyPosn(1,1)), new MyPosn(1,1)) &&
        t.checkExpect(new MyPosn(1,3).addPosn(new MyPosn(-2,5)), new MyPosn(-1,8)) &&
        t.checkExpect(new MyPosn(0,0).isOffscreen(500, 300), false) &&
        t.checkExpect(new MyPosn(-1, 30).isOffscreen(500, 300), true) &&
        t.checkExpect(new MyPosn(30,-1).isOffscreen(500, 300), true) &&
        t.checkExpect(new MyPosn(501,20).isOffscreen(500,300), true) &&
        t.checkExpect(new MyPosn(20, 301).isOffscreen(500, 300), true) &&
        t.checkExpect(new MyPosn(0,0).distance(new MyPosn(1,1)), Math.sqrt(2)) &&
        t.checkInexact(new MyPosn(30, 40).distance(new MyPosn(20,50)), 14.142, 0.01);
          
  }
  
  boolean testWorld(Tester t) {
    GameWorld w = new GameWorld(5);
    int worldWidth = 500;
    int worldHeight = 300;
    double tickRate = .0357;
    return w.bigBang(worldWidth, worldHeight, tickRate);
  }

}
