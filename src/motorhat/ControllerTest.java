package motorhat;

import java.util.Scanner;

public class ControllerTest {

	public static void main (String[] args) {
		
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
