
import mobileRobot.*;
import agents.*; 
import referees.OnePlayerReferee;

import algorithms.*;  




public class TestCourse{


    public static void main(String args[]){
	Pinball zolder=new Pinball(150.0);

	zolder.addBumper(10,0,0); 
	zolder.addBumper(10,90,0); 
	zolder.addBumper(10,-90,0); 
	zolder.addBumper(10,0,90);
	zolder.addBumper(10,0,-90); 

	zolder.addBumper(10,90,90); 
	zolder.addBumper(10,-90,90); 
	zolder.addBumper(10,-90,-90);
	zolder.addBumper(10,90,-90); 
 	WatkinsNNSelector sql=new WatkinsNNSelector(0.9); 
  
 	sql.setEpsilon(0.05); 
	double epsi=0.05; 

	IAgent zero07=new LoneAgent(zolder,sql);
	OnePlayerReferee arbitre=new OnePlayerReferee(zero07); 
	//arbitre.setGraphical(); 
	arbitre.setMaxIter(5000); 
	//	arbitre.setVerbosity();
	double buffer[]=new double[1000]; 
	int indice=0; 
	double total=0.0; 
	// 50000 episodes
	// Output 
	// - Episode number
	// - Episode length
	// - Global reward
	// - Average reward on the last 1000 episodes
	for(int episode=1;episode<50000;episode++){
		if(episode==1000)arbitre.setGraphical(); 
	    int u=arbitre.episode(zolder.defaultInitialState()); 
	    total-=buffer[indice]; 
	    buffer[indice]=arbitre.getRewardForEpisode(); 
	    total+=buffer[indice]; 
	    indice=(indice+1)%1000; 
	    System.out.println(episode+" "+u+" "+arbitre.getRewardForEpisode()
	    		+" "+(total/1000.0));
	    epsi*=0.999; 
	    sql.setEpsilon(epsi); 
	}
	   

    }
}

