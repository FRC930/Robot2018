package org.usfirst.frc.team930.robot;

import org.opencv.core.Mat;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Utilities {
	
	//public static Compressor comp = new Compressor(0);
	public static PowerDistributionPanel pdp = new PowerDistributionPanel(0);
	public static UsbCamera camera = new UsbCamera("Camera", Constants.cameraDeviceID);
	public static CvSink cvSink;
	public static CvSource outputStream;
	public static Mat source = new Mat();
	public static Mat output = new Mat();
	
	public static void turnOnCompressor() {
		
		//comp.setClosedLoopControl(true);
		
	}
	
	public static double getPDPCurrent() {
		
		return pdp.getCurrent(Constants.pdpIntakePort);
		
	}
	
	public static void updateDashboard() {
		SmartDashboard.putBoolean("Toggle Camera", false);
	}
	
	public static void startCapture() {
		CameraServer.getInstance().startAutomaticCapture("Robot Camera", Constants.cameraDeviceID);
		camera.setResolution(Constants.cameraResWidth, Constants.cameraResHeight);
		camera.setFPS(30);
		cvSink = CameraServer.getInstance().getVideo();
		outputStream = CameraServer.getInstance().putVideo("Video", Constants.cameraResWidth, Constants.cameraResHeight);
	}

}
