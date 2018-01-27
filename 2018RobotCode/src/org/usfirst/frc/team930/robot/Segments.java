package org.usfirst.frc.team930.robot;

public class Segments {
	
	private int path = 1;
	private int count = 1;
	
	public boolean seg1() {
		boolean isFinished = false;
		switch (this.path) {
		case 1:
			System.out.println("Running Segment 1 Case 1");
			System.out.println("Count: " + count);

			//Robot.leftMain.set(0);
			//Robot.rightMain.set(0);
			
			if(this.count == 100) {
				this.count = 1;
				this.path = 2;
			}
			
			this.count++;
			
			break;
		case 2:
			System.out.println("Running Segment 1 Case 2");
			System.out.println("Count: " + count);

			//Robot.leftMain.set(0);
			//Robot.rightMain.set(0);
			
			if(this.count == 100) {
				this.count = 1;
				this.path = 1;
				isFinished = true;
			}
			
			this.count++;
			
			break;
		}
		return isFinished;
	}

}
