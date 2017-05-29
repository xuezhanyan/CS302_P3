/**
 * This class represents a Fireball that is ejected from a burning fire. When a 
 * Fireball hits the Hero, they lose the game. When a Fireball hits a Pant, 
 * those Pants are replaced by a new Fire. 
 */

public class Fireball {

	private Graphic graphic;
	private float speed;
	private boolean isAlive;

	/**
	 * This constructor initializes a new instance of Fireball at the specified 
	 * location and facing a specific movement direction. This Fireball should 
	 * move with a speed of 0.2 pixels per millisecond.
	 * 
	 * @param x the x-coordinate of this new Fireball's position
	 * @param y the y-coordinate of this new Fireball's position
	 * @param directionAngle 
	 * 					the angle (in radians) from 0 to 2pi that this new 
	 * 					Fireball should be both oriented and moving according 
	 * 					to.
	 */
	public Fireball(float x, float y, float directionAngle) {
		this.graphic = new Graphic("FIREBALL");
		this.speed = 0.2f;
		this.isAlive = true;
		graphic.setPosition(x, y);
		graphic.setDirection(directionAngle);

	}
	
	/**
	 * This helper method allows other classes (like Pant) to destroy a Fireball 
	 * upon collision. This method should ensure that the shouldRemove() methods 
	 * only returns true after this method (destroy) has been called.
	 */
	public void destroy() {
		this.isAlive = false;
	}
	
	/**
	 * This is a simple accessor for this object's Graphic, which may be used by 
	 * other objects to check for collisions.
	 * 
	 * @return a reference to this Fireball's Graphic object.
	 */
	public Graphic getGraphic() {
		return graphic;
	}
	
	/**
	 * This method detects and handles collisions between any active (!= null) 
	 * Water objects, and the current Fireball. When a collision is found, the 
	 * colliding water should be removed (array reference set to null), and this 
	 * Fireball should also be removed from the game (its shouldRemove() should 
	 * begin to return true when called). When this Fireball's shouldRemove 
	 * method is already returning true, this method should not do anything.
	 * 
	 * @param water is the Array of Water objects that have been launched by the 
	 * 				Hero (ignore any null references within this array).
	 */
	public void handleWaterCollisions(Water[] water) {
		for (int i = 0; i < water.length; i++) {
			if(water[i] != null) {
				// if water collides with Fireball
				if(this.graphic.isCollidingWith(water[i].getGraphic())) {
					// update status of Fireball to false
					this.isAlive = false;
					// remove water
					water[i] = null;
				}
			}
		}
	}
	
	/**
	 * This method communicates to the Level whether this Fireball is still in 
	 * use versus ready to be removed from the Levels's ArrayList of Fireballs.
	 * 
	 * @return true when this Fireball has either gone off the screen or 
	 * 		   collided with a Water or Pant object, and false otherwise.
	 */
	public boolean shouldRemove() {
		return (!isAlive);
	}
	
	/**
	 * This method is called repeatedly by the Level to draw and move the 
	 * current Fireball. When a Fireball moves more than 100 pixels beyond any 
	 * edge of the screen, it should be destroyed and its shouldRemove() method 
	 * should begin to return true instead of false.
	 * 
	 * @param time is the amount of time in milliseconds that has elapsed since 
	 * 			   the last time this update was called.
	 */
	public void update(int time) {
		if (isAlive) {
			graphic.draw();
			// set the position of fireball
			graphic.setPosition(
					graphic.getX() + graphic.getDirectionX() * speed * time,
					graphic.getY() + graphic.getDirectionY() * speed * time);
			// check if Fireball is out of boundary
			if (graphic.getX() < -100 || graphic.getY() < -100
					|| ((graphic.getX() - GameEngine.getWidth()) > 100)
					|| ((graphic.getY()) - GameEngine.getHeight() > 100))
				isAlive = false;
		}
	}
}
