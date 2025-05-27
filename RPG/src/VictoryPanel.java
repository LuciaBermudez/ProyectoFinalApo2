import javax.swing.*;
import java.awt.*;

public class VictoryPanel extends JPanel {
    public VictoryPanel(Runnable onRestart) {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("¡Felicidades! Has ganado el juego.", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(0, 128, 0));  // Verde

        JButton restartBtn = new JButton("Volver a jugar");
        restartBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        restartBtn.addActionListener(e -> onRestart.run());

        add(label, BorderLayout.CENTER);
        add(restartBtn, BorderLayout.SOUTH);
    }

}
