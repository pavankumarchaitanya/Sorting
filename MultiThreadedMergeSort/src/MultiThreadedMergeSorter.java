import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedMergeSorter implements Runnable {

	int[] arrayToSort = null;
	int low, high;
	CountDownLatch latch;

	final static ExecutorService pool = Executors
			.newFixedThreadPool(Integer.MAX_VALUE);
	final static ArrayList<CountDownLatch> latchList = new ArrayList<CountDownLatch>();

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	public static ExecutorService getPool() {
		return pool;
	}

	public int[] getArrayToSort() {
		return arrayToSort;
	}

	public void setArrayToSort(int[] arrayToSort) {
		this.arrayToSort = arrayToSort;
	}

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public void Sort(int[] a, final int low, final int high)
			throws InterruptedException, BrokenBarrierException {

		final int mid = (low + high) / 2;

		if (low == high) {
			if (latch != null) {
				while (latch.getCount() != 0)
					latch.countDown();
			}
			return;
		}

		if (high - low <= 1) {
			merge(a, low, mid, high);
			while (latch.getCount() != 0)
				latch.countDown();

			return;
		}

		CountDownLatch countdownlatch1 = new CountDownLatch(1);
		CountDownLatch countdownlatch2 = new CountDownLatch(1);
		if (low < mid) {

			MultiThreadedMergeSorter m = new MultiThreadedMergeSorter();
			m.setArrayToSort(a);
			m.setLow(low);
			m.setHigh(mid);
			m.setLatch(countdownlatch1);
			pool.execute(m); // call sort in a different thread
		}

		if (mid < high) {
			MultiThreadedMergeSorter m = new MultiThreadedMergeSorter();
			m.setArrayToSort(a);
			m.setLow(mid + 1);
			m.setHigh(high);
			m.setLatch(countdownlatch2);
			pool.execute(m); // call sort in a different thread
		}

		if (countdownlatch1.getCount() != 0)
			countdownlatch1.await();

		if (countdownlatch2.getCount() != 0)
			countdownlatch2.await();
		merge(a, low, mid, high);

		if (latch != null) {
			latch.countDown();

		}
	}

	private void merge(int[] a, int low, int mid, int high) {

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

		// merge(arr, low, mid, high);

		for (int a : arr)
			System.out.println(a);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (arrayToSort != null) {
			try {
				Sort(arrayToSort, low, high);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void shutdown() {
		pool.shutdown();

	}
}
