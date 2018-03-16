package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;

public class StartLScaleLSwitchL extends Routine {
	
	private Timer time = new Timer();
	private TimeDelay delayElev = new TimeDelay();
	private TimeDelay delayOuttake = new TimeDelay();
	private TimeDelay delayStopIntake = new TimeDelay();
	public static Notifier n;
	
	public StartLScaleLSwitchL(String v, double d) {
		
		super(v, d);
		delayElev.set(2);
		delayOuttake.set(1);
		delayStopIntake.set(2);

		n = new Notifier (AutoHandler.myMP1A);
		AutoHandler.myMP1A.startPath();
		
		time.start();
		
		
	}
	
	public void variation() {
		
		switch (this.autoStep) {
		/*case 1:
			n.startPeriodic(0.02);
				this.autoStep = 4;
				System.out.println("DONE");
			break;*/
		case 1:
			System.out.println("Running case 1");
			actList.wristUp();
			n.startPeriodic(0.02);
			this.autoStep = 2;
			break;
		case 2:
			System.out.println("Running case 2");
			if(delayElev.execute(time.get()))	{
				this.autoStep = 3;
				actList.scaleMPosition();
				System.out.println("*****Transition to Case 2");
			}
			break;
		case 3:
			System.out.println("Running case 2");
			if(delayOuttake.execute(time.get()))	{
				this.autoStep = 4;
				actList.slowOuttake();
				System.out.println("*****Transition to Case 2");
			}
			break;
		case 4:
			System.out.println("Running case 3");
			if(segList.seg1A()) {
				this.autoStep = 5;
				n.stop();
				System.out.println("*****Transition to Case 4");
			}
			break;
		case 5:
			if(delayStopIntake.execute(time.get()))
				actList.stopIntake();
			Drive.runAt(0, 0);
			break;
		}
		
	}

}
