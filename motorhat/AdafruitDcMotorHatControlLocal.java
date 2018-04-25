package motorhat;
import java.util.Scanner;

import motorhatsupport.AdafruitDcMotor;
import motorhatsupport.AdafruitMotorHat;

/**
 * Example program commanding a DC Motor wired to a AdafruitMotorHat.
 * <p>
 * <a href="https://www.adafruit.com/products/2348">See MotorHAT</a>
 * <p>
 * In this example four DC motors are wired to the Adafruit motor HAT. They are commanded at
 * different speeds and motor direction.
 * 
 * @author Eric Eliason
 * @see com.pi4j.component.adafruithat.AdafruitDcMotor
 * @see com.pi4j.motorhatsupport.adafruithat.AdafruitMotorHat
 *
 */
public class AdafruitDcMotorHatControlLocal {
	
	public static void main(String[] args)  {
		final int motorHATAddress = 0X60;
		AdafruitMotorHat motorHat = new AdafruitMotorHat(motorHATAddress);		
		
		AdafruitDcMotor motorLeft  = motorHat.getDcMotor("M1");
		AdafruitDcMotor motorRight = motorHat.getDcMotor("M2");
		AdafruitDcMotor motorWrist = motorHat.getDcMotor("M3");
		AdafruitDcMotor motorElbow = motorHat.getDcMotor("M4");			

		//A speed value of 100 sets the DC motor to maximum throttle.
		//The default power range is 1.0.
		motorLeft.setPowerRange(100.0f);
		motorRight.setPowerRange(100.0f);
		motorWrist.setPowerRange(100.0f);
		motorElbow.setPowerRange(100.0f);
		/*
		 * Because the Adafruit motor HAT uses PWMs that pulse independently of
		 * the Raspberry Pi the motors will keep running at its current direction
		 * and power levels if the program abnormally terminates. 
		 * A shutdown hook like the one in this example is useful to stop the 
		 * motors when the program is abnormally interrupted.
		 */		
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() { 
		    	System.out.println("Turn off all motors");
		    	motorHat.stopAll();		    	
		    }
		 });

		
		
		Scanner keyboard = new Scanner(System.in);
		
		boolean exit = false;
		
		while(!exit) {
			System.out.println("Enter command (quit to exit):");
			String input = keyboard.nextLine();
			if(input != null) {
				System.out.println("Your input is : " + input);
				if("quit".equals(input)){
					System.out.println("Exit program");
					exit = true;
				}
				else if("w".equals(input)) {
					motorLeft.speed(50.0f);
					motorRight.speed(50.0f);
					motorWrist.speed(50.0f);
					motorElbow.speed(50.0f);
				}
				else if("s".equals(input)) {
					motorLeft.speed(-50.0f);
					motorRight.speed(-50.0f);
					motorWrist.speed(-50.0f);
					motorElbow.speed(-50.0f);
				}
				else if("d".equals(input)) {
					motorLeft.speed(-50.0f);
					motorRight.speed(50.0f);
					motorWrist.speed(-50.0f);
					motorElbow.speed(50.0f);
				}
				else if("a".equals(input)) {
					motorLeft.speed(50.0f);
					motorRight.speed(-50.0f);
					motorWrist.speed(50.0f);
					motorElbow.speed(-50.0f);
				}
				else if("q".equals(input)) {
					motorLeft.stop();
					motorRight.stop();
					motorWrist.stop();
					motorElbow.stop();
				}
			}
		}
		keyboard.close();
	}
}