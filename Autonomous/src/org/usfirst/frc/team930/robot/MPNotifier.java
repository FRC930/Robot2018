package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

public class MPNotifier implements Runnable {
	
	double time;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		time = Timer.getFPGATimestamp();
		
		Robot.auto.run();
		
		System.out.println("Time Difference: " + (Timer.getFPGATimestamp() - time) * 1000);
		
	}

}
