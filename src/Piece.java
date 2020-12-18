import java.awt.*;

public enum Piece {
    /*
        source: https://tetris.fandom.com/wiki/Tetromino

        I:  *
            *
            *
            *

        O:
            * *
            * *

        T:
            * * *
              *

        S:
              * *
            * *

        Z:
            * *
              * *

        J:
              *
              *
            * *

        L:
            *
            *
            * *

        Those are now defined in this enum:
     */
    I(Color.cyan,new Point[]{p(0,0),p(0,1),p(0,2),p(0,3)}),
    O(Color.YELLOW,new Point[]{p(0,0),p(1,0),p(0,1),p(1,1)}),
    T(Color.PINK,new Point[]{p(0,0),p(1,0),p(2,0),p(1,1)}),
    S(Color.GREEN,new Point[]{p(1,0),p(2,0),p(0,1),p(1,1)}),
    Z(Color.RED,new Point[]{p(0,0),p(1,0),p(1,1),p(2,1)}),
    J(Color.BLUE,new Point[]{p(1,0),p(1,1),p(0,2),p(1,2)}),
    L(Color.ORANGE,new Point[]{p(0,0),p(0,1),p(0,2),p(1,2)});

    //The blocks used for this piece
    private Block[] blocks;
    private Point position;

    public static Point p(int x,int y){
        return new Point(x,y);
    }

    Piece(Color color,Point[]points){
        //initialize blocks
        blocks = new Block[4];
        for (int i=0;i<points.length;i++) {
            //create new block
            blocks[i] = new Block(color, points[i].x, points[i].y);
        }
    }

    public void moveOneDown(){
        for (Block block: blocks) {
            block.setY(block.getY() + 1);
        }
    }

    public void paintPiece(Graphics g){
        g.setColor(this.blocks[0].getColor());
        for(int i=0;i<this.blocks.length;i++){
            g.fillRect(
                    (position.x + this.blocks[i].getX()) * 30 + 1,
                    (position.y + this.blocks[i].getY()) * 30 + 1,
                    28,28);
        }
    }

    public Block[] getBlocks() {
        return blocks;
    }

    public void setBlocks(Block[] blocks) {
        this.blocks = blocks;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
