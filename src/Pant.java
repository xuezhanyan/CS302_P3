
// Title:            Pants On Fire
// Files:            Fire.java, Fireball.java, Hero.java, Level.java, Pant.java,
//					 Water.java, GameEngine.java, Graphic.java
// Semester:         (302) Fall 2016
//
// Author:           Xuezhan Yan
// Email:            xyan56@wisc.edu
// CS Login:         xuezhan
// Lecturer's Name:  Williams, James S.
// Lab Section:      311
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:     No Partner
// Partner Email:     
// Partner CS Login: 
// Lecturer's Name:  
// Lab Section:      
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//    _X_ Write-up states that Pair Programming is allowed for this assignment.
//    _X_ We have both read the CS302 Pair Programming policy.
//    _X_ We have registered our team prior to the team registration deadline.
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents Pants that the Hero must protect from burning to win
 * the game. Whenever a Pant collides with a Fireball, that Pant will become to
 * be a Fire object.
 * 
 * @author Xuezhan Yan
 */
public class Pant {
	private Graphic graphic;
	private Random randGen;
	private boolean isAlive;

	/**
	 * This constructor creates a instance of Pant at the presetted location.
	 * The Random number is used to create a new Fire, after this pant is hit by
	 * a Fireball.
	 * 
	 * @param x
	 *            the x-coordinate of this new Pant's position
	 * @param y
	 *            the y-coordinate of this new Pant's position
	 * @param randGen
	 *            a Random number generator to pass onto any Fire that is
	 *            created as a result of this Pant being hit by a Fireball.
	 */
	public Pant(float x, float y, Random randGen) {
		graphic = new Graphic("PANT");
		graphic.setPosition(x, y);
		isAlive = true;
		this.randGen = randGen;
	}

	/**
	 * This method is responsible for drawing the Pant object to the screen.
	 * 
	 * @param time
	 *            is the amount of time in milliseconds that has elapsed since
	 *            the last time this update was called.
	 */
	public void update(int time) {
		if (isAlive) {
			graphic.draw();
		}
	}

	/**
	 * This is a accessor for getting object's Graphic, which may be used by
	 * other objects to check for collisions.
	 * 
	 * @return a reference of Fireball's Graphic object.
	 */
	public Graphic getGraphic() {
		return this.graphic;
	}

	/**
	 * This method detects an handles collisions between any active Fireball,
	 * wiht the Pant objects. When a collision is found, the colliding Fireball
	 * object should be removed from the game and the Pant object should also be
	 * removed from the game. A new Fire should be created in the position of
	 * the old Pant object and then be returned.
	 * 
	 * @param fireballs
	 *            the ArrayList of Fireballs that should be checked every
	 *            elements whether collides with any Pant object's Graphic.
	 * 
	 * @return a reference to the created Fire when a collision is found, and
	 *         null otherwise.
	 */
	public Fire handleFireballCollisions(ArrayList<Fireball> fireballs) {
		for (int i = 0; i < fireballs.size(); i++) {
			if (this.graphic.isCollidingWith(fireballs.get(i).getGraphic())) {
				isAlive = false;
				fireballs.get(i).destroy();
				return new Fire(graphic.getX(), graphic.getY(), randGen);
			}
		}
		return null;
	}

	/**
	 * This method will be used in Level to determine whether this Pants is
	 * still in use or ready to be removed from the ArrayList of Pants.
	 * 
	 * @return true when this Pants need to be removed and false otherwise.
	 */
	public boolean shouldRemove() {
		return !isAlive;
	}
}
