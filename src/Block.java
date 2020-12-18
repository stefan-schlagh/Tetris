import java.awt.*;

public class Block {

    public static final int NONE = 0;
    public static final int FIXED_BLOCK = 1;
    public static final int PIECE = 2;

    // x position from block origin
    private int x;
    //y position from block origin
    private int y;
    private int type;
    private Color color;

    public Block(int type){
        this.type = type;
    }

    public Block(Color color,int x,int y){
        this(PIECE);
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
