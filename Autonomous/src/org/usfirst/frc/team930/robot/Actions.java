package org.usfirst.frc.team930.robot;

public class Actions {
	
	private static int path = 1;
	
	public static boolean act1() {
		boolean isFinished = false;
		int x = 1;
		switch (x) {
		case 1:
			for(int i = 10; i >= 6; i--) {
				System.out.println("Running Action 1 Case 1");
				if(i == 6)
					x = 2;
			}
			break;
		case 2:
			System.out.println("\n");
			for(int i = 5; i >= 1; i--) {
				System.out.println("Running Action 1 Case 2");
				if(i == 1) {
					isFinished = true;
					path = 3;
				}
			}
			break;
		}
		
		return isFinished;
	}

}
