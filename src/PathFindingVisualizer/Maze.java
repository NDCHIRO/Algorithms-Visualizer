package PathFindingVisualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.*;

public class Maze extends  JFrame{
    static final long serialVersionUID = 19670916;

    protected GridPanel gridPanel = null;

    public Maze() {

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        createGui();
        setVisible( true );
    }

    protected void createGui() {

        setSize( 600, 600 );
        setTitle( "Test Grid" );

        gridPanel = new GridPanel();

        add( gridPanel );
    }

    public static void main(String args[]) {

        Maze mf = new Maze();
    }
}


class GridPanel extends JPanel {
    void GridPanel ()
    {

    }
     GridPanel(int x)
    {

    }
    //private static final long serialVersionUID = -5341480790176820445L;

    private final int NUM_SQUARES = 100;
    private final int RECT_SIZE = 30;
    private ArrayList<Rectangle> grid = null;

    public GridPanel() {

        // Build the grid
        grid = new ArrayList<Rectangle>( NUM_SQUARES );
        for( int y=0; y < NUM_SQUARES / 10; ++y ) {
            for( int x=0; x < NUM_SQUARES / 10; ++x ) {
                Rectangle rect = new Rectangle( x * RECT_SIZE, y * RECT_SIZE, RECT_SIZE, RECT_SIZE );
                grid.add( rect );
            }
        }


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for (Shape s : grid) {
                    if (s.contains(e.getPoint())) {//check if mouse is clicked within shape

                        //we can either just print out the object class name
                        System.out.println("Clicked a " + s.getClass().getName());

                        //or check the shape class we are dealing with using instance of with nested if
                        if (s instanceof Rectangle2D) {
                            System.out.println("Clicked a rectangle");
                        } else if (s instanceof Ellipse2D) {
                            System.out.println("Clicked a circle");
                        }

                    }
                }
            }
        });
        setSize( 600, 400 );


    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent( g );

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 600, 400);

        
        //q.add(5);
        // paint the grid
        for( Rectangle r : grid ) {

            g.setColor(Color.BLACK);
            g.drawRect( r.x, r.y, r.width, r.height );
        }
        
    }
}
class grid extends GridPanel {
    grid() {
        System.out.println("sa");
        try {
            GridPanel gridPanel = new GridPanel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        int x = 0;
        int[] arr = {1,2,3,3,4,5,6,7,8,9,19};
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        hashMap.put(3,0);
        hashMap.put(5,0);
        hashMap.put(2,0);
        hashMap.put(7,0);
        hashMap.put(9,0);


    }
}
