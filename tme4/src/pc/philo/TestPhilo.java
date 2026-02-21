package pc.philo;

public class TestPhilo {

	public static void main(String[] args) {
		final int NB_PHIL = 5;
		Thread[] tPhil = new Thread[NB_PHIL];
		Fork[] tChop = new Fork[NB_PHIL];
		for (int i = 0; i < NB_PHIL; i++) {
			tChop[i] = new Fork();
		}

		for (int i = 0; i < NB_PHIL; i++) {
			if (i == 0) {
				tPhil[i] = new Thread(new Philosopher(tChop[i],tChop[NB_PHIL-1]));  //inversement (question 5 ) 
				tPhil[i].start();
				continue;
			}
			tPhil[i] = new Thread(new Philosopher(tChop[i - 1], tChop[i]));
			tPhil[i].start();
			

		}
		
		try {
			Thread.sleep(3000);
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		
		for (Thread th : tPhil) {
			th.interrupt();
		}

		for (int i = 0; i < NB_PHIL; i++) {
			try {
				tPhil[i].join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		System.out.println("Fin du programme");

	}
}