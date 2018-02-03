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

	EncoderFollower enc;
	EncoderFollower enc2;
	Timer time;
	Drive d = new Drive();
	
	public void init(){
		time = new Timer();
		
		Waypoint[] points = new Waypoint[] {
				new Waypoint(0, 0, Pathfinder.d2r(0)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
			    new Waypoint(2,0,0),
			    new Waypoint(5,-2,Pathfinder.d2r(270)),
			   // new Waypoint(2,6,Pathfinder.d2r(180)),
			   // new Waypoint(5.5,5,Pathfinder.d2r(180))// Waypoint @ x=-2, y=-2, exit angle=0 radians
			};
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.01, 4.0, 4.0, 50.0);

		Trajectory tra = Pathfinder.generate(points, config);
		
		
		TankModifier modifier = new TankModifier(tra).modify(0.768);

	    // Do something with the new Trajectories...
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
	
	public void run(){
		if(time.get()>1){
			
			double heading = Math.toDegrees(enc.getHeading());
			if(heading >180)
				heading = heading%180-180;
			double yaw = Drive.gyro.getYaw();//(Math.toDegrees(enc.getHeading())-180)%180;
			/*if(gyro.getYaw()<0)
				yaw = yaw + 360;*/
			
			double error = heading + yaw;
			if(error>180)
				error = error-360;
			else if(error < -180)
				error = error+360;
			double kG = -0.025;//0.8 * (-1.0/80.0);

			double turn = kG * error;
				
			/*System.out.println("GYRO YAW value:   " +  gyro.getYaw());
			System.out.println("HEading we are shooting for:  " + Math.toDegrees(enc.getHeading()));
			System.out.println("Turn Value: " + turn);*/
			
			System.out.printf("Heading: %.2f  Gyro: %.2f  Turn:  %.2f \n", heading,-yaw,turn); 
			
			double calc = (enc.calculate(Drive.rightMain.getSelectedSensorPosition(0)));
			double calc2 = (enc2.calculate(Drive.leftMain.getSelectedSensorPosition(0)));

			Drive.rightMain.set(ControlMode.PercentOutput, calc - turn);
			Drive.leftMain.set(ControlMode.PercentOutput, -(calc2 + turn));
			
			/*if(enc.isFinished() && enc2.isFinished()){}
			else{
			System.out.println(calc);
			System.out.println(calc2);
			}
			}*/
			}
	}
	}

