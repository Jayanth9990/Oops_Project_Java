
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class dimensions extends JPanel implements ActionListener {

    static int reqwidth = 150;
    static int reqheight = 150;
    myButton button;
    JTextField xdimensions;
    JTextField ydimensions;
    JLabel width;
    JLabel height;

    public dimensions() {
        setLayout(null); // Null layout to manually place components
        setBackground(Color.DARK_GRAY);

        //setLayout(new FlowLayout());
        width = new JLabel("Width: ");
        width.setForeground(Color.WHITE);
        width.setFont(new Font("SansSerif", Font.BOLD, 14));
        width.setBounds(10, 10, 100, 25);

        xdimensions = new JTextField(5);
        xdimensions.setBounds(100, 10, 60, 25);

        height = new JLabel("Height: ");
        height.setForeground(Color.WHITE);
        height.setFont(new Font("SansSerif", Font.BOLD, 14));
        height.setBounds(10, 50, 100, 25);

        ydimensions = new JTextField(5);
        ydimensions.setBounds(100, 50, 60, 25);

        button = new myButton("Submit");
        button.setBackground(new Color(64, 224, 208));
        button.setForeground(Color.BLACK);
        button.setBounds(180, 30, 100, 40);
        button.addActionListener(this);

        // Add components
        add(width);
        add(xdimensions);
        add(height);
        add(ydimensions);
        add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            try {
                reqwidth = Integer.parseInt(xdimensions.getText());
                reqheight = Integer.parseInt(ydimensions.getText());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid dimensions entered");
            }
        }
    }
}

// import java.awt.Color;
// import java.awt.Font;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import javax.swing.JButton;
// import javax.swing.JLabel;
// import javax.swing.JPanel;
// import javax.swing.JTextField;

// public class dimensions extends JPanel implements ActionListener {

//     static int reqwidth = 100;
//     static int reqheight = 100;
//     JButton button;
//     JTextField xdimensions;
//     JTextField ydimensions;
//     JLabel width;
//     JLabel height;

//     public dimensions() {
//         //setPreferredSize(new Dimension(250,50));
//         button = new JButton("Submit");
//         button.setBackground(new Color(64, 224, 208));
//         button.setForeground(Color.BLACK);
//         setLayout(null);
//         button.setBounds(10, 90, 100, 25);
//         button.addActionListener(this);

//         xdimensions = new JTextField();
//         ydimensions = new JTextField();
//         xdimensions.setBounds(120, 10, 60, 25);
//         ydimensions.setBounds(120, 50, 60, 25);

//         width = new JLabel("Width: ");
//         width.setForeground(Color.WHITE);
//         width.setFont(new Font("SansSerif", Font.BOLD, 20));
//         width.setBounds(10, 10, 100, 25);

//         height = new JLabel("Height: ");
//         height.setForeground(Color.WHITE);
//         height.setFont(new Font("SansSerif", Font.BOLD, 20));
//         height.setBounds(10, 50, 100, 25);
//         this.add(button);
//         this.add(xdimensions);
//         this.add(ydimensions);
//         this.add(width);
//         this.add(height);

//     }

//     @Override
//     public void actionPerformed(ActionEvent e) {
//         if (e.getSource() == button) {
//             reqwidth = Integer.parseInt(xdimensions.getText());
//             reqheight = Integer.parseInt(ydimensions.getText());
//         }
//     }
// }











//     public int getReqwidth() {
//         return reqwidth;
//     }

//     public void setReqwidth(int reqwidth) {
//         this.reqwidth = reqwidth;
//     }

//     public int getReqheight() {
//         return reqheight;
//     }

//     public void setReqheight(int reqheight) {
//         this.reqheight = reqheight;
//     }

// }
