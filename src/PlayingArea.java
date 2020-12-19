import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayingArea extends JPanel implements Runnable{

    private Thread runner;

    private Block[][] playingArea = new Block[10][20];
    private Piece currentPiece;
    private boolean running = true;

    private final int ACTION_ROTATE = KeyEvent.VK_UP;
    private final int ACTION_LEFT = KeyEvent.VK_LEFT;
    private final int ACTION_RIGHT = KeyEvent.VK_RIGHT;
    private final int ACTION_DOWN = KeyEvent.VK_DOWN;

    public PlayingArea() {
        super();

        this.setBackground(Color.BLACK);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()){
                    case(ACTION_ROTATE):
                        currentPiece.rotate();
                        break;
                    case(ACTION_LEFT):
                        currentPiece.moveOneLeft();
                        break;
                    case(ACTION_RIGHT):
                        currentPiece.moveOneRight();
                        break;
                    case(ACTION_DOWN):
                        currentPiece.moveOneDown();
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

        currentPiece = Piece.S;
        currentPiece.setPosition(new Point(0,0));

        int i = 0;

        while(true){
            if(isRunning()){
                if(i == 19)
                    currentPiece.moveOneDown();
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
                g.drawRect(i*30,j*30,30,30);
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
