
import agents.TwoPlayerAgent; 

import tictactoe.TicTacToeBoard; 
import environment.*; 
import referees.TwoPlayerReferee;
import algorithms.*;



public class TestTicTacToe{

    public static void main(String argv[]) throws Exception{
	TicTacToeBoard p=new TicTacToeBoard(); 

	QLearningSelector sql1=new QLearningSelector(); 
	//QLearningSelector sql2=new QLearningSelector();
	RandomSelector sql2=new RandomSelector();

	double epsilon=0.5; 
	sql1.setEpsilon(epsilon); 
	sql1.setGeometricAlphaDecay(); 
	
   //    sql2.setEpsilon(epsilon); 
//	sql2.setGeometricAlphaDecay(); 

	TwoPlayerAgent j1=new TwoPlayerAgent(p,sql1); 
	TwoPlayerAgent j2=new TwoPlayerAgent(p,sql2); 


	TwoPlayerReferee arbitre=new TwoPlayerReferee(j1,j2); 
	int resu[]=new int[3]; 

	for(int i=1;i<200000;i++){
	    epsilon*=0.99999; 
	    sql1.setEpsilon(epsilon); 
	//     sql2.setEpsilon(epsilon); 
	  
	    resu[arbitre.episode()+1]++; 
	    if(i%100==0) {
		System.out.println(i+" "+resu[0]+" "+resu[1]+" "+resu[2]); 
		resu=new int[3]; 
	    }
	}

	
    }
    }
