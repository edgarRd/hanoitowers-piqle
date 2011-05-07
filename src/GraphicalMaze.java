/** An autonomous agent wandering into a closed environment */

import agents.LoneAgent;
import agents.IAgent; 
import mazes.*;
import environment.IAction; 
import environment.IState; 
import referees.OnePlayerReferee; 
import algorithms.*; 
import qlearning.*; 



import java.util.Enumeration; 
import java.io.*; 

public class GraphicalMaze{


    public static void main(String args[])throws Exception{
	// A maze surrounding with walls 
	int taille=20; 

	AliceMazeContinuousDistance cnossos=new AliceMazeContinuousDistance(taille,taille); 
	//Maze cnossos=new Maze(taille,taille); 
	// Maze design
	// 0 : free cell
	// 1 : wall
	// 2: treasure

	/* Central cross maze 
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
	*/

	/* Cross maze design (see masadpour's paper) */
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

	design[8][8]=2; 

// 	for(int i=0;i<taille;i++){
// 	    for(int j=0;j<taille;j++)
// 		if(design[i][j]==0) System.out.print("-"); 
// 		else System.out.print("X"); 
// 	    System.out.println();
// 	}
// 	System.exit(0); 

	
	cnossos.setDesign(design); 
	// Choose algorithm
	WatkinsSelector sql=new WatkinsSelector(0.9,new IntervalValueChooser(0.5,0.9)); 
	//SelectionneurQL sql=new SelectionneurQL(); 
	//SelectionneurQLINT sql=new SelectionneurQLINT(); 
	//sql.randomize(); 


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
	arbitre.setMaxIter(3000); 
	//arbitre.setGraphical(); 
	// Start wandering in the maze
	double reward=0;
	int tailleEpisode=1000; 
	for(int episode=1;episode<1000000;episode++){
		if(episode==300000)arbitre.setGraphical();
	    cnossos.randomInitialState(); 
	    arbitre.episode(cnossos.defaultInitialState()); 
	    reward+=arbitre.getRewardForEpisode();  
	    if((episode%tailleEpisode==0) &&(episode!=0))
		{
		    System.out.println(episode+" "+ (reward/(0.0+tailleEpisode))+" "+epsilon); 
		    // System.out.println("Episode : "+episode+" Average reward :"+ (reward/(0.0+tailleEpisode))); 
		    reward=0.0; 
		}
	    epsilon*=0.99999;
	    sql.setEpsilon(epsilon); 
	}
    }
}
