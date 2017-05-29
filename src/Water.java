
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
 * This Water class represents a splash of Water that is sprayed by the Hero to
 * extinguish Fireballs or Fires to save the Pants lighted.
 * 
 * @author Xuezhan Yan
 */

public class Water {
	private Graphic graphic;
	private float speed;
	private float distanceTraveled;

	/**
	 * This constructor creates a instance of Water at the presetted location
	 * and facing a presetted movement direction. This Water should move with a
	 * speed of 0.7 pixels per millisecond.
	 * 
	 * @param x
	 *            the x-coordinate of this new Water's position
	 * @param y
	 *            the y-coordinate of this new Water's position
	 * @param direction
	 *            the angle from 0 to 2PI that this new Fireball object should
	 *            follow this angle
	 */
	public Water(float x, float y, float direction) {
		graphic = new Graphic("WATER");
		speed = 0.7f;
		graphic.setPosition(x, y);
		graphic.setDirection(direction);
	}

	/**
	 * This method is called repeatedly by the Level to draw and move the
	 * current Water object. After this Water has moved a total of 200 pixels or
	 * further, it should stop displaying and this method should return null
	 * instead of a reference to the current instance of this Water object.
	 * 
	 * @param time
	 *            is the amount of time in milliseconds that has elapsed since
	 *            the last time this update was called.
	 * 
	 * @return a reference to this Water object until this water has traveled
	 *         200 or more pixels. It should then return null after traveling
	 *         this far.
	 */
	public Water update(int time) {
		if (distanceTraveled <= 200) {
			graphic.draw();
			graphic.setPosition(
					graphic.getX() + graphic.getDirectionX() * speed * time,
					graphic.getY() + graphic.getDirectionY() * speed * time);
			distanceTraveled += speed * time;
			return this;
		} else
			return null;
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
}
