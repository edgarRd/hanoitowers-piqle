
import agents.*; 
import acrobot.*;

import referees.OnePlayerReferee;

import algorithms.*;  
import qlearning.*;





public class TestAcrobotCLS2{


    public static void main(String args[]){
	Acrobot cirque=new AcrobotCLS2();  
	
	// Begins to learn in about 5000 steps
	WatkinsNNSelector sql = new WatkinsNNSelector(0.9,new ConstantValueChooser(2.0));    
	double epsi=0.5; 
	sql.setEpsilon(epsi); 
	sql.setGamma(1); 
	sql.setGeometricAlphaDecay(); 
	//sql.setReplace(); 
	sql.setAlpha(0.2); 
	LoneAgent zero07=new LoneAgent(cirque,sql);
	OnePlayerReferee arbitre=new OnePlayerReferee(zero07); 
	//arbitre.setGraphical(); 
	int maxIter=1000; 
	arbitre.setMaxIter(maxIter); 

	double buffer[]=new double[1000]; 
	int indice=0; 
	double total=0.0; 
	double facteur=0; 

	System.out.println("#Episode\t Length\t Average Length");
	for(int episode=0;episode<500000;episode++){
	    int u=arbitre.episode(cirque.defaultInitialState()); 
	     total-=buffer[indice]; 
	    buffer[indice]=u; 
	    
	    total+=buffer[indice]; 
	    indice=(indice+1)%1000;
	    if(episode>10000) arbitre.setGraphical(); 
	    if (episode<1000) facteur=episode+1; else facteur=1000;
	    if((episode%1000==0)&&(episode!=0))
	    		zero07.saveAgent("/tmp/agentCLS"+episode);
	    if(episode%10==0)
	    	System.out.println(episode+"\t\t"+u+"\t"+(total/facteur));
	    
	    
	   epsi*=0.9995; 
	    sql.setEpsilon(epsi); 
	    
	}
	
    
}
}
