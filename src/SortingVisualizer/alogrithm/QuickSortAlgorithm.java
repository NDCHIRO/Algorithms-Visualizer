package SortingVisualizer.alogrithm;

import SortingVisualizer.ui.SortListener;

public class QuickSortAlgorithm implements SortAlgorithm {

    @Override
    public void sort(int[] array, SortListener listener, int delay) throws InterruptedException {
        quickSort(array, 0, array.length - 1, listener, delay);
    }

    private void quickSort(int[] array, int low, int high, SortListener listener, int delay) throws InterruptedException {
        if (low < high) {
            // Partition the array and get the pivot index
            int pi = partition(array, low, high, listener, delay);

            // Recursively sort the elements before and after partition
            quickSort(array, low, pi - 1, listener, delay);
            quickSort(array, pi + 1, high, listener, delay);
        }
    }

    private int partition(int[] array, int low, int high, SortListener listener, int delay) throws InterruptedException {
        int pivot = array[high];  // Pivot element
        int i = (low - 1);  // Index of the smaller element

        for (int j = low; j < high; j++) {
            listener.onCompare(j, high);  // Notify listener of comparison

            // If current element is smaller than or equal to pivot
            if (array[j] <= pivot) {
                i++;

                // Swap array[i] and array[j]
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;

                listener.onSwap(i, j);  // Notify listener about the swap
                Thread.sleep(delay);  // Add delay to visualize the swap
            }
        }

        // Swap array[i+1] and array[high] (or pivot)
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        listener.onSwap(i + 1, high);  // Notify listener about the swap
        Thread.sleep(delay);  // Add delay to visualize the pivot placement

        return i + 1;  // Return the partitioning index
    }
}
