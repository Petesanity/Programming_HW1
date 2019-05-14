import java.util.concurrent.Semaphore;

public class TeachingAssistant implements Runnable {

	// Semaphore used to wakeup TA.
	private SignalSemaphore wakeup;

	// Semaphore used to wait in chairs outside office.
	private Semaphore chairs;

	// Mutex lock (binary semaphore) used to determine if TA is available.
	private Semaphore available;

	// A reference to the current thread.
	private Thread thread;

	public TeachingAssistant(SignalSemaphore w, Semaphore c, Semaphore a) {
		thread = Thread.currentThread();
		wakeup = w;
		chairs = c;
		available = a;
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("No students left.  The TA is going to nap.");
				wakeup.release();
				System.out.println("The TA was awoke by a student.");

				thread.sleep(5000);

				// If there are other students waiting.
				if (chairs.availablePermits() != 3) {
					do {
						thread.sleep(5000);
						chairs.release();
					} while (chairs.availablePermits() != 3);
				}
			} catch (InterruptedException e) {
				// Something bad happened.
				continue;
			}
		}
	}

}
