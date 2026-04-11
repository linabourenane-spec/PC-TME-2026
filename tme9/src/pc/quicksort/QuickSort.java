package pc.quicksort;

import java.util.Random;

public class QuickSort {
	public static int partition(int[] array, int low, int high) {
		int pivot = array[low];
		int i = low - 1;
		int j = high + 1;
		while (true) {
			do {
				i++;
			} while (array[i] < pivot);

			do {
				j--;
			} while (array[j] > pivot);

			if (i >= j) {
				return j;
			}
			swap(array, i, j);
		}
	}

	public static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	public static void quickSort(int[] array, int low, int high) {
		if (low < high) {
			int pi = partition(array, low, high);
			quickSort(array, low, pi);
			quickSort(array, pi + 1, high);
		}
	}
	
	public static int[] generateRandomArray(int size) {
		Random rand = new Random();
		int[] result = new int[size];
		for (int i = 0; i < size; i++) {
			result[i] = rand.nextInt();
		}
		return result;
	}
}
