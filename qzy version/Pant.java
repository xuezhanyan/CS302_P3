import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * This class represents a pair of Pants that the Hero must protect from 
 * burning. Whenever a Pant collides with a Fireball, that Pant will be 
 * replaced by a Fire. 
 */

public class Pant {

	private Graphic graphic;
	private Random randGen;
	private boolean isAlive;
	
	/**
	 * This constructor initializes a new instance of Pant at the appropriate 
	 * location. The Random number is only used to create a new Fire, after this
	 * pant is hit by a Fireball.
	 * 
	 * @param x the x-coordinate of this new Pant's position
	 * @param y the y-coordinate of this new Pant's position
	 * @param randGen a Random number generator to pass onto any Fire that is 
	 * 				  created as a result of this Pant being hit by a Fireball.
	 */
	public Pant(float x, float y, Random randGen) {
		this.graphic = new Graphic("PANT");
		this.randGen = randGen;
		this.isAlive = true;
		graphic.setPosition(x, y);
	}
	
	/**
	 * This is a simple accessor for this object's Graphic, which may be used by 
	 * other objects to check for collisions.
	 * 
	 * @return a reference to this Pant's Graphic object.
	 */
	public Graphic getGraphic() {
		return graphic;
	}
	
	/**
	 * This method detects an handles collisions between any active Fireball, 
	 * and the current Pant. When a collision is found, the colliding Fireball 
	 * should be removed from the game (by calling its destroy() method), and 
	 * the current Pant should also be removed from the game (by ensuring that 
	 * its shouldRemove() method returns true). A new Fire should be created in 
	 * the position of the old Pant object and then returned.
	 * 
	 * @param fireballs the ArrayList of Fireballs that should be checked 
	 * 		  against the current Pant object's Graphic for collisions.
	 * 
	 * @return a reference to the newly created Fire when a collision is found, 
	 * 		   and null otherwise.
	 */
	public Fire handleFireballCollisions(ArrayList<Fireball> fireballs) {
		for (int i = 0; i < fireballs.size(); i++) {
			// check if Pant collides with Fireball
			if(this.graphic.isCollidingWith(fireballs.get(i).getGraphic())) {
				this.isAlive = false;
				fireballs.get(i).destroy();
				// create a new Fire
				return new Fire(this.graphic.getX(), this.graphic.getY(), this.randGen);
			}
		}
		return null;
	}
	
	/**
	 * This method communicates to the Game whether this Pant is still in use 
	 * versus ready to be removed from the Game's ArrayList of Pants.
	 * 
	 * @return true when this Pant has been hit by a Fireball, otherwise false.
	 */
	public boolean shouldRemove() {
		return (!isAlive);
	}
	
	/**
	 * This method is simply responsible for draing the current Pant to the 
	 * screen.
	 * 
	 * @param time is the amount of time in milliseconds that has elapsed since 
	 * 			   the last time this update was called.
	 */
	public void update(int time) {
		if(isAlive)
			graphic.draw();
	}
}
