import javax.swing.*;

public class MainFrame extends JFrame {

    private PlayingArea playingArea = new PlayingArea();

    public MainFrame () {

        this.setTitle("Tetris");
        this.setSize(400,800);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.getContentPane().add(playingArea);
    }

    public PlayingArea getPlayingArea() {
        return playingArea;
    }
}
