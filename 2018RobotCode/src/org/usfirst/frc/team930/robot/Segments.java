package org.usfirst.frc.team930.robot;

public class Segments {
	
	private int path = 1;
	private int point = 1;
	
	public boolean seg1() {
		
		boolean isFinished = false;
		switch (this.path) {
		case 1:
			//System.out.println("Running Segment 1 Case 1");
			//System.out.println("Count: " + point);
			
			MotionProfile.run();
			
			if(MotionProfile.isLastPoint()) {
				this.path = 1;
				isFinished = true;
			}
			
			break;
		/*case 2:
			System.out.println("Running Segment 1 Case 2");
			System.out.println("Count: " + point);
			
			if(MotionProfile.isLastPoint()) {
				this.path = 2;
				isFinished = true;
			}
			
			break;*/
		}
		
		return isFinished;
		
	}

}
