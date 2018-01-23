package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class blah implements Runnable{
	double count1 =0;
	
	WPI_TalonSRX rightMain = new WPI_TalonSRX(0);  //Declarations for talons
	WPI_TalonSRX leftMain = new WPI_TalonSRX(1);  //These will be the main motor controllers
	VictorSPX rightFollow = new VictorSPX(2);     //Declarations for victors that are
	VictorSPX leftFollow = new VictorSPX(3);  
	VictorSPX right2Follow = new VictorSPX(4);
	VictorSPX left2Follow = new VictorSPX(5);
	
	Joystick stick = new Joystick(0);
	
  public void run(){
	  System.out.println("Time: " +((Timer.getFPGATimestamp()-count1)*1000));
	  count1 = Timer.getFPGATimestamp();
	  
	  rightMain.set(0);
	  leftMain.set(0);
	  rightFollow.follow(rightMain);
	  leftFollow.follow(leftMain);
	  left2Follow.follow(leftMain);
	  right2Follow.follow(rightMain);
	  
	  stick.getRawAxis(1);
	  stick.getRawAxis(4);
	  stick.getRawButtonPressed(1);	 
	  
  }
  
  }

