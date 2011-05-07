

import agents.*; 
import environment.IAction; 
import environment.IState; 
import referees.OnePlayerReferee; 

import algorithms.*;  



import mobileRobot.*;

public class Course{


    public static void main(String args[]) throws Exception{
	CircularCircuit zolder=new CircularCircuit(50.0,150.0); 
 	WatkinsSelector sql=new WatkinsSelector(0.9); 
	//SelectionneurQL sql=new SelectionneurQL();
 	sql.setEpsilon(0.05); 
	double epsi=0.05; 
	//sql.setGeometricAlphaDecay(); 
 	sql.setGamma(1.0); 
	//SelectionneurAleatoire sql=new SelectionneurAleatoire(); 
	IAgent zero07=new LoneAgent(zolder,sql);
	OnePlayerReferee arbitre=new OnePlayerReferee(zero07); 
	//arbitre.setGraphical(); 
	arbitre.setMaxIter(5000); 
	//	arbitre.setVerbosity();
	double buffer[]=new double[1000]; 
	int indice=0; 
	double total=0.0; 
	for(int episode=1;episode<100000;episode++){
		if(episode==1000)arbitre.setGraphical();
	    int u=arbitre.episode(zolder.defaultInitialState()); 
	    total-=buffer[indice]; 
	    buffer[indice]=arbitre.getRewardForEpisode(); 
	    total+=buffer[indice]; 
	    //if(total/1000.0>1000) break; 
	    indice=(indice+1)%1000; 
	    System.out.println(episode+" "+u+" "+arbitre.getRewardForEpisode()+" "+epsi+" "+(total/1000.0));
	      epsi*=0.999; 
	    sql.setEpsilon(epsi); 
	    
	    }
	   
	

	zero07.explainValues(); 

	MobileState ev=(MobileState)zolder.defaultInitialState(); 
	for(int i=0;i<1000;i++){
	    System.out.println(i+" "+ev.coordinates()); 
	    MobileAction av=(MobileAction)sql.bestAction(ev); 
	    ev=(MobileState)ev.modify(av); 
	    if (ev.isFinal()) break;
	}

    }
}

