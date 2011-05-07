/* Illustration of jack Car rental problem (Sutton and Barto page 98)
Issuing informations to be treated with FOIL or Weka */

import agents.*; 
import jacksCarRental.*; 
import referees.OnePlayerReferee;
import algorithms.*;  



public class CarRentalTest{

    public static void main(String argv[]) throws Exception{
	CarRental avis=new CarRental(); 


	WatkinsSelector sql=new WatkinsSelector(0.9); 
	sql.setGamma(0.9); 


	double epsilon=0.5; 
	sql.setEpsilon(epsilon); 
	sql.setAlpha(0.1); 
	sql.setDecay(1-1e-10); 
	sql.setGeometricAlphaDecay(); 



	IAgent jack=new LoneAgent(avis,sql); 
	
	avis.computeVStar(0.9); 
	avis.displayVStar(); 
	/* Comparing VStar to best Q(s,a) value will require some coding : look at
	GamblerTest.java to get ideas */ 

	OnePlayerReferee arbitre=new OnePlayerReferee(jack); 
	arbitre.setMaxIter(500); 
	double totalReward=0; 
	for(int i=0;i<1000;i++){
	    epsilon*=0.9999; 
	    sql.setEpsilon(epsilon); 
	    arbitre.episode(new EtatJack(avis,20,20)); 
	    totalReward+=arbitre.getRewardForEpisode(); 
	    if (i%10==0) System.out.println(i+" "+(totalReward/(i+1.0))); 	    
	}
	
	
    }

}
