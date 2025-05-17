import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements KeyListener {
    private final int TILE_SIZE = 30;
    private final int ROWS = 20;
    private final int COLS = 20;

    private Hero hero;
    private List<Enemy> enemies;
    private Timer enemyTimer;

    public GamePanel() {
        hero = new Hero(1, 1);
        enemies = new ArrayList<>();
        enemies.add(new Enemy(5, 5));
        enemies.add(new Enemy(6, 2));
        enemies.add(new Enemy(3, 7));

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
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

        // Dibuja héroe (puedes reemplazar por imagen si deseas)
        g.setColor(Color.BLUE);
        g.fillRect(hero.getX() * TILE_SIZE, hero.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Dibuja enemigos
        g.setColor(Color.RED);
        for (Enemy e : enemies) {
            g.fillRect(e.getX() * TILE_SIZE, e.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
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
    
        hero.setPosition(newX, newY);           // 1. mueve al héroe
        for (Enemy eEnemy : enemies) {
            eEnemy.moveRandom(COLS, ROWS, enemies); // 2. mueve a los enemigos
        }
    
        checkCombat(); // 3. verifica combates
        repaint();     // 4. vuelve a dibujar
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

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
