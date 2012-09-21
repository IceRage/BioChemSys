package ro.ubb.biochem.algorithm;

public class UserStopCondition {

	private static boolean shouldStopFlag = false;
	
	public static synchronized boolean shouldStop(){
		return shouldStopFlag;
	}
	
	public static synchronized void stop(){
		shouldStopFlag = true;
	}
	
	public static synchronized void start(){
		shouldStopFlag = false;
	}
	
}
