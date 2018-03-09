package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

// Middle Left Switch 1
public class MotionProfile4A implements Runnable {
	
	private static EncoderFollower rightFollower;
	private static EncoderFollower leftFollower;

	public MotionProfile4A() {
		
		//Drive.gyro.reset();
		
		Waypoint[] middleLeftSwitch = new Waypoint[] {
				new Waypoint(0.7, 3.1, Pathfinder.d2r(0)),
				new Waypoint(4.25, 3.1, Pathfinder.d2r(0)),
				new Waypoint(5.5, 2, Pathfinder.d2r(270)),
		}; // Vel:
		
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.02, 2.5, 2.3, 50.0);
		
		Trajectory tra = Pathfinder.generate(middleLeftSwitch, config);
		
		TankModifier modifier = new TankModifier(tra).modify(0.628);

	    Trajectory left = modifier.getLeftTrajectory();
	    
	    Trajectory right = modifier.getRightTrajectory();
	    
		rightFollower = new EncoderFollower(right);
		leftFollower = new EncoderFollower(left);
		rightFollower.configurePIDVA(0.9, 0, 0, 0.289, 0.06);
		leftFollower.configurePIDVA(0.9, 0, 0, 0.289, 0.06);
		
	}
	
	public void run() {
		
		double heading = Math.toDegrees(rightFollower.getHeading());
			
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
		double calc = (rightFollower.calculate(Drive.rightMain.getSelectedSensorPosition(0)));
		double calc2 = (leftFollower.calculate(Drive.leftMain.getSelectedSensorPosition(0)));
		// Driving forward
		Drive.rightMain.set(ControlMode.PercentOutput, (calc - turn));
		Drive.leftMain.set(ControlMode.PercentOutput, (calc2 + turn));
			
	}
	
	public boolean isLastPoint(){
		
		return (rightFollower.isFinished()&&leftFollower.isFinished());
		
	}

	public void startPath() {

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ START AUTO~~~~~~~~~~~~~");
		Drive.changeSensorPhase(false, true);
		rightFollower.configureEncoder(Drive.rightMain.getSelectedSensorPosition(0), 1024, .102);
		leftFollower.configureEncoder(Drive.leftMain.getSelectedSensorPosition(0), 1024, .102);
		
	}

}
