
import agents.TwoPlayerAgent; 

import jenga.*; 
import referees.TwoPlayerReferee; 
import algorithms.*;
import qlearning.ConstantValueChooser; 
import qlearning.NullValueChooser;

public class JengaTest{

    public static void main(String argv[]) throws Exception{
	JengaTower p=new JengaTower(); 

	QLearningSelector sql1=new QLearningSelector(new ConstantValueChooser(0.35)); 
	QLearningSelector sql2=new QLearningSelector(new NullValueChooser());

	double epsilon=0.5; 
	sql1.setEpsilon(epsilon); 
	sql1.setGeometricAlphaDecay(); 
	
       sql2.setEpsilon(epsilon); 
	sql2.setGeometricAlphaDecay(); 

	TwoPlayerAgent j1=new TwoPlayerAgent(p,sql1); 
	TwoPlayerAgent j2=new TwoPlayerAgent(p,sql2); 


	TwoPlayerReferee arbitre=new TwoPlayerReferee(j2,j1); 
	int resu[]=new int[3]; 

	for(int i=1;i<100000;i++){
	    epsilon*=0.999; 
	    sql1.setEpsilon(epsilon); 
	     sql2.setEpsilon(epsilon); 
	  
	    resu[arbitre.episode()+1]++; 
	    if(i%1000==0) {
		System.out.println(i+" "+resu[0]+" "+resu[1]+" "+resu[2]); 
		resu=new int[3]; 
	    }
	}


	
    }
}
