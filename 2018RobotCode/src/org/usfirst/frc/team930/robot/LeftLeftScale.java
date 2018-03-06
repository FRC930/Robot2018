package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

public class LeftLeftScale extends Routine {
	
	private Timer time = new Timer();
	private TimeDelay delayElev = new TimeDelay();
	private TimeDelay delayIntake = new TimeDelay();
	
	public LeftLeftScale(String v, double d) {
		
		super(v, d);
		delayElev.set(4.5);
		delayIntake.set(2);
		time.start();
		
		
	}
	
	public void variation() {
		
		switch (this.autoStep) {
			/*case 1:
				if(segList.seg1()) {
					this.autoStep = 4;
					System.out.println("DONE");
				}
				break;*/
			case 1:
				System.out.println("Running case 1");
				actList.wristUp();
				//segList.seg1();
				super.n.startPeriodic(0.02);
				if(delayElev.execute(time.get()))	{
					this.autoStep = 2;
					actList.scaleMPosition();
					System.out.println("*****Transition to Case 2");
				}
				break;
			case 2:
				System.out.println("Running case 2");
				//segList.seg1();
				if(delayIntake.execute(time.get())) {
					this.autoStep = 3;
					actList.slowOuttake();
					System.out.println("*****Transition to Case 3");
				}
				break;
			case 3:
				System.out.println("Running case 3");
				if(segList.seg1()) {
					this.autoStep = 4;
					actList.stopIntake();
					super.n.stop();
					System.out.println("*****Transition to Case 4");
				}
				break;
			case 4:
				Drive.runAt(0, 0);
				break;
		}
		
	}

}
