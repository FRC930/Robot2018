package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

// Left Left Switch 2
public class MotionProfile2C implements Runnable {
	
	private static EncoderFollower rightFollower;
	private static EncoderFollower leftFollower;
	private static int encPos;
	private static int enc2Pos;
	private static double yaw;

	public MotionProfile2C() {
		
		//Drive.gyro.reset();
		
		Waypoint[] leftLeftSwitch2 = new Waypoint[] {
				new Waypoint(0, 0, Pathfinder.d2r(90)),
				new Waypoint(0, 2, Pathfinder.d2r(90)),
				
				/*new Waypoint(4.0, 6.0, Pathfinder.d2r(0)),
				new Waypoint(4.5, 6.93, Pathfinder.d2r(60)),
				new Waypoint(5.15, 7.1, Pathfinder.d2r(90)),
				new Waypoint(5.8, 6.94, Pathfinder.d2r(120)),
				new Waypoint(6.3, 6.0, Pathfinder.d2r(180)),*/
				
				/*new Waypoint(4.0, 6.0, Pathfinder.d2r(270)),
				new Waypoint(4.5, 6.93, Pathfinder.d2r(330)),
				new Waypoint(5.15, 7.1, Pathfinder.d2r(0)),
				new Waypoint(5.8, 6.94, Pathfinder.d2r(30)),
				new Waypoint(6.3, 6.0, Pathfinder.d2r(90)),*/
		}; // Vel: 2.5
		
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.02, 1.5, 2.3, 50.0);   //2.5, 2.3
		
		Trajectory tra = Pathfinder.generate(leftLeftSwitch2, config);
		
		TankModifier modifier = new TankModifier(tra).modify(0.628);

	    Trajectory left = modifier.getLeftTrajectory();
	    
	    Trajectory right = modifier.getRightTrajectory();
	    
		rightFollower = new EncoderFollower(left);
		leftFollower = new EncoderFollower(right);
		rightFollower.configurePIDVA(0.9, 0, 0, 0.289, 0.06);
		leftFollower.configurePIDVA(0.9, 0, 0, 0.289, 0.06);
		
	}
	
	public void run() {
		
		double heading = Math.toDegrees(rightFollower.getHeading());
			
		if(heading >180)
			heading = heading%180-180;
		yaw = -1.0 * Drive.gyro.getYaw();
		
		double error = heading + yaw;
		if(error>180)
			error = error-360;
		else if(error < -180)
			error = error+360;
			
		double kG = -0.15;//0.8 * (-1.0/80.0);0.038

		double turn = kG * error;
			
		//System.out.printf("Heading: %.2f  Gyro: %.2f  Turn:  %.2f  LPos: %.4f  LEnc: %.4f  RPos: %.4f  REnc: %.4f \n", heading, -yaw, turn, rightFollower.getSegment().position, ((Drive.leftMain.getSelectedSensorPosition(0) - encPos) / 1024.0) * .102, leftFollower.getSegment().position, ((Drive.rightMain.getSelectedSensorVelocity(0) - enc2Pos) / 1024.0) * .102); 
		SmartDashboard.putNumber("Left Encoder", Drive.leftMain.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Right Encoder", Drive.rightMain.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Gyro", -yaw);
		
		//System.out.printf("Heading: %.2f  Gyro: %.2f  Turn:  %.2f \n", heading, -yaw, turn);
		
		double calc = (rightFollower.calculate(-1 * Drive.rightMain.getSelectedSensorPosition(0)));
		double calc2 = (leftFollower.calculate(-1 * Drive.leftMain.getSelectedSensorPosition(0)));
		// Driving backward
		Drive.rightMain.set(ControlMode.PercentOutput, -1.0 * (calc - turn));
		Drive.leftMain.set(ControlMode.PercentOutput, -1.0 * (calc2 + turn));
			
	}
	
	public boolean isLastPoint() {
		
		return (rightFollower.isFinished()&&leftFollower.isFinished());
		
	}
	
	public void startPath() {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ START AUTO~~~~~~~~~~~~~");
		//Drive.invertMotorsBackwards();
		encPos = Drive.leftMain.getSelectedSensorPosition(0);
		enc2Pos = Drive.rightMain.getSelectedSensorPosition(0);
		rightFollower.configureEncoder(-1 * Drive.rightMain.getSelectedSensorPosition(0), 1024, .102);
		leftFollower.configureEncoder(-1 * Drive.leftMain.getSelectedSensorPosition(0), 1024, .102);
		SmartDashboard.putNumber("Left Encoder", Drive.leftMain.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Right Encoder", Drive.rightMain.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Gyro", -yaw);
		
	}

}
