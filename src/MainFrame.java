import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private PlayingArea playingArea = new PlayingArea();

    public MainFrame () {

        setTitle("Tetris");
        //setSize(320,640);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);

        GridLayout layout = new GridLayout();
        JPanel mainPanel = new JPanel(layout);

        setSize(700,640);
        setMinimumSize(new Dimension(700,640));

        playingArea.setSize(320,640);
        playingArea.setMinimumSize(new Dimension(320,640));

        mainPanel.add(playingArea);

        JPanel statsPane = new StatsPane(playingArea);

        mainPanel.add(statsPane);

        getContentPane().add(mainPanel);
    }

    public PlayingArea getPlayingArea() {
        return playingArea;
    }
}
