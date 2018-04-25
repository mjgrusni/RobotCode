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
public class AdafruitDcMotorHatControl {

	final int motorHATAddress = 0x60;

	AdafruitMotorHat motorHat;

	AdafruitDcMotor motorLeft, motorRight, motorWrist, motorElbow;

	public AdafruitDcMotorHatControl() {
		
		motorHat = new AdafruitMotorHat(motorHATAddress);
		
		motorLeft  = motorHat.getDcMotor("M1");
		motorRight = motorHat.getDcMotor("M2");
		motorWrist = motorHat.getDcMotor("M3");
		motorElbow = motorHat.getDcMotor("M4");			

		//A speed value of 100 sets the DC motor to maximum throttle.
		//The default power range is 1.0.
		motorLeft.setPowerRange(100.0f);
		motorRight.setPowerRange(100.0f);
		motorWrist.setPowerRange(100.0f);
		motorElbow.setPowerRange(100.0f);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() { 
				System.out.println("Turn off all motors");
				motorHat.stopAll();		    	
			}
		});
	}

	public void goForward(float speed) {
		speed = 50.0f;
		motorLeft.speed(speed);
		motorRight.speed(speed);
		motorWrist.speed(speed);
		motorElbow.speed(speed);
	}

	public void goBackwards(float speed) {
		speed = -50.0f;
		motorLeft.speed(speed);
		motorRight.speed(speed);
		motorWrist.speed(speed);
		motorElbow.speed(speed);
	}

	public void turnRight(float speed) {
		speed = 50.0f;
		motorLeft.speed(-speed);
		motorRight.speed(speed);
		motorWrist.speed(-speed);
		motorElbow.speed(speed);
	}

	public void turnLeft(float speed) {
		speed = 50.0f;
		motorLeft.speed(speed);
		motorRight.speed(-speed);
		motorWrist.speed(speed);
		motorElbow.speed(-speed);
	}
	
	public void stop() {
		motorLeft.stop();
		motorRight.stop();
		motorWrist.stop();
		motorElbow.stop();
	}

	public static void main(String[] args)  {	
		
		AdafruitDcMotorHatControl control = new AdafruitDcMotorHatControl();
		
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
					control.goForward(5);
				}
				else if("s".equals(input)) {
					control.goBackwards(5);
				}
				else if("d".equals(input)) {
					control.turnLeft(5);
				}
				else if("a".equals(input)) {
					control.turnRight(5);
				}
				else if("q".equals(input)) {
					control.stop();
				}
			}
		}
		keyboard.close();
	}
}