/** Test of an wandering agent */

import agents.IAgent;
import agents.LoneAgent;
import mazes.*;
import environment.IAction; 
import environment.IState; 
import referees.OnePlayerReferee; 
import algorithms.*; 
import qlearning.*; 



import java.util.Random; 
public class MazeTest{


    public static void main(String args[])throws Exception{
	// A 10x10 maze
	int taille=100; 
	//Maze cnossos=new Maze(10,10); 
	//LabLocal cnossos=new LabLocal(taille,taille);
	AliceMazeContinuousDistance cnossos=new AliceMazeContinuousDistance(taille,taille); 
	// Maze design
	// 0 : free cell
	// 1 : wall
	// 2: treasure
	//int design[][]={{0,0,0,0,0,0,0,0,0,0},{1,1,1,1,0,0,0,0,0,0},{1,0,0,1,0,0,0,0,0,0},{1,0,1,1,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,1,1,1,1,1,0,0},{0,0,0,0,1,1,0,0,0,0},{0,0,0,1,1,1,1,1,0,0},{0,0,0,0,0,0,0,0,0,0}}; 
	//	int design[][]={{0,0,0,0,0,0,0,0,0,0},{1,1,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,1,0},{0,0,0,0,0,0,0,2,1,0},{0,1,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,1,0},{1,1,1,1,1,1,1,0,1,0},{0,0,0,0,0,0,0,0,1,0},{0,1,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0}}; 
	
	Random generateur=new Random(); 
	
	int design[][]=new int[taille][]; 
	for(int i=0;i<taille;i++) {
	    design[i]=new int[taille]; 
	    for(int j=0;j<taille;j++) {
		double v=generateur.nextDouble(); 
		if(v<0.2) design[i][j]=1; 
		else design[i][j]=0;
	    } 
	}
	
	design[taille/2][taille/2]=2; 
	cnossos.setDesign(design); 
	// Choose algorithm
	//SelectionneurQL sql=new SelectionneurQL(); 
	WatkinsSelector sql=new WatkinsSelector(0.9); 

	

	// Control exploration/exploitation
	double epsilon=0.5; 
	sql.setEpsilon(epsilon); 
	sql.setAlphaDecayPower(0.5); 
	//	sql.setGeometricAlphaDecay(); 

	// Build agent
	IAgent zero07=new LoneAgent(cnossos,sql); 
	// Call referee
	OnePlayerReferee arbitre=new OnePlayerReferee(zero07); 
	arbitre.setMaxIter(10000); 
	//arbitre.setGraphical(); 
	// Start wandering in the maze
	double total=0; 
	double reward=0;
	int tailleEpisode=10; 
	for(int episode=0;episode<100000;episode++){
	    //cnossos.randomInitialState();
		if(episode==5000)arbitre.setGraphical();
	    int value=arbitre.episode(cnossos.defaultInitialState()); 
	    total+=value;
	    reward+=arbitre.getRewardForEpisode(); 
	    if(episode%tailleEpisode==0) 
		{
		    System.out.println("Episode : "+episode+" Average reward :"
		    		+ (reward/(0.0+tailleEpisode))); 
		    reward=0.0; 
		}
	    epsilon*=0.999999;
	    sql.setEpsilon(epsilon); 
	}
	zero07.explainValues(); 
   
    }
}

