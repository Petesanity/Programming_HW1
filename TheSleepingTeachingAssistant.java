/*
Group Names:
	Peter Farquharson
	Davian Farquharson
	Jose Cantres
	Mahboba Mim	
	
cited: repl.it/@Saimani/CS446-Project-Sleeping-TA-Assignment
*/


import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.Semaphore;

public class TheSleepingTeachingAssistant {

	public static void main(String[] args) {
		// list of all sttudents.
		int numOfStudents = 5;

		// Create semaphores.
		Semaphore1 wakeup = new Semaphore1();
		Semaphore chairs = new Semaphore(3);
		Semaphore available = new Semaphore(1);

		// Used for randomly generating program time.
		Random studentWait = new Random();

		// Create student threads and start them.
		for (int i = 0; i < numOfStudents; i++) {
			Thread student = new Thread(new Student(studentWait.nextInt(20), wakeup, chairs, available, i + 1));
			student.start();
		}

		// creating the thread then start.
		Thread thread = new Thread(new TeachingAssistant(wakeup, chairs, available));
		thread.start();
	}
}

class Student implements Runnable {

	// declaring a reference to the thread.
	private Thread thread;

	// used to determine the availability of the Teacher assistant(mutex lock).
	private Semaphore available;

	// Semaphore used to wakeup TA.
	private Semaphore1 wakeup;

	// programming time before going getting help
	private int programTime;

	// Student number.
	private int studentNum;

	// Semaphore used to wait in chairs outside office.
	private Semaphore seats;

	// created the constructor to initialize the global references that was declare
	// above.
	public Student(int programTime, Semaphore1 wakeup2, Semaphore seats, Semaphore available, int studentNum) {
		this.programTime = programTime;
		this.wakeup = wakeup2;
		this.seats = seats;
		this.available = available;
		this.studentNum = studentNum;

		thread = Thread.currentThread();
	}

	@Override
	public void run() {
		// Infinite loop.
		while (true) {
			try {
				// Program first.
				System.out.println(
						"Student @ " + studentNum + " started" + " programming for " + programTime + " seconds.");

				thread.sleep(programTime * 1000);

				// Check to availability of the Teacher Assistant
				System.out.println("Student @ " + studentNum + " is checking when Teacher Assistant is free.");
				if (available.tryAcquire()) {
					try {
						// Wakeup the TA.
						wakeup.take();
						System.out.println("Student @ " + studentNum + " wakes up the Teacher.");
						System.out.println("Student @ " + studentNum + " started working with the Teacher.");
						thread.sleep(5000);
						System.out.println("Student @ " + studentNum + " session ended.");
					} catch (InterruptedException e) {
						// Catch any exception if anything terrible happen.
						continue;
					} finally {
						available.release();
					}
				} else {
					// check the availability of the seats.
					System.out.println("Student @ " + studentNum
							+ " can't see the Teacher. Still looking for any seats that are free.");
					if (seats.tryAcquire()) {
						try {
							// Wait for TA to finish with other student.
							System.out.println("Student @ " + studentNum + " is waiting outside of the office. "
									+ "He's @" + ((3 - seats.availablePermits())) + " in the queue.");
							available.acquire();
							System.out.println(
									"Student @ " + studentNum + " started working with the Teacher Assistant.");
							thread.sleep(5000);
							System.out.println("Student @ " + studentNum + " ended the session with the Teacher.");
							available.release();
						} catch (InterruptedException e) {
							// Something bad happened.
							continue;
						}
					} else

						System.out.println("Student @ " + studentNum + " could not see the Teacher because"
								+ " all seats were filled");

				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}

class Semaphore1 {

	private boolean signal = false;

	// Used to send the signal.
	public synchronized void take() {
		this.signal = true;
		this.notify();
	}

	// Will wait until it receives a signal before continuing.
	public synchronized void release() throws InterruptedException {
		while (!this.signal)
			wait();
		this.signal = false;
	}

}

class TeachingAssistant implements Runnable {

	// Semaphore used to wakeup TA.
	private Semaphore1 wakeup;

	// Semaphore used to wait who is used outside office.
	private Semaphore seats;

	// Mutex lock used to determine if Teacher is available.
	private Semaphore available;

	// A reference to the current thread.
	private Thread thread;

	public TeachingAssistant(Semaphore1 wakeup, Semaphore seats, Semaphore available) {
		thread = Thread.currentThread();
		this.wakeup = wakeup;
		this.seats = seats;
		this.available = available;
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("No students left. The Teacher is going to sleep.");
				wakeup.release();
				System.out.println("A student wake up the the teacher.");

				thread.sleep(5000);

				// to see if other students are waiting.
				if (seats.availablePermits() != 3) {
					do {
						thread.sleep(5000);
						seats.release();
					} while (seats.availablePermits() != 3);
				}
			} catch (InterruptedException e) {
				// catch any exception that happened.
				continue;
			}
		}
	}

}
