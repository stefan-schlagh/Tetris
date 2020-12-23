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
              *
            * * *

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
    I(
            Color.cyan,
            new Point[]{p(0,0),p(0,1),p(0,2),p(0,3)},
            1,4
            ),
    O(
            Color.YELLOW,
            new Point[]{p(0,0),p(1,0),p(0,1),p(1,1)},
            2,2
            ),
    T(
            Color.PINK,
            new Point[]{p(0,0),p(1,0),p(2,0),p(1,1)},
            3,2
            ),
    S(
            Color.GREEN,
            new Point[]{p(1,0),p(2,0),p(0,1),p(1,1)},
            3,2
            ),
    Z(
            Color.RED,
            new Point[]{p(0,0),p(1,0),p(1,1),p(2,1)},
            3,2
            ),
    J(
            Color.BLUE,
            new Point[]{p(1,0),p(1,1),p(0,2),p(1,2)},
            2,3
            ),
    L(
            Color.ORANGE,
            new Point[]{p(0,0),p(0,1),p(0,2),p(1,2)},
            2,3
            );

    //The blocks used for this piece
    private Block[] blocks;
    private Block[][] blockMatrix;
    private Point position;
    private Color color;
    private Point[] points;
    private int columns;
    private int rows;

    public static Point p(int x,int y){
        return new Point(x,y);
    }

    Piece(Color color,Point[]points,int columns,int rows){
        this.color = color;
        this.points = points;
        this.columns = columns;
        this. rows = rows;

        init();
    }

    public void init(){
        //initialize BlockMatrix
        blockMatrix = new Block[rows][columns];
        for(int i = 0;i < blockMatrix.length;i++){
            for(int j = 0;j < blockMatrix[i].length;j++){
                blockMatrix[i][j] = new Block(Block.NONE);
            }
        }
        //initialize blocks
        blocks = new Block[4];
        for (int i=0;i<points.length;i++) {
            //create new block
            int x = points[i].x;
            int y = points[i].y;
            Block block = new Block(color, x, y);
            blocks[i] = block;
            // write block to the location in the blockMatrix
            blockMatrix[y][x] = block;
        }
        /*System.out.println();
        for(int i = 0;i < blockMatrix.length;i++){
            for(int j = 0;j < blockMatrix[i].length;j++){
                System.out.print(blockMatrix[i][j].getType() + Integer.toString(i) + j + " ");
            }
            System.out.print("\n");
        }*/
        //set Position
        setPosition(new Point(PlayingArea.areaWidth / 2 - 1,1 - getHeight()));
    }
    /*
        the position of the piece moves down by one block
     */
    public void moveOneDown(){
        position.y = position.y + 1;
    }
    /*
        the position of the piece moves left by one block
     */
    public void moveOneLeft(){
        // if the piece would not stay in the playing area, do not allow
        if(position.x > 0)
            position.x = position.x - 1;
    }
    /*
        the position of the piece moves right by one block
     */
    public void moveOneRight(){
        // if the piece would not stay in the playing area, do not allow
        if(position.x + getWidth() < PlayingArea.areaWidth)
            position.x = position.x + 1;
    }
    /*
        the piece is rotated clockwise
     */
    public void rotate(){
        /*
            new block matrix is created with cols of prev. = rows and rows of prev. = cols
         */
        Block[][] blockMatrixNew = new Block[blockMatrix[0].length][blockMatrix.length];
        /*
            loop over block matrix
                i = rows, starting at 0
                k = rows, starting at length -1
                j = cols, starting at 0
         */
        for(
                int i = 0,k=blockMatrix.length - 1;
                i < blockMatrix.length;
                i++,k--
        ){
            for(
                    int j = 0;
                    j < blockMatrix[i].length;
                    j++
            ){
                // get block at the position of the matrix
                Block block = blockMatrix[i][j];
                /*
                    set it to new position
                        row = col
                        col = row,starting at the bottom
                 */
                blockMatrixNew[j][k] = block;
                // block gets new coordinates
                //if(block.getType() == Block.PIECE){
                    block.setX(k);
                    block.setY(j);
                //}
            }
        }
        blockMatrix = blockMatrixNew;

        /*
            check if block would be outside
                --> if, move it to the left
         */
        if(position.x + getWidth() > PlayingArea.areaWidth){
            position.x = PlayingArea.areaWidth - getWidth();
        }
        /*
            check if y of the lowest block would be negative
                --> if, move it down
         */
        if(position.y + getHeight() < 0)
            position.y = 0;
    }

    public void paintPiece(Graphics g){
        g.setColor(this.blocks[0].getColor());
        for(int i=0;i<this.blocks.length;i++){
            this.blocks[i].paintBlock(position,g);
        }
    }

    public int getHeight(){
        return blockMatrix.length;
    }

    public int getWidth(){
        return blockMatrix[0].length;
    }

    public boolean isAtBottom(){
        // +1 because coordinate system starts at 0, last position is areaHeight -1
        return position.y + getHeight() >= PlayingArea.areaHeight;
    }

    public Block[] getBlocks() {
        return blocks;
    }

    public Block[] getBlocks(int direction){

        Block[] blocks = new Block[1];

        switch (direction){
            case PlayingArea.DIRECTION_LEFT:
                /*
                    return all left blocks in the blockMatrix
                    length of blocks = height
                 */
                blocks = new Block[getHeight()];
                for(int i = 0;i < blockMatrix.length;i++){
                    for(int j = 0;j < blockMatrix[i].length;j++){
                        /*
                            if the block belongs to the piece, add to blocks
                            otherwise look to the block right of it
                         */
                        if(blockMatrix[i][j].getType() == Block.PIECE){
                            blocks[i] = blockMatrix[i][j];
                            break;
                        }
                    }
                }
                break;

            case PlayingArea.DIRECTION_RIGHT:
                /*
                    return all right blocks in the blockMatrix
                    length of blocks = height
                 */
                blocks = new Block[getHeight()];
                for(int i = 0;i < blockMatrix.length;i++){
                    for(int j = blockMatrix[i].length - 1;j >= 0;j--){
                        /*
                            if the block belongs to the piece, add to blocks
                            otherwise look to the block left of it
                         */
                        if(blockMatrix[i][j].getType() == Block.PIECE){
                            blocks[i] = blockMatrix[i][j];
                            break;
                        }
                    }
                }
                break;

            case PlayingArea.DIRECTION_DOWN:
                /*
                    return the lower blocks in the blockMatrix
                    length of blocks = width
                 */
                blocks = new Block[getWidth()];
                for(int i = blockMatrix.length - 1;i >= 0;i--){
                    for(int j = 0;j < blockMatrix[i].length;j++){
                        /*
                            if blocks[j] is not already initialized
                            if the block belongs to the piece, add to blocks
                            otherwise look to the block on top of it in the cycle of i
                         */
                        if(blocks[j] == null
                                && blockMatrix[i][j].getType() == Block.PIECE){

                            blocks[j] = blockMatrix[i][j];
                        }
                    }
                }
                break;
            default:
                assert (false);
        }
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
