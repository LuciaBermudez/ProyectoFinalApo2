import java.util.List;
import java.util.Random;

public class Enemy extends Character {
    private static final Random rand = new Random();
    private String type;

    public Enemy(int x, int y, String type) {
        super(x, y, 50, 10);
        this.type = type.toLowerCase();

        switch(this.type) {
            case "enemigo1":
                this.hp = 100;
                this.atk = 15;
                break;
            case "enemigo2":
                this.hp = 100;
                this.atk = 18;
                break;
            case "enemigo3":
                this.hp = 100;
                this.atk = 20;
                break;
                 case "enemigo4":
                this.hp = 100;
                this.atk = 12;
                break;
            default:
                this.hp = 100;
                this.atk = 10;
        }
    }

    public String getType() {
        return type;
    }

    public void moveRandom(int maxCols, int maxRows, List<Enemy> others) {
        int[] dx = {0, 0, -1, 1};
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

