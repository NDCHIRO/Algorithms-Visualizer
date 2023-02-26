import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Algorithms visualizer app");
        JButton jButton = new JButton();
        jButton.setText("N Queens visualizer");

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                N_Queens n_queens = new N_Queens();
                n_queens.solveNQeens();
            }
        });
        frame.add(jButton);
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        //new N_Queens();
    }
}