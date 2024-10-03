package SortingVisualizer.alogrithm;

import SortingVisualizer.ui.SortListener;

public interface SortAlgorithm {
    void sort(int[] array, SortListener listener, int delay) throws InterruptedException;
}

