package org.usfirst.frc.team930.robot;

import java.util.ArrayList;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 * CameraHandler is a class that creates, operates, and maintains a camera server that automatically detects yellow 
 * cube-shaped objects and drives a robot towards the closest of any such object that passes its filters. Note that the 
 * external class CubeDetectorTest.java should also be added to a program so that this class can function.
 * 
 * @author Matthew Ryczek
 */
public class CameraHandler implements Runnable
{
	/**The amount of periods that are allowed to pass without an update from the Camera Server until the CameraHandler is
	 * forced to pause and wait*/
	private static final int ACCEPTABLE_CAMERA_LAG = 1000;
	
	/**The object used for Camera Server synchronization*/
	private final Object serverObject = new Object();
	
	/**The width of the images that the camera will be recording*/
	private final int IMG_WIDTH;
	
	/**The height of the images that the camera will be recording*/
	private final int IMG_HEIGHT;
	
	/**The largest size of a cube that could realistically pass the filter*/
	private final double MAX_AREA;
	
	/**The object used to detect cubes in images*/
	private static CubeDetectorTest CDT;
	
	/**The thread object that runs the camera alongside the main thread*/
	private VisionThread visionThread;
	
	/**The center value of a detected cube*/
	private double centerX = 0;
	
	/**As long as the camera is running properly, this will be set to true. If anything stops or slows down the camera
	 * unexpectedly, however, this will be set to false and halt CameraHandler's influence on driving.*/
	private boolean cameraUpdated = false;
	
	/**If a cube passed the last filtering, this will be set to true. Otherwise, this will be set to false.*/
	private boolean cubeFound = false;
	
	/**The size of the picture of the most recent cube to be detected*/
	private double cubeArea = 0.0;
	
	/**The amount of time that has passed since the Camera Server's last update. If this grows too large, cameraUpdated will
	 * be set to false and halt CameraHandler's influence on driving.*/
	private int updateLag = 0;
	
	/**Determines when the camera is allowed to pass images through its filter and drive the robot. Note that this has no
	 * influence on the camera server or whether or not the robot's camera is recording images.*/
	private boolean enabled = false;
	
	DifferentialDrive drive;
	
	/**
	 * CameraHandler is a class that creates, operates, and maintains a camera server that automatically detects yellow 
	 * cube-shaped objects and drives a robot towards the closest of any such object that passes its filters. Note that the
	 * external class CubeDetectorTest.java should also be added to a program so that this class can function.
	 * 
	 * @param width The width of the images that the camera will be recording
	 * @param height The height of the images that the camera will be recording
	 */
	public CameraHandler(int width, int height)
	{
		drive = new DifferentialDrive(Drive.leftMain, Drive.rightMain);
		
		IMG_WIDTH = width;
		IMG_HEIGHT = height;
		MAX_AREA = (IMG_WIDTH * 0.95) * (IMG_HEIGHT * 0.95);
		
		CDT = new CubeDetectorTest(IMG_WIDTH, IMG_HEIGHT);
		
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	    camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
	    
	    visionThread = new VisionThread(camera, CDT, pipeline -> 
	    {
	    	if(enabled)
	    	{
		    	ArrayList<MatOfPoint> filteredContours = pipeline.filterContoursOutput();
		    	
		    	if (!filteredContours.isEmpty()) 
		        {
		    		//The robot only follows the closest cube. In this case, distance to cube is based on its size.
		    		Rect closest = null;
		    		double centerX;
		    		
		    		for(MatOfPoint mat : filteredContours)
		    		{
		    			Rect rect = Imgproc.boundingRect(mat);
		    			
		    			if(closest == null || closest.area() < rect.area()) closest = rect;
		    		}
		    		
		    		centerX = closest.x + closest.width / 2.0 - IMG_WIDTH / 2.0;
		    		
		        	System.out.println("Detected object with an X value of " + closest.x + " and a center value of "
		        			+ centerX);
		        	
		            synchronized (serverObject) 
		            {
		            	System.out.println("Synchronizing (true; " + closest.x + " + " + closest.width + " / 2 - " 
		            			+ IMG_WIDTH + " / 2)");
		                this.centerX = centerX;
		                cubeArea = closest.area();
			            cubeFound = true;
		            }
		        }
		        else 
		        {
		        	System.out.println("No object passed the filer.");
		        	synchronized (serverObject)
		        	{
		        		System.out.println("Synchronizing (false)");
			            cubeFound = false;
		        	}
		        }
	            
	            cameraUpdated = true;
	            updateLag = 0;
	    	}
	    });
	    
	    visionThread.start();
	}
	
	@Override
	public void run() 
	{
		run(true, 0.75, drive);
	}
	
	/**
	 * Periodically, this method should be called in order to operate a robot's drive-trains in the appropriate direction 
	 * and speed. These are in relation to the location and distance of the closest detected cube.
	 * 
	 * @param enableMotors A debug boolean that lets you test this function without running the robot around, and without
	 * temporarily removing the list of drivers
	 * @param baseSpeed The default forward speed that the robot will be driven at by this method. Note that turning speed is
	 * proportional to this speed; at max baseSpeed, the robot will be unable to turn, but at 0 baseSpeed, the robot will only
	 * be able to turn. [-1.0 to 1.0]
	 * @param drives A list of all the Differential Drives to be driven by this method
	 */
	public void run(boolean enableMotors, double baseSpeed, DifferentialDrive... drives)
	{
		if(enabled)
		{
			//If a cube passed the last filtering, this will be set to true. Otherwise, this will be set to false.
			boolean cubeFound;
			
			//The center value of a detected cube
			double centerX;
			
			//The size of the picture of the most recent cube to be detected
			double cubeArea;

		    //Speed is the amount of forward momentum that the robot has.
			double speed;

	    	//The value that speed will be increased and decreased by in order to rotate in the correct direction
			double turn;
			
		    synchronized (serverObject) 
		    {
		        centerX = this.centerX;
		        cubeFound = this.cubeFound;
		        cubeArea = this.cubeArea;
		    }
	        
	        if(updateLag++ > ACCEPTABLE_CAMERA_LAG - 1) cameraUpdated = false;
		    
		    if(cameraUpdated)
		    {
			    //If cube was detected, drive forward while turning. Otherwise, continue turning but don't drive forward
		    	
			    turn = 
			    		//The direction itself, calculated by finding the ratio of the detected object's center to the image's center
			    		(centerX / (IMG_WIDTH / 2.0)
			    		//If a cube was found, turn less in order to drive forward. Otherwise, turn in place
			    		* (cubeFound ? 1.0 - Math.abs(baseSpeed) : 0.5)) 
			    		//The turn rate will decrease as the cube grows larger, as cubes only grow larger when they get closer
			    		* ((MAX_AREA - cubeArea) / MAX_AREA);
			    
			    speed = 
			    		//Only drive forward if a cube is in sight. Otherwise, turn in place to try and find one or another to chase
			    		(cubeFound ? baseSpeed : 0.0) 
			    		//The forward speed will decrease as the cube grows larger, as cubes only grow larger when they get closer
			    		* ((MAX_AREA - cubeArea) / MAX_AREA);
			    
			    if(enableMotors) multiTankDrive(speed - turn, speed + turn, drives);
			    else multiTankDrive(0, 0, drives);
			    
			    if(updateLag == 1)
			    	System.out.println("Turning at angle " + turn + ", speed " + speed + ", and area " + cubeArea + 
			    		" towards an object with a center value of " + centerX + ".");
		    }
		    else 
		    {
		    	multiTankDrive(0, 0, drives);
		    	
		    	//Any time updateLag reaches an exact multiple of 1000 (e.g. 1000, 2000, 3000, etc)...
		    	if(updateLag % ACCEPTABLE_CAMERA_LAG == 0) 
		    		//Warn user that the camera seems to not be working, or is being unusually slow
		    		System.out.println("Warning: camera hasn't updated since " + updateLag + " periods.");
		    }
		}
	}
	
	/**
	 * Allows the CameraHandler to begin passing the images it takes through a filter to detect yellow cube-like objects. This 
	 * also allows the run() function to control the robot according to what has or has not been detected in the filter.
	 */
	public void enable()
	{
		if(enabled == false)
		{
			enabled = true;
			System.out.println("Camera has been fully enabled.");
		}
	}
	
	/**
	 * Prevents the CameraHandler from passing any more images it takes through its filter, and will also halt any influence it
	 * has over the robot's control. Note that any camera ran by this will still continue to take images, despite doing nothing
	 * with them.
	 */
	public void disable()
	{
		if(enabled)
		{
			enabled = false;
			System.out.println("Camera is now in standby mode. It will still record and show images, but it will do nothing else.");
		}
	}
	
	/**
	 * Determines when the CameraHandler is allowed to pass images through its filter and drive the robot. Note that this has 
	 * no influence on the camera server or whether or not the robot's camera is recording images.
	 * 
	 * @return True when enabled, false when disabled
	 */
	public boolean enabled()
	{
		return enabled;
	}
	
	/**
	 * This function allows a robot to control any amount of pairs of its motors using a single function call. 
	 * Other than that, it works just like the regular tankDrive() method. Note that if no Differential Drives are provided, 
	 * this function will not run. Null Differential Drives will automatically be skipped as well.
	 * 
	 * @param leftSpeed The robot's left side speed along the X axis [-1.0 to 1.0]. Forward is positive.
	 * @param rightSpeed The robot's right side speed along the X axis [-1.0 to 1.0]. Forward is positive.
	 * @param drives Each of the DifferentialDrives to be affected by this function. 
	 */
	private void multiTankDrive(double leftSpeed, double rightSpeed, DifferentialDrive... drives)
	{
		if(drives.length == 0) return;
		
		for(DifferentialDrive drive : drives)
		{
			if(drive != null) drive.tankDrive(leftSpeed, rightSpeed);
		}
	}
}
