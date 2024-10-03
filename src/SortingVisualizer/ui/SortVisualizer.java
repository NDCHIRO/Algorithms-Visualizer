package SortingVisualizer.ui;

import SortingVisualizer.alogrithm.InsertionSortAlgorithm;
import SortingVisualizer.alogrithm.MergeSortAlgorithm;
import SortingVisualizer.alogrithm.QuickSortAlgorithm;
import SortingVisualizer.alogrithm.SortAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SortVisualizer extends JPanel implements SortListener {

    private int[] array;
    private int arraySize = 50;
    private final int barWidth = 10;
    private int delay = 50; // Default sorting speed in milliseconds

    // Variables to track compared and swapped indices for UI
    private int currentCompareIndex = -1;
    private int currentSwapIndex = -1;

    private SortAlgorithm algorithm;  // General algorithm

    public SortVisualizer(SortAlgorithm algorithm) {
        this.algorithm = algorithm;  // Algorithm can be any sorting algorithm
        array = new int[arraySize];
        generateArray();
    }

    // Method to generate random array
    public void generateArray() {
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = rand.nextInt(300); // random heights for the bars
        }
        repaint();
    }

    // Method to set the sorting speed from the slider
    public void setSortingSpeed(int speed) {
        this.delay = speed;
    }

    // Method to start the sorting process in a new thread
    public void startSorting() {
        new Thread(() -> {
            try {
                algorithm.sort(array, this, delay);  // Call the sort method of the selected algorithm
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Overridden methods from the SortListener to update UI
    @Override
    public void onCompare(int index1, int index2) {
        currentCompareIndex = index1;
        repaint();
    }

    @Override
    public void onSwap(int index1, int index2) {
        currentSwapIndex = index2;
        repaint();
    }

    @Override
    public void onSortComplete() {
        currentCompareIndex = -1;
        currentSwapIndex = -1;
        repaint();
    }

    // Override the paintComponent to draw bars
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < array.length; i++) {
            if (i == currentCompareIndex) {
                g.setColor(Color.RED); // Bar being compared
            } else if (i == currentSwapIndex) {
                g.setColor(Color.GREEN); // Bar being swapped
            } else {
                g.setColor(Color.BLACK); // Normal bar
            }
            g.fillRect(i * barWidth, getHeight() - array[i], barWidth, array[i]);
        }
    }

    // Main method to create and run the visualizer
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sort Visualizer");

        // Change the algorithm here to any implemented sorting algorithm
        SortVisualizer visualizer = new SortVisualizer(new QuickSortAlgorithm());

        // Create a slider to control sorting speed
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 1, 200, 50);
        speedSlider.setMajorTickSpacing(50);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener(e -> visualizer.setSortingSpeed(speedSlider.getValue()));

        // Add the panel and slider to the frame
        frame.setLayout(new BorderLayout());
        frame.add(visualizer, BorderLayout.CENTER);
        frame.add(speedSlider, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(visualizer.arraySize * visualizer.barWidth, 400);
        frame.setVisible(true);

        // Start sorting in a new thread
        visualizer.startSorting();
    }
}

