
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

public class Fixtures extends room implements ActionListener {

    private static final long serialVersionUID = 1L;

    myButton2 commode = new myButton2("Commode");
    myButton2 washbasin = new myButton2("Washbasin");
    myButton2 shower = new myButton2("Shower");
    myButton2 sink = new myButton2("Sink");
    myButton2 stove = new myButton2("Stove");

    CanvasPanel pa = new CanvasPanel();

    public Fixtures(CanvasPanel pa) {
        this.pa = pa;
        setLayout(new GridLayout(5, 1, 5, 5));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        Font buttonFont = new Font("Verdana", Font.PLAIN, 20);
        myButton2[] buttons = {commode, washbasin, shower, sink, stove};
        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setBackground(new Color(255, 218, 185));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            button.addActionListener((ActionListener) this);
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
            obj newFixture = null;
            if (e.getSource() == commode) {
                newFixture = new obj("lib/commode.png");
            } else if (e.getSource() == washbasin) {
                newFixture = new obj("lib/washbasin.png");
            } else if (e.getSource() == shower) {
                newFixture = new obj("lib/shower.png");
            } else if (e.getSource() == sink) {
                newFixture = new obj("lib/sink.png");
            } else if (e.getSource() == stove) {
                newFixture = new obj("lib/stove.png");
            }

            if (newFixture != null) {
                room selectedRoom = pa.getSelectedRoom();
                if (selectedRoom.changetovalidpos(newFixture)) {
                    selectedRoom.placedObjs.add(newFixture);
                    selectedRoom.add(newFixture);
                    selectedRoom.revalidate();
                    selectedRoom.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "No valid position found for the fixture.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
