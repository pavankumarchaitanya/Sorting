public class MergeSorter {
	public void Sort(int[] a, int low, int high) {
		int mid = (low + high) / 2;

		if (low < mid) {
			Sort(a, low, mid);
		}

		if (mid < high) {
			Sort(a, mid + 1, high);
		}

		merge(a, low, mid, high);

	}

	private static void merge(int[] a, int low, int mid, int high) {

		if (high - low <= 1) {
			if (a[high] < a[low]) {
				int temp = a[high];
				a[high] = a[low];
				a[low] = temp;
			}
			return;
		} else {

			int[] temp = new int[high - low + 1];
			int firstIndex = low, secondIndex = mid + 1;
			for (int i = 0; i < temp.length; i++) {
				if (a[firstIndex] <= a[secondIndex]) {
					temp[i] = a[firstIndex];

					if (firstIndex < mid) {
						firstIndex++;
					} else if (firstIndex == mid) {

						for (int k = secondIndex; k <= high; k++) {
							i++;
							temp[i] = a[k];

						}

					}
				} else if ((a[secondIndex] < a[firstIndex])) {
					temp[i] = a[secondIndex];
					if (secondIndex < high) {
						secondIndex++;
					} else if (secondIndex == high) {

						for (int k = firstIndex; k <= mid; k++) {
							i++;
							temp[i] = a[k];

						}

					}
				}
			}
			for (int i = 0; i < temp.length; i++) {
				a[low + i] = temp[i];
			}

		}

	}

	public static void main(String args[]) {
		int arr[] = { 1, 2, 7, 9 };// ,3,4,5,8,9,12,43,46,76,87};

		int low = 0, mid = 1, high = arr.length - 1;

		merge(arr, low, mid, high);

		for (int a : arr)
			System.out.println(a);

	}
}
