
import java.awt.Color;
import javax.swing.JFrame;

public class App extends JFrame {

    ControlPanel cop;
    CanvasPanel cap = new CanvasPanel();
    //Furniture myfurnituremenu = new Furniture(cap);
    //Fixtures myfixturemenu = new Fixtures(cap);
    //AddRoomPanel ar = new AddRoomPanel(cap);

    App() {
        this.setTitle("                                                                                                                                                                                      2D FLOOR PLANNER");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.getContentPane().setBackground(Color.CYAN);
        //this.setResizable(false);
        this.setLayout(null);
        cop = new ControlPanel(cap);
        this.add(cop);
        this.add(cap);
        this.revalidate();
        this.repaint();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        App app = new App();
        int x = app.getWidth();
        int y = app.getHeight();
        //System.out.println(y);
        app.cop.setBounds(0, 0, x / 4, y);
        app.cap.setBounds(x / 4, 0, 3 * x / 4, y);
        app.setVisible(true);
    }
}
