package sprite.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Bouncing Sprites implementation for the Server. 
 * Server specifies some values which an RMI client then can receive.
 * @author Calvin A. Williams
 *
 */
public class BouncingSpritesImpl extends UnicastRemoteObject implements BouncingSprites {
	private List<Sprite> sprites;
	private int Height;
	private int Width;
	
	/**
	 * Constructor for bouncing sprite implementation.
	 * @param sprites List of sprites.
	 * @param h Height of frame.
	 * @param w Width of frame.
	 * @throws RemoteException exception thrown to client.
	 */
	public BouncingSpritesImpl(List<Sprite> sprites,int h, int w) throws RemoteException {
		super();
		this.sprites = sprites;
		this.Height = h;
		this.Width = w;
	}

	/**
	 * Getter for the height so the client can get the height of the frame.
	 * @throws RemoteException exception thrown to client.
	 */
	@Override
	public int getHeight() throws RemoteException {
		return Height;
	}

	/**
	 * Getter for the width so the client can get the width of the frame.
	 * @throws RemoteException exception thrown to client.
	 */
	@Override
	public int getWidth() throws RemoteException {
		return Width;
	}

	/**
	 * Getter for the sprites so the client can grab the sprites.
	 * @throws RemoteException exception thrown to client.
	 */
	@Override
	public List<Sprite> getSprites() throws RemoteException {
		return sprites;
	}

	/**
	 * Method that can be called by the client to create a new sprite.
	 * @throws RemoteException exception thrown to client.
	 */
	@Override
	public void createSprite(int x, int y) throws RemoteException {
		sprites.add(new Sprite(Height,Width,x,y));
	}


}
