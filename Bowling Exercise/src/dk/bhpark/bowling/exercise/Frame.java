package dk.bhpark.bowling.exercise;

/**
 * This class represents a frame and contains calculation of accumulated points for the frame.
 * <p>
 * <b>(C) Copyright Claus Jensen. 2016
 * @version 1.00 - 28/10/2016
 * @author Claus Jensen (claus@bhpark.dk)
 */

public class Frame {

	private int ball1 = 0;
	private int ball2 = 0;
	private boolean strike = false;
	private boolean spare = false;
	private int points = 0;
	
	/**
	 * The constructor contains all the code needed for creating a frame.
	 * 
	 * @param balls
	 * @param i
	 * @param p
	 */
	public Frame(int[] balls, int i, int p) {

		ball1 = balls[i];
		ball2 = balls[i + 1];
		strike = (balls[i] == 10);
		spare = (ball1 + ball2 == 10 && balls[i] < 10);
		// dirty way to calculate the accumulated points :-)
		points = p + balls[i] + balls[i + 1];
		try {
			if (strike) {
				points += balls[i + 2];
				points += (balls[i + 3] > 0) ? balls[i + 3] : balls[i + 4];
			}
			if (spare) points += balls[i + 2];	
		} catch (IndexOutOfBoundsException e) {}
	}
	
	/**
	 * This method returns the accumulated points
	 * 
	 * @return
	 */
	public int getPoints() {
		
		return points;
	}

	/**
	 * This method returns a textual representation of the Frame object.
	 */
	@Override
	public String toString() {
		
		if (strike) return "[[X] " + points + "] - ";
		if (spare) return "[[" + ball1 + ",/] " + points + "] - "; 
		return "[[" + ball1 + "," + ball2 + "] " + points + "] - ";
	}
}
