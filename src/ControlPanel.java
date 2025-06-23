
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ControlPanel extends JPanel implements ActionListener {

    private JPanel cards;
    private CardLayout cardLayout;
    private AddRoomPanel addRoomPanel;
    private Fixtures fixturesPanel;
    private Furniture furniturePanel;

    private myButton addRoomButton;
    private myButton addFurnitureButton;
    private myButton addFixturesButton;
    private JButton rotate;
    private JButton save;
    private JButton load;
    private JButton clear;

    dimensions dim = new dimensions();
    CanvasPanel canvasPanel;

    ControlPanel(CanvasPanel canvasPanel) {
        this.canvasPanel = canvasPanel;
        setBackground(Color.DARK_GRAY);
        setLayout(null);

        // Initialize sub-panels
        addRoomPanel = new AddRoomPanel(canvasPanel);
        fixturesPanel = new Fixtures(canvasPanel);
        furniturePanel = new Furniture(canvasPanel);

        // Setup card layout for switching between room, furniture, and fixture panels
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.setBounds(10, 130, 380, 240);
        cards.add(addRoomPanel, "Add Room");
        cards.add(fixturesPanel, "Add Fixtures");
        cards.add(furniturePanel, "Add Furniture");

        // Create buttons
        addRoomButton = new myButton("ADD ROOM");
        addRoomButton.setBounds(20, 30, 150, 40);
        addRoomButton.addActionListener(this);

        addFurnitureButton = new myButton("ADD FURNITURE");
        addFurnitureButton.setBounds(180, 30, 180, 40);
        addFurnitureButton.addActionListener(this);

        addFixturesButton = new myButton("ADD FIXTURES");
        addFixturesButton.setBounds(100, 90, 180, 40);
        addFixturesButton.addActionListener(this);

        rotate = new JButton("Rotate");
        rotate.setBounds(120, 370, 100, 40);
        rotate.addActionListener(this);

        save = new JButton("Save");
        save.setBounds(10, 0, 110, 25);
        save.addActionListener(e -> canvasPanel.saveToFile());
        load = new JButton("Load");
        load.setBounds(130, 0, 110, 25);
        load.addActionListener(e -> canvasPanel.loadFromFile());
        clear = new JButton("Clear");
        clear.setBounds(250, 0, 110, 25);
        clear.addActionListener(e -> canvasPanel.clearCanvas());

        dim.setBounds(20, 380, 380, 100);

        // Add components to ControlPanel
        add(addRoomButton);
        add(addFurnitureButton);
        add(addFixturesButton);
        add(cards);
        add(rotate);
        add(dim);
        add(save);
        add(load);
        add(clear);

        cards.setVisible(false);
        dim.setVisible(false);
        rotate.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cards.setVisible(true);
        if (e.getSource() == addRoomButton) {
            dim.setVisible(true);
            cardLayout.show(cards, "Add Room");
            rotate.setVisible(false);

        } else if (e.getSource() == addFurnitureButton) {
            dim.setVisible(false);
            cardLayout.show(cards, "Add Furniture");
            rotate.setVisible(true);

        } else if (e.getSource() == addFixturesButton) {
            dim.setVisible(false);
            cardLayout.show(cards, "Add Fixtures");
            rotate.setVisible(true);

        } else if (e.getSource() == rotate) {
            if (e.getSource() == rotate) {
                obj selectedObject = CanvasPanel.getSelectedObj(); // Get the selected object

                if (selectedObject != null) {
                    selectedObject.rotate();
                    selectedObject.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "No object selected!", "Rotation Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}
