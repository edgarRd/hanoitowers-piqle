
import mountaincar.*; 
import algorithms.*; 
import agents.LoneAgent; 
import referees.OnePlayerReferee; 




public class MountainGraphic{
    
    public static void main(String argv[]) throws Exception{
	MountainCar alpes=new MountainCar(); 
	// Choose here your algorithm
	QLearningSelector sel=new QLearningSelector(); 

	
	sel.setAlpha(0.1);
	sel.setGamma(0.9); 
	// Geometric decay seems to give better results, despite the fact that convergence is not
	// proven.
	sel.setGeometricAlphaDecay(); 
	sel.setDecay(1.0); 
	double epsilon=0.5;
	 
	sel.setEpsilon(epsilon); 
	LoneAgent car=new LoneAgent(alpes,sel); 
	OnePlayerReferee arbitre=new OnePlayerReferee(car);
	arbitre.setMaxIter(500); 
	//arbitre.setGraphical(); 
	
	
	
	// To show the average reward on 10 sucessive episodes
	double totalReward=0.0; 
	for(int i=0;i<200000;i++){
		//if(i==5000)arbitre.setGraphical();
	    int t = arbitre.episode(alpes.defaultInitialState()); 
	    totalReward+=arbitre.getRewardForEpisode(); 	    
	    if( (i%100==0) && (i!=0)) { 		
 		    System.err.println(i+" "+totalReward/100+ " " +t);
		    totalReward=0.0; 
		    epsilon*=0.9999; 
		   sel.setEpsilon(epsilon);
	    }
	}


	// Produce graphs similar to those depicted in Sutton & Barto page 214
	for(double p=-1.2;p<=0.5;p+=0.01){
	    for(double sp=-0.07;sp<=0.07;sp+=0.01){
		MountainCarState e=new MountainCarState(p,sp,alpes); 
		ActionMountainCar a=(ActionMountainCar)sel.bestAction(e); 
		double value=sel.getValue(e,a); 
		System.out.println(p+" "+sp+" "+(-value)); 
	    }
	    System.out.println(); 
	}



	}

    }


