package leetcode.editor.cn;

import java.util.List;

public class QuickSort {

    public static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1, 2);
    }

    //    private static void quickSort(int[] array, int low, int high) {
//        if (low < high) {
//            int pivotIndex = partition(array, low, high);
//            quickSort(array, low, pivotIndex - 1);
//            quickSort(array, pivotIndex + 1, high);
//        }
//    }
    private static int quickSort(int[] array, int low, int high, int k) {
        int pivotIndex = partition(array, low, high);
        if (pivotIndex == k) {
            return array[pivotIndex];
        }
        if (pivotIndex > k) {
            return quickSort(array, low, pivotIndex - 1, k);
        }
        return quickSort(array, pivotIndex + 1, high, k);
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low;
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                swap(array, i, j);
                i++;
            }
        }
        swap(array, i, high);
        return i;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        int[] array = {1};
//        quickSort(array);
        int i = quickSort(array, 0, array.length - 1, 0);
        System.out.println(i);
        double l = (1 + 2) / 2.0;
        System.out.println(l);
//        System.out.println("Sorted array:");
//        for (int num : array) {
//            System.out.print(num + " ");
//        }
    }
}
