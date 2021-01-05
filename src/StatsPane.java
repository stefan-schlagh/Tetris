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
                System.out.println("game over!");
            }
        });
    }
}
