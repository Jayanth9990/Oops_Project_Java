
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Door extends JPanel {

    private static final long serialVersionUID = 1L;
    private int ix = 0, fx = 0;
    private int iy = 0, fy = 0;
    private int doorAlignment;

    public int getDoorAlignment() {
        return doorAlignment;
    }

    Point prevPt;
    int width = 0;
    int height = 0;

    Door(Color rc) {
        this.setBackground(rc);
        this.setBounds(0, 0, 4, 20);
        ClickAndDragListener listener = new ClickAndDragListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
        width = 4;
        height = 20;
    }

    Door(Color rc, int direction) {
        this.doorAlignment = direction;
        this.setBackground(rc);
        this.setBounds(0, 0, 0, 0);
        initListeners();
    }

    public void initListeners() {
        ClickAndDragListener listener = new ClickAndDragListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    public void setall() {
        Container parent = getParent();
        Dimension parentsize = parent.getSize();
        switch (doorAlignment) {
            case 1:
                ix = 4;
                iy = 0;
                fy = 4;
                fx = parentsize.width - 4;
                width = 30;
                height = 4;
                break;
            case 2:
                ix = 4;
                iy = parentsize.height - 4;
                fx = parentsize.width - 4;
                fy = parentsize.height;
                width = 30;
                height = 4;
                break;
            case 3:
                ix = parentsize.width - 4;
                iy = 4;
                fx = parentsize.width;
                fy = parentsize.height - 4;
                width = 4;
                height = 30;
                break;
            case 4:
                ix = 0;
                iy = 4;
                fx = 4;
                fy = parentsize.height - 4;
                width = 4;
                height = 30;
                break;
        }
        this.setBounds(ix, iy, width, height);
    }

    public void setLocation1(int x, int y) {
        setBounds(x, y, width, height);
    }

    private class ClickAndDragListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            prevPt = e.getPoint();

            if (SwingUtilities.isRightMouseButton(e)) {
                showContextMenu(e);
                return;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            setBorder(BorderFactory.createEmptyBorder());
        }

        @Override
        public void mouseDragged(MouseEvent e) {

            Point currentPt = e.getPoint();
            Container parent = getParent();
            Point panelLocation = getLocation();
            int newX = panelLocation.x + (currentPt.x - prevPt.x);
            int newY = panelLocation.y + (currentPt.y - prevPt.y);
            Rectangle temp = new Rectangle();

            if (parent != null) {
                boolean collisionDetected = false;
                Dimension parentSize = parent.getSize();
                if (newX > parentSize.width - width || newY > parentSize.height - height) {
                    collisionDetected = true;
                }
                newX = Math.max(ix, Math.min(newX, fx - width));
                newY = Math.max(iy, Math.min(newY, fy - height));
                temp.height = height;
                temp.width = width;
                temp.x = newX;
                temp.y = newY;
                for (Component comp : parent.getComponents()) {
                    if (comp != Door.this && comp instanceof Door) {
                        Rectangle otherBounds = comp.getBounds();
                        if (temp.intersects(otherBounds)) {
                            collisionDetected = true;
                            break;
                        }
                    }
                }
                if (!collisionDetected) {
                    setLocation(newX, newY);
                }
            }
        }
    }

    private void showContextMenu(MouseEvent e) {
        JPopupMenu menu = new JPopupMenu();
        // Option 1: Delete Object
        JMenuItem deletedoor = new JMenuItem("Delete this door");
        deletedoor.addActionListener(event -> deletedoor());
        menu.add(deletedoor);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

    public boolean isMouseInRoom(MouseEvent e, Container targetRoom) {
        Point mousePoint = SwingUtilities.convertPoint(this, e.getPoint(), targetRoom);
        return targetRoom.contains(mousePoint);
    }

    private void deletedoor() {
        Container parent = getParent();
        if (parent != null) {
            parent.remove(Door.this);
            parent.repaint();
        }
    }
}
