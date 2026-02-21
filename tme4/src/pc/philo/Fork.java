package pc.philo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {
	
	Lock lock = new ReentrantLock();
	
	public void acquire () {
		lock.lock();
    }
	
	
	public void release () {
		lock.unlock();
	}
}
