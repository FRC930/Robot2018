package org.usfirst.frc.team930.robot;

public class Segments {
	
	private static int path = 1;
	
	public static boolean seg1() {
		boolean isFinished = false;
		
		switch (path) {
		case 1:
			// Generate points
			if(true)
				path = 2;
			break;
		case 2:
			// Send points
			if(true)
				isFinished = true;
			break;
	}
		
		return isFinished;
	}

}
