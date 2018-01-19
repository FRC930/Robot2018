package org.usfirst.frc.team930.robot;

public class Actions {
	
	private static int path = 1;
	
	public static boolean act1() {
		boolean isFinished = false;
		
		switch (path) {
		case 1:
			for(int i = 1; i <= 5; i++)
				System.out.println(i);
			path = 2;
			break;
		case 2:
			System.out.println("\n");
			for(int i = 6; i <= 10; i++)
				System.out.println(i);
			isFinished = true;
			break;
		}
		
		return isFinished;
	}

}
