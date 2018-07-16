package motionProfile;

import java.io.File;

import org.usfirst.frc.team930.robot.Constants;
import org.usfirst.frc.team930.robot.Drive;
import org.usfirst.frc.team930.robot.Utilities;

import com.ctre.phoenix.motorcontrol.ControlMode;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

// Middle Right Switch 1
public class MPStartMSwitchR implements Runnable {
	
	private static EncoderFollower rightFollower;
	private static EncoderFollower leftFollower;

	public MPStartMSwitchR() {
		
		Waypoint[] rightRightScale = new Waypoint[] {
				new Waypoint(0.7, 3.1, Pathfinder.d2r(0)),
				new Waypoint(4.25, 3.1, Pathfinder.d2r(0)),
				new Waypoint(5.5, 2, Pathfinder.d2r(270)),
		}; // Vel: 3.0
		
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.02, 3.0, 2.3, 50.0);
		
		Trajectory tra = this.generate(rightRightScale, config);
		
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
		
		double heading = Math.toDegrees(rightFollower.getHeading());
			
		if(heading >180)
			heading = heading%180-180;
		double yaw = Drive.gyro.getYaw();
		
		double error = heading + yaw;
		if(error>180)
			error = error-360;
		else if(error < -180)
			error = error+360;
			
		double kG = Constants.gyroPID;

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
		rightFollower.configureEncoder(Drive.rightMain.getSelectedSensorPosition(0), 1024, .102);
		leftFollower.configureEncoder(Drive.leftMain.getSelectedSensorPosition(0), 1024, .102);
		
	}

}
