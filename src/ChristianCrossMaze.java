/** An autonomous agent wandering into a closed environment : 
Robot only knows what is surrounding it, in the adjecent cells.
It can move forward, backward, on turn right or left.
Reward is given when going forward
Punishment is worst when running backward than when turning.

Inspired by A. Masadpour and R. Siegwart : Compact Q-Learning optimized for micro-robots with processing and memory constraints
*/

import agents.IAgent;
import agents.LoneAgent;
import mazes.*;
import referees.OnePlayerReferee; 
import algorithms.*; 



public class ChristianCrossMaze{


    public static void main(String args[])throws Exception{
	// A maze surrounding with walls 
	int taille=20; 

	AliceMaze cnossos=new AliceMaze(taille,taille); 
	// Maze design
	// 0 : free cell
	// 1 : wall
	// 2: treasure



	/* Crhistian cross maze design (see Masadpour's paper) */
	
	  int design[][]=new int[taille][]; 
	  for(int i=0;i<taille;i++) {
	  design[i]=new int[taille]; 
	  for(int j=0;j<taille;j++)  design[i][j]=0;
	  }
	  // Upper left corner
	  for(int i=0;i<taille/4;i++)
	  for(int j=0;j<taille/4;j++) design[i][j]=1; 
	  // Upper right corner
	  for(int i=0;i<taille/4;i++)
	  for(int j=3*taille/4;j<taille;j++) design[i][j]=1; 
	  // Lower left corner
	  for(int i=3*taille/4;i<taille;i++)
	  for(int j=0;j<taille/4;j++) design[i][j]=1; 
	  // Lower right corner
	  for(int i=3*taille/4;i<taille;i++)
	  for(int j=3*taille/4;j<taille;j++) design[i][j]=1; 
	  // Vertical bar
	  for(int i=taille/4;i<taille;i++)
	  for(int j=1+(7*taille/16);j<9*taille/16;j++)
	  design[i][j]=1; 
	  // Horizontal bar
	  for(int i=1+(7*taille/16);i<9*taille/16;i++)
	  for(int j=(taille/4)-3;j<(3*taille/4)+3;j++)
	  design[i][j]=1; 

	


	
	cnossos.setDesign(design); 
	// Choose algorithm
	WatkinsSelector sql=new WatkinsSelector(0.9); 

	// Control exploration/exploitation
	double epsilon=0.5; 
	sql.setEpsilon(epsilon); 
	sql.setAlphaDecayPower(0.5);     // A utiliser pour Watkins
	//sql.setGeometricAlphaDecay();  // Ne marche pas pour Watkins

	// Build agent
	IAgent zero07=new LoneAgent(cnossos,sql); 

	// Call referee
	OnePlayerReferee arbitre=new OnePlayerReferee(zero07); 
	// Length of episode
	arbitre.setMaxIter(300); 
	//arbitre.setGraphical(); 
	// Start wandering in the maze
	double reward=0;
	int tailleEpisode=100; 
	for(int episode=1;episode<100000;episode++){
		if(episode==50000)arbitre.setGraphical();
	    cnossos.randomInitialState(); 
	    arbitre.episode(cnossos.defaultInitialState()); 
	    reward+=arbitre.getRewardForEpisode();  
	    if((episode%tailleEpisode==0) &&(episode!=0))
		{
		    System.out.println(episode+" "+ (reward/(0.0+tailleEpisode))+" "+epsilon); 
		    // System.out.println("Episode : "+episode+" Average reward :"+ (reward/(0.0+tailleEpisode))); 
		    reward=0.0; 
		}
	    epsilon*=0.9999;
	    sql.setEpsilon(epsilon); 
	}
    }
}
