
import agents.TwoPlayerAgent; 

import memory.*; 
import referees.TwoPlayerReferee; 
import algorithms.*;



public class Memory{

    public static void main(String argv[]) throws Exception{
	MemoryBoard p=new MemoryBoard(14,9); 

	WatkinsSelector sql1=new WatkinsSelector(0.7);
	//WatkinsSelector sql2=new WatkinsSelector(0.8); 
	ISelector sql2=new RandomSelector();

	double epsilon=0.5; 
	sql1.setEpsilon(epsilon); 
	sql1.setGeometricAlphaDecay(); 
	sql1.setAlpha(0.3); 
	

	
	TwoPlayerAgent j1=new TwoPlayerAgent(p,sql1); 
	TwoPlayerAgent j2=new TwoPlayerAgent(p,sql2); 


	TwoPlayerReferee arbitre=new TwoPlayerReferee(j2,j1); 
	int resu[]=new int[3]; 

	for(int i=1;i<100000;i++){
	    epsilon*=0.99999; 
	   sql1.setEpsilon(epsilon);
	    resu[arbitre.episode()+1]++; 
	    if((i%100==0)&&(i!=0)) {
		System.out.println(i+" "+resu[0]/(i+0.0)+" "+resu[1]/(i+0.0)+" "
				+resu[2]/(i+0.0)); 
		//resu=new int[3]; 
	    }
	}

    }
}
