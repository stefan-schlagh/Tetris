import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatsPane extends JPanel {

    private NextPiecePane nextPiecePane;

    public StatsPane(PlayingArea playingArea){

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(20,0,20,20));
        setBackground(Color.BLACK);

        JLabel points = new JLabel();
        Font pFont = new Font("arial",Font.PLAIN,30);
        points.setFont(pFont);
        points.setForeground(Color.WHITE);
        points.setText("Points: 0");
        points.setBorder(new EmptyBorder(10,10,10,10));

        add(points);

        JLabel nextPiece = new JLabel();
        Font nFont = new Font("arial",Font.PLAIN,24);
        nextPiece.setFont(nFont);
        nextPiece.setForeground(Color.WHITE);
        nextPiece.setText("Next Piece:");
        nextPiece.setBorder(new EmptyBorder(10,10,10,10));

        add(nextPiece);

        nextPiecePane = new NextPiecePane(playingArea);

        add(nextPiecePane);

        JLabel pausedLabel1 = new JLabel();
        JLabel pausedLabel2 = new JLabel();
        Font plFont = new Font("arial",Font.PLAIN,24);
        pausedLabel1.setFont(plFont);
        pausedLabel2.setFont(plFont);
        pausedLabel1.setForeground(Color.RED);
        pausedLabel2.setForeground(Color.RED);
        pausedLabel1.setText("Game is paused!");
        pausedLabel2.setText("Press Space to continue!");
        pausedLabel1.setBorder(new EmptyBorder(10,10,5,10));
        pausedLabel2.setBorder(new EmptyBorder(5,10,10,10));
        pausedLabel1.setVisible(false);
        pausedLabel2.setVisible(false);

        add(pausedLabel1);
        add(pausedLabel2);

        playingArea.setAreaChangeListener(new AreaChangeListener() {

            @Override
            public void rowCollapsed(int pointsNum) {
                points.setText("Points: " + pointsNum);
            }

            @Override
            public void nextPiece() {
                nextPiecePane.repaint();
            }

            @Override
            public void gameOver() {
                points.setText("Points: 0");
                System.out.println("game over!");
            }

            @Override
            public void pausedChanged(boolean value) {
                pausedLabel1.setVisible(value);
                pausedLabel2.setVisible(value);
            }
        });
    }
}
