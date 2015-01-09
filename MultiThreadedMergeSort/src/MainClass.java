import java.util.Arrays;

public class MainClass {

	public static void main(String[] args) throws Exception {
		int arraySize =1000;
		int arr[] = new int[arraySize];

		for (int i = 0; i < arraySize; i++) {
			arr[i] = (int) (Math.random() * arraySize);
		}
		int copyArray[] = Arrays.copyOf(arr, arr.length);
		
		int copyArrayForMultiThreadedSort[] = Arrays.copyOf(arr, arr.length);

		for (int i = 0; i < arraySize; i++) {
			if (copyArray[i] != arr[i])
				throw new Exception("Array not copied correctly");
		}
		
		for (int a : arr) {
			 System.out.print(a + " ");
		}
		
		System.out.println();
		long initTime = System.currentTimeMillis();
		MultiThreadedMergeSorter multiThreadedMergeSorter = new MultiThreadedMergeSorter();
		multiThreadedMergeSorter.Sort(copyArrayForMultiThreadedSort, 0, arr.length - 1);
		multiThreadedMergeSorter.shutdown();
		long endTime = System.currentTimeMillis();
		System.out.println("MultiThreadedMergeSorter took : " + (endTime - initTime)
				+ " milli seconds");
		
		
		initTime = System.currentTimeMillis();
		Arrays.sort(copyArray);
		endTime = System.currentTimeMillis();
		System.out.println("Arrays sort took : " + (endTime - initTime)
				+ " milli seconds");
		
		for (int i = 0; i < arraySize; i++) {
			if (copyArrayForMultiThreadedSort[i] != copyArray[i])
				throw new Exception("Array not sorted correctly");

			// System.out.println(arr[i]);
		}
		System.out.println("done");
	}

}
