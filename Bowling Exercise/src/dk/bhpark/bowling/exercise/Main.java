package dk.bhpark.bowling.exercise;

/**
 * This class simply kicks off the program execution. Default is one game, but it can be extended
 * to several games by specifying the requested number as a parameter to the program. 
 * <p>
 * <b>(C) Copyright Claus Jensen. 2016
 * @version 1.00 - 285/10/2016
 * @author Claus Jensen (claus@bhpark.dk)
 */

public class Main {

	/**
	 * Initiate execution of one or more games.
	 * 
	 * @param args
	 */
	public static void main(String[] args) { 
		
		// decide how many rounds to run
		int count = 1;
		try {
			count = new Integer(args[0]).intValue();
		} catch (Exception e) {}
		
		// perform the run(s)
		for (int i = 0; i < count; i++) new Game();
	}
}

