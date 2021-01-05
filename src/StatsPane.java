import javax.swing.*;
import java.awt.*;

public class StatsPane extends JPanel {

    public StatsPane(PlayingArea playingArea){

        setLayout(null);
        setBackground(Color.BLACK);

        JLabel points = new JLabel();
        Font pFont = new Font("arial",Font.PLAIN,30);
        points.setFont(pFont);
        points.setForeground(Color.WHITE);
        points.setText("Points: 0");
        points.setBounds(40,40,150,30);

        add(points);

        JLabel nextPiece = new JLabel();
        Font nFont = new Font("arial",Font.PLAIN,24);
        nextPiece.setFont(nFont);
        nextPiece.setForeground(Color.WHITE);
        nextPiece.setText("Next Piece:");
        nextPiece.setBounds(40,100,150,30);

        add(nextPiece);

        playingArea.setAreaChangeListener(new AreaChangeListener() {
            @Override
            public void nextPiece(int pointsNum) {
                points.setText("Points: " + pointsNum);
            }

            @Override
            public void gameOver() {

            }
        });
    }
}
