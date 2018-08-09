package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * Initializing drive motors and sensors and controlling driving
 */
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
	
	/*
	 * Inverts the motors if needed, sets sensor phase and followers
	 */
	public static void init(){
		gyro.reset();
		
		rightMain.setInverted(false);
		leftMain.setInverted(true);
		leftFollow.setInverted(true);
		leftFollow2.setInverted(true);
		
		// Competition robot
		rightMain.setSensorPhase(false);
		leftMain.setSensorPhase(true);
		
		invertMotorsForwards();
		
		rightFollow2.follow(rightMain);
		rightFollow.follow(rightMain);
		leftFollow2.follow(leftMain);
		leftFollow.follow(leftMain);
	}
	
	/*
	 * Gets the joystick values, calculates throttle and turn, and sends it to the motors
	 */
	public static void run(double xStick, double yStick){
		
		System.out.println("Gyro: " + gyro.getYaw() + "  Connected: " + gyro.isConnected());
		yStick = Math.pow(yStick,3);
		xStick = Math.pow(xStick, 3);
		
		// Deadband avoids movement caused by joystick error near 0
		if(Math.abs(xStick) < Constants.driveDeadBand)
			xStick = 0;
		if(Math.abs(yStick) < Constants.driveDeadBand)
			yStick = 0;
		yStick *= -1;
		
		rightMain.set((yStick-xStick));
		leftMain.set((yStick+xStick));
	}
	
	/*
	 * Sets left and right motors to a specific speed
	 */
	public static void runAt(double left, double right){
		rightMain.set(right);
		leftMain.set(left);
	}
	
	/*
	 * Returns true if gyro or either left or right encoder are not working
	 */
	public static boolean checkSensors(){
		
		if(!gyro.isConnected()){
			System.out.println("Killing Gyro");
			return true;
		}
		if(Math.abs(leftMain.getMotorOutputPercent())> 0.1 && leftMain.getSelectedSensorVelocity(0) == 0){
			
			leftMotorCounter++;
			
			if(leftMotorCounter >= 4){
				System.out.println("Killing Left");
				return true;
			}
			
		}
		
		else if((Math.abs(rightMain.getMotorOutputPercent())>0.1) && (rightMain.getSelectedSensorVelocity(0) == 0)){
			
			rightMotorCounter++;
			
			if(rightMotorCounter >= 4){
				System.out.println("Killing Right");
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Sets both sensor check counters to 0
	 */
	public static void resetSensorCheck() {
		
		leftMotorCounter = 0;
		rightMotorCounter = 0;
		
	}
	
	/*
	 * Changes sensor phase of encoders
	 */
	public static void changeSensorPhase(boolean left, boolean right) {
		
		rightMain.setSensorPhase(right);
		leftMain.setSensorPhase(left);
		
	}
	
	/*
	 * Inverts motors backwards
	 */
	public static void invertMotorsBackwards() {
		
		rightMain.setInverted(true);
		rightFollow.setInverted(true);
		rightFollow2.setInverted(true);
		leftMain.setInverted(false);
		leftFollow.setInverted(false);
		leftFollow2.setInverted(false);
		
	}
	
	/*
	 * Inverts motors forwards
	 */
	public static void invertMotorsForwards() {
		
		rightMain.setInverted(false);
		rightFollow.setInverted(false);
		rightFollow2.setInverted(false);
		leftMain.setInverted(true);
		leftFollow.setInverted(true);
		leftFollow2.setInverted(true);
		
	}
}
