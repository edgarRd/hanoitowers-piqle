package gambler; 
 



import environment.AbstractEnvironmentSingle; 
import environment.ActionList; 
import environment.IState; 
import environment.IAction; 

import qlearning.ValueIteration; 

import java.util.Random; 
/** A simple game : 
 <ul>
    <li> The player has an initial capital. </li>
    <li> His aim is to reach 100 </li>
    <li> He can bet as much money as he wants, provided he has it.</li>
    <li> He flips a coins and wins with probability p.</li>.
    <li> If he wins, he is credited with its bet.</li>
    <li> Otherwise, he looses it. </li>
    <li> He wins when reaching a capital of 100</li>
    <li> He looses when he runs out of money. </li>
    </ul>
 Description of this game can be found in <a
href="http://www.cs.ualberta.ca/~sutton/book/4/node45.html">Sutton
& Barto page 101.</a><p>




<i> Some functions are added outside the definition of <code>Contraintes</code>, to apply value iteration, finding optimal policy, and plot Q(s,a) values, in order to compare with the book discussion : see manual and example.
</i> 

*/
public class GamblerGame extends AbstractEnvironmentSingle implements ValueIteration {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The probability the coin comes up heads*/
    private double proba; 

    static private Random generateur=new Random(); 

    /** An array for Value Iteration */
    private double vStar[]; 

    public GamblerGame(double pro){
	this.proba=pro;
    }
    
    public ActionList  getActionList(IState s){
	GamblerState eg=(GamblerState)s; 
	ActionList loa=new ActionList(eg); 
	for(int i=1;i<=Math.min(eg.getValue(),100-eg.getValue());i++)
	    loa.add(new ActionGambler(i)); 
	return loa; 
    }

    public IState successorState(IState s,IAction a){
	GamblerState eg=(GamblerState)s; 
	ActionGambler ag=(ActionGambler)a;
	if(generateur.nextDouble()<=this.proba)
	    return new GamblerState(this,eg.getValue()+ag.getValue()); 
	else  
	    return new GamblerState(this,eg.getValue()-ag.getValue()); 
    }

    public IState defaultInitialState(){
	return new GamblerState(this,50); 
    }

    public double getReward(IState s1,IState s2,IAction a){
	if(!s2.isFinal()) return 0; 
	if (whoWins(s2)==-1) return 1; 
	else return 0; 
    }

    public int whoWins(IState s){
	GamblerState eg=(GamblerState)s; 
	if (eg.getValue()==100) return -1; 
	if (eg.getValue()==0) return 0; 
	return 0; 
    }

    public boolean isFinal(IState s){
	GamblerState eg=(GamblerState)s; 
	return ((eg.getValue()==0)||(eg.getValue()==100)); 
    }

    /** Value Iteration */
    public void computeVStar(double gamma){
	double delta; 
	vStar=new double[101]; 
	vStar[100]=0.0;
	do{
	    delta=0.0; 
	    for(int i=1;i<1000;i++){
		int indice=generateur.nextInt(99)+1; 
		double v=vStar[indice]; 
		double max=0.0;
		double sum=0.0; 
		// find action that maximize
		for(int j=1;j<=Math.min(indice,100-indice);j++){  
		    if(indice+j==100) sum=proba*(1.0+gamma*vStar[100]);
		    else sum=proba*gamma*vStar[indice+j]; 
		    sum+=(1-proba)*gamma*vStar[indice-j]; 
		   
		    if(sum>max) max=sum; 
		    
		}
		vStar[indice]=max; 
		delta=Math.max(delta,Math.abs(v-vStar[indice])); 
	    }
	   
	}while(delta>1e-30); 
	

    }

    /** Display value iteration result */
 public void displayVStar(){
	for(int i=0;i<101;i++)
	    System.out.println(i+" "+vStar[i]); 
    }

    public double getVStar(IState s){
	GamblerState ej=(GamblerState)s; 
	return vStar[ej.getValue()];
    }

    public void extractPolicy(double gamma){
	for(int i=1;i<100;i++){
	    // Find best action
	    double max=-1.0; 
	    double indice=-1; 
	    for(int j=1;j<=Math.min(i,100-i);j++){
		double sum=0.0; 
		if(i+j==100) sum=proba*(1.0+gamma*vStar[100]);
		    else sum=proba*gamma*vStar[i+j]; 
		sum+=(1-proba)*gamma*vStar[i-j]; 
		if(sum-max>1e-6)
		    {max=sum; indice=j;}
		
	    }
	    System.out.println(i+" "+indice); 
	}
    }

}
