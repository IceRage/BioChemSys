package ro.ubb.biochem.tests.util;

import ro.ubb.biochem.tests.util.exception.ExecutionTimerException;

/**
 * A timer which measures the execution of a piece of code using the following
 * public methods:
 * 	- start() is used for starting the timer
 * 	- stop() is used for returning the elapsed time
 */
public class ExecutionTimer {

	private static final String EXC_START_MSG = "The timer was already started.";
	private static final String EXC_STOP_MSG = "The timer was not started yet.";
	
	/**
	 * The value of startTime is:
	 * 	- greater than 0 (>0) if the timer was started
	 *  - equal to 0 (=0) if the timer was not started yet
	 */
	private long startTime;
	
	public ExecutionTimer() {
		init();
	}

	/**
	 * Start the timer
	 * 
	 * @throws ExecutionTimerException The timer was started before
	 */
	public void start() throws ExecutionTimerException {
		if (startTime == -1) {
			startTime = System.nanoTime();
		} else {
			throw new ExecutionTimerException(EXC_START_MSG);
		}
	}
	
	/**
	 * Stop the timer
	 * 
	 * @return The elapsed time
	 * @throws ExecutionTimerException The timer was not started yet 
	 */
	public long stop() throws ExecutionTimerException {
		if (startTime != -1) {
			long elapsedTime = (System.nanoTime() - startTime);
			
			startTime = -1;
			
			return elapsedTime;
		} else {
			throw new ExecutionTimerException(EXC_STOP_MSG);
		}
	}
	
	/**
	 * Initialize the members of the class
	 */
	private void init() {
		startTime = -1;
	}
	
}
