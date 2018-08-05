import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

	public static void main(String args[]) {
		final ReentrantLock lockA = new ReentrantLock();
		final ReentrantLock lockB = new ReentrantLock();
		new Thread() {
			public void run() {
				while (1 == 1) {
					try {
						System.out.println(this + " acquiring lockA");
						if (lockA.tryLock()) {
							System.out.println(this + " acquired lockA");
							System.out.println(this + " acquiring lockB");
							if (lockB.tryLock()) {
								System.out.println(this + " acquired lockB");
							}
						}
					} finally {
						if (lockB.isHeldByCurrentThread())
							lockB.unlock();
						if (lockA.isHeldByCurrentThread())
							lockA.unlock();
					}
				}
			}
		}.start();

		new Thread() {
			public void run() {
				while (1 == 1) {
					try {
						System.out.println(this + " acquiring lockB");
						if (lockB.tryLock()){
						System.out.println(this + " acquired lockB");
						System.out.println(this + " acquiring lockA");
						if (lockA.tryLock()) {
							System.out.println(this + " acquired lockA");
						}
					}
					} finally {
						if (lockA.isHeldByCurrentThread())
							lockA.unlock();
						if (lockB.isHeldByCurrentThread())
							lockB.unlock();

					}
				}
			}
		}.start();
	}

}
