
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class room extends JPanel implements Serializable {

    private static final long serialVersionUID = 1L;

    // Transient fields
    transient CanvasPanel panel;
    transient private Point initialClick;
    transient private boolean isSelected;
    transient private Border border;
    // static room selectedRoom;

    public List<Door> placeddoors = new ArrayList<>();
    public List<Window> placedwindows = new ArrayList<>();
    int roomtype;
    //CanvasPanel panel;
    public List<obj> placedObjs = new ArrayList<>();
    //private Point initialClick;
    private int originalX;
    private int originalY;
    private Color roomColor = Color.white;

    room() {
        addMouseListeners1();
    }

    room(int x, int y, int width, int height, int r, CanvasPanel panel) {
        this.roomtype = r;
        this.panel = panel;
        this.setLayout(null);
        this.setBounds(x, y, width, height);
        setRoomBackground(r);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 4);
        this.setBorder(border);
        originalX = x;
        originalY = y;

        addMouseListeners1();
    }

    private void setRoomBackground(int r) {
        switch (r) {
            case 1 ->
                this.setBackground(new Color(00, 200, 00));
            case 2 ->
                this.setBackground(new Color(0, 0, 200));
            case 3 ->
                this.setBackground(new Color(200, 0, 0));
            case 4 ->
                this.setBackground(new Color(250, 250, 0));
            case 5 ->
                this.setBackground(new Color(255, 153, 50));
        }
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        if (selected) {
            this.setBackground(new Color(173, 216, 230));
        } else {
            setRoomBackground(roomtype);

        }

    }

    public boolean isOverlap(obj furniture) {
        for (obj existingFurniture : placedObjs) {
            if (furniture == existingFurniture) {
                continue; // Skip self-check
            }
            if (furniture.getBounds().intersects(existingFurniture.getBounds())) {
                return true; // Overlap detected
            }
        }

        // Check if furniture is out of the room's bounds
        if (furniture.getX() + furniture.getWidth() > this.getWidth()
                || furniture.getY() + furniture.getHeight() > this.getHeight()) {
            return true;
        }
        return false;
    }

    public boolean changetovalidpos(obj furniture) {
        int gap = 5; // Space between objects
        int x = 0, y = 0;

        // Try to find a valid position in row-major order
        while (isOverlap(furniture)) {
            x += gap;
            furniture.setLocation(x, y);

            if (x + furniture.getWidth() > this.getWidth()) {
                x = 0; // Move to the next row
                y += furniture.getHeight() + gap;
                furniture.setLocation(x, y);

            }

            // If we run out of vertical space, return false
            if (y + furniture.getHeight() > this.getHeight()) {
                return false;
            }
        }

        furniture.setLocation(x, y);
        // Set the valid position
        return true;
    }

    private void addDoor(int direction) {
        Door newDoor = new Door(roomColor, direction);
        int roomWidth = getWidth();
        int roomHeight = getHeight();
        int x = 0, y = 0;
        switch (direction) {
            case 1 -> {
                x = 4;
                y = 0;
            }
            case 2 -> {
                x = 4;
                y = roomHeight - 4;
            }
            case 3 -> {
                x = roomWidth - 4;
                y = 4;
            }
            case 4 -> {
                x = 0;
                y = 4;
            }
        }
        boolean hasOverlap;
        do {
            hasOverlap = false;
            for (Door placedDoor : placeddoors) {
                if (placedDoor.getDoorAlignment() == direction && placedDoor.getBounds().intersects(new Rectangle(x, y, 30, 30))) {
                    switch (direction) {
                        case 1, 2 ->
                            x = placedDoor.getX() + 30; // Move horizontally
                        case 3, 4 ->
                            y = placedDoor.getY() + 30; // Move vertically
                    }
                    hasOverlap = true;
                    break;
                }
            }
            for (Window placedWindow : placedwindows) {
                if (placedWindow.getwindowAlignment() == direction
                        && placedWindow.getBounds().intersects(new Rectangle(x, y, 20, 20))) {
                    switch (direction) {
                        case 1, 2 ->
                            x = placedWindow.getX() + 30;
                        case 3, 4 ->
                            y = placedWindow.getY() + 30;
                    }
                    hasOverlap = true;
                    break;
                }
            }
        } while (hasOverlap && (x < roomWidth || y < roomHeight)); // Ensure the door stays within bounds

        if ((direction == 1 || direction == 2) && x + 26 < roomWidth
                || (direction == 3 || direction == 4) && y + 26 < roomHeight) {
            add(newDoor);
            placeddoors.add(newDoor);
            newDoor.setall();
            newDoor.setLocation1(x, y);
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Cannot place the door. No space found");
        }
    }

    private void addWindow(int direction) {
        Window newWindow = new Window(roomColor, direction);
        int roomWidth = getWidth();
        int roomHeight = getHeight();
        int x = 0, y = 0;
        switch (direction) {
            case 1 -> {
                x = 4;
                y = 0;
            }
            case 2 -> {
                x = 4;
                y = roomHeight - 4;
            }
            case 3 -> {
                x = roomWidth - 4;
                y = 4;
            }
            case 4 -> {
                x = 0;
                y = 4;
            }
        }
        boolean hasOverlap;
        do {
            hasOverlap = false;
            for (Window placedWindow : placedwindows) {
                if (placedWindow.getwindowAlignment() == direction
                        && placedWindow.getBounds().intersects(new Rectangle(x, y, 20, 20))) {
                    switch (direction) {
                        case 1, 2 ->
                            x = placedWindow.getX() + 30;
                        case 3, 4 ->
                            y = placedWindow.getY() + 30;
                    }
                    hasOverlap = true;
                    break;
                }
            }
            for (Door placedDoor : placeddoors) {
                if (placedDoor.getDoorAlignment() == direction && placedDoor.getBounds().intersects(new Rectangle(x, y, 30, 30))) {
                    switch (direction) {
                        case 1, 2 ->
                            x = placedDoor.getX() + 30;
                        case 3, 4 ->
                            y = placedDoor.getY() + 30;
                    }
                    hasOverlap = true;
                    break;
                }
            }
        } while (hasOverlap && (x < roomWidth || y < roomHeight));

        if ((direction == 1 || direction == 2) && x + 16 < roomWidth
                || (direction == 3 || direction == 4) && y + 16 < roomHeight) {
            add(newWindow);
            placedwindows.add(newWindow);
            newWindow.setall();
            newWindow.setLocation(x, y);
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Cannot place the door. No space found");
        }
    }

    private void handleRightClick(MouseEvent e) {
        JPopupMenu contextMenu = new JPopupMenu();
        JMenu addDoorMenu = new JMenu("Add Door");
        JMenu addWindowMenu = new JMenu("Add Window");
        JMenuItem removeroom = new JMenuItem("Delete Room");
        JMenuItem addDoorNorth = new JMenuItem("North");
        JMenuItem addDoorSouth = new JMenuItem("South");
        JMenuItem addDoorEast = new JMenuItem("East");
        JMenuItem addDoorWest = new JMenuItem("West");

        JMenuItem addWindowNorth = new JMenuItem("North");
        JMenuItem addWindowSouth = new JMenuItem("South");
        JMenuItem addWindowEast = new JMenuItem("East");
        JMenuItem addWindowWest = new JMenuItem("West");

        removeroom.addActionListener(ev -> removeRoom(this));
        addDoorNorth.addActionListener(ev -> addDoor(1));
        addDoorSouth.addActionListener(ev -> addDoor(2));
        addDoorEast.addActionListener(ev -> addDoor(3));
        addDoorWest.addActionListener(ev -> addDoor(4));

        addWindowNorth.addActionListener(ev -> addWindow(1));
        addWindowSouth.addActionListener(ev -> addWindow(2));
        addWindowEast.addActionListener(ev -> addWindow(3));
        addWindowWest.addActionListener(ev -> addWindow(4));

        addDoorMenu.add(addDoorNorth);
        addDoorMenu.add(addDoorSouth);
        addDoorMenu.add(addDoorEast);
        addDoorMenu.add(addDoorWest);

        addWindowMenu.add(addWindowNorth);
        addWindowMenu.add(addWindowSouth);
        addWindowMenu.add(addWindowEast);
        addWindowMenu.add(addWindowWest);

        contextMenu.add(addDoorMenu);
        contextMenu.add(addWindowMenu);
        contextMenu.add(removeroom);
        contextMenu.show(this, e.getX(), e.getY());
    }

    public void removeRoom(room room1) {
        if (CanvasPanel.selectedroom == room.this) {
            room1.setSelected(false);
            CanvasPanel.selectedroom = null;
        }
        CanvasPanel.placedrooms.remove(room1);
        panel.remove(room1);
        panel.repaint();
        room1.setSelected(false);
    }

    public void addMouseListeners1() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Record the original position before dragging
                originalX = getLocation().x;
                originalY = getLocation().y;
                // Get the initial click position
                initialClick = e.getPoint();

                if (SwingUtilities.isRightMouseButton(e)) {
                    handleRightClick(e);
                    return;
                }
                CanvasPanel panel = (CanvasPanel)room.this.getParent();
                panel.toggleRoomSelection(room.this);

            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point currentPt = e.getPoint();
                Container parent = getParent();
                Point panelLocation = getLocation();
                int newX = panelLocation.x + (currentPt.x - initialClick.x);
                int newY = panelLocation.y + (currentPt.y - initialClick.y);
                Dimension parentSize = parent.getSize();
                newX = Math.max(0, Math.min(newX, parentSize.width - getWidth()));
                newY = Math.max(0, Math.min(newY, parentSize.height - getHeight()));
                setLocation(newX, newY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //Container c = (Container) getParent();
                if (CanvasPanel.isOverlap1(room.this)) {
                    JOptionPane.showMessageDialog(null, "Cannot be added here. Getting back to previous position!", "Overlapping", JOptionPane.WARNING_MESSAGE);
                    setLocation(originalX, originalY);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        };

        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }
}
