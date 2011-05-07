/** An autonomous agent wandering into a closed environment 

Reward is due when the treasure is found.

Agent only sees the squares around it, and knows its distance toward treasure

*/

import agents.IAgent;
import agents.LoneAgent;
import mazes.*;
import referees.OnePlayerReferee; 
import algorithms.*;
import qlearning.ConstantValueChooser;





public class LocalVisionMazeExample{


    public static void main(String args[])throws Exception{
	// A maze surrounding with walls 
	int taille=20; 

	LocalVisionMaze cnossos=new LocalVisionMaze(taille,taille); 
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
	for(int i=((taille+1)/3);i<(2*(taille/3));i++)
	    for(int j=2;j<taille-2;j++) design[i][j]=1;
	// Vertical part of the cross
	for(int j=(taille+1)/3;j<(2*(taille/3));j++)
	    for(int i=2;i<taille-2;i++) design[i][j]=1;
	
	design[3][3]=2; 
	
	
	cnossos.setDesign(design); 
	// Choose algorithm
	QLearningSelector sql=new QLearningSelector();
	//NNSelectorSinglePass sql=new NNSelectorSinglePass(new ConstantValueChooser(1.0)); 

	// Control exploration/exploitation
	double epsilon=0.4; 
	sql.setEpsilon(epsilon); 
	//sql.setAlphaDecayPower(0.5);     // For WatkinsSelector
	sql.setGeometricAlphaDecay();  // Do not use this for WatkinsSelector (empirical)

	// Build agent
	IAgent zero07=new LoneAgent(cnossos,sql); 

	// Call referee
	OnePlayerReferee arbitre=new OnePlayerReferee(zero07); 
	// Length of episode
	arbitre.setMaxIter(1000); 
	//arbitre.setGraphical();
	// Start wandering in the maze
	double reward=0;
	int tailleEpisode=10; 
	for(int episode=1;episode<100000;episode++){
		if(episode==5000)arbitre.setGraphical();
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
