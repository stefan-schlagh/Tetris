import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayingArea extends JPanel implements Runnable{

    private Thread runner;

    public static final int areaHeight = 20;
    public static final int areaWidth = 10;
    public static final int pixelsPerSquare = 30;

    private Block[][] playingArea = new Block[areaHeight][areaWidth];
    private Piece currentPiece;
    private boolean running = true;
    //the points the player earned
    private int points = 0;
    private AreaChangeListener areaChangeListener;

    private static final int ACTION_ROTATE = KeyEvent.VK_UP;
    private static final int ACTION_LEFT = KeyEvent.VK_LEFT;
    private static final int ACTION_RIGHT = KeyEvent.VK_RIGHT;
    private static final int ACTION_DOWN = KeyEvent.VK_DOWN;

    public static final int ROTATE = 0;
    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = 2;
    public static final int DIRECTION_DOWN = 3;

    public PlayingArea() {
        super();

        this.setBackground(Color.BLACK);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()){
                    case(ACTION_ROTATE):
                        rotate();
                        break;
                    case(ACTION_LEFT):
                        moveOneLeft();
                        break;
                    case(ACTION_RIGHT):
                        moveOneRight();
                        break;
                    case(ACTION_DOWN):
                        moveDown();
                        break;
                }
            }
        });
        this.setFocusable(true);

        initBlocks();
    }

    public synchronized void startAnimation(){
        if(runner != null)
            return;
        runner = new Thread(this);
        runner.start();
    }

    @Override
    public void run() {

        generateNewPiece();

        int i = 0;

        while(true){
            if(isRunning()){
                if(i == 19)
                    moveDown();
                i = (i+1)%20;
                this.repaint();
            }else{

            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /*
        if allowed, rotate
     */
    public void rotate(){
        currentPiece.rotate();
    }
    /*
        if allowed, move left
     */
    public void moveOneLeft(){
        if(!isCollision(DIRECTION_LEFT))
            currentPiece.moveOneLeft();
    }
    /*
        if allowed, move right
     */
    public void moveOneRight(){
        if(!isCollision(DIRECTION_RIGHT))
            currentPiece.moveOneRight();
    }
    /*
        if allowed, move down
     */
    public void moveDown(){
        /*
            if piece is at bottom
                --> convert piece into fixed blocks
         */
        if(currentPiece.isAtBottom() || isCollision(DIRECTION_DOWN)) {
            pieceIntoBlocks(currentPiece);
            generateNewPiece();
            collapseFullRows();
        }else {
            currentPiece.moveOneDown();
        }
    }
    /*
        returns if there will be a collision after movement
     */
    public boolean isCollision(int direction){

        //rotate needs extra treatment
        if(direction == ROTATE){
            //TODO
        }else {
            // get the blocks of the piece that are facing in the direction
            Block[] blocks = currentPiece.getBlocks(direction);
            //loop over blocks of piece
            for (int i = 0; i < blocks.length; i++) {

                Block currentBlock = blocks[i];
                int block_x = currentBlock.getX() + currentPiece.getPosition().x;
                int block_y = currentBlock.getY() + currentPiece.getPosition().y;

                switch (direction) {

                    case DIRECTION_LEFT:
                        /*
                            check if the type of block left to the block is Block.NONE
                                if block_y < 0, do not check
                            if block_x <= 0 --> collision (with edge)
                         */
                        if(block_x <= 0)
                            return true;
                        if(block_y >= 0) {
                            Block blockLeft = playingArea[block_y][block_x - 1];
                            if (blockLeft.getType() != Block.NONE)
                                return true;
                        }
                        break;
                    case DIRECTION_RIGHT:
                        /*
                            check if the type of block left to the block is Block.NONE
                                if block_y < 0, do not check
                            if block_x >= width - 1 --> collision (with edge)
                         */
                        if(block_x >= areaWidth - 1)
                            return true;
                        if(block_y >= 0) {
                            Block blockRight = playingArea[block_y][block_x + 1];
                            if (blockRight.getType() != Block.NONE)
                                return true;
                        }
                        break;
                    case DIRECTION_DOWN:
                        /*
                            check if the type of the block below the block is Block.NONE
                         */
                        Block blockBelow = playingArea[block_y + 1][block_x];
                        if(blockBelow.getType() != Block.NONE)
                            return true;
                        break;
                }
            }
        }
        return false;
    }
    /*
        make blocks of piece into blocks in the playingArea
     */
    public void pieceIntoBlocks(Piece piece){
        // if the block is over the playing field, end game
        Block[] pBlocks = piece.getBlocks();
        for(int i = 0;i < pBlocks.length;i++){
            Block pBlock = pBlocks[i];
            int x_block = piece.getPosition().x + pBlock.getX();
            int y_block = piece.getPosition().y + pBlock.getY();

            if(y_block < 0) {
                gameOver();
                break;
            }

            Block paBlock = playingArea[y_block][x_block];
            // paBlock.type has to be Block.NONE
            if(paBlock.getType()  != Block.NONE){
                System.out.println("There has been a undetected Collision");
                assert(false);
            }
            paBlock.setType(Block.FIXED_BLOCK);
            paBlock.setColor(pBlock.getColor());
        }
    }
    /*
        game over
     */
    public void gameOver(){
        if(areaChangeListener != null)
            areaChangeListener.gameOver();
    }
    /*
        collapse full rows and increase point counter
     */
    public void collapseFullRows(){
        /*
            loop over playing area
            if full row is type Block.FIXED_BLOCK,
                row is removed from array and the upper parts of the field collapse down
            if a row has been collapsed, the loop has to be started new
         */
        boolean lookedAtAll = false;
        while(!lookedAtAll) {
            boolean nonFixedBlockInRow;
            for (int i = playingArea.length - 1;i >= 0;i--) {
                //if this remains false, collapse
                nonFixedBlockInRow = false;
                if(playingArea[i].length != areaWidth)
                    throw  new RuntimeException("length");
                for (int j = 0; j < playingArea[i].length; j++) {
                    if (playingArea[i][j].getType() == Block.NONE) {
                        nonFixedBlockInRow = true;
                        break;
                    }
                }
                // if there was no non-fixed block in the row, collapse
                if (!nonFixedBlockInRow){
                    collapseRow(i);
                    points += 10;
                    if(areaChangeListener != null)
                        areaChangeListener.nextPiece(points);
                    break;
                }
                // all rows checked
                if(i <= 0)
                    lookedAtAll = true;
            }
        }
    }
    /*
        row is collapsed
     */
    public void collapseRow(int rowIndex){
        for(int i = rowIndex;i >= 1;i--){
            // each row drops 1 down, row at rowIndex gets deleted
            playingArea[i] = playingArea[i-1];
        }
        // last row has to be new initialized
        Block[] blockRow = new Block[areaWidth];
        for (int i = 0;i<blockRow.length;i++) {
            blockRow[i] = new Block(Block.NONE);
        }
        playingArea[0] = blockRow;
    }
    /*
        generate new piece
     */
    public void generateNewPiece(){
        currentPiece = Piece.getPiece(Piece.getRandPNumber());
        currentPiece.init();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        /*
            paint block matrix
         */
        for(int i=0;i < playingArea.length;i++){
            for(int j=0;j < playingArea[i].length;j++){
                g.setColor(Color.WHITE);
                //paint grid
                g.drawRect(
                        j * pixelsPerSquare,
                        i * pixelsPerSquare,
                        pixelsPerSquare,
                        pixelsPerSquare);
                // paintBlock, if fixed
                Block block = playingArea[i][j];
                if(block.getType() == Block.FIXED_BLOCK)
                    block.paintBlock(new Point(j,i),g);
            }
        }
        /*
            paint currentPiece
         */
        currentPiece.paintPiece(g);
    }

    private void initBlocks(){
        for (Block [] blockRow: playingArea) {
            for (int i = 0;i<blockRow.length;i++) {
                blockRow[i] = new Block(Block.NONE);
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setAreaChangeListener(AreaChangeListener areaChangeListener) {
        this.areaChangeListener = areaChangeListener;
    }
}
