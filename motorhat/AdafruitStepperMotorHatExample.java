package motorhat;
import motorhatsupport.AdafruitMotorHat;
import motorhatsupport.AdafruitStepperMotor;
import motorhatsupport.StepperMode;
/**
 * Demonstrate commanding a single stepper motor wired to an "Adafruit DC and Stepper Motor HAT"
 * for the Raspberry Pi computer. 
 * <p>
 * <a href="https://www.adafruit.com/products/2348">See MotorHAT</a>
 * <p>
 * 
 * @author Eric Eliason
 * @see com.pi4j.component.adafruithat.AdafruitStepperMotor
 * @see com.pi4j.component.motorhatsupport.AdafruitMotorHat
 */
public class AdafruitStepperMotorHatExample {
	
	public static void main(String[] args) {		
		final int motorHATAddress = 0X60;
		
		//create instance of a motor HAT
		AdafruitMotorHat motorHat = new AdafruitMotorHat(motorHATAddress);
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
			
		
		
		//Create an instance for this stepper motor. A motorHAT can command
		//two stepper motors ("SM1" and "SM2")
		AdafruitStepperMotor stepper = motorHat.getStepperMotor("SM1");
			
		//Set Stepper Mode to SINGLE_PHASE
		stepper.setMode(StepperMode.SINGLE_PHASE);
		
		//Set the number of motor steps per 360 degree
		//revolution for this stepper mode.
		stepper.setStepsPerRevolution(200);
		
		//Time between each step in milliseconds. 
		//In this example, "true" indicates to terminate if 
		//stepper motor can not achieve 100ms per step.
		stepper.setStepInterval(100, true);

		//forward
		stepper.step(100);
		System.out.format("currentStep: %d\n",stepper.getCurrentStep());
		
		//reverse
		stepper.step(-100);
		System.out.format("currentStep: %d\n",stepper.getCurrentStep());
		
		//Move 2.5 revolutions at the fastest possible speed in forward direction
		stepper.setStepInterval(0,false);
		stepper.rotate(2.5);
		System.out.format("currentStep: %d\n",stepper.getCurrentStep());
		
		//Move 2.5 revolutions at the fastest possible speed in reverse direction
		stepper.setStepInterval(0,false);
		stepper.rotate(-2.5);
		System.out.format("currentStep: %d\n",stepper.getCurrentStep());
		
		//Stop all motors attached to this HAT
		motorHat.stopAll();
	
	}
}
    