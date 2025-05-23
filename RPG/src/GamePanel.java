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
        enemies.add(new Enemy(5, 5, "enemigo1"));
        enemies.add(new Enemy(6, 2, "enemigo2"));
        enemies.add(new Enemy(3, 7, "enemigo3"));

        // Cargar imágenes (ajusta ruta a tus imágenes)
        heroImage = new ImageIcon(getClass().getResource("/resources/hero.png")).getImage();
        enemyImage = new ImageIcon(getClass().getResource("/resources/enemy.png")).getImage();

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

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        if (heroImage != null) {
            g.drawImage(heroImage, hero.getX() * TILE_SIZE, hero.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
        }

        if (enemyImage != null) {
            for (Enemy enemy : enemies) {
                g.drawImage(enemyImage, enemy.getX() * TILE_SIZE, enemy.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
            }
        }

        // Mostrar HP del héroe
        g.setColor(Color.BLACK);
        g.drawString("HP Héroe: " + hero.getHp(), 10, ROWS * TILE_SIZE + 20);
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
                while(hero.isAlive() && e.isAlive()) {
                    // Heroe ataca (30% chance especial)
                    if (Math.random() < 0.3) {
                        int dmg = hero.specialAttack();
                        e.receiveDamage(dmg);
                        JOptionPane.showMessageDialog(this, "¡Héroe usa ataque especial y hace " + dmg + " de daño!");
                    } else {
                        int dmg = hero.getAtk();
                        e.receiveDamage(dmg);
                        JOptionPane.showMessageDialog(this, "Héroe ataca y hace " + dmg + " de daño!");
                    }

                    if (e.isAlive()) {
                        hero.receiveDamage(e.getAtk());
                        JOptionPane.showMessageDialog(this, "Enemigo " + e.getType() + " ataca y hace " + e.getAtk() + " de daño!");
                    }
                }

                if (!hero.isAlive()) {
                    JOptionPane.showMessageDialog(this, "¡El héroe ha sido derrotado! Juego terminado.");
                    System.exit(0);
                }

                if (!e.isAlive()) {
                    JOptionPane.showMessageDialog(this, "¡Enemigo " + e.getType() + " derrotado!");
                    toRemove.add(e);
                }
            }
        }
        enemies.removeAll(toRemove);
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
