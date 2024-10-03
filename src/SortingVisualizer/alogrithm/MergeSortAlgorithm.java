package SortingVisualizer.alogrithm;

import SortingVisualizer.ui.SortListener;

public class MergeSortAlgorithm implements SortAlgorithm {

    @Override
    public void sort(int[] array, SortListener listener, int delay) throws InterruptedException {
        mergeSort(array, 0, array.length - 1, listener, delay);
    }

    private void mergeSort(int[] array, int left, int right, SortListener listener, int delay) throws InterruptedException {
        if (left < right) {
            int mid = (left + right) / 2;

            // Recursively split and sort the array
            mergeSort(array, left, mid, listener, delay);
            mergeSort(array, mid + 1, right, listener, delay);

            // Merge the two sorted halves
            merge(array, left, mid, right, listener, delay);
        }
    }

    private void merge(int[] array, int left, int mid, int right, SortListener listener, int delay) throws InterruptedException {
        // Calculate sizes of two subarrays to merge
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Temporary arrays
        int[] L = new int[n1];
        int[] R = new int[n2];

        // Copy data to temporary arrays
        System.arraycopy(array, left, L, 0, n1);
        System.arraycopy(array, mid + 1, R, 0, n2);

        // Merge the two halves
        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            listener.onCompare(left + i, mid + 1 + j);  // Notify listener of comparison

            if (L[i] <= R[j]) {
                array[k] = L[i];
                listener.onSwap(k, left + i);  // Notify listener of placement
                i++;
            } else {
                array[k] = R[j];
                listener.onSwap(k, mid + 1 + j);  // Notify listener of placement
                j++;
            }
            k++;

            Thread.sleep(delay);  // Delay to visualize the merge step
        }

        // Copy remaining elements of L[]
        while (i < n1) {
            array[k] = L[i];
            listener.onSwap(k, left + i);
            i++;
            k++;
            Thread.sleep(delay);
        }

        // Copy remaining elements of R[]
        while (j < n2) {
            array[k] = R[j];
            listener.onSwap(k, mid + 1 + j);
            j++;
            k++;
            Thread.sleep(delay);
        }
    }
}

