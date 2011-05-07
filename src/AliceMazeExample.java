/** An autonomous agent wandering into a closed environment */

import agents.IAgent; 
import agents.LoneAgent;
import mazes.*;
import referees.OnePlayerReferee;
import algorithms.*; 
 


public class AliceMazeExample{


    public static void main(String args[])throws Exception{
	// A maze surrounding with walls 
	int mazeSize=51; 

	//AliceMazeDistance cnossos=new AliceMazeDistance(mazeSize,mazeSize);
	//AliceMaze cnossos=new AliceMaze(mazeSize,mazeSize);
	AliceMazeContinuousDistance cnossos=new AliceMazeContinuousDistance(mazeSize,mazeSize);
	// Maze design
	// 0 : free cell
	// 1 : wall
	// 2: treasure

	
	/* Cross maze design (see masadpour's paper) */
	int design[][]=new int[mazeSize][]; 
	for(int i=0;i<mazeSize;i++) {
	    design[i]=new int[mazeSize]; 
	    for(int j=0;j<mazeSize;j++)  design[i][j]=0;
	}
	// Upper left corner
	for(int i=0;i<mazeSize/4;i++)
	    for(int j=0;j<mazeSize/4;j++) design[i][j]=1; 
	// Upper right corner
	for(int i=0;i<mazeSize/4;i++)
	    for(int j=1+(3*mazeSize/4);j<mazeSize;j++) design[i][j]=1; 
	// Lower left corner
	for(int i=1+(3*mazeSize/4);i<mazeSize;i++)
	    for(int j=0;j<mazeSize/4;j++) design[i][j]=1; 
	// Lower right corner
	for(int i=1+(3*mazeSize/4);i<mazeSize;i++)
	   for(int j=1+(3*mazeSize/4);j<mazeSize;j++) design[i][j]=1; 
	// Vertical bar
	for(int i=mazeSize/4;i<mazeSize;i++)
	    for(int j=7*mazeSize/16;j<9*mazeSize/16;j++)
		design[i][j]=1; 
	// Horizontal bar
	for(int i=7*mazeSize/16;i<9*mazeSize/16;i++)
	    for(int j=(mazeSize/4)-3;j<(3*mazeSize/4)+3;j++)
		design[i][j]=1; 


	cnossos.setDesign(design); 
	// Choose algorithm
	//QLearningSelector sql=new QLearningSelector();
	NNSelectorSinglePass sql=new NNSelectorSinglePass();


	// Control exploration/exploitation
	double epsilon=0.9; 
	sql.setEpsilon(epsilon); 
	sql.setAlphaDecayPower(0.5);     

	// Build agent
	IAgent zero07=new LoneAgent(cnossos,sql); 

	// Call referee
	OnePlayerReferee arbitre=new OnePlayerReferee(zero07); 
	// Length of episode
	arbitre.setMaxIter(1000); 
	// Start wandering in the maze
	double reward=0;
	int tailleEpisode=100; 
	for(int episode=1;episode<1000000;episode++){
		if(episode==100000)arbitre.setGraphical();
	    cnossos.randomInitialState(); 
	    arbitre.episode(cnossos.defaultInitialState()); 
	    reward+=arbitre.getRewardForEpisode();  
	    if((episode%tailleEpisode==0) &&(episode!=0))
		{
		    System.out.println(episode+" "+ (reward/(0.0+tailleEpisode))); 
		    // System.out.println("Episode : "+episode+" Average reward :"+ (reward/(0.0+tailleEpisode))); 
		    reward=0.0; 
		}
	    epsilon*=0.9999;
	    sql.setEpsilon(epsilon); 
	}
	
}
}
 

