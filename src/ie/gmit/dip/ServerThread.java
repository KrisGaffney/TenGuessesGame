package ie.gmit.dip;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerThread extends Thread{
	
	private Socket skt = null;//Initialise socket as null
	private static final int MAX_GUESSES = 10;
	public ServerThread(Socket socket){
		skt = socket;
	}
	
	private void game(){
	    String inputLine;			//This is the user input as a string
		int playerguess;			//This is the user input as an integer
		int counter = 0;			//The players guess count starts as 0
			
		int Random =(int) (Math.random() * 1000); //Create a random number between 0 and 1000
		
		try{
			Scanner in = new Scanner(skt.getInputStream());			
			PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
			
			while (true){
				inputLine = in.nextLine();					//Each new line is the users input
				playerguess = Integer.parseInt(inputLine);	//Convert the userinput to an integer
				
				
				if (playerguess == Random){					//If the playerguess equals the random number
					out.println("Congratulations! You guessed correctly!" + " You guessed the correct number in " + counter + " attempts. " +"Would you like to play again? y/n");
					inputLine = in.nextLine();	
				
					if (inputLine.equalsIgnoreCase("y")){		//If the user wants to replay they enter y
					   
						
						out.println("I'm thinling of another numbeer between 1 and 1000. Can you guess what it is?");
						game(); 
						
					}
					else if (inputLine.equalsIgnoreCase("n")){	//If the user wants to quit and enters n
					
						out.println("Thank you for playing.");
						break;									//Break out of the While loop
					}
				}
				else if (playerguess < Random){					//If the player guess is below the random number
					out.println(playerguess + "? that's too low!");
					counter ++;									//Add one to the counter
				}
				else if (playerguess > Random){					//If the player guess is above the random number
				    out.println(playerguess + "? that's too high!");
					counter ++;									//Add one to the counter
				}
				else if (inputLine.equalsIgnoreCase("quit")){	//If the player types quit, ignoring case
					out.println("Thank you for playing.");
					break;										//Break out of the While loop
				}
			}
			//Close all connections
			out.close();			
			in.close();
			skt.close();
		}
		catch (Exception e){
		    //If the client has disconnected from the server
			System.err.println("Client connection termintated" + e);	//Print error message to server screen
		}
	}
	
	public void run(){
		System.err.println("Client has connected successfully");	//Print to server screen
		
		game(); 
	
	}
}