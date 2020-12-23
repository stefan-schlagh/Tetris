import javax.swing.*;

public class Start {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame f = new MainFrame();
                f.setVisible(true);
                f.getPlayingArea().startAnimation();
            }
        });
    }
}
