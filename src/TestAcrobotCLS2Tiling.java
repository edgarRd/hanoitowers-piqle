
import agents.*; 
import acrobot.*;

import referees.OnePlayerReferee;

import algorithms.*;  



public class TestAcrobotCLS2Tiling{

    public static void main(String args[]){
    Acrobot cirque= new AcrobotCLS2Tiling(); 
	//Acrobot cirque=new AcrobotCLS2TilingSimple();  
	
	TDFASelector sql=new TDFASelector(0.9);  
	//WatkinsSelector sql=new WatkinsSelector(0.9); 
	//SelectionneurAleatoire sql=new SelectionneurAleatoire(); 
	double epsi=0.3; 
	sql.setEpsilon(epsi); 
	sql.setGamma(1); 
	sql.setGeometricAlphaDecay(); 
	sql.setDecay(1.0);
	//sql.setAlphaDecayPower(0.75);
	sql.setAlpha(0.0007); 
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
	int first=1000; 
	for(int episode=0;episode<500000;episode++){
		//if(episode==5000) arbitre.setGraphical();
	    int u=arbitre.episode(cirque.defaultInitialState()); 
	    if((first!=0) &&(u<1000)){ 
		first--;
		System.err.println("---> "+first); 
		System.out.println();
		if(first==0) arbitre.setMaxIter(500); 
	    }
	     total-=buffer[indice]; 
	    buffer[indice]=u; 
	    total+=buffer[indice]; 
	    indice=(indice+1)%1000; 
	    if (episode<1000) facteur=episode+1; else facteur=1000;
	    if((episode%1000==0)&&(episode!=0))zero07.saveAgent("/tmp/agentCLS_X00_"+episode); 
	    System.out.println(episode+"\t"+u+"\t"+(total/facteur)+" "+epsi);    
	    //System.out.println(episode+"\t"+u+"\t"+(total/facteur)); 
	     if(first==0){
		 if(epsi>1e-5){
		     epsi*=0.9997; 
		     sql.setEpsilon(epsi);
		 }
	     }
	    
	    
	}
	
    
}
}
