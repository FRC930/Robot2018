package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class MotionProfile {

	private static EncoderFollower enc;
	private static EncoderFollower enc2;
	private static Timer time;
	
	public static void init() {
		
		time = new Timer();
		
		Waypoint[] points = AutoRoutine.points;
		
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.01, 4.0, 4.0, 50.0);

		Trajectory tra = Pathfinder.generate(points, config);
		
		TankModifier modifier = new TankModifier(tra).modify(0.768);

	    Trajectory left = modifier.getLeftTrajectory();
	    Trajectory right = modifier.getRightTrajectory();
	    
		enc = new EncoderFollower(right);
		enc2 = new EncoderFollower(left);
		enc.configurePIDVA(0.9, 0, 0, 0.3, 0.08); 
		enc2.configurePIDVA(0.9, 0, 0, 0.3, 0.08);
		enc.configureEncoder(Drive.rightMain.getSelectedSensorPosition(0), 1024, .102);
		enc2.configureEncoder(Drive.leftMain.getSelectedSensorPosition(0), 1024, .102);

		Drive.gyro.reset();
		time.start();
		
	}
	
	public static void run(double d) {
		
		if(time.get()>d){
			
			double heading = Math.toDegrees(enc.getHeading());
			
			if(heading >180)
				heading = heading%180-180;
			double yaw = Drive.gyro.getYaw();
		
			double error = heading + yaw;
			if(error>180)
				error = error-360;
			else if(error < -180)
				error = error+360;
			
			double kG = -0.025;//0.8 * (-1.0/80.0);

			double turn = kG * error;
			
			System.out.printf("Heading: %.2f  Gyro: %.2f  Turn:  %.2f \n", heading,-yaw,turn); 
			
			double calc = (enc.calculate(Drive.rightMain.getSelectedSensorPosition(0)));
			double calc2 = (enc2.calculate(Drive.leftMain.getSelectedSensorPosition(0)));

			Drive.rightMain.set(ControlMode.PercentOutput, calc - turn);
			Drive.leftMain.set(ControlMode.PercentOutput, -(calc2 + turn));
			
		}
			
	}
	
	public static boolean isLastPoint(){
		
		return (enc.isFinished()&&enc2.isFinished());
		
	}
	
}
	
	

