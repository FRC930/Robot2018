package org.usfirst.frc.team930.robot;

public class Routine {

	private static String variation;
	private int segment = 1;
	private Segments segList;
	
	
	public Routine(String v) {
		
		variation = v;
		
	}
	
	public void run() {
		
		switch (variation) {
			case "LRL":
				System.out.println("Runnning Variation 1");
				variation1();
				break;
			case "RLR":
				//variation2();
				break;
			case "LLL":
				//variation3();
				break;
			case "RRR":
				//variation4();
				break;
		}
		
	}

}
