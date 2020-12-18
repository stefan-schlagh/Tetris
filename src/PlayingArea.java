import Block.Block;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayingArea extends JPanel implements Runnable{

    private Thread runner;

    private Block[][] playingArea = new Block[20][10];
    private boolean running = false;

    private final int Action_rotate = KeyEvent.VK_UP;
    private final int Action_left = KeyEvent.VK_LEFT;
    private final int Action_right = KeyEvent.VK_RIGHT;
    private final int Action_down = KeyEvent.VK_DOWN;

    public PlayingArea() {
        super();

        this.setBackground(Color.BLACK);

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
