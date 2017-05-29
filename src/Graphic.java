// Copyright (C) 2016 Gary Dahl <dahl@cs.wisc.edu>.
// Developed for UW-Madison's CS302 P3 Assignment in the Summer of 2016.

import java.io.File;
import java.util.HashMap;
//import processing.core.*;

/**
 * Instances of the Graphic class are used to represent each graphic in a game.
 * If an image for an object's type exists, that will be drawn instead of the
 * default circles of varying colors and sizes.  Graphics can be drawn to the
 * screen in different positions and orientations using their draw() method.
 * @author dahl
 */
public class Graphic
{
	private final static int DEFAULT_SIZE = 20; // default circle radius
//	private static HashMap<String,PImage> images = null; // hold each image once
//	private static PApplet app = null; // used for loading images and drawing

	private String type; // appearance name (or name of image file)
	private float  x; // window position in pixels (left to right)
	private float  y; // window position in pixels (top to bottom)
	private float  size; // radius in pixels for circles and collision
	private float  direction; // orientation in radians (right:0, down: PI/2)
//	private PImage image; // reference to image representation (when exists)
	private int    color; // alternative circle color for appearance

	/**
	 * This constructor creates a Graphic object at position 0,0 with a 
	 * direction of 0 (facing the right), and the specified appearance.
	 * @param type - defines the appearance of the newly created object.
	 */
	public Graphic(String type)
	{
//		if(images == null) { System.err.println(
//				"ERROR Usage: You must call GameEngine.start() before creating "
//						+ "any Graphic objects." ); return; }

		// initialize all fields
		this.type	= null;
		x         	= 0;
		y         	= 0;		
		size      	= DEFAULT_SIZE;
		direction 	= 0;
//		image     	= null;
		color     	= 0;

		setType(type); // then overwrite with the specified type
	}

	/**
	 * Sets the type of graphic used to represent this object.  If an image of 
	 * this type cannot be found, then a default circle will be drawn in its 
	 * place with a single line designating this object's direction.  Some of 
	 * the default types recognized by this method include: "HERO", "FIRE", 
	 * "PANT", "WATER", and "FIREBALL".
	 * @param type The type of graphic used to represent this object.
	 */
	public void setType(String type)
	{
		if(type == null) { System.err.println("ERROR Usage: "
				+ "You cannot set the type of a Graphic to null."); return; }

		// update images hash to contain images or null values for every type
//		if(!images.containsKey(type)) 	// when a new (unused) type is specified
//		{							  	// attempt to load an image for it
//			image = loadImageHelper("images/"+type+".png");
//			images.put(type,image);  	// stores null, when no image exists
//		}
//		else image = images.get(type);	// otherwise, retrieve an existing image

		// then update the current graphic based on whether an image exists
		this.type = type;
//		if(image != null) size = ( image.width + image.height ) / 2.0f;
//		else overwriteKnownSizeAndColor(type);
	}

	/**
	 * This method retrieves the type of the current Graphic.
	 * @return The name of this Graphic's type.
	 */
	public String getType() { return type; }
	
	/**
	 * This method retrieves the x-position in pixels of the current Graphic.
	 * @return The Graphic's x-position in pixels (increasing left to right).
	 */
	public float getX() { return x; }
	
	/**
	 * This method retrieves the y position in pixels of the current Graphic.
	 * @return The Graphic's y-position in pixels (increasing top to bottom).
	 */
	public float getY() { return y; }
	
	/**
	 * This method retrieves the orientation of the current Graphic as an
	 * angle measured in radians, where 0: facing right, and PI/2: facing down, 
	 * PI: facing left, and 3PI/2: facing up.
	 * @return The Graphic's direction in randians.
	 */
	public float getDirection() { return direction; }

	/**
	 * This method retrieves part of the orientation of the current graphic as a
	 * point that is one unit away, and in the direction this Graphic is facing.
	 * In other words, it returns the cosine of the Graphic's direction angle.
	 * @return The x-coordinate of a point that is one unit away, and in the
	 * direction that the Graphic is facing.
	 */
	public float getDirectionX() { return (float)Math.cos(direction); }
	
	/**
	 * This method retrieves part of the orientation of the current graphic as a
	 * point that is one unit away, and in the direction this Graphic is facing.
	 * In other words, it returns the sine of the Graphic's direction angle.
	 * @return The y-coordinate of a point that is one unit away, and in the 
	 * direction that the Graphic is facing.
	 */
	public float getDirectionY() { return (float)Math.sin(direction); }
	
	/**
	 * This method allows you to update the horizontal position of a Graphic.
	 * @param x - is the x-position that you would like to move this Graphic to.
	 */
	public void setX(float x) { this.x = x; }	
	
	/**
	 * This method allows you to update the vertical position of a Graphic.
	 * @param y - is the y-position that you would like to move this Graphic to.
	 */
	public void setY(float y) { this.y = y; }
	
	/**
	 * This method allows you to update the position of a Graphic.
	 * @param x - is the x-position that you would like to move this Graphic to.
	 * @param y - is the y-position that you would like to move this Graphic to.
	 */
	public void setPosition(float x, float y) { setX(x); setY(y); }
	
	/**
	 * This method allows you to update the direction that a Graphic is facing,
	 * by specifying an angle (in radians) of rotation, where 0: facing right, 
	 * and pi/2: facing down, pi: facing left, and 3pi/2: facing up.
	 * @param angle The angular amount of rotation you would like to apply to 
	 * this Graphic.
	 */
	public void setDirection(float angle) { direction = angle; }

	/**
	 * This method allows you to update the direction that a Graphic is facing, 
	 * by specifying any position on the screen that you would like it to face
	 * toward.  
	 * @param x - is the x-coordinate of the position for the Graphic to face.
	 * @param y - is the y-coordinate of the position for the Graphic to face.
	 */
	public void setDirection(float x, float y) 
	{ direction = (float)Math.atan2(y-this.y, x-this.x); }
	
	/**
	 * Determines whether this Graphic is overlapping or colliding with another
	 * Graphic on the screen.
	 * @param other - is the Graphic being checked against for collisions.  
	 * @return true when the two objects overlap, and false otherwise.
	 */
	public boolean isCollidingWith(Graphic other)
	{
		float dx = x - other.x;
		float dy = y - other.y;
		float threshold = (size + other.size)/2;
		// check for overlapping collision circles where dist <= sum of radius'
		boolean output = dx*dx+dy*dy <= threshold*threshold;
		return output;
	}

	/**
	 * This method draws a Graphic to the screen at its current location, and 
	 * facing in its current direction (orientation).
	 */
	public void draw()
	{
		System.out.println("Drawing " + type + " at (" + x + ", " + y + ").");
//		if(image != null) 
//		{			
//			// draw image with the appropriate transformations
//			app.pushMatrix();
//				app.translate(x, y);
//				app.rotate(direction);
//				app.image(image,0,0);
//			app.popMatrix();
//		}
//		else
//		{
//			// draw a circles with the appropriate color, and a line for heading
//			app.stroke(0);
//			app.fill(color);
//			app.ellipse(x,  y,  size,  size);
//			app.line(x, y,x+(float)Math.cos(direction)*size/2, 
//					      y+(float)Math.sin(direction)*size/2);
//		}
	}	

	/**
	 * This helper method attempts to load and return an image object.
	 * @param filename - is the name of the image file that should be loaded.  
	 * @return an image object after one is successfully loaded, otherwise null.
	 */
//	private PImage loadImageHelper(String filename)
//	{
//		PImage image = null;
//		try { if((new File(filename)).exists()) image = app.loadImage(filename); 
//		} catch(Exception e) { } // return null when a file cannot be loaded
//		return image;
//	}
	/**
	 * This helper method sets the size and color for known types.
	 * @param type - determines what size and color combination to set.  
	 */
//	private void overwriteKnownSizeAndColor(String type)
//	{
//		switch(type.toUpperCase())
//		{
//		case "HERO":     color = app.color(255,255,0);   size = 20; break;
//		case "FIRE":     color = app.color(255,0,0);     size = 15; break;
//		case "PANT":     color = app.color(0,255,0);     size = 15; break;
//		case "WATER":    color = app.color(0,255,255);   size = 10; break;
//		case "FIREBALL": color = app.color(255,128,0);   size = 10; break;
//		case "CURSOR":	 color = 0;                      size = 1;  break;
//		default: color = app.color(196,196,196); size = DEFAULT_SIZE; break;
//		}
//	}

	/**
	 * This method is used by the GameEngine to register itself as the PApplet
	 * rendering target, and to initialize the images hash when this happens.
	 * @exclude
	 */
//	public static void register(PApplet app)
//	{
//		Graphic.app = app;
//		Graphic.images = new HashMap<String,PImage>();
//	}
}
