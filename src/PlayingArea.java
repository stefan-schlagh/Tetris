import Block.Block;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayingArea extends JPanel implements Runnable{

    private Thread runner;

    private Block[][] playingArea = new Block[10][20];
    private boolean running = false;

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

                        break;
                    case(ACTION_LEFT):

                        break;
                    case(ACTION_RIGHT):

                        break;
                    case(ACTION_DOWN):

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

        while(true){
            if(isRunning()){

            }else{

            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        /*
            paint playing area
         */
        for(int i=0;i < playingArea.length;i++){
            for(int j=0;j < playingArea[i].length;j++){
                g.setColor(Color.WHITE);
                //paint grid
                g.drawRect(i*30,j*30,30,30);
            }
        }
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
