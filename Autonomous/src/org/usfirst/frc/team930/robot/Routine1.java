package org.usfirst.frc.team930.robot;

public class Routine1 extends Routine {
	
	public Routine1(String v) {
		
		super(v);
		
	}
	
	public void run() {
		
		switch (variation) {
			case "LRL":
				System.out.println("Runnning Variation 1");
				this.variation1();
				break;
			case "RLR":
				//variation2();
				break;
			case "LLL":
				//variation3();
				break;
			case "RRR":
				//variation4();
				break;
		}
		
	}
	
	public void variation1() {
		int x = 1;
		switch (segment) {
			case 1:
				System.out.println("VARIATION 1");
				if(segList.seg1())	{
					this.segment = 2;
					System.out.println("Segment 1 Done");
				}
				break;
			case 2:
				if(Actions.act1()) {
					this.segment = 3;
					System.out.println("Action 1 Done");
				}
				break;
		}
	}
	/*
	public static void variation2() {
		
		switch (segment) {
			case 1:
				if(Segments.seg1())
					segment = 2;
				break;
			case 2:
				if(Actions.act1())
					segment = 3;
				break;
		}
	}

	public static void variation3() {
		
		switch (segment) {
			case 1:
				if(Segments.seg1())
					segment = 2;
				break;
			case 2:
				if(Actions.act1())
					segment = 3;
				break;
		}
	}

	public static void variation4() {
		
		switch (segment) {
			case 1:
				if(Segments.seg1())
					segment = 2;
				break;
			case 2:
				if(Actions.act1())
					segment = 3;
				break;
		}
	}*/

}
