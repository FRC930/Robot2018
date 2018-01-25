/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	static AutoRoutine auto;
	SendableChooser<Routines> autoChooser = new SendableChooser<Routines>();
		
	public static WPI_TalonSRX rightMain = new WPI_TalonSRX(0);  //Declarations for talons
	public static WPI_TalonSRX leftMain = new WPI_TalonSRX(1);  //These will be the main motor controllers
	VictorSPX rightFollow = new VictorSPX(2);     //Declarations for victors that are
	VictorSPX leftFollow = new VictorSPX(3);   //followers to the talons
	VictorSPX rightFollow2 = new VictorSPX(4);     //Declarations for victors that are
	VictorSPX leftFollow2 = new VictorSPX(5);   //followers to the talons
	
	MPNotifier mp = new MPNotifier();
	Notifier n = new Notifier(mp);
	
	enum Routines {
		
		 ROUTINE1,
		 ROUTINE2
		 
	}

	@Override
	public void robotInit() {
		
		autoChooser.addObject("Routine 1", Routines.ROUTINE1);
        autoChooser.addObject("Routine 2", Routines.ROUTINE2);
        SmartDashboard.putData("Auto Routines", autoChooser);
        
        rightFollow.follow(rightMain);   //Sets the victors to follow their 
		leftFollow.follow(leftMain);   //respective talons
		rightFollow2.follow(rightMain);   //Sets the victors to follow their 
		leftFollow2.follow(leftMain);   //respective talons
        
	}

	@Override
	public void autonomousInit() {
		
		Enum routine = Routines.ROUTINE1;//(Enum) autoChooser.getSelected();
		String variation = "LRL";//DriverStation.getInstance().getGameSpecificMessage();
		
		auto = new AutoRoutine(routine, variation);
		
		n.startPeriodic(0.01);
				
	}

	@Override
	public void autonomousPeriodic() {
		
		//System.out.println(Timer.getFPGATimestamp());
		
		//auto.run();
		
	}

	@Override
	public void teleopPeriodic() {
		
	}

	@Override
	public void testPeriodic() {
	}
	
	@Override
	public void disabledInit() {
		
		n.stop();
		
	}
}
