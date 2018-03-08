package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

// Right Left Scale 1
public class MotionProfile8A implements Runnable {
	
	private static EncoderFollower enc;
	private static EncoderFollower enc2;

	public static void init() {
		
		//Drive.gyro.reset();
		
		Drive.changeSensorPhase(false, true);
		
		Waypoint[] rightLeftScale = AutoHandler.rightLeftScale; // Vel: 3.0
		
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.02, 3.0, 2.3, 50.0);
		
		Trajectory tra = Pathfinder.generate(rightLeftScale, config);
		
		TankModifier modifier = new TankModifier(tra).modify(0.628);

	    Trajectory left = modifier.getLeftTrajectory();
	    
	    Trajectory right = modifier.getRightTrajectory();
	    
		enc = new EncoderFollower(right);
		enc2 = new EncoderFollower(left);
		enc.configurePIDVA(0.9, 0, 0, 0.289, 0.06);
		enc2.configurePIDVA(0.9, 0, 0, 0.289, 0.06);
		enc.configureEncoder(Drive.rightMain.getSelectedSensorPosition(0), 1024, .102);
		enc2.configureEncoder(Drive.leftMain.getSelectedSensorPosition(0), 1024, .102);
		
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
			
		System.out.printf("Heading: %.2f  Gyro: %.2f  Turn:  %.2f \n", heading,-yaw,turn); 
		double calc = (enc.calculate(Drive.rightMain.getSelectedSensorPosition(0)));
		double calc2 = (enc2.calculate(Drive.leftMain.getSelectedSensorPosition(0)));
		// Driving forward
		Drive.rightMain.set(ControlMode.PercentOutput, (calc - turn));
		Drive.leftMain.set(ControlMode.PercentOutput, (calc2 + turn));
			
	}
	
	public static boolean isLastPoint(){
		
		return (enc.isFinished()&&enc2.isFinished());
		
	}

}
