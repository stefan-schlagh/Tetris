import javax.swing.*;
import java.awt.*;

public class NextPiecePane extends JPanel {

    private PlayingArea playingArea;

    public NextPiecePane(PlayingArea playingArea){
        this.playingArea = playingArea;

        setBackground(Color.BLACK);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Piece nextPiece = playingArea.getNextPiece();
        Point[] points = nextPiece.getPoints();

        g.setColor(nextPiece.getColor());

        for(int i = 0;i < points.length;i ++){
            g.fillRect(
                    points[i].x * (PlayingArea.pixelsPerSquare + 1),
                    points[i].y * (PlayingArea.pixelsPerSquare + 1),
                    28,
                    28
            );
        }
    }
}
