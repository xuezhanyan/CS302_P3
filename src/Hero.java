
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

/**
 * This class represents the player's character which is a hero spraying water
 * to extinguishe Fires and Fireballs. To win the game, player have to avoid all
 * pants on fire and destroy Fireballs and Fires in the process.
 * 
 * @author Xuezhan Yan
 */
public class Hero {
	private Graphic graphic;
	private float speed;
	private int controlType;

	/**
	 * This constructor initializes a instance of Hero at the presetted location
	 * with presetted controlType. This Hero should move with a speed of 0.12
	 * pixels per millisecond.
	 * 
	 * @param x
	 *            the x-coordinate of this new Hero's position
	 * @param y
	 *            the y-coordinate of this new Hero's position
	 * @param controlType
	 *            specifies which control scheme (1, 2 or 3) should be used by
	 *            the player to move this hero.
	 */
	public Hero(float x, float y, int controlType) {
		graphic = new Graphic("HERO");
		speed = 0.12f;
		this.controlType = controlType;
		// set the initial position of x,y and draw it
		graphic.setPosition(x, y);
	}

	/**
	 * This method is called repeated by the Level to draw and move the Hero,
	 * basing on control type, as well as to spray new Water in the direction
	 * that this Hero is currently facing when presetted button is pressed.
	 * 
	 * @param time
	 *            is the amount of time in milliseconds that has elapsed since
	 *            the last time this update was called.
	 * @param water
	 *            recording the array of Water that the Hero has sprayed in the
	 *            past, and if there is an null element in this array, they can
	 *            add a new Water object to this array by pressing the
	 *            appropriate controls.
	 */
	public void update(int time, Water[] waterList) {
		graphic.draw();

		switch (controlType) {
		case 1: {
			if (GameEngine.isKeyHeld("D")) {
				graphic.setX(graphic.getX() + speed * time);
				graphic.setDirection(0);
			} else if (GameEngine.isKeyHeld("S")) {
				graphic.setY(graphic.getY() + speed * time);
				graphic.setDirection((float) Math.PI / 2);
			} else if (GameEngine.isKeyHeld("A")) {
				graphic.setX(graphic.getX() - speed * time);
				graphic.setDirection((float) Math.PI);
			} else if (GameEngine.isKeyHeld("W")) {
				graphic.setY(graphic.getY() - speed * time);
				graphic.setDirection((float) Math.PI * 3 / 2);
			}
			break;
		}

		case 2: {
			if (GameEngine.isKeyHeld("D"))
				graphic.setX(graphic.getX() + speed * time);
			else if (GameEngine.isKeyHeld("S"))
				graphic.setY(graphic.getY() + speed * time);
			else if (GameEngine.isKeyHeld("A"))
				graphic.setX(graphic.getX() - speed * time);
			else if (GameEngine.isKeyHeld("W"))
				graphic.setY(graphic.getY() - speed * time);
			graphic.setDirection(GameEngine.getMouseX(),
					GameEngine.getMouseY());
			break;
		}

		case 3: {
			graphic.setDirection(GameEngine.getMouseX(),
					GameEngine.getMouseY());
			if (Math.hypot(GameEngine.getMouseX() - graphic.getX(),
					GameEngine.getMouseY() - graphic.getY()) >= 20)
				graphic.setPosition(
						graphic.getX() + graphic.getDirectionX() * speed * time,
						graphic.getY()
								+ graphic.getDirectionY() * speed * time);
			break;
		}
		}
		if (GameEngine.isKeyPressed("MOUSE")
				|| (GameEngine.isKeyPressed("SPACE"))) {
			Water water = new Water(graphic.getX(), graphic.getY(),
					graphic.getDirection());
			for (int i = 0; i < 8; i++) {
				if (waterList[i] == null) {
					waterList[i] = water;
					break;
				}
			}
		}
		// update all water location
		for (int i = 0; i < 8; i++) {
			if (waterList[i] != null)
				waterList[i] = waterList[i].update(time);
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
	 * This method detects any collisions between any active Fireball objects
	 * with the Hero object. When a collision is found, this method returns true
	 * to indicate that the player has lost the Game.
	 * 
	 * @param fireballs
	 *            the ArrayList of Fireballs that should be checked every
	 *            elements whether collide with Hero object.
	 * 
	 * @return true when a Fireball collision is detected, otherwise false.
	 */
	public boolean handleFireballCollisions(ArrayList<Fireball> fireballs) {
		for (int i = 0; i < fireballs.size(); i++) {
			if (this.graphic.isCollidingWith(fireballs.get(i).getGraphic()))
				return true;
		}
		return false;
	}

}
