public class GameMap {
    private Tile[][] grid;

    public GameMap(int width, int height) {
        grid = new Tile[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new Tile(x, y);
            }
        }
    }

    public Tile[][] getGrid() {
        return grid;
    }
}
