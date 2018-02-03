package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;

public class Drive {
	
	public static final Joystick stick = new Joystick(0);
	public static final WPI_TalonSRX rightMain = new WPI_TalonSRX(4);  
	public static final WPI_TalonSRX leftMain = new WPI_TalonSRX(1); 
	public static final VictorSPX rightFollow = new VictorSPX(2);    
	public static final VictorSPX leftFollow = new VictorSPX(3);   
	public static final VictorSPX rightFollow2 = new VictorSPX(4);     
	public static final VictorSPX leftFollow2 = new VictorSPX(5);
	public static final AHRS gyro = new AHRS(SerialPort.Port.kUSB);
	
	public static void init(){
		rightFollow2.follow(rightMain);
		rightFollow.follow(rightMain);
		leftFollow2.follow(leftMain);
		leftFollow.follow(leftMain);
	}
	
	public static void run(Joystick stick1){
		double xStick = -stick1.getRawAxis(4);
		double yStick = stick1.getRawAxis(1);
		if(Math.abs(xStick) < 0.15)
			xStick = 0;
		if(Math.abs(yStick) <0.15)
			yStick = 0;
		rightMain.set(-(yStick-xStick));
		leftMain.set(yStick+xStick);
	}
	
}
