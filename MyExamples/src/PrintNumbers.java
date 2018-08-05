import java.util.concurrent.locks.ReentrantLock;

public class PrintNumbers extends Thread {
	volatile static int i = 1;
	ReentrantLock lock;

	PrintNumbers(ReentrantLock lock) {
		this.lock = lock;
	}

	public static void main(String args[]) {
		ReentrantLock obj = new ReentrantLock();
		// This constructor is required for the identification of wait/notify
		// communication
		PrintNumbers odd = new PrintNumbers(obj);
		PrintNumbers even = new PrintNumbers(obj);
		odd.setName("Odd");
		even.setName("Even");
		odd.start();
		even.start();
	}

	@Override
	public void run() {
		while (i <= 10) {
			if (i % 2 == 0 && Thread.currentThread().getName().equals("Even")) {
				boolean flag = lock.tryLock();
				if (flag) {
					System.out.println(Thread.currentThread().getName() + " - "
							+ i);
					i++;
					lock.unlock();
				}
			}
			if (i % 2 == 1 && Thread.currentThread().getName().equals("Odd")) {
				boolean flag = lock.tryLock();
				if (flag) {
					System.out.println(Thread.currentThread().getName() + " - "
							+ i);
					i++;
					lock.unlock();
				}
			}
		}
	}
}