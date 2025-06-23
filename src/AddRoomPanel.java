
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AddRoomPanel extends JPanel implements ActionListener {

    private myButton2 room1 = new myButton2("Bedroom");
    private myButton2 room2 = new myButton2("Bathroom");
    private myButton2 room3 = new myButton2("Kitchen");
    private myButton2 room4 = new myButton2("Drawing Room");
    private myButton2 room5 = new myButton2("Dining Room");
    private CanvasPanel pa;

    public AddRoomPanel(CanvasPanel pa) {
        this.pa = pa;
        setLayout(new GridLayout(5, 1, 5, 5));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.darkGray);
        setBounds(0, 180, 200, 800);

        Font buttonFont = new Font("Verdana", Font.PLAIN, 20);
        myButton2[] buttons = {room1, room2, room3, room4, room5};
        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setBackground(new Color(255, 218, 185));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            button.addActionListener((ActionListener) this);
            button.setFocusPainted(false);
            add(button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (pa.getSelectedRoom() != null) {
            String[] positionDetails = RoomPositionDialog.getPositionDetails();
            String direction = positionDetails[0];
            String alignment = positionDetails[1];

            if (e.getSource() == room1) {

                pa.addroomRelativeToSelectedRoom(1, pa.getSelectedRoom(), direction, alignment);
            } else if (e.getSource() == room2) {
                pa.addroomRelativeToSelectedRoom(2, pa.getSelectedRoom(), direction, alignment);

            } else if (e.getSource() == room3) {

                pa.addroomRelativeToSelectedRoom(3, pa.getSelectedRoom(), direction, alignment);
            } else if (e.getSource() == room4) {
                pa.addroomRelativeToSelectedRoom(4, pa.getSelectedRoom(), direction, alignment);
            } else if (e.getSource() == room5) {
                pa.addroomRelativeToSelectedRoom(5, pa.getSelectedRoom(), direction, alignment);
            }

        } else {
            if (e.getSource() == room1) {

                pa.normaladdroom(1);
            } else if (e.getSource() == room2) {
                pa.normaladdroom(2);

            } else if (e.getSource() == room3) {

                pa.normaladdroom(3);
            } else if (e.getSource() == room4) {
                pa.normaladdroom(4);
            } else if (e.getSource() == room5) {
                pa.normaladdroom(5);
            }
        }
    }
}
