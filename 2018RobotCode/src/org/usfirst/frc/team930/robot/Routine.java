package org.usfirst.frc.team930.robot;

public abstract class Routine {
	
	protected static String variation;
	protected int segment = 1;
	protected Segments segList = new Segments();
	protected Actions actList = new Actions();
	
	public Routine(String v) {
		
		variation = v;
		reset();
		
	}
	
	public void run() {
		
		switch (variation) {
			case "LRL":
				System.out.println("Running Variation 1");
				this.variation1();
				break;
			case "RLR":
				System.out.println("Running Variation 2");
				this.variation2();
				break;
			case "LLL":
				System.out.println("Running Variation 3");
				this.variation3();
				break;
			case "RRR":
				System.out.println("Running Variation 4");
				this.variation4();
				break;
		}
		
	}
	
	public abstract void variation1();
	
	public abstract void variation2();
	
	public abstract void variation3();
	
	public abstract void variation4();
	
	public void reset() {
		
		this.segment = 1;
		
	}

}
