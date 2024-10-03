import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    static JFrame jFrame;
    public static void main(String[] args) {
        jFrame = new JFrame("algorithms visualizer");
        jFrame.setLayout(new GridLayout(8,8));
        jFrame.setSize(800,800);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);

        JButton button = new JButton("N Q");
        button.setSize(100,100);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.setVisible(false); // Hide current frame
                N_Queens n_queens = new N_Queens();
                n_queens.startSolve();
                //n_queens.solveNQeens();

            }
        });
        jFrame.add(button);
    }
}