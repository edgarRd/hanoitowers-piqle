package algorithms; 

import java.util.Random;

import qlearning.RewardMemorizerInteger;
import dataset.Dataset;
import environment.ActionList;
import environment.IAction;
import environment.IState;

/** Experimental implementation of <it> Compact Q-Learning</it> algorithm : 
    All numbers are integers, and can be restricted to bytes

    <a href="http://asl.epfl.ch/aslInternalWeb/ASL/publications/uploadedFiles/compactQlearning_for%20print%20version.pdf "> See the paper</a>
*/

public class QLIntegerSelector implements ISelector{

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** We always need some randomness */
    protected static Random generateur=new Random(); 

    protected int f1=1,f2=2,f3=4; 
    protected int p1=1; 
    protected int m1=3; 
    
    /** Memorizing  Q(s,a) */
    protected RewardMemorizerInteger  memoire=new RewardMemorizerInteger(); 

    /** The threshold under which Q(s,a) is actually updated */
    protected int theta=200; 

    public void setThreshold(int v){
	this.theta=v; 
    }

    public int getThreshold(){
	return this.theta; 
    }
    public void randomize(){
	f1=1+generateur.nextInt(10); 
	f2=1+generateur.nextInt(10); 
	f3=1+generateur.nextInt(10);
	p1=1+generateur.nextInt(10); 
	m1=2+generateur.nextInt(10); 
	theta=50+generateur.nextInt(190); 
    }

    /** Only the roulette wheel selection is available */
    public IAction getChoice(ActionList l){
	if(l.size()==0) return null; 
	IState s=l.getState();
	int sum=0; 
	for(int i=0;i<l.size();i++) {
	    sum+=memoire.get(s,l.get(i))+1; 
	}
	int choix=generateur.nextInt(sum); 
	int indice=0;
	int partialSum=memoire.get(s,l.get(indice))+1; 
	while(choix>partialSum){
	    indice++; 
	    partialSum+=1+memoire.get(s,l.get(indice)); 
	}
	return l.get(indice);



    }


    public void learn(IState s1,IState s2, IAction a,double reward){
	int qsa=memoire.get(s1,a); 
	ActionList la=s2.getActionList();
	if(la.size()!=0){
	    int maxqsap=memoire.get(s2,la.get(0)); 
	    for(int i=1;i<la.size();i++){
		IAction aprime=la.get(i); 
		int qsap=memoire.get(s2,aprime);
		if(qsap>maxqsap) maxqsap=qsap; 
	    }
	    int f=0; 
	    if(maxqsap>20) f=f1; 
	    if(maxqsap>40) f=f2; 
	    if(maxqsap>80) f=f3;
	    
	//    int oldqsa=qsa; 
	    qsa+=m1*(int)reward+f;
	    if(qsa>theta) qsa=qsa-p1; 
	}
	else qsa=m1*(int)reward-qsa; 
	if(qsa<0) qsa=0; 
	//	if(qsa>65000) qsa=65000; 
	 
	memoire.put(s1,a,s2,qsa); 

    }//learn


    /** Auxiliary/debug method : find the best action from a state.*/
    public IAction bestAction(IState s){
	ActionList l=s.getActionList(); 
	IAction meilleure=l.get(0); 
	double maxqsap=memoire.get(s,meilleure); 
	for(int i=1;i<l.size();i++){
	    IAction a=l.get(i); 
	    double qsap=memoire.get(s,a);
	    if(qsap>maxqsap) {
		maxqsap=qsap; 
		meilleure=a;
	    }
	}
	return meilleure; 
    }
    
    /** No extensions towards NN at this time ... */
    public Dataset extractDataset(){return null;}

    public void newEpisode(){}; 

      public String toString(){
	  String s=""; 
	  s+="f1 : "+f1+" f2 : "+f2+" f3 : "+f3+" p1 : "+p1+" Threshold : "+theta; 
	  s+="\n"+memoire.toString(); 
	return s;
    }

    public void showHistogram(){
	memoire.makeHistogram(); 
	memoire.displayHistogram(); 
    }

 




}