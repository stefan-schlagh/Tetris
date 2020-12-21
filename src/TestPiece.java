import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPiece {
    @Test
    void DimensionsI() {
        Piece piece = Piece.I;
        Assertions.assertEquals(piece.getHeight(), 4);
        Assertions.assertEquals(piece.getWidth(), 1);
    }
    @Test
    void DimensionsO() {
        Piece piece = Piece.O;
        Assertions.assertEquals(piece.getHeight(), 2);
        Assertions.assertEquals(piece.getWidth(), 2);
    }
    @Test
    void DimensionsT() {
        Piece piece = Piece.T;
        Assertions.assertEquals(piece.getHeight(), 2);
        Assertions.assertEquals(piece.getWidth(), 3);
    }
    @Test
    void DimensionsS() {
        Piece piece = Piece.S;
        Assertions.assertEquals(piece.getHeight(), 2);
        Assertions.assertEquals(piece.getWidth(), 3);
    }
    @Test
    void DimensionsZ() {
        Piece piece = Piece.Z;
        piece.init();
        Assertions.assertEquals(piece.getHeight(), 2);
        Assertions.assertEquals(piece.getWidth(), 3);
    }
    @Test
    void DimensionsJ() {
        Piece piece = Piece.J;
        Assertions.assertEquals(piece.getHeight(), 3);
        Assertions.assertEquals(piece.getWidth(), 2);
    }
    @Test
    void TestDimensionsL() {
        Piece piece = Piece.L;
        Assertions.assertEquals(piece.getHeight(), 3);
        Assertions.assertEquals(piece.getWidth(), 2);
    }
    @Test
    void DimensionsRotateI() {
        Piece piece = Piece.I;
        piece.rotate();
        Assertions.assertEquals(piece.getHeight(), 1);
        Assertions.assertEquals(piece.getWidth(), 4);
        piece.rotate();
        Assertions.assertEquals(piece.getHeight(), 4);
        Assertions.assertEquals(piece.getWidth(), 1);
    }
    @Test
    void DimensionsRotateL() {
        Piece piece = Piece.L;
        piece.rotate();
        Assertions.assertEquals(piece.getHeight(), 2);
        Assertions.assertEquals(piece.getWidth(), 3);
        piece.rotate();
        Assertions.assertEquals(piece.getHeight(), 3);
        Assertions.assertEquals(piece.getWidth(), 2);
    }
    @Test
    void GetBlocksDirectionLeftZ() {
        Piece piece = Piece.Z;
        // get blocks
        Block[] blocks = piece.getBlocks(PlayingArea.DIRECTION_LEFT);
        // test blocks length
        Assertions.assertEquals(2,blocks.length);
        //test coordinates
        Assertions.assertEquals(0,blocks[0].getX());
        Assertions.assertEquals(1,blocks[1].getX());
    }
    @Test
    void GetBlocksDirectionRightZ() {
        Piece piece = Piece.Z;
        // get blocks
        Block[] blocks = piece.getBlocks(PlayingArea.DIRECTION_RIGHT);
        // test blocks length
        Assertions.assertEquals(2,piece.getHeight());
        Assertions.assertEquals(2,blocks.length);
        //test coordinates
        Assertions.assertEquals(1,blocks[0].getX());
        Assertions.assertEquals(2,blocks[1].getX());

        //rotate
        piece.rotate();
        // get blocks
        blocks = piece.getBlocks(PlayingArea.DIRECTION_RIGHT);
        // test blocks length
        Assertions.assertEquals(3,piece.getHeight());
        Assertions.assertEquals(3,blocks.length);
        //test coordinates
        Assertions.assertEquals(1,blocks[0].getX());
        Assertions.assertEquals(1,blocks[1].getX());
        Assertions.assertEquals(0,blocks[2].getX());
    }
    @Test
    void GetBlocksDirectionDownZ() {
        Piece piece = Piece.Z;
        piece.init();
        // get blocks
        Block[] blocks = piece.getBlocks(PlayingArea.DIRECTION_DOWN);
        // test blocks length
        Assertions.assertEquals(3,blocks.length);
        //test coordinates
        Assertions.assertEquals(0,blocks[0].getY());
        Assertions.assertEquals(1,blocks[1].getY());
        Assertions.assertEquals(1,blocks[2].getY());
    }
    @Test
    void GetBlocksDirectionDownO() {
        Piece piece = Piece.O;
        // get blocks
        Block[] blocks = piece.getBlocks(PlayingArea.DIRECTION_DOWN);
        // test blocks length
        Assertions.assertEquals(2,blocks.length);
        //test coordinates
        Assertions.assertEquals(1,blocks[0].getY());
        Assertions.assertEquals(1,blocks[1].getY());
        Assertions.assertEquals(0,blocks[0].getX());
        Assertions.assertEquals(1,blocks[1].getX());
    }
}
