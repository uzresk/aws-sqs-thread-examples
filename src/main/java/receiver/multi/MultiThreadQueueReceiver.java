package receiver.multi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
*
* 複数スレッド内でQueue内のMessageを取得するサンプル
*
* @author uzresk
*
*/
public class MultiThreadQueueReceiver {

	public static void main(String[] args) {

		long start = System.currentTimeMillis();

		ExecutorService executor = Executors.newFixedThreadPool(5);
		for (int i = 1; i <= 5; i++) {
			Runnable worker = new Worker("" + i);
			executor.execute(worker);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		System.out.println("Finished all threads");

		long end = System.currentTimeMillis();
		System.out.println((end - start) + "ms");
	}
}
