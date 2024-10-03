package SortingVisualizer.ui;

public interface SortListener {
    void onCompare(int index1, int index2);
    void onSwap(int index1, int index2);
    void onSortComplete();
}
