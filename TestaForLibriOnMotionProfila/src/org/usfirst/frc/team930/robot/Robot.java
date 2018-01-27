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

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;


public class Robot extends IterativeRobot {
	
	Joystick stick = new Joystick(0);
	
	Waypoint[] points = new Waypoint[] {
		    new Waypoint(0, 0, 0),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
		    new Waypoint(2, 0, Pathfinder.d2r(0))                        // Waypoint @ x=-2, y=-2, exit angle=0 radians
		};
	Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.01, 4.0, 4.0, 50.0);

	Trajectory tra = Pathfinder.generate(points, config);
	
	
	 TankModifier modifier = new TankModifier(tra).modify(0.5);

     // Do something with the new Trajectories...
     Trajectory left = modifier.getLeftTrajectory();
     Trajectory right = modifier.getRightTrajectory();
	private EncoderFollower enc = new EncoderFollower(right);
	private EncoderFollower enc2 = new EncoderFollower(left);
	WPI_TalonSRX rightMain = new WPI_TalonSRX(4);  //Declarations for talons
	WPI_TalonSRX leftMain = new WPI_TalonSRX(1); 
	
	WPI_TalonSRX rightFollow = new WPI_TalonSRX(5);     //Declarations for victors that are
	WPI_TalonSRX leftFollow = new WPI_TalonSRX(2);   //followers to the talons
	WPI_TalonSRX rightFollow2 = new WPI_TalonSRX(6);     //Declarations for victors that are
	WPI_TalonSRX leftFollow2 = new WPI_TalonSRX(3);
	//These will be the main motor controllers
	//VictorSPX rightFollow = new VictorSPX(2);     //Declarations for victors that are
	//VictorSPX leftFollow = new VictorSPX(3);   //followers to the talons
	//VictorSPX rightFollow2 = new VictorSPX(4);     //Declarations for victors that are
	//VictorSPX leftFollow2 = new VictorSPX(5);
	
	
	@Override
	public void robotInit() {
		
		/*rightFollow.follow(rightMain);   //Sets the victors to follow their 
		leftFollow.follow(leftMain);   //respective talons
		rightFollow2.follow(rightMain);   //Sets the victors to follow their 
		leftFollow2.follow(leftMain); */
		
		enc.configurePIDVA(0.9, 0, 0, 0.3, 0);
		enc2.configurePIDVA(0.9, 0, 0, 0.3, 0);
		rightFollow2.follow(rightMain);
		rightFollow.follow(rightMain);
		leftFollow2.follow(leftMain);
		leftFollow.follow(leftMain);
		
	}


	@Override
	public void autonomousInit() {
		
		enc.configureEncoder(rightMain.getSelectedSensorPosition(0), 1024, .102);
		enc2.configureEncoder(leftMain.getSelectedSensorPosition(0), 1024, .102);

		
		
		

		
	}


	@Override
	public void autonomousPeriodic() {
		double calc = (enc.calculate(rightMain.getSelectedSensorPosition(0)));
		double calc2 = (enc2.calculate(leftMain.getSelectedSensorPosition(0)));

		rightMain.set(ControlMode.PercentOutput, calc);
		leftMain.set(ControlMode.PercentOutput, calc2);
		
		if(enc.isFinished() && enc2.isFinished()){}
		else{
		System.out.println(calc);
		System.out.println(calc2);
		}
		
	}

	
	@Override
	public void teleopPeriodic() {
		
		double xStick = stick.getRawAxis(4);
		double yStick = stick.getRawAxis(1);
		
		rightMain.set(-(yStick-xStick));
		leftMain.set(yStick+xStick);
		
	}

	
	@Override
	public void testPeriodic() {
	}
}
