# Java RMI Client/Server with Hibernate
This is an RMI with Hibernate combination of the Client/Server Project [here](https://github.com/CalvinWilliams1012/JavaClientServer) and the Bouncing Sprites Project [here](https://github.com/CalvinWilliams1012/BouncingSprites) but without the multithreading.
Sprites are saved in a MySQL database using Hibernate (Sprites class annotated, Server class committing).
Clients connect to the Server and gets an RMI object that hold an array of the sprites and methods to get height/width.
To add sprites the Client clicks and a method on the RMI object is called to create a new sprite at the position clicked.