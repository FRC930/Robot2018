package org.usfirst.frc.team930.robot;

public class Segments {
	
	public boolean seg1() {
		
		boolean isFinished = false;
		
		if(MotionProfile2B.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}

}
