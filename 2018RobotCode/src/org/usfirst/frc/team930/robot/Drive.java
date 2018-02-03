package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;

public class Drive {
	
	public static final WPI_TalonSRX rightMain = new WPI_TalonSRX(Constants.rightMainTalonID);  
	public static final WPI_TalonSRX leftMain = new WPI_TalonSRX(Constants.leftMainTalonID); 
	public static final VictorSPX rightFollow = new VictorSPX(Constants.rightFollowVictorID);    
	public static final VictorSPX leftFollow = new VictorSPX(Constants.leftFollowVictorID);   
	public static final VictorSPX rightFollow2 = new VictorSPX(Constants.rightFollow2VictorID);     
	public static final VictorSPX leftFollow2 = new VictorSPX(Constants.leftFollow2Victor);
	public static final AHRS gyro = new AHRS(SerialPort.Port.kUSB);
	
	public static void init(){
		rightFollow2.follow(rightMain);
		rightFollow.follow(rightMain);
		leftFollow2.follow(leftMain);
		leftFollow.follow(leftMain);
	}
	
	public static void run(Joystick stick1){
		double xStick = -stick1.getRawAxis(Constants.rightXaxis);
		double yStick = stick1.getRawAxis(Constants.leftYaxis);
		if(Math.abs(xStick) < Constants.deadBand)
			xStick = 0;
		if(Math.abs(yStick) < Constants.deadBand)
			yStick = 0;
		rightMain.set(-(yStick-xStick));
		leftMain.set(yStick+xStick);
	}
	
}
