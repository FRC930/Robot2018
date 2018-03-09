package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;

public class LeftLeftSwitch extends Routine {
	
	private Timer time = new Timer();
	private TimeDelay delayElev = new TimeDelay();
	private TimeDelay delayOuttake = new TimeDelay();
	private TimeDelay delayStopIntake = new TimeDelay();
	public static Notifier n;
	
	public LeftLeftSwitch(String v, double d) {
		
		super(v, d);
		delayElev.set(0);
		delayOuttake.set(3.5);
		delayStopIntake.set(1);

		//n = new Notifier (AutoHandler.myMP2A);
		//AutoHandler.myMP2A.startPath();
		n = new Notifier (AutoHandler.myMP2B);
		AutoHandler.myMP2B.startPath();
		
		time.start();
		
		
	}
	
	public void variation() {
		
		switch (this.autoStep) {
			case 1:
				n.startPeriodic(0.02);
					this.autoStep = 3;
					//System.out.println("DONE");
				break;
			/*case 1:
				System.out.println("Running case 1");
				actList.wristUp();
				n.startPeriodic(0.02);
				this.autoStep = 2;
				break;*/
			case 2:
				System.out.println("Running case 2");
				if(delayElev.execute(time.get()))	{
					this.autoStep = 3;
					actList.switchPosition();
					System.out.println("*****Transition to Case 3");
				}
				break;
			case 3:
				//System.out.println("Running case 3");
				if(segList.seg2B()/*segList.seg2A*/) {
					this.autoStep = 4;
					n.stop();
					//n = new Notifier (AutoHandler.myMP2B);
					//actList.slowOuttake();
					//System.out.println("*****Transition to Case 4");
				}
				break;
			/*case 4:
				System.out.println("Running case 4");
				actList.wristUp();
				n.startPeriodic(0.02);
				this.autoStep = 5;
				break;
			case 5:
				System.out.println("Running case 5");
				if(delayElev.execute(time.get()))	{
					this.autoStep = 6;
					actList.switchPosition();
					System.out.println("*****Transition to Case 6");
				}
				break;
			case 6:
				System.out.println("Running case 6");
				if(segList.seg2B()) {
					this.autoStep = 7;
					n.stop();
					n = new Notifier (myMP2);
					//actList.slowOuttake();
					System.out.println("*****Transition to Case 7");
				}
				break;*/
			case 4:
				if(delayStopIntake.execute(time.get()))
					actList.stopIntake();
				Drive.runAt(0, 0);
				break;
		}
		
	}

}
