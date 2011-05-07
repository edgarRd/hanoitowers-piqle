/** An autonomous agent wandering into a closed environment 

Robot only knows what is surrounding it, in the adjecent cells.
It can move forward, backward, on turn right or left.
Reward is given when going forward
Punishment is worst when running backward than when turning.

Inspired by A. Masadpour and R. Siegwart : Compact Q-Learning optimized for micro-robots with processing and memory constraints

*/

import agents.LoneAgent; 
import agents.IAgent;
import mazes.*;
import referees.OnePlayerReferee;
import algorithms.*; 





public class CentralCrossMaze{


    public static void main(String args[])throws Exception{
	// A maze surrounding with walls 
	int taille=20; 

	AliceMaze cnossos=new AliceMaze(taille,taille); 
	// Maze design
	// 0 : free cell
	// 1 : wall
	// 2: treasure

	/* Central cross maze */
	// first, an empty maze
	int design[][]=new int[taille][]; 
	for(int i=0;i<taille;i++) {
	    design[i]=new int[taille]; 
	    for(int j=0;j<taille;j++)  design[i][j]=0;
	} 
	// Horizontal part of the cross
	for(int i=(taille+1)/3;i<(2*(taille/3));i++)
	    for(int j=1;j<taille-1;j++) design[i][j]=1;
	// Vertical part of the cross
	for(int j=(taille+1)/3;j<(2*(taille/3));j++)
	    for(int i=1;i<taille-1;i++) design[i][j]=1;
	

	
	
	cnossos.setDesign(design); 
	// Choose algorithm
	NNSelector sql=new NNSelector(); 

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
	// Start wandering in the maze
	double reward=0;
	int tailleEpisode=10; 
	for(int episode=1;episode<100000;episode++){
	    cnossos.randomInitialState(); 
	    if(episode==60000) arbitre.setGraphical(); 
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
