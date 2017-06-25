package sprite.rmi;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * 
 * @author Calvin A. Williams
 *
 */
@SuppressWarnings("serial")
public class Client extends JPanel{
	
	/**
	 * KEEP THE SAME AS SERVER
	 */
	private final static int PORT_NUM = 8092;

	/**
	 * ArrayList of Sprite objects, needed to draw all sprites.
	 */
	private List<sprite.rmi.Sprite> sprites;
	
	/**
	 * BouncingSprites RMI object of the interface to call methods on.
	 */
	BouncingSprites bs;
	
	/**
	 * CLient JFrame to put panel in.
	 */
	private JFrame frame;
	
	/**
	 * Constructor to initialize sprites list and add a mouse listener to the
	 * panel. The constructor also grabs the RMI object.
	 */
	public Client() {
		try {
			bs = (BouncingSprites) Naming.lookup("//localhost:" + PORT_NUM + "/Sprite");
			sprites = bs.getSprites();
			System.out.println("Client running, Remote Object retrieved");
			if(sprites != null){
				for(Sprite s : sprites){
					System.out.println(s);
				}
			}
		} catch (Exception e) {
			System.out.println("Grabbing init sprites error: " + e.getMessage());
		}
		
		addMouseListener(new Mouse());
	}
	/**
	 * Java entry point, starts client.
	 * @param args
	 */
	public static void main(String[] args) {
		Client c = new Client(); // 
		c.runClient();
		
	}
	
	/**
	 * runClient method creates the GUI and then runs the client through a loop.
	 */
	private void runClient(){
		frame = new JFrame("Bouncing Sprite Client");
		try{
			frame.setSize(bs.getWidth(), bs.getHeight());
		}catch(RemoteException e){
			System.out.println("Error with setting client frame size" + e.getMessage());
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(this);
		frame.setVisible(true);
			
		
		
		while(true){
			try {
				sprites = bs.getSprites();
				repaint();
				Thread.sleep(40);
			} catch (Exception e) {
				System.out.println("Grabbing sprites error: " + e.getMessage());
			}
		}
		
		
	}
	
	/**
	 * paints the canvas with graphics, iterates through sprites and animates
	 * then through draw, draws the center circle.
	 * 
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Sprite p : sprites) {
			p.draw(g);
		}
	}

	/**
	 * Mouse click listener.
	 */
	private class Mouse extends MouseAdapter {
		@Override
		public void mousePressed(final MouseEvent event) {
			try {
				bs.createSprite(event.getX(), event.getY());
			} catch (RemoteException e) {
				System.out.println("Error creating a new rmi sprite" + e.getMessage());
			}
		}
	}

}
