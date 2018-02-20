/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
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
			new Waypoint(0, 0, Pathfinder.d2r(0)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
		    new Waypoint(2,0,0),
//		    new Waypoint(5,-2,Pathfinder.d2r(270)),
//		    new Waypoint(0, 0, Pathfinder.d2r(0)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
//		    new Waypoint(3,0,0),
//		    new Waypoint(5,-2,Pathfinder.d2r(270)),
//		    new Waypoint(7,-4,Pathfinder.d2r(0)),
//		    new Waypoint(9,-2, Pathfinder.d2r(90)),
//		    new Waypoint(7,0,Pathfinder.d2r(180)),
//		    new Waypoint(5,-2,Pathfinder.d2r(270)),
//		    new Waypoint(3,-4,Pathfinder.d2r(180)),
//		    new Waypoint(1,-2, Pathfinder.d2r(90)),
//		    new Waypoint(3,0,0)
			/*new Waypoint(0, 0, Pathfinder.d2r(0)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
		    new Waypoint(4,0,0),
		    new Waypoint(6,-2,Pathfinder.d2r(270)),
		    new Waypoint(4,-4,Pathfinder.d2r(180)),
		    new Waypoint(2,-2, Pathfinder.d2r(90)),
		    new Waypoint(4,0,0),
		    new Waypoint(6,-2,Pathfinder.d2r(270)),
		    new Waypoint(4,-4,Pathfinder.d2r(180)),
		    new Waypoint(2,-2, Pathfinder.d2r(90)),
		    new Waypoint(4,0,0),
		    new Waypoint(6,-2,Pathfinder.d2r(270)),
		    new Waypoint(4,-4,Pathfinder.d2r(180)),
		    new Waypoint(2,-2, Pathfinder.d2r(90)),
		    new Waypoint(4,0,0),
		    new Waypoint(6,-2,Pathfinder.d2r(270)),
		    new Waypoint(4,-4,Pathfinder.d2r(180)),
		    new Waypoint(2,-2, Pathfinder.d2r(90)),
		    new Waypoint(4,0,0),*/
			 /*new Waypoint(0, 0, Pathfinder.d2r(0)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
			    new Waypoint(3,0,0),
			    new Waypoint(4,-1,Pathfinder.d2r(315)),
			    new Waypoint(5,-2,Pathfinder.d2r(0)),
			    new Waypoint(6,-1, Pathfinder.d2r(90)),
			    new Waypoint(5,0,Pathfinder.d2r(180)),
			    new Waypoint(4,-1,Pathfinder.d2r(225)),
			    new Waypoint(3,-2,Pathfinder.d2r(180)),
			    new Waypoint(2,-1, Pathfinder.d2r(90)),
			    new Waypoint(3,0,0)*/
		    
		   // new Waypoint(2,6,Pathfinder.d2r(180)),
		   // new Waypoint(5.5,5,Pathfinder.d2r(180))// Waypoint @ x=-2, y=-2, exit angle=0 radians
		};
	Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.01, 4.0, 4.0, 50.0);

	Trajectory tra = Pathfinder.generate(points, config);
	
	
	 TankModifier modifier = new TankModifier(tra).modify(0.622);

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
		
		rightMain.setInverted(false);
		leftMain.setInverted(false);
		leftFollow.setInverted(false);
		leftFollow2.setInverted(false);
		
		rightMain.setSensorPhase(false);
		leftMain.setSensorPhase(false);
		
		rightFollow2.follow(rightMain);
		rightFollow.follow(rightMain);
		leftFollow2.follow(leftMain);
		leftFollow.follow(leftMain);
		
		enc.configurePIDVA(0.19, 0, 0, 0.03, 0.09); //Ka = 0.3
		enc2.configurePIDVA(0.19, 0, 0, 0.03, 0.09);
		
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
		
		double calc = (enc.calculate(rightMain.getSelectedSensorPosition(0)));
		double calc2 = (enc2.calculate(leftMain.getSelectedSensorPosition(0)));

		rightMain.set(ControlMode.PercentOutput, calc - turn);
		leftMain.set(ControlMode.PercentOutput, -(calc2 + turn));
		
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
		
		double xStick = stick.getRawAxis(4);
		double yStick = -stick.getRawAxis(1);
		if(Math.abs(xStick) < 0.15)
			xStick = 0;
		if(Math.abs(yStick) <0.15)
			yStick = 0;
		rightMain.set(-(yStick-xStick));
		leftMain.set(yStick+xStick);
		
	}

	
	@Override
	public void testPeriodic() {
	}
}
