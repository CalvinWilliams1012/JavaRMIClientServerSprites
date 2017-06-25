package sprite.rmi;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * A Sprite is a graphics object of an oval with a size and a speed and has collision
 * detection with the panel walls.
 * 
 * @author Calvin A. Williams
 *
 */
@Entity
@Table(name="sprites")
public class Sprite implements Serializable {

	
	/**
	 * Sprite ID for PK in database.
	 */
	private int spriteID;
	
	/**
	 * random object for creating random integer for sprite placement.
	 */
	public final static Random random = new Random();

	/**
	 * Size of the sprite
	 */
	final static int SIZE = 10;
	/**
	 * Maximum speed of the sprite, randomized with random object.
	 */
	final static int MAX_SPEED = 5;

	/**
	 * sprites x value on the panel.
	 */
	private int x;

	/**
	 * sprites y value on the panel.
	 */
	private int y;

	/**
	 * dx value which is actual x speed.
	 */
	private int dx;

	/**
	 * dy value which is actual y speed.
	 */
	private int dy;

	/**
	 * color to make sprites
	 */
	private Color color;
	
	/**
	 * Database version of colour, 
	 */
	private String colour;
	


	/**
	 * Size of the JPanel/Frame 
	 */
	private int sizeH,SizeW;

	/**
	 * Hibernate was throwing a few things when this did not have a default constructor.
	 */
	public Sprite(){}
	
	/**
	 * Constructor to initialize speed and placement of sprite
	 * 
	 * @param panel
	 *            Panel object to get the size of the panel.
	 */
	public Sprite(int sizeH,int sizeW,int x, int y) {
		this.sizeH = sizeH;
		this.SizeW = sizeW;
		this.x = x;		
		this.y = y;
		dx = random.nextInt(2 * MAX_SPEED) - MAX_SPEED;
		dy = random.nextInt(2 * MAX_SPEED) - MAX_SPEED;
	}

	/**
	 * Draws the sprite using a graphics object from SpritePanel.
	 * 
	 * @param g
	 *            Graphics object from sprite panel.
	 */
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, SIZE, SIZE);
	}

	/**
	 * Changes speed if the side of the panel is hit and changes the xy values
	 * based on speed.
	 */
	public void move() {

		// check for bounce and make the ball bounce if necessary
		//
		if (x < 0 && dx < 0) {
			// bounce off the left wall
			x = 0;
			dx = -dx;
		}
		if (y < 0 && dy < 0) {
			// bounce off the top wall
			y = 0;
			dy = -dy;
		}
		if (x > SizeW - SIZE && dx > 0) {
			// bounce off the right wall
			x = SizeW - SIZE;
			dx = -dx;
		}
		if (y > sizeH - SIZE && dy > 0) {
			// bounce off the bottom wall
			y = sizeH - SIZE;
			dy = -dy;
		}

		// make the ball move
		x += dx;
		y += dy;
	}
	/***********************************************************************************
	 ***************************HIBERNATE WITH GETTERS BELOW****************************
	 ***********************************************************************************/
	
	/**
	 * Getter for the spriteID.
	 * @return The sprite ID.
	 */
	@Id
	@GeneratedValue
	public int getSpriteID(){
		return spriteID;
	}
	/**
	 * Setter for the sprite ID
	 * @param spriteID Value to set the spriteID too.
	 */
	public void setSpriteID(int spriteID) {
		this.spriteID = spriteID;
	}
	/**
	 * Getter for the Size of the sprite.
	 * @return SIZE of the sprite.
	 */
	public static int getSize() {
		return SIZE;
	}

	/**
	 * Getter for the x position of the sprite.
	 * @return x position.
	 */
	public int getX() {
		return x;
	}
	/**
	 * Setter for the X position of the sprite.
	 * @param x x value to change sprites x too.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Getter for the Y position of the sprite.
	 * @return y position.
	 */
	public int getY() {
		return y;
	}
	/**
	 * Setter for the Y position of the sprite.
	 * @param y y value to change sprites y too.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Getter for the current X speed of the sprite.
	 * @return dx speed
	 */
	@Column(name = "xSpeed")
	public int getDx() {
		return dx;
	}
	/**
	 * Setter for the current X speed of the sprite.
	 * @param dx Value to change sprites X speed too.
	 */
	public void setDx(int dx) {
		this.dx = dx;
	}
	/**
	 * Getter for the current Y speed of the sprite.
	 * @return dy speed.
	 */
	@Column(name = "ySpeed")
	public int getDy() {
		return dy;
	}
	/**
	 * Setter for the current Y speed of the sprite.
	 * @param dy Value to change sprites Y speed too.
	 */
	public void setDy(int dy) {
		this.dy = dy;
	}
	
	/**
	 * Getter for the colour of the sprite.
	 * @return Sprite colour.
	 */
	@Transient
	public Color getColor() {
		return color;
	}
	/**
	 * Setter for the colour of the sprite.
	 * @param color Value to set the sprites colour too.
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Getter for the colour string version.
	 * @return colour in a string
	 */
	public String getColour() {
		return colour;
	}

	/**
	 * Setter for the colour string which also sets the actual GUI color of the sprite.
	 * @param colour string to set colour too.
	 */
	public void setColour(String colour) {
		this.colour = colour;
		if(colour.equals("RED")){
			color = Color.RED;
		}else if(colour.equals("BLUE")){
			color = Color.BLUE;
		}else if(colour.equals("GREEN")){
			color = Color.GREEN;
		}else{
			color = Color.BLACK;
		}
	}
	/**
	 * Getter for the Height of the panel/frame the sprite is in for the collision calculation.
	 * @return Height of the panel/frame.
	 */
	public int getSizeH() {
		return sizeH;
	}
	/**
	 * Setter for the Height of the panel/frame the sprite is in for the collision calculation.
	 * @param sizeH Value to change height too.
	 */
	public void setSizeH(int sizeH) {
		this.sizeH = sizeH;
	}
	
	/**
	 * Getter for the Width of the panel/frame the sprite is in for the collision calculation.
	 * @return Width of the panel/frame.
	 */
	public int getSizeW() {
		return SizeW;
	}
	/**
	 * Setter for the Width of the panel/frame the sprite is in for the collision calculation.
	 * @param sizeW Value to change width too.
	 */
	public void setSizeW(int sizeW) {
		SizeW = sizeW;
	}
	
	/**
	 * Getter for the Random number generator that calculates speed.
	 * @return
	 */
	@Transient
	public static Random getRandom() {
		return random;
	}


	/**
	 * Getter for the max speed of the sprite.
	 * @return Max speed of the sprite.
	 */
	public static int getMaxSpeed() {
		return MAX_SPEED;
	}

}
