import javax.swing.*;

public class Main {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(1800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel panel = new Panel();
        frame.add(panel);
        frame.setVisible(true);
        panel.run();
    }
}
