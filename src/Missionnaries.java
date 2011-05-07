
import agents.*; 
import missionaries.*; 
import referees.OnePlayerReferee;
import qlearning.*;

import algorithms.*;  


public class Missionnaries{


    public static void main(String args[]){
	//Congo kinshasa=new Congo();
    	Brazzaville kinshasa=new Brazzaville();
	
	/*Learns in approx 5000 steps */
	//NNSelectorSinglePass sql=new NNSelectorSinglePass(); 
	/* Learns in approx 5000 steps */
	//NNSelector sql=new NNSelector(); 
	/* Learns in approx 300 steps if epsi*=0.95 */
	QLearningSelector sql=new QLearningSelector();
	/* Learns in approx 400 steps if epsi*=0.95 */
	//WatkinsSelector sql=new WatkinsSelector(0.9);  
	double epsi=0.1; 
	sql.setEpsilon(epsi);
	sql.setGeometricAlphaDecay(); 
	sql.setGamma(1.0);
	IAgent zero07=new LoneAgent(kinshasa,sql);
	OnePlayerReferee arbitre=new OnePlayerReferee(zero07); 
	arbitre.setMaxIter(500); 
	for(int episode=0;episode<5000;episode++){
	    int u=arbitre.episode(kinshasa.defaultInitialState());
	    if(episode%1000==0){
	    System.out.println(episode+" "+u+" "+arbitre.getRewardForEpisode());
	    zero07.saveAgent("/tmp/testErase");
		epsi*=0.999; 
		sql.setEpsilon(epsi); 
	    }
	}
    }
}

