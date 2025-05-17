import java.util.List;
import java.util.Random;

public class Enemy extends Character {
    private static final Random rand = new Random();

    public Enemy(int x, int y) {
        super(x, y);
    }

    public void moveRandom(int maxCols, int maxRows, List<Enemy> others) {
        int[] dx = {0, 0, -1, 1}; // direcciones
        int[] dy = {-1, 1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int dir = rand.nextInt(4);
            int newX = x + dx[dir];
            int newY = y + dy[dir];

            boolean occupied = others.stream().anyMatch(e -> e != this && e.x == newX && e.y == newY);

            if (newX >= 0 && newX < maxCols && newY >= 0 && newY < maxRows && !occupied) {
                this.x = newX;
                this.y = newY;
                break;
            }
        }
    }
}
