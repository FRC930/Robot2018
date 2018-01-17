package org.usfirst.frc.team930.robot;

public class Routine1 {
	
	private static String variation;
	
	public Routine1(String v) {
		
		variation = v;
		
	}
	
	public void run() {
		
		switch (variation) {
			case "LRR":
				variation1();
				break;
			case "RLR":
				variation2();
				break;
		}
		
	}
	
	public static void variation1() {
		
		Segments.seg1();
		
	}
	
	public static void variation2() {
		
	}

}
