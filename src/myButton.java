
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class myButton extends JButton {

    public myButton(String text) {
        super(text);
        setContentAreaFilled(false); // Turn off default button fill
        setFocusPainted(false); // Remove the focus border
        setBorder(new EmptyBorder(10, 20, 10, 20)); // Add padding
        setForeground(Color.WHITE); // Text color
        setFont(new Font("Arial", Font.BOLD, 16));
    }

    // Custom paint method for styling
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background with gradient
        GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 122, 204), 0, getHeight(), new Color(0, 184, 255));
        g2.setPaint(gradient);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        super.paintComponent(g);
        g2.dispose();
    }
}
