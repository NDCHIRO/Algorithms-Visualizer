package SortingVisualizer.alogrithm;

import SortingVisualizer.ui.SortListener;

public class InsertionSortAlgorithm implements SortAlgorithm {

    @Override
    public void sort(int[] array, SortListener listener, int delay) throws InterruptedException {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            // Compare the key with each element on its left until an element smaller than it is found
            while (j >= 0 && array[j] > key) {
                listener.onCompare(j, i);  // Notify listener about the comparison

                // Move the larger element one position to the right
                array[j + 1] = array[j];
                listener.onSwap(j + 1, j);  // Notify listener about the "swap"
                j = j - 1;

                // Adding delay to visualize each step
                Thread.sleep(delay);
            }

            // Place the key at its correct position in the sorted part of the array
            array[j + 1] = key;
            listener.onSwap(j + 1, i);  // Notify listener about the insertion

            Thread.sleep(delay);  // Adding delay to see the insertion
        }

        listener.onSortComplete();  // Notify listener when sorting is complete
    }
}

