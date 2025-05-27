import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("RPG Turnos - Mapa con Casillas");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 700);

            // Usamos GamePanel sin pasarle el frame
            GamePanel panel = new GamePanel();
            frame.add(panel);

            frame.setVisible(true);
        });
    }
}

