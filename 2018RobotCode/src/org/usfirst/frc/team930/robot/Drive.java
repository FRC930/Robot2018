package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {
	
	public static final WPI_TalonSRX rightMain = new WPI_TalonSRX(Constants.rightMainTalonID);  
	public static final WPI_TalonSRX leftMain = new WPI_TalonSRX(Constants.leftMainTalonID); 
	public static final VictorSPX rightFollow = new VictorSPX(Constants.rightFollowVictorID);    
	public static final VictorSPX leftFollow = new VictorSPX(Constants.leftFollowVictorID);   
	public static final VictorSPX rightFollow2 = new VictorSPX(Constants.rightFollow2VictorID);     
	public static final VictorSPX leftFollow2 = new VictorSPX(Constants.leftFollow2Victor);
	public static final AHRS gyro = new AHRS(SerialPort.Port.kUSB);
	
	public static int leftMotorCounter = 0;
	public static int rightMotorCounter = 0;
	public static boolean check = false;
	
	public static void init(){
		rightMain.setInverted(false);
		rightFollow.setInverted(false);
		rightFollow2.setInverted(false);
		
		leftMain.setInverted(true);
		leftFollow.setInverted(true);
		leftFollow2.setInverted(true);
		
		rightFollow2.follow(rightMain);
		rightFollow.follow(rightMain);
		leftFollow2.follow(leftMain);
		leftFollow.follow(leftMain);
	}
	
	public static void run(double xStick, double yStick){
		
		System.out.println("Gyro: " + gyro.getYaw());
		
		updateDashboard();
		if(Math.abs(xStick) < Constants.deadBand)
			xStick = 0;
		if(Math.abs(yStick) < Constants.deadBand)
			yStick = 0;
		yStick *= -1;
		rightMain.set((yStick-xStick));
		leftMain.set((yStick+xStick));
	}
	public static void runAt(double left, double right){
		rightMain.set(right);
		leftMain.set(left);
	}
	public static void updateDashboard(){
		SmartDashboard.putNumber("Gyro", gyro.getYaw());
		SmartDashboard.putNumber("Left Encoder", leftMain.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Right Encoder", rightMain.getSelectedSensorVelocity(0));

	}
	public static boolean checkSensors(){
		
		if(!gyro.isConnected())
			check = true;
		
		else if(leftMain.get()!= 0 && leftMain.getSelectedSensorVelocity(0) == 0){
			leftMotorCounter++;
			if(leftMotorCounter >= 4){
				leftMotorCounter = 0;
				check= true;
			}
		}
		
		else if(rightMain.get()!=0 && rightMain.getSelectedSensorVelocity(0) == 0){
			rightMotorCounter++;
			if(rightMotorCounter >= 4){
				check= true;
				rightMotorCounter = 0;
			}
		}
		return check;
	}
}
