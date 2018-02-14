package org.usfirst.frc.team930.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Utilities {
	
	public static Compressor comp = new Compressor(0);
	public static PowerDistributionPanel pdp = new PowerDistributionPanel(0);
	public static UsbCamera camera = new UsbCamera("Camera", 0);
	
	public static void turnOnCompressor() {
		
		comp.setClosedLoopControl(true);
		
	}
	
	public static void getPDPCurrent() {
		
		pdp.getCurrent(Constants.pdpIntakePort);
		
	}
	
	public static void startCapture() {
		camera = CameraServer.getInstance().startAutomaticCapture();
	}
	
	public static void setResolution(int width, int height) {
		camera.setResolution(width, height);
	}

}
