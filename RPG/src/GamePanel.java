import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements KeyListener {
    private final int TILE_SIZE = 30;
    private final int ROWS = 20;
    private final int COLS = 20;
    private Image heroImage;
    private Image enemyImage;

    private Hero hero;
    private List<Enemy> enemies;
    private Timer enemyTimer;

    public GamePanel() {
        hero = new Hero(1, 1);
        enemies = new ArrayList<>();
        enemies.add(new Enemy(5, 5));
        enemies.add(new Enemy(6, 2));
        enemies.add(new Enemy(3, 7));

        // Cargar imágenes
        heroImage = new ImageIcon(getClass().getResource("/resources/hero.png")).getImage();
        enemyImage = new ImageIcon(getClass().getResource("/resources/enemy.png")).getImage();

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        // Movimiento aleatorio de enemigos cada 500 ms
        enemyTimer = new Timer(500, e -> {
            for (Enemy enemy : enemies) {
                enemy.moveRandom(COLS, ROWS, enemies);
            }
            checkCombat();
            repaint();
        });
        enemyTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibuja el grid
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        // Dibujo del héroe
        if (heroImage != null) {
            g.drawImage(heroImage, hero.getX() * TILE_SIZE, hero.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
        }

        // Dibujo de los enemigos
        if (enemyImage != null) {
            for (Enemy enemy : enemies) {
                g.drawImage(enemyImage, enemy.getX() * TILE_SIZE, enemy.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
            }
        }
    }

    private boolean isOccupied(int x, int y) {
        for (Enemy e : enemies) {
            if (e.getX() == x && e.getY() == y) return true;
        }
        return false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        int newX = hero.getX();
        int newY = hero.getY();

        if (key == KeyEvent.VK_LEFT && newX > 0) newX--;
        if (key == KeyEvent.VK_RIGHT && newX < COLS - 1) newX++;
        if (key == KeyEvent.VK_UP && newY > 0) newY--;
        if (key == KeyEvent.VK_DOWN && newY < ROWS - 1) newY++;

        // Solo permite el movimiento si la casilla no está ocupada
        if (!isOccupied(newX, newY)) {
            hero.setPosition(newX, newY);
            checkCombat();
            repaint();
        }
    }

    private void checkCombat() {
        List<Enemy> toRemove = new ArrayList<>();
        for (Enemy e : enemies) {
            if (e.getX() == hero.getX() && e.getY() == hero.getY()) {
                JOptionPane.showMessageDialog(this, "¡Enfrentamiento con enemigo!");
                toRemove.add(e);
            }
        }
        enemies.removeAll(toRemove);
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
