/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;


public class Robot extends IterativeRobot {
	
	Joystick stick = new Joystick(0);
	
	Timer time = new Timer();
	
	Waypoint[] points = new Waypoint[] {
			new Waypoint(0, 0, Pathfinder.d2r(0)),
			new Waypoint(7, 0, Pathfinder.d2r(0)),
			
			// Right Scale
			/*new Waypoint(0.7, 4.0, Pathfinder.d2r(0)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
		    new Waypoint(2.7, 2.1, Pathfinder.d2r(310)),
		    new Waypoint(5.1, 1.1,Pathfinder.d2r(346)),
		    new Waypoint(5.45, 1.02, Pathfinder.d2r(350)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
		    new Waypoint(7.2, 1.45, Pathfinder.d2r(45)),*/
		};
	Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.01, 6.0, 6.0, 50.0);

	Trajectory tra = Pathfinder.generate(points, config);
	
	
	 TankModifier modifier = new TankModifier(tra).modify(0.628);

     // Do something with the new Trajectories...
     Trajectory left = modifier.getLeftTrajectory();
     Trajectory right = modifier.getRightTrajectory();
	private EncoderFollower enc = new EncoderFollower(right);
	private EncoderFollower enc2 = new EncoderFollower(left);
	WPI_TalonSRX rightMain = new WPI_TalonSRX(1);  //Declarations for talons
	WPI_TalonSRX leftMain = new WPI_TalonSRX(4); 
	
	
	/*WPI_TalonSRX rightFollow = new WPI_TalonSRX(5);     //Declarations for victors that are
	WPI_TalonSRX leftFollow = new WPI_TalonSRX(2);   //followers to the talons
	WPI_TalonSRX rightFollow2 = new WPI_TalonSRX(6);     //Declarations for victors that are
	WPI_TalonSRX leftFollow2 = new WPI_TalonSRX(3);*/
	//These will be the main motor controllers
	VictorSPX rightFollow = new VictorSPX(2);     //Declarations for victors that are
	VictorSPX leftFollow = new VictorSPX(5);   //followers to the talons
	VictorSPX rightFollow2 = new VictorSPX(3);     //Declarations for victors that are
	VictorSPX leftFollow2 = new VictorSPX(6);
	
	AHRS gyro = new AHRS(SerialPort.Port.kUSB);
	
	
	@Override
	public void robotInit() {
		
		gyro.reset();
		rightMain.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		leftMain.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0 ,0);
		rightMain.setInverted(false);
		leftMain.setInverted(true);
		leftFollow.setInverted(true);
		leftFollow2.setInverted(true);
		
		rightMain.setSensorPhase(true);//true
		leftMain.setSensorPhase(false);
		
		rightFollow2.follow(rightMain);
		rightFollow.follow(rightMain);
		leftFollow2.follow(leftMain);
		leftFollow.follow(leftMain);
		
	/*	enc.configurePIDVA(0.01, 0, 0, 0.24, 0.0); //Kv = 0.24 //Ka = 0.05
		enc2.configurePIDVA(0.01, 0, 0, 0.24, 0.0);*/
		enc.configurePIDVA(0.9, 0, 0, 0.285, 0.05); //Kv = 0.24 //Ka = 0.05
		enc2.configurePIDVA(0.9, 0, 0, 0.285, 0.05);
		
	}


	@Override
	public void autonomousInit() {
		
		enc.configureEncoder(rightMain.getSelectedSensorPosition(0), 1024, .102);
		enc2.configureEncoder(leftMain.getSelectedSensorPosition(0), 1024, .102);

		gyro.reset();
		time.start();
		
	}


	@Override
	public void autonomousPeriodic() {
		if(time.get()>1){
		
		double heading = Math.toDegrees(enc.getHeading());
		if(heading >180)
			heading = heading%180-180;
		double yaw = gyro.getYaw();//(Math.toDegrees(enc.getHeading())-180)%180;
		/*if(gyro.getYaw()<0)
			yaw = yaw + 360;*/
		//System.out.printf("Velocity: %.3f  Position: %.2f \n", enc.getSegment().velocity,enc.getSegment().position); 
		
		double error = heading + yaw;
		if(error>180)
			error = error-360;
		else if(error < -180)
			error = error+360;
		double kG = -0.038;//0.8 * (-1.0/80.0);

		double turn = kG * error;
		//System.out.println("GYRO YAW value:   " +  gyro.getYaw());
		//System.out.println("Heading we are shooting for:  " + Math.toDegrees(enc.getHeading()));
		//System.out.println("Turn Value: " + turn);
		
		//System.out.printf("Turn: %.2f Heading: %.2f Gyro: %.2f\n", turn, enc.getHeading(), gyro.getYaw()); 
	
		double calc = (enc.calculate(rightMain.getSelectedSensorPosition(0)));
		double calc2 = (enc2.calculate(leftMain.getSelectedSensorPosition(0)));
		rightMain.set(ControlMode.PercentOutput, (calc -turn));
		leftMain.set(ControlMode.PercentOutput, (calc2 +turn));
		//System.out.println("Right: " + calc);
		//System.out.println("Left Side: " + leftMain.getSelectedSensorVelocity(0) + "Left: " + -calc2);
		//System.out.println("Pos: " + enc.getSegment().position);
		SmartDashboard.putNumber("Left", calc2);
		//SmartDashboard.putNumber("Left Pos", enc2.getSegment().position);
		SmartDashboard.putNumber("Encoder", leftMain.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("encoder2",(rightMain.getSelectedSensorVelocity(0)));
		
		System.out.printf("%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t\n", enc.getSegment().x, enc.getSegment().y, enc.getSegment().heading, enc.getSegment().velocity, ((rightMain.getSelectedSensorVelocity(0) * 10 * 4 * Math.PI * 0.305) / (1024.0 * 12.0)), gyro.getYaw());
		
		/*if(enc.isFinished() && enc2.isFinished()){}
		else{
		System.out.println(calc);
		System.out.println(calc2);
		}
		}*/
		}
		
	}
	
	public void teleopInit() {
		
		gyro.reset();
		
	}

	
	@Override
	public void teleopPeriodic() {
		
		/*gyro.setAngleAdjustment(180);*/
		System.out.println(gyro.getAngle()%360);
		
		double xStick = Math.pow(stick.getRawAxis(4), 3);
		double yStick = Math.pow(-stick.getRawAxis(1),3);
		if(Math.abs(xStick) < 0.005)
			xStick = 0;
		if(Math.abs(yStick) <0.005)
			yStick = 0;
		rightMain.set((xStick-yStick) * -1.0);
		leftMain.set((xStick+yStick));
		SmartDashboard.putNumber("Encoder", leftMain.getSelectedSensorVelocity(0));
	}

	
	@Override
	public void testPeriodic() {
	}
	
	@Override
	public void disabledInit(){
		System.out.println("Prettty fpoink faries");
	}
	
}
