package sprite.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Server class 
 * @author Calvin A. Williams
 *
 */

public class Server{
	/**
	 * random object for creating random integer for sprite placement.
	 */
	public final static Random random = new Random();
	/**
	 * Constant width object for the width of the client.
	 */
	private final static int WIDTH = 500;
	/**
	 * Constant height object for the height of the client.
	 */
	private final static int HEIGHT = 500;
	
	/**
	 * Constant port number for the RMI server **MUST BE CHANGED IN CLIENT IF CHANGED HERE**
	 */
	private final static int PORT_NUM = 8092;
	/**
	 * ArrayList of Sprite objects, needed to hold all sprites and there values.
	 */
	private List<Sprite> sprites;
	
	/**
	 * thread pool for multithreading of server in the moveUpdate method.
	 */
	ExecutorService pool = Executors.newCachedThreadPool();
	
	/**
	 * RMI object being used for this assignment.
	 */
	BouncingSpritesImpl bs;
	
	//HIBERNATE OBJECTS//
	Configuration config;
	StandardServiceRegistryBuilder sRBuilder;
	ServiceRegistry sR;
	SessionFactory factory;

	/**
	 * Empty constructor for server class.
	 */
	public Server() {}

	/**
	 * Start point for java.
	 * @param args Command line values entered.
	 */
	public static void main(String[]args){
		Server s = new Server();
		s.runServer();
	}
	
	/**
	 * Method to start up the server.
	 * Initializes all hibernate objects, gets the existing sprites from a database, if none currently exist insert 3.
	 * Initializes RMI object then starts the infinite loop in the moveUpdate method for running the server.
	 */
	private void runServer(){
		//Hibernate config for the next few lines.
		config = new Configuration()
			    .addAnnotatedClass(Sprite.class)
			    .configure("hibernate.cfg.xml");
		
		sRBuilder = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
		
		sR = sRBuilder.build();
		
		factory = config.buildSessionFactory(sR);
		
		Session s = factory.getCurrentSession();
		s.beginTransaction();
		//Query the sprite DB for the list of items.
		sprites = (List<Sprite>) s.createQuery("from Sprite").list();
		s.getTransaction().commit();
		
		if(sprites.isEmpty()){
			sprites.add(new Sprite(HEIGHT,WIDTH,random.nextInt(2*WIDTH)-WIDTH,random.nextInt(2*HEIGHT)-HEIGHT));
			sprites.add(new Sprite(HEIGHT,WIDTH,random.nextInt(2*WIDTH)-WIDTH,random.nextInt(2*HEIGHT)-HEIGHT));
			sprites.add(new Sprite(HEIGHT,WIDTH,random.nextInt(2*WIDTH)-WIDTH,random.nextInt(2*HEIGHT)-HEIGHT));
		}
		
		
		try{
			//Creating RMI and starting the loop.
			LocateRegistry.createRegistry(PORT_NUM);
			bs = new BouncingSpritesImpl(sprites,HEIGHT,WIDTH);
			Naming.rebind("//localhost:" + PORT_NUM + "/Sprite",bs );
			System.out.println("I'm running!");
			moveUpdate();
		}catch(Exception e){
			System.out.println("Server failed: " + e);
		}
	}
	
	
	/**
	 * Infinite loop moves the sprite objects, then saves them to the database.
	 */
	private void moveUpdate(){
		pool.execute(new Runnable(){

			@Override
			public void run() {
				while(true){
					try {
						//If the sprites list that the client can add to is different than our current list update it.
						if(!bs.getSprites().equals(sprites)){
							sprites = bs.getSprites();
						}
					} catch (RemoteException e1) {
						System.out.println("Error checking and setting sprites.");
					}
					// Move and save the sprites.
					Session sess = factory.getCurrentSession();
					sess.beginTransaction();
					calcColour();
					for(Sprite s : sprites){
						s.move();
						sess.saveOrUpdate(s);	
					}
					sess.getTransaction().commit();
					try {
						//Sleep for 40 seconds for the fake 25 FPS
						Thread.sleep(40);
					} catch (InterruptedException e) {
						System.out.println("Error with sleep." + e.getMessage());
					}
				}
			}
		});
	}
	
	/**
	 * Calculates what colour the sprites should be based on what the previous sprite in the list is.
	 * The first sprite is set to red and the order is RED -> BLUE -> GREEN.
	 * This iterates through every element and sets the colour through every iteration, There most certainly is a better way to do this
	 * if I thought about it some more, but as extremely few sprites are expected, this will work.
	 */
	private void calcColour(){
		sprites.get(0).setColour("RED");
		for(int i = 1;i < sprites.size();i++){
			switch(sprites.get(i-1).getColour()){
				case "RED":
					sprites.get(i).setColour("BLUE");
					break;
				case "BLUE":
					sprites.get(i).setColour("GREEN");
					break;
				case "GREEN":
					sprites.get(i).setColour("RED");
					break;
			}
		}
	}

}
