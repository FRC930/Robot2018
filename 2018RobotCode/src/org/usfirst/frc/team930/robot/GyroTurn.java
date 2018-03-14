package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class GyroTurn implements Runnable {
	
	private static int encPos;
	private static int enc2Pos;
	private static int heading;

	public GyroTurn() {
		
		//Drive.gyro.reset();
		
	}
	
	public void run() {
		
		heading = 180;
			
		if(heading >180)
			heading = heading%180-180;
		double yaw = Drive.gyro.getYaw();
		
		double error = heading + yaw;
		if(error>180)
			error = error-360;
		else if(error < -180)
			error = error+360;
			
		double kG = -0.007;//0.8 * (-1.0/80.0);

		double turn = kG * error;
			
		//System.out.printf("Heading: %.2f  Gyro: %.2f  Turn:  %.2f  LPos: %.4f  LEnc: %.4f  RPos: %.4f  REnc: %.4f \n", heading, -yaw, turn, rightFollower.getSegment().position, ((Drive.leftMain.getSelectedSensorPosition(0) - encPos) / 1024.0) * .102, leftFollower.getSegment().position, ((Drive.rightMain.getSelectedSensorVelocity(0) - enc2Pos) / 1024.0) * .102); 
		//System.out.printf("Heading: %.2f  Gyro: %.2f  Turn:  %.2f \n", heading, -yaw, turn);
		// Gyro turn in place
		Drive.rightMain.set(ControlMode.PercentOutput, -turn);
		Drive.leftMain.set(ControlMode.PercentOutput, turn);
			
	}
	
	public boolean isFinished() {
		
		if((Drive.gyro.getYaw() > (heading - 5)) && (Drive.gyro.getYaw() < (heading + 5))) {
			System.out.println("Finished");
			return true;
		}
		else {
			System.out.println("Not Finished");
		}
		
		return false;
		
	}
	
	public void startPath() {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ START AUTO~~~~~~~~~~~~~");
		Drive.changeSensorPhase(true, false);
		//Drive.changeSensorPhase(false, true);
		encPos = Drive.leftMain.getSelectedSensorPosition(0);
		enc2Pos = Drive.rightMain.getSelectedSensorPosition(0);
		
	}

}
