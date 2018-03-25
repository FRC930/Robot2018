package motionProfile;

import java.io.File;

import org.usfirst.frc.team930.robot.Drive;
import org.usfirst.frc.team930.robot.Utilities;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

// Start R Double Scale R 1
public class MPStartRScaleR implements Runnable {
	
	private static EncoderFollower rightFollower;
	private static EncoderFollower leftFollower;
	private static double calc;
	private static double calc2;
	private static double heading;
	private static double turn;
	private static double yaw;

	public MPStartRScaleR() {
		
		//Drive.gyro.reset();
		
		Waypoint[] rightLeftScale = new Waypoint[] {
				new Waypoint(0.7, -3.1, Pathfinder.d2r(0)),
				new Waypoint(5.0, -3.1, Pathfinder.d2r(0)),
				new Waypoint(7.3, -2.1, Pathfinder.d2r(0)),
		}; // Vel: 3.0
		
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.02, 4.0, 2.3, 50.0);
		
		Trajectory tra = this.generate(rightLeftScale, config);
		
		TankModifier modifier = new TankModifier(tra).modify(0.628);

	    Trajectory left = modifier.getLeftTrajectory();
	    
	    Trajectory right = modifier.getRightTrajectory();
	    
		rightFollower = new EncoderFollower(right);
		leftFollower = new EncoderFollower(left);
		rightFollower.configurePIDVA(0.9, 0, 0, 0.289, 0.06);
		leftFollower.configurePIDVA(0.9, 0, 0, 0.289, 0.06);
		
	}
	
	public Trajectory generate(Waypoint[] waypoints, Trajectory.Config config) {
		//String hash = Utilities.hash(waypoints, config);
		//File pathFile = new File("/home/lvuser/" + hash + ".traj");
		//System.out.println("CAN WRITE TO FILE: " + pathFile.canWrite());
		//System.out.println("CAN READ FROM FILE: " + pathFile.canRead());
		//System.out.println("EXISTS: " + pathFile.exists());
		//pathFile.setWritable(true);
		//pathFile.setReadable(true);
		//System.out.println("CAN WRITE TO FILE: " + pathFile.canWrite());
		//System.out.println("CAN READ FROM FILE: " + pathFile.canRead());
		//System.out.println("POOOOO~~~~~OOOOP: " + pathFile.exists());
		Trajectory traj;
		
		//System.out.println("Trying to Reads");
		
		//if(pathFile.exists()) {
			//System.out.println("READING");
			//traj = Pathfinder.readFromFile(pathFile);
		//} else {
			traj = Pathfinder.generate(waypoints, config);
			//Pathfinder.writeToFile(pathFile, traj);
		//}
		
		return traj;
	}
	
	public void run() {
		
		heading = Math.toDegrees(rightFollower.getHeading());
			
		if(heading >180)
			heading = heading%180-180;
		yaw = Drive.gyro.getYaw();
		
		double error = heading + yaw;
		if(error>180)
			error = error-360;
		else if(error < -180)
			error = error+360;
			
		double kG = -0.038;//0.8 * (-1.0/80.0);

		turn = kG * error;
			
		//System.out.printf("Heading: %.2f  Gyro: %.2f  Turn:  %.2f \n", heading,-yaw,turn);
		calc = (rightFollower.calculate(Drive.rightMain.getSelectedSensorPosition(0)));
		calc2 = (leftFollower.calculate(Drive.leftMain.getSelectedSensorPosition(0)));
		
		SmartDashboard.putNumber("Right Motor Sent", (calc - turn));
		SmartDashboard.putNumber("Left Motor Sent", (calc2 - turn));
		SmartDashboard.putNumber("Left Enc Count", Drive.leftMain.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Right Enc Count", Drive.rightMain.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Left Seg Vel", leftFollower.getSegment().velocity);
		SmartDashboard.putNumber("Right Seg Vel", rightFollower.getSegment().velocity);
		SmartDashboard.putNumber("Heading", heading);
		SmartDashboard.putNumber("Gyro Received", -yaw);
		
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
	
	public void disabled() {
		SmartDashboard.putNumber("Right Motor Sent", (calc - turn));
		SmartDashboard.putNumber("Left Motor Sent", (calc2 - turn));
		SmartDashboard.putNumber("Left Enc Count", Drive.leftMain.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Right Enc Count", Drive.rightMain.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Left Seg Vel", leftFollower.getSegment().velocity);
		SmartDashboard.putNumber("Right Seg Vel", rightFollower.getSegment().velocity);
		SmartDashboard.putNumber("Heading", heading);
		SmartDashboard.putNumber("Gyro Received", -yaw);
	}

}
