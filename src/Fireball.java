
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

/**
 * This class prints a Fireball that is ejected from a burning fire. When a
 * Fireball hits the Hero, player lose the game. When a Fireball hits a Pant,
 * that Pant will be replaced by a new Fire.
 * 
 * @author Xuezhan Yan
 */
public class Fireball {
	private Graphic graphic;
	private float speed;
	private boolean isAlive;

	/**
	 * This constructor initializes a new instance of Fireball at the presetted
	 * location and facing a presetted moving direction. This Fireball object
	 * should move with the speed of 0.2 pixels per millisecond.
	 * 
	 * @param x
	 *            the x-coordinate of this new Fireball's position
	 * @param y
	 *            the y-coordinate of this new Fireball's position
	 * @param directionAngle
	 *            the angle from 0 to 2PI that this new Fireball object should
	 *            follow this angle
	 */
	public Fireball(float x, float y, float directionAngle) {
		graphic = new Graphic("FIREBALL");
		graphic.setPosition(x, y);
		graphic.setDirection(directionAngle);
		isAlive = true;
		speed = 0.2f;
	}

	/**
	 * This method is called repeatedly by the Level to draw and move the
	 * current Fireball. When a Fireball moves more than 100 pixels beyond any
	 * edge of the screen, it should be destroyed and its shouldRemove() method
	 * should be called to return true instead of false.
	 * 
	 * @param time
	 *            is the amount of time in milliseconds that has elapsed since
	 *            the last time this update was called.
	 */
	public void update(int time) {
		if ((graphic.getX() <= -100) || (graphic.getY() <= -100)
				|| (graphic.getX() >= GameEngine.getWidth() + 100)
				|| (graphic.getY() >= GameEngine.getHeight() + 100))
			isAlive = false;
		if (isAlive) {
			// normally move
			graphic.draw();
			graphic.setPosition(
					graphic.getX() + graphic.getDirectionX() * speed * time,
					graphic.getY() + graphic.getDirectionY() * speed * time);
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
	 * This is a helper method allowing other classes to destroy a Fireball
	 * object upon collision.
	 */
	public void destroy() {
		isAlive = false;
	}

	/**
	 * This method detects and handles collisions between any active (not null)
	 * Water objects, with the current Fireball. When a collision is found, the
	 * colliding water should be removed and this Fireball should also be
	 * removed from the game. When this Fireball's shouldRemove method is
	 * already returning true, this method should not do anything.
	 * 
	 * @param water
	 *            is the Array of water objects that have been launched by the
	 *            Hero (all null elements should be ignored).
	 */
	public void handleWaterCollisions(Water[] water) {
		for (int i = 0; i < water.length; i++) {
			if (water[i] != null)
				if (this.graphic.isCollidingWith(water[i].getGraphic())) {
					isAlive = false; // move this dead fireball in the future
					water[i] = null;
				}
		}
	}

	/**
	 * This method will be used in Level to determine whether this Fireball is
	 * still in use or ready to be removed from the ArrayList of Fireballs.
	 * 
	 * @return true when this Fireball need to be removed and false otherwise.
	 */
	public boolean shouldRemove() {
		return !isAlive;
	}
}
