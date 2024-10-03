package SortingVisualizer.alogrithm;

import SortingVisualizer.ui.SortListener;

public class BubbleSortAlgorithm implements SortAlgorithm {

    @Override
    public void sort(int[] array, SortListener listener, int delay) throws InterruptedException {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                listener.onCompare(j, j + 1);
                if (array[j] > array[j + 1]) {
                    // Swap the elements
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    listener.onSwap(j, j + 1);  // Notify listener about the swap
                }
                Thread.sleep(delay);  // Pause for the given delay
            }
        }
        listener.onSortComplete();  // Notify when sorting is complete
    }
}
