
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

import java.util.Random;

/**
 * This class prints a burning fire, which ejects from a Fireball to a random
 * direction every 3-6 seconds. The fire can slowly be extinguished through
 * repeated collisions with water until achieving 40 times collisions.
 * 
 * @author Xuezhan Yan
 */
public class Fire {
	private Graphic graphic;
	private Random randGen;
	private int fireballCountdown;
	private int heat;
	private boolean flase;

	/**
	 * This constructor creates a new instance of Fire at the presetted location
	 * and with the presetted amount of heat. The Random number generator should
	 * be used both to determine how much time remains before the next Fireball
	 * is propelled and direction of Fireball spraying.
	 * 
	 * @param x
	 *            the x-coordinate of new Fire instance's position
	 * @param y
	 *            the y-coordinate of new Fire instance's position
	 * @param randGen
	 *            a Random number generator to determine when and which
	 *            direction new Fireballs are created and launched.
	 */
	public Fire(float x, float y, Random randGen) {
		graphic = new Graphic("FIRE");
		graphic.setPosition(x, y);
		this.randGen = randGen;
		fireballCountdown = randGen.nextInt(3001) + 3000;
		heat = 40;//should use final 
	}

	/**
	 * This method will be called repeatedly by the Level to draw and launch a
	 * new Fireball object in the random direction.
	 * 
	 * @param time
	 *            is the amount of time in milliseconds that representing
	 *            different states
	 * 
	 * @return null unless a new Fireball object was just created and launched.
	 *         In that case, a reference to that new Fireball object will be
	 *         returned instead.
	 */
	public Fireball update(int time) {
		fireballCountdown -= time;
		if (heat >= 1) {
			graphic.draw();
			if (fireballCountdown <= 0) {
				// reset a random # from 3000 to 6000
				fireballCountdown = randGen.nextInt(3001) + 3000;
				Fireball fireball = new Fireball(graphic.getX(), graphic.getY(),
						(float) (randGen.nextFloat() * Math.PI * 2));
				return fireball;
			}
		}
		return null;
	}

	/**
	 * This is a accessor for getting object's Graphic, which may be used by
	 * other objects to check for collisions.
	 * 
	 * @return a reference of Fire's Graphic object.
	 */
	public Graphic getGraphic() {
		return this.graphic;
	}

	/**
	 * This method detects and handles collisions between any active (not null)
	 * Water objects with the Fire. When a collision is found, the Water object
	 * should be removed and the Fire's heat should be decremented by 1. If this
	 * Fire's heat approaches below one, then it should no longer be drawn,
	 * following by ejecting a new Fireball object. When colliding with Water
	 * and its shouldRemove() method should start returning true.
	 * 
	 * @param water
	 *            is the Array of water objects that have been launched by the
	 *            Hero (all null elements should be ignored).
	 */
	public void handleWaterCollisions(Water[] water) {
		for (int i = 0; i < water.length; i++) {
			if (water[i] != null)
				if (this.graphic.isCollidingWith(water[i].getGraphic())) {
					heat--;
					water[i] = null;
				}
		}
	}

	/**
	 * This method should return false when this Fire's heat approach to 0 or
	 * less. After that, true will be returned.
	 * 
	 * @return false when this Fire's heat is greater than zero, otherwise true.
	 */
	public boolean shouldRemove() {
		if (heat < 1)
			return true;
		else
			return flase;
	}
}
