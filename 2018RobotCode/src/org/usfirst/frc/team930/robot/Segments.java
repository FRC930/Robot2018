package org.usfirst.frc.team930.robot;

public class Segments {
	
	public boolean seg1A() {
		
		boolean isFinished = false;
		
		if(AutoHandler.myMP1A.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	public boolean seg2A() {
		
		boolean isFinished = false;
		
		if(AutoHandler.myMP2A.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}

	public boolean seg2B() {
	
		boolean isFinished = false;
		
		if(AutoHandler.myMP2B.isLastPoint()) {
			isFinished = true;
		}
	
		return isFinished;
	
	}
	
	public boolean seg2C() {
		
		boolean isFinished = false;
		
		if(AutoHandler.myMP2C.isLastPoint()) {
			isFinished = true;
		}
	
		return isFinished;
	
	}
	
	public boolean seg3A() {
		
		boolean isFinished = false;
		
		if(AutoHandler.myMP3A.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	public boolean seg4A() {
		
		boolean isFinished = false;
		
		if(AutoHandler.myMP4A.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	public boolean seg5A() {
		
		boolean isFinished = false;
		
		if(AutoHandler.myMP5A.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	public boolean seg6A() {
		
		boolean isFinished = false;
		
		if(AutoHandler.myMP6A.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	public boolean seg7A() {
		
		boolean isFinished = false;
		
		if(AutoHandler.myMP7A.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	public boolean seg8A() {
		
		boolean isFinished = false;
		
		if(AutoHandler.myMP8A.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	public boolean segGyroTurn() {
		
		boolean isFinished = false;
		
		if(AutoHandler.myAutoGT.isFinished()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}

}
