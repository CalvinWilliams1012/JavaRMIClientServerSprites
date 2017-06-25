package sprite.rmi;

import java.util.List;
/**
 * Interface for BouncingSprites remote object interface.
 * @author Calvin A. Williams
 *
 */
public interface BouncingSprites extends java.rmi.Remote {
	
	/**
	 * Method that can be invoked remotely to get the playing field height.
	 * @return Height of the playing field.
	 * @throws java.rmi.RemoteException
	 */
	public int getHeight() throws java.rmi.RemoteException;
	/**
	 * Method that can be invoked remotely to get the playing field width.
	 * @return Width of the playing field.
	 * @throws java.rmi.RemoteException
	 */
	public int getWidth() throws java.rmi.RemoteException;
	/**
	 * Method that can be invoked remotely to get a list of the sprites.
	 * @return List of sprites.
	 * @throws java.rmi.RemoteException
	 */
	public List<Sprite> getSprites() throws java.rmi.RemoteException;
	/**
	 * Method that can be invoked remotely to create an additional sprite at the x,y location specified by the client.
	 * @param x X value of the new sprite.
	 * @param y Y value of the new sprite.
	 * @throws java.rmi.RemoteException
	 */
	public void createSprite(int x,int y) throws java.rmi.RemoteException;
	
}

