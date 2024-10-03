import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

public class N_Queens {
    JFrame jFrame;
    //size of the board
    int size = 6;
    int board[][] = new int[size][size];
    JLabel jLabel[][] = new JLabel[size][size];

    //function for checking the current position if safe or no
    public boolean isSafe(int row,int col)
    {
        jLabel[row][col].setBackground(Color.YELLOW);
        makeDelay(200);
        //check the rows
        for(int i=0;i<col;i++)
            if(board[row][i]==1)
            {
                jLabel[row][col].setBackground(Color.WHITE);
                return false;
            }
       //check the current col
        for(int i=0;i<row;i++)
            if(board[i][col]==1)
            {
                jLabel[row][col].setBackground(Color.WHITE);
                return false;
            };
        //check the upper diagonal
        for(int i=row,j=col; i>=0 && j>=0;i--,j--)
            if(board[i][j]==1)
            {
                jLabel[row][col].setBackground(Color.WHITE);
                return false;
            }
        //check the lower diagonal
        for(int i=row,j=col; i<size && j>=0;i++,j--)
            if(board[i][j]==1)
            {
                jLabel[row][col].setBackground(Color.WHITE);
                return false;
            }

        return true;
    }

    public boolean NQutil(int col)
    {

        if(col >= size)
            return true;

        for (int i=0;i<size;i++)
        {
            //to slow the speed of the iterations
            makeDelay(200);

            if(isSafe(i,col))
            {
                //mark the cell
                jLabel[i][col].setBackground(Color.GREEN);
                jLabel[i][col].setText("Q");
                //just for SET FONT SIZE
                jLabel[i][col].setFont(new Font("Arial", Font.PLAIN, 80));
                board[i][col]=1;

                if(NQutil(col+1))
                    return true;
                //make it as red to indicate wrong position then reset color
                jLabel[i][col].setBackground(Color.RED);
                makeDelay(500);
                //reset the cell color and text
                jLabel[i][col].setBackground(Color.WHITE);
                jLabel[i][col].setText("");
                board[i][col]=0;
            }
        }
        return false;
    }

    //start and build the board
    public void solveNQeens() {
        makeDelay(500);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                makeDelay(20);
                board[i][j] = 0;
                jLabel[i][j].setBackground(Color.WHITE);

            }
        }

        if (NQutil(0) == false) {
            System.out.println("No Solution.\n");
            JOptionPane.showMessageDialog(jFrame, "No Solution",
                    "Solved?", JOptionPane.ERROR_MESSAGE);
        } else {
            printSolution();
            JOptionPane.showMessageDialog(jFrame, "solved",
                    "Solved?", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    void printSolution(){

        for(int i = 0; i < size; ++i){
            for(int j = 0; j < size; ++j){
                System.out.printf("%d ", board[i][j]);
            }
            System.out.printf("\n");
        }

    }
    //simple delay just for the GUI
    public void makeDelay(int delay)
    {
        try {
            Thread.sleep(delay);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    N_Queens()
    {
        jFrame = new JFrame("N Queens visualizer");
        jFrame.setLayout(new GridLayout(size,size));
        jFrame.setSize(800,800);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);


        for(int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                //"(" + i + "," + j + ")"
                jLabel[i][j] = new JLabel();
                jLabel[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                jLabel[i][j].setSize(50, 50);
                jLabel[i][j].setOpaque(true);
                jLabel[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                jFrame.add(jLabel[i][j]);
            }
        }

        jFrame.setVisible(true);

    }
    public void startSolve()
    {
        N_Queens n_queens = new N_Queens();
        n_queens.solveNQeens();
    }
    public static void main(String[] args) {
        N_Queens n_queens = new N_Queens();
        n_queens.solveNQeens();
    }

}

