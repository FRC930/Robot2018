package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

/* 
 * Turning in place using gyro in motion profiling 
 */
public class GyroTurn implements Runnable {
	
	private static int encPos;
	private static int enc2Pos;
	private static int heading;
	private static double yaw;
	private static double turn;

	/* 
	 * No waypoints or path generation needed
	 */
	public GyroTurn() {
		
	}
	
	/* 
	 * Determining error between target heading and actual gyro input to calculate amount of turn needed
	 */
	public void run() {
			
		if(heading >180)
			heading = heading%180-180;
		yaw = Drive.gyro.getYaw();
		
		double error = heading + yaw;
		if(error>180)
			error = error-360;
		else if(error < -180)
			error = error+360;
			
		double kG = -0.006;

		turn = kG * error;
			
		System.out.printf("Heading: %d  Gyro: %.2f  Turn:  %.2f \n", heading, -yaw, turn);
		// Gyro turn in place
		Drive.rightMain.set(ControlMode.PercentOutput, -turn);
		Drive.leftMain.set(ControlMode.PercentOutput, turn);
			
	}
	
	/* 
	 * Returns true if path is done, false if actual gyro input is not equal to target heading
	 */
	public boolean isFinished() {
		
		System.out.printf("Heading: %d  Gyro: %.2f  Turn:  %.2f \n", heading, -yaw, turn);
		
		if((Drive.gyro.getYaw() > (heading - 5)) && (Drive.gyro.getYaw() < (heading + 5))) {
			System.out.println("Finished");
			return true;
		}
		else {
			System.out.println("Not Finished");
		}
		
		return false;
		
	}
	
	/*
	 * Gets called at the beginning of auto routine to configure encoders and set target heading
	 */
	public void startPath() {
		
		heading = 345;
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ START AUTO~~~~~~~~~~~~~");
		Drive.changeSensorPhase(false, true);
		encPos = Drive.leftMain.getSelectedSensorPosition(0);
		enc2Pos = Drive.rightMain.getSelectedSensorPosition(0);
		
	}

}
