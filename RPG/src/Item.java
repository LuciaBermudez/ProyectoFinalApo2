public class Item {
    private int x, y;
    private String type;

    public Item(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type.toLowerCase();
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public String getType() { return type; }
}
