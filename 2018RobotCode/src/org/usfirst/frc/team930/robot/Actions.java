package org.usfirst.frc.team930.robot;

public class Actions {
	
	private int path = 1;
	private int point = 1;
	
	public boolean act1() {
		
		boolean isFinished = false;
		switch (this.path) {
		case 1:
			System.out.println("Running Action 1 Case 1");
			System.out.println("Count: " + point);
			
			if(MotionProfile.isLastPoint()) {
				this.point = 0;
				this.path = 2;
			}
			
			this.point++;
			
			break;
		case 2:
			System.out.println("Running Action 1 Case 2");
			System.out.println("Count: " + point);
			
			if(MotionProfile.isLastPoint()) {
				this.point = 0;
				this.path = 1;
				isFinished = true;
			}
			
			this.point++;
			
			break;
		}
		
		return isFinished;
		
	}

}
