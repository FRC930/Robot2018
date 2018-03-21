//package org.usfirst.frc.team930.robot;
//
//import edu.wpi.first.wpilibj.Notifier;
//import edu.wpi.first.wpilibj.Timer;
//
//public class LeftLeftSwitch extends Routine {
//	
//	private Timer time = new Timer();
//	private TimeDelay delayElev = new TimeDelay();
//	private TimeDelay delayOuttake = new TimeDelay();
//	private TimeDelay delayStopIntake = new TimeDelay();
//	private TimeDelay delayAfterGyro = new TimeDelay();
//	public static Notifier n;
//	
//	public LeftLeftSwitch(String v, double d) {
//		
//		super(v, d);
//		delayElev.set(0);
//		delayOuttake.set(3.5);
//		delayStopIntake.set(0.25);
//
//		n = new Notifier (AutoHandler.myMP2A);
//		AutoHandler.myMP2A.startPath();
//		System.out.println("IN LEFT LEFT SWITCH CONSTRUCTOR");
//		//n = new Notifier (AutoHandler.myMP2B);
//		//AutoHandler.myMP2B.startPath();
//		//n = new Notifier (AutoHandler.myMP2C);
//		//AutoHandler.myMP2C.startPath();
//		//n = new Notifier (AutoHandler.myAutoGT);
//		//AutoHandler.myAutoGT.startPath();
//		
//		time.start();
//		
//		
//	}
//	
//	public void variation() {
//		
//		switch (this.autoStep) {
//			case 1:
//				System.out.println("Running case 1");
//				n.startPeriodic(0.02);
//				this.autoStep = 3;
//				//System.out.println("DONE");
//				break;
//			/*case 1:
//				System.out.println("Running case 1");
//				actList.wristUp();
//				n.startPeriodic(0.02);
//				this.autoStep = 2;
//				break;*/
//			case 2:
//				System.out.println("Running case 2");
//				if(delayElev.execute(time.get()))	{
//					this.autoStep = 3;
//					actList.switchPosition();
//					System.out.println("*****Transition to Case 3");
//				}
//				break;
//			case 3:
//				System.out.println("Running case 3");
//				if(segList.seg2A()) {
//					this.autoStep = 4;
//					n.stop();
//					n = new Notifier (AutoHandler.myAutoGT);
//					AutoHandler.myAutoGT.startPath();
//					//actList.slowOuttake();
//				}
//				break;
//			case 4:
//				System.out.println("Running case 4");
//				n.startPeriodic(0.02);
//				this.autoStep = 10;
//				break;
//			case 5:
//				System.out.println("Running case 5");
//				if(segList.segGyroTurn()) {
//					this.autoStep = 6;
//					n.stop();
//					n = new Notifier (AutoHandler.myMP2B);
//					AutoHandler.myMP2B.startPath();
//				}
//				break;
//			case 6:
//				System.out.println("Running case 6");
//				n.startPeriodic(0.02);
//				this.autoStep = 7;
//				break;
//			case 7:
//				System.out.println("Running case 7");
//				if(segList.seg2B()) {
//					this.autoStep = 13;
//					n.stop();
//					//n = new Notifier (AutoHandler.myAutoGT);
//					//AutoHandler.myAutoGT.startPath();
//				}
//				break;
//			case 9:
//				System.out.println("Running case 9");
//				n.startPeriodic(0.02);
//				this.autoStep = 10;
//				break;
//			case 10:
//				if(segList.segGyroTurn()) {
//					this.autoStep = 11;
//					n.stop();
//					n = new Notifier (AutoHandler.myMP2C);
//					AutoHandler.myMP2C.startPath();
//				}
//				break;
//			case 11:
//				System.out.println("Running case 11");
//				n.startPeriodic(0.02);
//				this.autoStep = 12;
//				break;
//			case 12:
//				System.out.println("Running case 12");
//				if(segList.seg2C()) {
//					this.autoStep = 13;
//					n.stop();
//				}
//				break;
//			case 13:
//				System.out.println("Running case 13");
//				if(delayStopIntake.execute(time.get()))
//					actList.stopIntake();
//				Drive.runAt(0, 0);
//				break;
//		}
//		
//	}
//
//}
