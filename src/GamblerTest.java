

import algorithms.*; 
import agents.LoneAgent; 
import referees.OnePlayerReferee; 
import gambler.*;  

import java.util.Random; 

public class GamblerTest{

    public static void main(String[] argv){ 
	Random generateur=new Random(); 

	// Conditions defined in Sutton & Barto
	double proba=0.4; 
	double gamma=1.0; 
	GamblerGame casino=new GamblerGame(proba); 

	casino.computeVStar(gamma); 
	// Uncomment the following lines if you want to print vstar values
	// and optimal policy
	//casino.displayVStar(); 
	// Using the following data, one can redraw fig 4.6 page 103
	//casino.extractPolicy(gamma); 


	// Using a Q-Learning algorithm
	QLearningSelector algo=new QLearningSelector();
	// Choose the discount factor
	algo.setGamma(gamma);
	// Control alpha decay
    //    algo.setAlphaDecayPower(0.5);
  	// algo.setAlpha(0.5);

	// Control epsilon (exploration-exploitation balance)
	double epsi=0.5; 
	algo.setEpsilon(epsi); 

	// Set the player
	LoneAgent joueur=new LoneAgent(casino,algo); 
	// Set the referee
	OnePlayerReferee arbitre=new OnePlayerReferee(joueur); 
	arbitre.setMaxIter(10000);

	// Initializing episodes loop
	double total=0.0; 
	double moyenne=0.0; 
	for(int i=0;i<500000;i++){// One episode
	    int initialCapital=generateur.nextInt(99)+1; 
	    int u=arbitre.episode(new GamblerState(casino,initialCapital));
	    double rew=arbitre.getRewardForEpisode(); 
	    total+=rew; 
	    moyenne+=rew; 
	    if((i%10000==0)&&(i!=0)) {
		System.out.println("Episodes "+i+" Average reward "+ total/i+" Av. Reward for last 10000 episodes "+moyenne/10000.0); 
		moyenne=0.0;
		// to monitor the evolution of Q(s,a)
		//algo.makeHistogram(i/10000); 
		//	System.out.println(); 
	    }
	    // Decay epsilon
	    epsi*=0.99999; 
	    algo.setEpsilon(epsi); 
    }
	//joueur.explainValues(); 
	
	// See the difference between Vstar and Q(s,a)
	for(int i=1;i<100;i++){
	    GamblerState e=new GamblerState(casino,i); 
	    ActionGambler a=(ActionGambler)algo.bestAction(e); 
	    // One can see than Q(s,a) curve is very close to Vstar estimates
	    // Uncomment next line if you want to print this comparison
	    System.out.println(i+" "+(casino.getVStar(e)-algo.getValue(e,a))+" "+casino.getVStar(e)+" "+algo.getValue(e,a)); 
	}

	// Use Q(s,a) to extract policy (not as good as Vstar)

	for(int i=1;i<100;i++){
	    double max=-1; 
	    double indice=-1; 
	    for(int j=1;j<=Math.min(i,100-i);j++){
		GamblerState e=new GamblerState(casino,i+j); 
		ActionGambler a=(ActionGambler)algo.bestAction(e); 
		double vs=proba*gamma*algo.getValue(e,a); 
		if(i+j==100) vs+=proba; 
		e=new GamblerState(casino,i-j); 
		a=(ActionGambler)algo.bestAction(e); 
		vs+=(1-proba)*gamma*algo.getValue(e,a); 
		if(vs-max>1e-4)
		    {max=vs; indice=j;}
	    }
	    // Uncomment next line to print the result
	    // System.out.println(i+" "+indice); 
	}
   
}
}
