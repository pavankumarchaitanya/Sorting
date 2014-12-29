import java.util.Arrays;

public class MainClass {

	public static void main(String[] args) throws Exception {
		int arraySize = 1000000;
		int arr[] = new int[arraySize];

		for (int i = 0; i < arraySize; i++) {
			arr[i] = (int) (Math.random() * arraySize);
		}
		int copyArray[] = Arrays.copyOf(arr, arr.length);

		for (int i = 0; i < arraySize; i++) {
			if (copyArray[i] != arr[i])
				throw new Exception("Array not copied correctly");
		}

		long initTime = System.currentTimeMillis();
		MergeSorter mergeSorter = new MergeSorter();
		mergeSorter.Sort(arr, 0, arr.length - 1);
		long endTime = System.currentTimeMillis();

		System.out.println("MergeSorter took : " + (endTime - initTime)
				+ " milli seconds");

		for (int a : arr) {
			// System.out.println(a);
		}
		initTime = System.currentTimeMillis();
		Arrays.sort(copyArray);
		endTime = System.currentTimeMillis();
		System.out.println("Arrays sort took : " + (endTime - initTime)
				+ " milli seconds");
		for (int i = 0; i < arraySize; i++) {
			if (copyArray[i] != arr[i])
				throw new Exception("Array not sorted correctly");

			// System.out.println(arr[i]);
		}

		System.out.println("done");
	}

}
