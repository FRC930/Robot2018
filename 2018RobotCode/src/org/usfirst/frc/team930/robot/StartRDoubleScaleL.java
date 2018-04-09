package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;

public class StartRDoubleScaleL extends Routine {
	
	private Timer time = new Timer();
	private TimeDelay delayElev = new TimeDelay();
	private TimeDelay delayOuttake = new TimeDelay();
	private TimeDelay delayStopIntake = new TimeDelay();
	public static Notifier n;
	
	public StartRDoubleScaleL(String v, double d) {
		
		super(v, d);
		delayElev.set(Constants.ETime3);
		delayOuttake.set(Constants.ITime4);
		delayStopIntake.set(Constants.OTime1);

		n = new Notifier (AutoHandler.mpStartRScaleL);
		AutoHandler.mpStartRScaleL.startPath();
		
		time.start();
		
		
	}
	
	public void variation() {
		
		switch (this.autoStep) {
		/*case 1:
			super.n.startPeriodic(0.02);
				this.autoStep = 3;
				System.out.println("DONE");
			break;*/
		case 1:
			System.out.println("Running case 1");
			actList.wristUp();
			actList.intake();
			n.startPeriodic(0.02);
			this.autoStep = 2;
			break;
		case 2:
			System.out.println("Running case 2");
			if(delayElev.execute(time.get()))	{
				this.autoStep = 3;
				actList.scaleHPosition();
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
			if(segList.segStartRScaleL()) {
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
