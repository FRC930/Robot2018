package motionProfile;

import java.io.File;

import org.usfirst.frc.team930.robot.Constants;
import org.usfirst.frc.team930.robot.Drive;
import org.usfirst.frc.team930.robot.Utilities;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/* 
 * Starting in left position going to left scale auto 
 */
public class MPStartLScaleL implements Runnable {

	private static EncoderFollower rightFollower;
	private static EncoderFollower leftFollower;
	public static double first;
	private static double yaw;
	private static double heading;

	/* 
	 * Defining points, generating the path, and setting PID 
	 */
	public MPStartLScaleL() {
		
		first = Timer.getFPGATimestamp();
		SmartDashboard.putNumber("Auto Time Difference", 0);
		
		Waypoint[] leftLeftScale = new Waypoint[] {
				new Waypoint(0.7, 3.1, Pathfinder.d2r(0)),
				new Waypoint(5.0, 3.1, Pathfinder.d2r(0)),
				new Waypoint(7.1, 2.6, Pathfinder.d2r(10)),
		}; 
		
		// Setting timestep, max velocity, max acceleration, max jerk
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.02, 4.01, 2.3, 50.0);
		
		// Generating the trajectory
		Trajectory tra = this.generate(leftLeftScale, config);
		
		TankModifier modifier = new TankModifier(tra).modify(0.628);

		// Splitting the trajectory into left and right sides
	    Trajectory left = modifier.getLeftTrajectory();
	    Trajectory right = modifier.getRightTrajectory();
	    
	    // Setting PIDVA
		rightFollower = new EncoderFollower(right);
		leftFollower = new EncoderFollower(left);
		rightFollower.configurePIDVA(Constants.RightP, 0, 0, Constants.RightV, Constants.RightA);
		leftFollower.configurePIDVA(Constants.LeftP, 0, 0, Constants.LeftV, Constants.LeftA);	
		
	}
	
	/* 
	 * Checking to see if path already exists, if not generates the new path.
	 * If the path exists, a new path is not generated.
	 */
	public Trajectory generate(Waypoint[] waypoints, Trajectory.Config config) {
		String hash = Utilities.hash(waypoints, config);
		File pathFile = new File("/home/lvuser/" + hash + ".traj");
		Trajectory traj;
		
		if(pathFile.exists()) {
			traj = Pathfinder.readFromFile(pathFile);
		} else {
			traj = Pathfinder.generate(waypoints, config);
			Pathfinder.writeToFile(pathFile, traj);
		}
		
		return traj;
	}
	
	/* 
	 * Sending the points to the drivetrain 
	 */
	public void run() {
		
		// Getting the heading and making adjustments with the gyro
		heading = Math.toDegrees(rightFollower.getHeading());
			
		if(heading >180)
			heading = heading%180-180;
		yaw = Drive.gyro.getYaw();
		
		double error = heading + yaw;
		if(error>180)
			error = error-360;
		else if(error < -180)
			error = error+360;
			
		double kG = Constants.gyroPID;

		double turn = kG * error;
		
		double calc = (rightFollower.calculate(Drive.rightMain.getSelectedSensorPosition(0)));
		double calc2 = (leftFollower.calculate(Drive.leftMain.getSelectedSensorPosition(0)));
		
		Drive.rightMain.set(ControlMode.PercentOutput, (calc - turn));
		Drive.leftMain.set(ControlMode.PercentOutput, (calc2 + turn));
		
		SmartDashboard.putNumber("Auto Time Difference", Timer.getFPGATimestamp() - first);
		first = Timer.getFPGATimestamp();
		
		SmartDashboard.putNumber("Gyro", -yaw);	

		SmartDashboard.putNumber("Left Enc Vel", Drive.leftMain.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Right Enc Vel", Drive.rightMain.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Heading", heading);
		
	}
	
	/* 
	 * Returns true if path is done, false if there are still points left
	 */
	public boolean isLastPoint(){
		
		System.out.println("Finished");
		
		return (rightFollower.isFinished()&&leftFollower.isFinished());
		
	}

	/*
	 * Gets called at the beginning of auto routine to configure encoders
	 */
	public void startPath() {

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ START AUTO~~~~~~~~~~~~~");
		rightFollower.configureEncoder(Drive.rightMain.getSelectedSensorPosition(0), 1024, .102);
		leftFollower.configureEncoder(Drive.leftMain.getSelectedSensorPosition(0), 1024, .102);
		SmartDashboard.putNumber("Gyro", -yaw);
		
		
	}
	
	/*
	 * Printing data to SmartDashboard
	 */
	public void disabled() {
		
		SmartDashboard.putNumber("Gyro", -yaw);
		SmartDashboard.putNumber("Heading", heading);
		
	}
	
}
	
	

