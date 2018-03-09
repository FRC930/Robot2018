package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

// Left Left Switch 2
public class MotionProfile2B implements Runnable {
	
	private static EncoderFollower enc;
	private static EncoderFollower enc2;
	private static int encPos;
	private static int enc2Pos;

	public static void init() {
		
		//Drive.gyro.reset();
		
		Waypoint[] leftLeftSwitch2 = AutoHandler.leftLeftSwitch2; // Vel: 2.5
		
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.02, 2.5, 2.3, 50.0);   //2.5, 2.3
		
		Trajectory tra = Pathfinder.generate(leftLeftSwitch2, config);
		
		TankModifier modifier = new TankModifier(tra).modify(0.628);

	    Trajectory left = modifier.getLeftTrajectory();
	    
	    Trajectory right = modifier.getRightTrajectory();
	    
		enc = new EncoderFollower(right);
		enc2 = new EncoderFollower(left);
		enc.configurePIDVA(0.9, 0, 0, 0.289, 0.06);
		enc2.configurePIDVA(0.9, 0, 0, 0.289, 0.06);
		
		
	}
	
	public void run() {
		
		double heading = Math.toDegrees(enc.getHeading());
			
		if(heading >180)
			heading = heading%180-180;
		double yaw = Drive.gyro.getYaw();
		
		double error = heading + yaw;
		if(error>180)
			error = error-360;
		else if(error < -180)
			error = error+360;
			
		double kG = -0.038;//0.8 * (-1.0/80.0);

		double turn = kG * error;
			
		System.out.printf("Heading: %.2f  Gyro: %.2f  Turn:  %.2f  LPos: %.4f  LEnc: %.4f  RPos: %.4f  REnc: %.4f \n", heading, -yaw, turn, enc.getSegment().position, ((Drive.leftMain.getSelectedSensorPosition(0) - encPos) / 1024.0) * .102, enc2.getSegment().position, ((Drive.rightMain.getSelectedSensorVelocity(0) - enc2Pos) / 1024.0) * .102); 
		double calc = (enc.calculate(Drive.leftMain.getSelectedSensorPosition(0)));
		double calc2 = (enc2.calculate(Drive.rightMain.getSelectedSensorPosition(0)));
		// Driving backward
		Drive.rightMain.set(ControlMode.PercentOutput, -(calc2 + turn));
		Drive.leftMain.set(ControlMode.PercentOutput, -(calc - turn));
			
	}
	
	public static boolean isLastPoint(){
		
		return (enc.isFinished()&&enc2.isFinished());
		
	}
	
	public static void startPath(){
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ START AUTO~~~~~~~~~~~~~");
		Drive.changeSensorPhase(true, false);
		//Drive.changeSensorPhase(false, true);
		encPos = Drive.leftMain.getSelectedSensorPosition(0);
		enc2Pos = Drive.rightMain.getSelectedSensorPosition(0);
		enc.configureEncoder(Drive.leftMain.getSelectedSensorPosition(0), 1024, .102);
		enc2.configureEncoder(Drive.rightMain.getSelectedSensorPosition(0), 1024, .102);
	}

}
