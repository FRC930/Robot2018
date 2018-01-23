package org.usfirst.frc.team930.robot;

public class Routine1 implements Routine {
	
	private static String variation;
	private int segment = 1;
	private Segments segList = new Segments();
	private Actions actList = new Actions();
	
	public Routine1(String v) {
		
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
	
	public void variation1() {
		
		switch (this.segment) {
			case 1:
				System.out.println("VARIATION 1");
				if(segList.seg1())	{
					this.segment = 2;
					System.out.println("Segment 1 Done");
				}
				break;
			case 2:
				if(actList.act1()) {
					this.segment = 3;
					System.out.println("Action 1 Done");
				}
				break;
			case 3:
				System.out.println("DONE");
				break;
		}
		
	}
	
	public void variation2() {
		
		switch (this.segment) {
			case 1:
				System.out.println("VARIATION 1");
				if(segList.seg1())	{
					this.segment = 2;
					System.out.println("Segment 1 Done");
				}
				break;
			case 2:
				if(actList.act1()) {
					this.segment = 3;
					System.out.println("Action 1 Done");
				}
				break;
			case 3:
				break;
		}
		
	}

	public void variation3() {
		
		switch (this.segment) {
			case 1:
				System.out.println("VARIATION 1");
				if(segList.seg1())	{
					this.segment = 2;
					System.out.println("Segment 1 Done");
				}
				break;
			case 2:
				if(actList.act1()) {
					this.segment = 3;
					System.out.println("Action 1 Done");
				}
				break;
			case 3:
				break;
		}
		
	}

	public void variation4() {
		
		switch (this.segment) {
			case 1:
				System.out.println("VARIATION 1");
				if(segList.seg1())	{
					this.segment = 2;
					System.out.println("Segment 1 Done");
				}
				break;
			case 2:
				if(actList.act1()) {
					this.segment = 3;
					System.out.println("Action 1 Done");
				}
				break;
			case 3:
				break;
		}
		
	}
	
	public void reset() {
		
		this.segment = 1;
		
	}

}
