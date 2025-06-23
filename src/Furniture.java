
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

public class Furniture extends room implements ActionListener {

    private static final long serialVersionUID = 1L;

    myButton2 bed = new myButton2("Bed");
    myButton2 chair = new myButton2("Chair");
    myButton2 table = new myButton2("Table");
    myButton2 sofa = new myButton2("Sofa");
    myButton2 dining = new myButton2("Dining Set");

    CanvasPanel pa = new CanvasPanel();

    public Furniture(CanvasPanel pa) {
        this.pa = pa;
        setLayout(new GridLayout(5, 1, 5, 5));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        myButton2[] buttons = {bed, chair, table, sofa, dining};
        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setBackground(new Color(255, 218, 185));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            button.addActionListener(this);
            button.setFocusPainted(false);
            add(button);
        }

        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.darkGray);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (pa.getSelectedRoom() != null) {
            room selectedRoom = pa.getSelectedRoom();
            obj newObj = null;
            if (e.getSource() == bed) {
                newObj = new obj("lib/bed2.png");
            } else if (e.getSource() == chair) {
                newObj = new obj("lib/chair.png");
            } else if (e.getSource() == table) {
                newObj = new obj("lib/table2.png");
            } else if (e.getSource() == sofa) {
                newObj = new obj("lib/sofa.png");
            } else if (e.getSource() == dining) {
                newObj = new obj("lib/dining.jpg");
            }

            if (newObj != null) {
                // Check for a valid position
                if (selectedRoom.changetovalidpos(newObj)) {
                    selectedRoom.placedObjs.add(newObj); // Add to the room's furniture list
                    selectedRoom.add(newObj);
                    selectedRoom.repaint();
                    selectedRoom.revalidate();
                } else {
                    JOptionPane.showMessageDialog(null, "No valid position found for furniture.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
