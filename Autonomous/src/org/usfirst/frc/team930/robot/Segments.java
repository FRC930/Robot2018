package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

public class Segments {
	
	private int path = 1;
	
	public boolean seg1() {
		boolean isFinished = false;
		switch (this.path) {
		case 1:
			for(int i = 1; i <= 5; i++) {
				System.out.println("Running Segment 1 Case 1");
				if(i == 5)
					this.path = 2;
			}
			break;
		case 2:
			System.out.println("\n");
			for(int i = 6; i <= 10; i++) {
				System.out.println("Running Segment 1 Case 2");
				if(i == 10) {
					this.path = 1;
					isFinished = true;
				}
			}
			break;
		}
		return isFinished;
	}

}
