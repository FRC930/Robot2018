package org.usfirst.frc.team930.robot.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.CircularBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DrivetrainCharacterizer extends Command {
	
	public static final WPI_TalonSRX rightMain = new WPI_TalonSRX(1);  
	public static final WPI_TalonSRX leftMain = new WPI_TalonSRX(4); 
	public static final VictorSPX rightFollow = new VictorSPX(2);    
	public static final VictorSPX leftFollow = new VictorSPX(5);   
	public static final VictorSPX rightFollow2 = new VictorSPX(3);     
	public static final VictorSPX leftFollow2 = new VictorSPX(6);

	public static enum TestMode {
		QUASI_STATIC, STEP_VOLTAGE;
	}

	public static enum Direction {
		Forward, Backward;
	}

	private final TestMode mode;
	private final Direction direction;
	
	double leftEncoder;
	double rightEncoder;

	public DrivetrainCharacterizer(TestMode mode, Direction direction) {
		this.mode = mode;
		this.direction = direction;
	}

	private FileWriter fw;

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
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
		
		leftEncoder = leftMain.getSelectedSensorVelocity(0);
		rightEncoder = rightMain.getSelectedSensorVelocity(0);

		String name;
		double scale;
		if (direction.equals(Direction.Forward)) {
			name = "Forward";
			scale = 1;
		} else {
			name = "Backward";
			scale = -1;
		}

		String path = "/U/DriveCharacterization/" + name;

		if (mode.equals(TestMode.QUASI_STATIC)) {
			System.out.println("QUASI STATIC");
			System.out.println(rightMain.configOpenloopRamp(90, 10).name());
			System.out.println(leftMain.configOpenloopRamp(90, 10).name());
			path = path + "QuasiStatic.csv";
			// voltageStep = 1 / 24.0 / 100.0 * scale;
			//Robot.drivetrain.drive.tankDrive(1 * scale, 1 * scale);
			leftMain.set(1 * scale);
			rightMain.set(1 * scale);
		} else {
			System.out.println("STEP");
			System.out.println(rightMain.configOpenloopRamp(0, 10).name());
			System.out.println(leftMain.configOpenloopRamp(0, 10).name());
			path = path + "StepVoltage.csv";
			double speed = 0.7;
			//Robot.drivetrain.drive.tankDrive(speed * scale, speed * scale);
			leftMain.set(speed * scale);
			rightMain.set(speed * scale);
		}
		try {
			File f = new File(path);
			if (f.exists()) {
				f.delete();
			}
			fw = new FileWriter(f, true);
			fw.write("");
			fw.flush();
			fw.write("LeftVolt, LeftVel, LeftAcc, RightVolt, RightVel, RightAcc\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int i = 0;
	private int length = 3;
	private final CircularBuffer timeBuff = new CircularBuffer(length);
	private final CircularBuffer leftVelBuff = new CircularBuffer(length);
	private final CircularBuffer rightVelBuff = new CircularBuffer(length);

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double time = Timer.getFPGATimestamp();
		double leftVel = ((leftMain.getSelectedSensorVelocity(0) - leftEncoder) * 10 * 4 * Math.PI) / (1024.0);
		double rightVel = ((rightMain.getSelectedSensorVelocity(0) - rightEncoder) * 10 * 4 * Math.PI) / (1024.0);
		double leftVolt = leftMain.getMotorOutputVoltage();
		double rightVolt = leftMain.getMotorOutputVoltage();
		timeBuff.addLast(time);
		leftVelBuff.addLast(leftVel);
		rightVelBuff.addLast(rightVel);
		if (i < length - 1) {
			i++;
			return;
		}
		double dt = time - timeBuff.removeFirst();
		double leftDv = leftVel - leftVelBuff.removeFirst();
		double rightDv = rightVel - rightVelBuff.removeFirst();
		double leftAcc = leftDv / dt;
		double rightAcc = rightDv / dt;
		String result = leftVolt + ", " + leftVel + ", " + leftAcc + ", " + rightVolt + ", " + rightVel + ", "
				+ rightAcc + "\n";
		try {
			fw.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		//Robot.drivetrain.drive.tankDrive(0, 0);
		leftMain.set(0);
		rightMain.set(0);
		System.out.println(rightMain.configOpenloopRamp(0, 10).name());
		System.out.println(leftMain.configOpenloopRamp(0, 10).name());
		try {
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}