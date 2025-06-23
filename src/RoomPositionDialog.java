
import javax.swing.JOptionPane;

public class RoomPositionDialog {

    public static String[] getPositionDetails() {
        String[] directions = {"North", "East", "South", "West"};
        String[] alignments1 = {"Left", "Center", "Right"};
        String[] alignments2 = {"Top", "Center", "Bottom"};
        //String alignment = (String) JOptionPane.showInputDialog
        String direction = null;
        String alignment = null;
        direction = (String) JOptionPane.showInputDialog(
                null,
                "Select direction for the new room:",
                " Relative Room Position",
                JOptionPane.QUESTION_MESSAGE,
                null,
                directions,
                directions[0]
        );
        //String alignment = (String) JOptionPane.showInputDialog
        if (direction == "North" || direction == "South") {
            alignment = (String) JOptionPane.showInputDialog(
                    null,
                    "Select alignment for the new room:",
                    " Relative Room Alignment",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    alignments1,
                    alignments1[1]
            );
            return new String[]{direction, alignment};
        } else if (direction == "West" || direction == "East") {
            alignment = (String) JOptionPane.showInputDialog(
                    null,
                    "Select alignment for the new room:",
                    "Room Alignment",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    alignments2,
                    alignments2[1]
            );
        }

        return new String[]{direction, alignment};
    }
}
