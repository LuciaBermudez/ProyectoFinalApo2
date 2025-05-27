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
    private Image fondoImage;
    private Image itemImage;
    private int itemX = 3;
    private int itemY = 6;
    private boolean itemActive = true;
    private Hero hero;
    private List<Enemy> enemies;
    private Timer enemyTimer;
    private final int HERO_MAX_HP = 100;
    private boolean gameOver = false;

    public GamePanel() {
        hero = new Hero(1, 1);

        enemies = new ArrayList<>();
        enemies.add(new Enemy(5, 5, "."));
        enemies.add(new Enemy(6, 2, "."));
        enemies.add(new Enemy(3, 7, "."));
         enemies.add(new Enemy(7, 3, "."));

         // implementacion
        heroImage = new ImageIcon(getClass().getResource("/resources/hero.png")).getImage();
        enemyImage = new ImageIcon(getClass().getResource("/resources/enemy.png")).getImage();
        fondoImage = new ImageIcon(getClass().getResource("/resources/fondo.png")).getImage();
        itemImage =    new ImageIcon(getClass().getResource("/resources/Item.png")).getImage();
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

    // Dibujar fondo en cada casilla
    for (int row = 0; row < ROWS; row++) {
        for (int col = 0; col < COLS; col++) {
            if (fondoImage != null) {
                g.drawImage(fondoImage, col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
            } else {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
            g.setColor(Color.BLACK);
            g.drawRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
        // Mostrar pantalla Game Over si el héroe muere
if (gameOver) {
    g.setColor(new Color(0, 0, 0, 180)); // fondo semi-transparente
    g.fillRect(0, 0, getWidth(), getHeight());

    g.setColor(Color.RED);
    g.setFont(new Font("Arial", Font.BOLD, 48));
    g.drawString("GAME OVER", getWidth() / 2 - 150, getHeight() / 2);
}
    }

    // Dibujar ítem si está activo
    if (itemActive && itemImage != null) {
        g.drawImage(itemImage, itemX * TILE_SIZE, itemY * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
    }

    // Dibujar héroe
    if (heroImage != null) {
        g.drawImage(heroImage, hero.getX() * TILE_SIZE, hero.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);

        // Barra de vida del héroe
        int heroHpWidth = (int) ((double) hero.getHp() / HERO_MAX_HP * TILE_SIZE);
        g.setColor(Color.RED);
        g.fillRect(hero.getX() * TILE_SIZE, hero.getY() * TILE_SIZE - 10, TILE_SIZE, 5); // Fondo barra
        g.setColor(Color.GREEN);
        g.fillRect(hero.getX() * TILE_SIZE, hero.getY() * TILE_SIZE - 10, heroHpWidth, 5); // Vida actual
        g.setColor(Color.BLACK);
        g.drawRect(hero.getX() * TILE_SIZE, hero.getY() * TILE_SIZE - 10, TILE_SIZE, 5); // Borde
    }

    // Dibujar enemigos
    if (enemyImage != null) {
        for (Enemy enemy : enemies) {
            g.drawImage(enemyImage, enemy.getX() * TILE_SIZE, enemy.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);

            // Barra de vida del enemigo
            int enemyHpWidth = (int) ((double) enemy.getHp() / 100 * TILE_SIZE); // Asumiendo HP máximo de 100
            g.setColor(Color.RED);
            g.fillRect(enemy.getX() * TILE_SIZE, enemy.getY() * TILE_SIZE - 10, TILE_SIZE, 5); // Fondo barra
            g.setColor(Color.GREEN);
            g.fillRect(enemy.getX() * TILE_SIZE, enemy.getY() * TILE_SIZE - 10, enemyHpWidth, 5); // Vida actual
            g.setColor(Color.BLACK);
            g.drawRect(enemy.getX() * TILE_SIZE, enemy.getY() * TILE_SIZE - 10, TILE_SIZE, 5); // Borde
        }
    }

    // Mostrar información del héroe (opcional)
    g.setColor(Color.BLACK);
    g.drawString("HP Héroe: " + hero.getHp(), 10, ROWS * TILE_SIZE + 20);
}


    private boolean isOccupied(int x, int y) {
        for (Enemy e : enemies) {
            if (e.getX() == x && e.getY() == y) return true;
        }
        return false;
    }
    public void heal(int amount) {
        int newHp = Math.min(HERO_MAX_HP, hero.getHp() + amount);
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
               // Aquí agregas esta comprobación para el ítem
    if (itemActive && newX == itemX && newY == itemY) {
        int healAmount = 80;
        hero.heal(healAmount);
        itemActive = false;
        JOptionPane.showMessageDialog(this, "¡Héroe recogió un ítem y recuperó " + healAmount + " de vida!");
    }
            checkCombat();
            repaint();
        }
    }
private void showVictoryPanel() {
    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

    if (topFrame != null) {
        topFrame.getContentPane().removeAll();

        VictoryPanel vp = new VictoryPanel(() -> {
            // Acción para reiniciar el juego:
            topFrame.getContentPane().removeAll();
            topFrame.getContentPane().add(new GamePanel());
            topFrame.revalidate();
            topFrame.repaint();
        });

        topFrame.getContentPane().add(vp);
        topFrame.revalidate();
        topFrame.repaint();
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
                }
                if (!hero.isAlive()) {
                 gameOver = true;
                 repaint();
                return;}
                if (!e.isAlive()) {
                    JOptionPane.showMessageDialog(this, "¡Enemigo " + e.getType() + " derrotado!");
                    toRemove.add(e);
                }
            }
        }
        enemies.removeAll(toRemove);
          if (enemies.isEmpty()) {
        showVictoryPanel();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
