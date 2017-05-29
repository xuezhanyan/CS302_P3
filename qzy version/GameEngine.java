// Copyright (C) 2016 Gary Dahl <dahl@cs.wisc.edu>.
// Developed for UW-Madison's CS302 P3 Assignment in the Summer of 2016.

import java.util.Random;
//import processing.core.PApplet;

/**
 * This class manages an entire game: loading and playing a sequence of Levels,
 * providing access to user input, and setting up the infrastructure for 
 * creating, drawing, and colliding many Graphic objects. 
 * @author dahl
 */
public class GameEngine // extends PApplet
{
	public int width = 800;
	public int height = 600;
	public int mouseX = 400;
	public int mouseY = 400;
	public int keyCode = 0;
	
	// PUBLIC FACING METHODS FOR STUDENTS TO USE ///////////////////////////////

	/**
	 * This method creates a single Engine, and begins playing through the
	 * specified sequence of levels.  If a seed is provided, it will be used
	 * within the sole Random number generator used by this game.  This method
	 * should be called before any of the other GameEngine methods.
	 * @param rngSeed - is an optional seed for a random number generator.  
	 * Passing null sets this seed to a value that is likely different upon each
	 * invocation.
	 * @param levels - is the list of level file names that you would like the
	 * player to progress through in this game.
	 */
	public static void start(Integer rngSeed, String[] levels)
	{ 
		if(rngSeed == null) GameEngine.randGen = new Random();
		else GameEngine.randGen = new Random(rngSeed);
		if(levels == null) levels = new String[0];
		GameEngine.levels = levels;

//		PApplet.main("GameEngine"); 
		new GameEngine().setup();
	}
	
	/**
	 * This method retrieves the width in pixels of the game's window display.
	 * @return The width of the graphical display (in pixels), or -1 if
	 * GameEngine.start() has not yet been called.
	 */
	public static int getWidth() { return engine!=null?engine.width:-1; }

	/**
	 * This method retrieves the height in pixels of the game's window display.
	 * @return The height of the graphical display (in pixels), or -1 if
	 * GameEngine.start() has not yet been called.
	 */
	public static int getHeight() { return engine!=null?engine.height:-1; }
	
	/**
	 * This method retrieves whether a key has been just pressed by the user.
	 * This will only be true once for each complete press and release of a key.
	 * @param key - is the key (or mouse) that you would like to retrieve the 
	 * current status of.  Most keys are retrieved by their face character: 
	 * "Q", "W', "1", "2", etc.  Some other keys that can be detected by name 
	 * include: "TAB", "ENTER", "SHIFT", "SPACE", "LEFT", "UP", "RIGHT", "DOWN", 
	 * and "MOUSE" (where "MOUSE" represents the left mouse button).
	 * @return true when the key has just been pressed, and false otherwise.  
	 * Returns false when GameEngine.start() has not yet been called.
	 */
	public static boolean isKeyPressed(String key) 
	{ return engine!=null?engine.inputPressed.contains("["+key+"]"):false; }

	/**
	 * This method retrieves whether a key is currently being held by the user.
	 * This will remain true for as long as the key continues to be held down.
	 * @param key - is the key (or mouse) that you would like to retrieve the 
	 * current status of.  Most keys are retrieved by their face character: 
	 * "Q", "W', "1", "2", etc.  Some other keys that can be detected by name 
	 * include: "TAB", "ENTER", "SHIFT", "SPACE", "LEFT", "UP", "RIGHT", "DOWN", 
	 * and "MOUSE" (where "MOUSE" represents the left mouse button).
	 * @return true while the key is being held, and false otherwise.  Returns
	 * false when GameEngine.start() has not yet been called.
	 */
	public static boolean isKeyHeld(String key) 
	{ return engine!=null?engine.inputHeldDown.contains("["+key+"]"):false; }
	
	/**
	 * This method retrieves the x-position of the mouse in pixels within the 
	 * game window.  Zero corresponds to the left-most column of pixels, and
	 * GameEngine.getWidth()-1 corresponds to the right-most column of pixels.
	 * @return The x-coordinate of the mouse in pixels within the display, or
	 * returns -1 when GameEngine.start() has not yet been called.
	 */
	public static int getMouseX() { return engine!=null?engine.mouseX:-1; }

	/**
	 * This method retrieves the y-position of the mouse in pixels within the 
	 * game window.  Zero corresponds to the top-most row of pixels, and
	 * GameEngine.getHeight()-1 corresponds to the bottom-most row of pixels.
	 * @return The y-coordinate of the mouse in pixels within the display, or
	 * returns -1 when GameEngine.start() has not yet been called.
	 */
	public static int getMouseY() { return engine!=null?engine.mouseY:-1;}
		
	// PRIVATE / HIDDEN IMPLEMENTATION DETAILS /////////////////////////////////

	private final static int TRANSITION_TIME_MS = 3000; // delay between levels

	private static GameEngine engine; // this single instance of this object
	private static Random randGen; // passed into each game for random numbers
	private static String[] levels; // list of levels that players play through
	
	private int 		levelIndex; // index of the current level within levels
	private long 		previousTimeMS; // time of last update
	private int 		transitionIndex; // indicates a level to transition to
	private int			transitionTimeMS; // tracks delay until transition
	private Level		level; // the current level objet

	private boolean[]	keyStates; // the state of 256 different keys
	private String		inputHeldDown; // those keys being held down
	private String		inputPressed; // those keys that were just pressed

	// PROCESSING CALLBACKS ////////////////////////////////////////////////////
	
	/**
	 * This processing method runs before setup to initialize the display size.
	 * @exclude
	 */	
//	public void settings() { size(800,600); } // set size for the game window

	/**
	 * This processing method is run once after the GameEngine object is created
	 * as a result of calling PApplet.main("Engine") from GameEngine.start().
	 * @exclude
	 */	
	public void setup()
	{
		// initialize processing state
//		surface.setTitle("P3: Pants On Fire");
//		textAlign(PApplet.LEFT,PApplet.CENTER);
//		ellipseMode(PApplet.CENTER);
//		rectMode(PApplet.CENTER);
//		imageMode(PApplet.CENTER);

		// initialize member variables
		levelIndex			= 0;
		previousTimeMS		= 0;
		transitionIndex		= 0;
		transitionTimeMS	= 0;
		keyStates 			= new boolean[256];
		inputHeldDown 		= "";
		inputPressed 		= "";

		engine = this;
//		Graphic.register(this);
		if(levelIndex < levels.length)
			level = new Level(randGen, levels[levelIndex]);
		else
			level = new Level(randGen, "RANDOM");
		previousTimeMS =  System.currentTimeMillis();
	}	
	
	/**
	 * This processing method is run repeatedly after setup, as a result of 
	 * calling PApplet.main("Engine") from GameEngine.start().  It takes care of
	 * clearing the screen, drawing the HUD, updating the current level, and 
	 * also handles the transition delay between levels and quitting.
	 * @exclude
	 */	
	public void draw()
	{	
//		this.background(64,96,64); // clear screen to background color
//		this.fill(255); text(level.getHUDMessage(), 32, 32); // display HUD info
		processUserInput(); // update state of user input

		// continue to update the current level normally
		if(this.levelIndex == this.transitionIndex)
		{
			// update game
			long currentTimeMS = System.currentTimeMillis();
			String status = level.update((int)(currentTimeMS - previousTimeMS));
			previousTimeMS = currentTimeMS;
			// check whether game need to be ADVANCED or QUIT
			if( status.equals("ADVANCE") ) transitionIndex = levelIndex + 1; 
			if( status.equals("QUIT") ) transitionIndex = -1;
		}
		// transition to quit the game
		else if(this.transitionIndex == -1)
		{
			// advance count down timer
			long currentTimeMS = System.currentTimeMillis();
			transitionTimeMS +=  (int)(currentTimeMS - previousTimeMS);
			previousTimeMS = currentTimeMS;
			
			// diplay lose message
//			String msg = "YOU LOST.";
//			text(msg,(width-textWidth(msg))/2.0f,height/2.0f);
			
			// QUIT when count down expires
//			if(transitionTimeMS >= TRANSITION_TIME_MS) exit();
		}
		// transition into starting the next game level
		else
		{
			// advance count down timer
			long currentTimeMS = System.currentTimeMillis();
			transitionTimeMS +=  (int)(currentTimeMS - previousTimeMS);
			previousTimeMS = currentTimeMS;
			
			// display win message
//			String msg = "YOU WON!!!";
//			text(msg,(width-textWidth(msg))/2.0f,height/2.0f);
			
			// ADVANCE when count down expires
			if(transitionTimeMS >= TRANSITION_TIME_MS)
			{
				levelIndex = transitionIndex;
				transitionTimeMS = 0;
				if(levelIndex < levels.length)
					level = new Level( randGen, levels[levelIndex] );
				else
					level = new Level( randGen, "RANDOM" );
			}			
		}		
	}

	// PROCESSING INPUT ////////////////////////////////////////////////////////

	/**
	 * This processing method is called whenever a key is first pressed as a 
	 * result of calling PApplet.main("Engine") from GameEngine.start().  It 
	 * logs key-state for easy retrieval through is isKeyHeld and isKeyPressed.
	 * @exclude
	 */	
	public void keyPressed() 
	{ if(keyCode < keyStates.length) keyStates[keyCode] = true; } 

	/**
	 * This processing method is called whenever a key is released as a result
	 * of calling PApplet.main("Engine") from GameEngine.start().  It logs key-
	 * state for easy retrieval through is isKeyHeld and isKeyPressed.
	 * @exclude
	 */	
	public void keyReleased() 
	{ if(keyCode < keyStates.length) keyStates[keyCode] = false; }
	
	/**
	 * This helper method is called every update to convert key state from 
	 * a boolean into strings describing which keys have just been pressed, and
	 * which are currently being held.
	 */		
	private void processUserInput()
	{
		String previousInputHeldDown = inputHeldDown;
		// clear inputHeldDown before adding all keysDown to this String
		inputHeldDown = "";
		for(int i=0;i<keyStates.length;i++)
			if(keyStates[i])
				switch(i)
				{
				case 9:  inputHeldDown += "[TAB] ";   break;
				case 10: inputHeldDown += "[ENTER] "; break;
				case 16: inputHeldDown += "[SHIFT] "; break;
				case 32: inputHeldDown += "[SPACE] "; break;
				case 37: inputHeldDown += "[LEFT] ";  break;
				case 38: inputHeldDown += "[UP] ";    break;
				case 39: inputHeldDown += "[RIGHT] "; break;
				case 40: inputHeldDown += "[DOWN] ";  break;
				default: inputHeldDown += "[" + (char)i + "] ";
				}
		// adding support for detecting mouse button presses along with keys
//		if(mousePressed && mouseButton == PApplet.LEFT) 
//			inputHeldDown += "[MOUSE] ";
		
		// inputPressed only includes keys that were just pressed down
		// ie. keys that are in inputHeldDown and not in previousInputHeldDown
		inputPressed = "";
		for(String key : inputHeldDown.split(" "))
			if(!previousInputHeldDown.contains(key))
				inputPressed += key + " ";
	}	
}
