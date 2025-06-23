
import java.awt.Color;
import java.awt.LayoutManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class CanvasPanel extends JPanel {

    public CanvasPanel() {
        setBackground(Color.lightGray);
        setLayout(null);
    }

    public void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showSaveDialog(this);

        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(CanvasPanel.placedrooms);
                JOptionPane.showMessageDialog(this, "Saved successful!", "Save", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showOpenDialog(this);

        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                this.removeAll();
                placedrooms = (List<room>) ois.readObject();
                for (room r : placedrooms) {
                    this.add(r);
                    r.addMouseListeners1();
                    if (r.placeddoors != null) {
                        for (Door d : r.placeddoors) {
                            r.add(d);
                            d.initListeners(); // Restoring object functionality
                        }
                    }
                    if (r.placedwindows != null) {
                        for (Window d : r.placedwindows) {
                            r.add(d);
                            d.initListeners();
                        }
                    }
                    if (r.placedObjs != null) {
                        for (obj o : r.placedObjs) {
                            r.add(o);
                            o.initListeners();
                            o.restoreimage();
                        }
                    }
                }
                repaint();
                revalidate();
                JOptionPane.showMessageDialog(this, "Loaded successful!y", "Load", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void clearCanvas() {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear the canvas?", "Clear Canvas", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            selectedroom = null;
            placedrooms.clear();
            this.removeAll();
            repaint();
            revalidate();
        }
    }

    public static List<room> placedrooms = new ArrayList<>();
    public static room selectedroom;
    private static obj selectedObj;

    private Border selectedBorder = BorderFactory.createLineBorder(Color.RED, 5);

    public void toggleRoomSelection(room clickedRoom) {
        if (selectedroom == clickedRoom) {
            // Deselect if clicking the already selected room
            selectedroom.setSelected(false);
            selectedroom = null; // Clear selection
        } else {
            // Deselect previous room, if any
            if (selectedroom != null) {
                selectedroom.setSelected(false);
            }
            // Select new room
            selectedroom = clickedRoom;
            selectedroom.setSelected(true);
        }
        repaint();
    }

    public room getSelectedRoom() {
        return selectedroom;
    }

    public static obj getSelectedObj() {
        return selectedObj;
    }

    public static void setSelectedObj(obj selected) {
        if (selectedObj != null) {
            selectedObj.setSelected(false); // Deselect the previously selected object
        }
        selectedObj = selected;
        if (selectedObj != null) {
            selectedObj.setSelected(true); // Highlight the newly selected object
        }
    }

    @Override
    public void setLayout(LayoutManager mgr) {
        super.setLayout(null);
    }

    public boolean changetovalidpos(room r) {

        int yPosition = 0;
        int x = 0;
        while (isOverlap(r)) {
            x += 5;
            r.setLocation(x, yPosition);
            if (x + r.getWidth() > this.getWidth()) {
                x = 0;
                yPosition += 5;
                r.setLocation(x, yPosition);
            }
            if (yPosition > getHeight()) {
                return false;
            }
            if (r.getWidth() > this.getWidth()) {
                return false;
            }
        }
        x += 5;
        yPosition += 5;
        r.setLocation(x, yPosition);
        return true;
    }

    public boolean isOverlap(room r) {
        for (room p : placedrooms) {
            if (p == r) {
                continue;
            }
            if (r.getBounds().intersects(p.getBounds())) {
                return true;
            }
        }
        if (r.getX() + r.getWidth() > this.getWidth() || r.getY() + r.getHeight() > this.getHeight() - 30 || r.getX() < 0 || r.getY() < 0) {
            return true;
        }
        return false;
    }

    public static boolean isOverlap1(room r) {
        for (room p : placedrooms) {
            if (p == r) {
                continue;
            }
            if (r.getBounds().intersects(p.getBounds())) {
                return true;
            }
        }
        // if (r.getX()+r.getWidth() > this.getWidth() || r.getY() + r.getHeight() > this.getHeight()-30 || r.getX()<0||r.getY()<0) {
        //     return true;
        // }
        return false;
    }

    public void normaladdroom(int n) {

        room myroom = new room(0, 0, dimensions.reqwidth, dimensions.reqheight, n, this); // Start at (0, 0)
        // Find a valid position for the new room

        if (changetovalidpos(myroom)) {
            placedrooms.add(myroom);
            add(myroom);
            repaint();
            revalidate();
        } else {
            JOptionPane.showMessageDialog(null, "NO valid Position Found for Room to be Added", "    Error", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void addroomRelativeToSelectedRoom(int n, room selectedRoom, String direction, String alignment) {
        if (direction == null || alignment == null) {
            JOptionPane.showMessageDialog(this, "Select something", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int newX = selectedRoom.getX();
        int newY = selectedRoom.getY();
        int gap = 0;
        switch (direction) {
            case "North":
                newY = newY - dimensions.reqheight - gap;
                break;
            case "South":
                newY = newY + selectedRoom.getHeight() + gap;
                break;
            case "East":
                newX = newX + selectedRoom.getWidth() + gap;
                break;
            case "West":
                newX = newX - dimensions.reqwidth - gap;
                break;
            default:
                return;
        }

        // Adjust based on alignment
        if (direction == "North" || direction == "South") {
            if (alignment.equals("Center")) {
                newX = newX - ((dimensions.reqwidth) / 2) + (selectedRoom.getWidth() / 2);
            } else if (alignment.equals("Right")) {
                newX = newX - dimensions.reqwidth + selectedRoom.getWidth();
            }
        } else {
            if (alignment.equals("Center")) {
                newY = newY - ((dimensions.reqheight) / 2) + (selectedRoom.getHeight() / 2);
            } else if (alignment.equals("Bottom")) {
                newY = newY - dimensions.reqheight + selectedRoom.getHeight();
            }
        }
        room newRoom = new room(newX, newY, dimensions.reqwidth, dimensions.reqheight, n, this);
        if (!isOverlap(newRoom)) {
            placedrooms.add(newRoom);
            add(newRoom);
            repaint();
            revalidate();
        } else {
            JOptionPane.showMessageDialog(this, "Position overlaps with another room or is out of bounds.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
