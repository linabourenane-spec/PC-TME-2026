package pc.philo;

public class Philosopher implements Runnable {
	private Fork left;
	private Fork right;

	public Philosopher(Fork left, Fork right) {
		this.left = left;
		this.right = right;
	}

	public void run() {
		while (! Thread.currentThread().isInterrupted()) {
			think();
			if (Thread.currentThread().isInterrupted()) break;

			boolean hasLeft = false;
			boolean hasRight = false;

			try {
				left.acquire();
				hasLeft = true;
				System.out.println(Thread.currentThread().getName() + " has one fork");

				right.acquire();
				hasRight = true;
				System.out.println(Thread.currentThread().getName() + " has two forks");

				eat();
			} finally {
				if (hasRight)
					right.release();
				if (hasLeft)
					left.release();
			}
		}
	}

	private void eat() {
		System.out.println(Thread.currentThread().getName() + " is eating");
	}

	private void think() {
		System.out.println(Thread.currentThread().getName() + " is thinking");
	}
}
