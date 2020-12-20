import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayingArea extends JPanel implements Runnable{

    private Thread runner;

    public static final int areaHeight = 20;
    public static final int areaWidth = 10;
    public static final int pixelsPerSquare = 30;

    private Block[][] playingArea = new Block[areaWidth][areaHeight];
    private Piece currentPiece;
    private boolean running = true;

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

        currentPiece = Piece.O;

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
        currentPiece.moveOneLeft();
    }
    /*
        if allowed, move right
     */
    public void moveOneRight(){
        currentPiece.moveOneRight();
    }
    /*
        if allowed, move down
     */
    public void moveDown(){

        if(!currentPiece.isAtBottom())
            currentPiece.moveOneDown();
    }
    /*
        returns if there will be a collision after movement
     */
    public boolean isCollision(int direction){
        //rotate needs extra treatment
        if(direction == ROTATE){
            //TODO
        }else {
            //loop over blocks of piece
            for (int i = 0; i < currentPiece.getBlocks().length; i++) {

                Block currentBlock = currentPiece.getBlocks()[i];
                int block_x = currentBlock.getX() + currentPiece.getPosition().x;
                int block_y = currentBlock.getX() + currentPiece.getPosition().y;

                switch (direction) {

                    case DIRECTION_LEFT:
                        //TODO
                        break;
                    case DIRECTION_RIGHT:
                        //TODO
                        break;
                    case DIRECTION_DOWN:
                        //TODO
                        break;
                }
            }
        }
        return false;
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
                        i * pixelsPerSquare,
                        j * pixelsPerSquare,
                        pixelsPerSquare,
                        pixelsPerSquare);
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
}
