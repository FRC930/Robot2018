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

// Start L Double Scale L 1
public class MPStartLScaleL implements Runnable {

	private static EncoderFollower rightFollower;
	private static EncoderFollower leftFollower;
	public static double first;
	private static double yaw;

	public MPStartLScaleL() {
		
		first = Timer.getFPGATimestamp();
		SmartDashboard.putNumber("Auto Time Difference", 0);
		
		//Drive.gyro.reset();
		
		Waypoint[] leftLeftScale = new Waypoint[] {
				/*new Waypoint(0, 0, Pathfinder.d2r(0)),
				new Waypoint(3.5, 0, Pathfinder.d2r(0)),*/
				new Waypoint(0.7, 3.1, Pathfinder.d2r(0)),
				new Waypoint(5.0, 3.1, Pathfinder.d2r(0)),
				new Waypoint(7.3, 2.1, Pathfinder.d2r(0)),
		}; // Vel: 4.0
		
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.02, 4.0, 2.3, 50.0);
		
		Trajectory tra = this.generate(leftLeftScale, config);
		
		TankModifier modifier = new TankModifier(tra).modify(0.628);

	    Trajectory left = modifier.getLeftTrajectory();
	    
	    Trajectory right = modifier.getRightTrajectory();
	    
		rightFollower = new EncoderFollower(right);
		leftFollower = new EncoderFollower(left);
		rightFollower.configurePIDVA(Constants.RightP, 0, 0, Constants.RightV, Constants.RightA);
		leftFollower.configurePIDVA(Constants.LeftP, 0, 0, Constants.LeftV, Constants.LeftA);
		
		
	}
	
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
	
	public void run() {
		
		//System.out.println("Running Notifier");
		
		double heading = Math.toDegrees(rightFollower.getHeading());
			
		if(heading >180)
			heading = heading%180-180;
		yaw = Drive.gyro.getYaw();
		
		double error = heading + yaw;
		if(error>180)
			error = error-360;
		else if(error < -180)
			error = error+360;
			
		double kG = Constants.gyroPID;//0.8 * (-1.0/80.0);

		double turn = kG * error;
			
		//System.out.printf("Heading: %.2f  Gyro: %.2f  Turn:  %.2f \n", heading,-yaw,turn);
		double calc = (rightFollower.calculate(Drive.rightMain.getSelectedSensorPosition(0)));
		double calc2 = (leftFollower.calculate(Drive.leftMain.getSelectedSensorPosition(0)));
		// Driving forward
		//System.out.println("Left Driving: " + (calc2 + turn));
		//System.out.println("Right Driving: " + (calc - turn));
		Drive.rightMain.set(ControlMode.PercentOutput, (calc - turn));
		Drive.leftMain.set(ControlMode.PercentOutput, (calc2 + turn));
		
		SmartDashboard.putNumber("Auto Time Difference", Timer.getFPGATimestamp() - first);
		first = Timer.getFPGATimestamp();
		
		SmartDashboard.putNumber("Gyro", -yaw);
		//System.out.println("LSent: " + (calc2+turn) + "RSent: " + (calc-turn) + "LEnc: " + Drive.leftMain.getSelectedSensorVelocity(0) + "\tREnc: " + Drive.rightMain.getSelectedSensorVelocity(0) + "\tLVel: " + leftFollower.getSegment().velocity + "\tRVel: " + rightFollower.getSegment().velocity);
			

		SmartDashboard.putNumber("Left Enc Vel", Drive.leftMain.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Right Enc Vel", Drive.rightMain.getSelectedSensorVelocity(0));
		
	}
	
	public boolean isLastPoint(){
		
		System.out.println("Finished");
		
		return (rightFollower.isFinished()&&leftFollower.isFinished());
		
	}

	public void startPath() {

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ START AUTO~~~~~~~~~~~~~");
		rightFollower.configureEncoder(Drive.rightMain.getSelectedSensorPosition(0), 1024, .102);
		leftFollower.configureEncoder(Drive.leftMain.getSelectedSensorPosition(0), 1024, .102);
		SmartDashboard.putNumber("Gyro", -yaw);
		
		
	}
	
	public void disabled() {
		
		SmartDashboard.putNumber("Gyro", -yaw);

		//SmartDashboard.putNumber("Left Enc Vel", Drive.leftMain.getSelectedSensorVelocity(0));
		//SmartDashboard.putNumber("Right Enc Vel", Drive.rightMain.getSelectedSensorVelocity(0));
		
	}
	
}
	
	

