
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.*;

public class obj extends JPanel implements Serializable {

    private static final long serialVersionUID = 1L;

    private JLabel label;
    private Point initialClick;
    private int originalX, originalY;
    private int lastValidX, lastValidY;
    private int rotationAngle = 0;
    private boolean isSelected = false;
    private transient BufferedImage image;
    String imagepath;

    public obj(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);

        this.imagepath = imagePath;
        loadImage();
        setBounds(0, 0, 50, 50);
        setOpaque(false);
        setLayout(null);
        // Store the last valid position
        lastValidX = originalX = getX();
        lastValidY = originalY = getY();

        addMouseListeners();
    }

    private void loadImage() {
        try {
            this.image = ImageIO.read(new File(imagepath)); // Load the image as BufferedImage
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject(); // Serialize non-transient fields
        if (image != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos); // Write image to byte array
            oos.writeObject(baos.toByteArray()); // Save image bytes
        } else {
            oos.writeObject(null); // Handle missing image
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // Deserialize non-transient fields
        byte[] imageBytes = (byte[]) ois.readObject(); // Read image bytes
        if (imageBytes != null) {
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            this.image = ImageIO.read(bais); // Restore image from bytes
        }
    }

    public void restoreimage() {
        loadImage();
    }

    public void initListeners() {
        ClickAndDragListener listener = new ClickAndDragListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    private class ClickAndDragListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            initialClick = e.getPoint();
            originalX = getX();
            originalY = getY();
            lastValidX = originalX; // Set the initial position as the last valid position
            lastValidY = originalY;
            if (CanvasPanel.getSelectedObj() == obj.this) {
                // If this object is already selected, deselect it
                setSelected(false);
                CanvasPanel.setSelectedObj(null);
            } else {
                // If another object is selected, deselect it first
                obj previouslySelected = CanvasPanel.getSelectedObj();
                if (previouslySelected != null) {
                    previouslySelected.setSelected(false);
                }

                // Select this object
                setSelected(true);
                CanvasPanel.setSelectedObj(obj.this);
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point currentPt = e.getPoint();
            int newX = getX() + currentPt.x - initialClick.x;
            int newY = getY() + currentPt.y - initialClick.y;

            // Ensure furniture stays within room bounds
            JPanel parentRoom = (JPanel) getParent();
            if (parentRoom != null) {
                int maxX = parentRoom.getWidth() - getWidth();
                int maxY = parentRoom.getHeight() - getHeight();
                newX = Math.max(0, Math.min(newX, maxX));
                newY = Math.max(0, Math.min(newY, maxY));
            }

            // Update the furniture position temporarily
            setLocation(newX, newY);

            // Check for overlap
            if (isOverlap()) {
                // Revert back to the last valid position if overlap is detected
                setLocation(lastValidX, lastValidY);
            } else {
                // If no overlap, update the last valid position
                lastValidX = newX;
                lastValidY = newY;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // Final check for overlap after release
            if (isOverlap()) {
                // Revert back to the last valid position
                setLocation(lastValidX, lastValidY);
                JOptionPane.showMessageDialog(
                        null,
                        "Cannot place here due to overlap. Reverting to the last valid position!",
                        "Invalid Position",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }

    private void addMouseListeners() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                originalX = getX();
                originalY = getY();
                lastValidX = originalX; // Set the initial position as the last valid position
                lastValidY = originalY;
                if (CanvasPanel.getSelectedObj() == obj.this) {
                    // If this object is already selected, deselect it
                    setSelected(false);
                    CanvasPanel.setSelectedObj(null);
                } else {
                    // If another object is selected, deselect it first
                    obj previouslySelected = CanvasPanel.getSelectedObj();
                    if (previouslySelected != null) {
                        previouslySelected.setSelected(false);
                    }

                    // Select this object
                    setSelected(true);
                    CanvasPanel.setSelectedObj(obj.this);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point currentPt = e.getPoint();
                int newX = getX() + currentPt.x - initialClick.x;
                int newY = getY() + currentPt.y - initialClick.y;

                // Ensure furniture stays within room bounds
                JPanel parentRoom = (JPanel) getParent();
                if (parentRoom != null) {
                    int maxX = parentRoom.getWidth() - getWidth();
                    int maxY = parentRoom.getHeight() - getHeight();
                    newX = Math.max(0, Math.min(newX, maxX));
                    newY = Math.max(0, Math.min(newY, maxY));
                }

                // Update the furniture position temporarily
                setLocation(newX, newY);

                // Check for overlap
                if (isOverlap()) {
                    // Revert back to the last valid position if overlap is detected
                    setLocation(lastValidX, lastValidY);
                } else {
                    // If no overlap, update the last valid position
                    lastValidX = newX;
                    lastValidY = newY;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Final check for overlap after release
                if (isOverlap()) {
                    // Revert back to the last valid position
                    setLocation(lastValidX, lastValidY);
                    JOptionPane.showMessageDialog(
                            null,
                            "Cannot place here due to overlap. Reverting to the last valid position!",
                            "Invalid Position",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    private boolean isOverlap() {
        JPanel parentRoom = (JPanel) getParent();
        if (parentRoom != null && parentRoom instanceof room) {
            room r = (room) parentRoom;
            return r.isOverlap(this);
        }
        return false;
    }

    // Rotate the furniture by 90 degrees
    public void rotate() {
        rotationAngle = (rotationAngle + 90) % 360;
        if (rotationAngle == 90 || rotationAngle == 270) {
            int temp = getWidth();
            setSize(getHeight(), temp);
        } else {
            int temp = getHeight();
            setSize(temp, getWidth());
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();

            // Calculate the center of the object
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            // Rotate the graphics context
            g2d.rotate(Math.toRadians(rotationAngle), centerX, centerY);

            // Draw the image centered in the panel
            int drawX = (getWidth() - 50) / 2;
            int drawY = (getHeight() - 50) / 2;
            g2d.drawImage(image, drawX, drawY, 50, 50, this);

            g2d.dispose();
        }
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        if (selected) {
            // Highlight the object when selected
            setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        } else {
            setBorder(null);
        }
        repaint();
    }
}
