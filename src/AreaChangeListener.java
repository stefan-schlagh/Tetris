public interface AreaChangeListener {
    void rowCollapsed(int points);
    void nextPiece();
    void gameOver();
    void pausedChanged(boolean paused);
}
