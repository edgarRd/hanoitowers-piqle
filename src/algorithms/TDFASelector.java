package algorithms; 
/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation; either version 2.1 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    TDFASelector.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */

import java.util.Iterator;
import java.util.Random;

import tiling.EligibleTiles;
import tiling.ListOfTiles;
import tiling.Tile;
import dataset.Dataset;
import environment.ActionList;
import environment.IAction;
import environment.IState;
import environment.TileAbleEnvironment;


/** A linear gradient-descent version of Watkins Q(lambda)

See<a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node89.html">Sutton & Barto p 213 Q-Learning</a> and <a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node89.html#fig:FAQ">the erratum</a> for the details. 



 @author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 


*/

public class TDFASelector implements ISelector{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/** We always need a part of randomness */
    protected static Random generator=new Random(); 
   
    // DEBUG : ONLY FOR MAABAC

	//private static int counter[]=new int[3]; 

    protected double lambda=0.9; 
    /** Learning rate */
    protected double alpha=0.1;
    /** discount rate */
    protected double gamma=0.9; 
    /** balance between exploration and exploitation */
    protected double epsilon=0.5;
    /** Factor by which we multiply alpha at each learning step (geometric decay)<br>
	<i> Note : geometric decay does no insure convergence.</i>
    */
    protected double decayAlpha=0.999999999; 
    /** Number of learning steps */
    protected double count=1.0; 
    /** Power of decay when alpha=1/(count^alphaDecayPower)
	@see <a href="http://www.cs.tau.ac.il/~evend/papers/ql-jmlr.ps">Learning rates for Q-Learning</a>
    */
    protected double alphaDecayPower=0.8; 

    public void setAlpha(double a){this.alpha=a; }
    public void setGamma(double g){this.gamma=g;}
    public void setEpsilon(double e){this.epsilon=e;}
    public void setDecay(double d){this.decayAlpha=d;}
    public void setAlphaDecayPower(double a){this.alphaDecayPower=a;}
    public void setLambda(double l){this.lambda=l;}

    public double getAlpha(){return alpha;}
    public double getGamma(){return gamma;}
    public double getEpsilon(){return epsilon;}
    public double getDecay(){return this.decayAlpha;}
    public double getAlphaDecayPower(){return this.alphaDecayPower;}
    public double getLambda(){return this.lambda;}

    /** Alpha decay methods
	<ul>
	<li> Geometric : use decayAlpha </li>
	<li> Exponential : use alphaDecayPower (default)</li>
	<ul>
    */
    protected boolean geometricDecay=false; 
    
    
    public void setGeometricAlphaDecay(){geometricDecay=true; }
    public void setExponentialAlphaDecay(){geometricDecay=false;}

    /** How convergence is controlled ? 
	<ul>
	<li> true : alpha decays geometrically </li>
	<li> false : alpha decays exponentially</li>
	</ul>
    */
    public boolean getGeometricDecay(){
	return geometricDecay;}

    public TDFASelector(double l){
	this.lambda=l;
    }

    /* Specific Fields for linear approximator */
    
	@SuppressWarnings("unused")
	private ListOfTiles bestSoFar=null; 
    private EligibleTiles ely=new EligibleTiles(); 
    
 
    /** Learn 
	@param s1 The state the agent is in before the action is performed.
     * @param s2 The state the agent goes to when the action is performed. 
     * @param a The action the agent took.
     * @param reward The reward obtained for this move.
    */
    public void learn(IState s1,IState s2, IAction a,double reward){
	if(geometricDecay)
	    alpha*=decayAlpha;
	else{
	    alpha=1/Math.pow(count+1.0,this.alphaDecayPower);
	}
	
	count++; 


	ActionList la=s2.getActionList(); 
	if(la.size()!=0){
	    TileAbleEnvironment universe=(TileAbleEnvironment)s1.getEnvironment(); 
	    ListOfTiles lt=universe.getTiles(s1,a); 
	    Iterator c=lt.iterator(); 
	    while(c.hasNext()){
		Tile t=(Tile)c.next(); 
		ely.put(t); 
	    }
	    double delta=reward-lt.sumTheta(); 
	    ListOfTiles current=universe.getTiles(s2,la.get(0)); 
	    double maxqsap=current.sumTheta();
	    for(int i=1;i<la.size();i++){
		current=universe.getTiles(s2,la.get(i)); 
		double qsap=current.sumTheta(); 
		if(qsap>maxqsap) maxqsap=qsap; 
	    }
	delta+=gamma*maxqsap; 
	ely.modifyTheta(alpha*delta); 
	}
    }//apprend

    /** Format the experience in a shape usable by Neural Networks*/
    public Dataset extractDataset(){return null;}

   

     /** There might be some things to do at the beginning of each episode... */
    public void newEpisode(){ely.eraseAll();}

 /** Reset eligibility traces */
    public void reset(){ ely.eraseAll(); }
    
 /** How to implement randomness ? 
	<ul> 
	<li>epsilon-greedy</li>
	<li>Roulette wheel selection</li>
	<li>Boltzmann </li>
	<ul>
	Roulette wheel or Boltzmann selection makes epsilon useless.
    */
    protected boolean rouletteWheel=false; 
    protected boolean epsilonGreedy=true; 
    protected boolean boltzmann=false; 
    protected double tau=0.5; 

    public void setTau(double t){this.tau=t;}
    public double getTau(){return this.tau;}

    public void setRouletteWheel(){
	rouletteWheel=true; 
	epsilonGreedy=false; 
	boltzmann=false;
    }

    /** Set the epsilon-greedy policy */
    public void setEpsilonGreedy(){
	epsilonGreedy=true; 
	rouletteWheel=false;
	boltzmann=false; 
    }

    /** Set Boltzmann selection */
    public void setBoltzmann(){
	epsilonGreedy=false; 
	rouletteWheel=false;
	boltzmann=true; 
    }

   
    public boolean getRouletteWheel(){
	return rouletteWheel;
    }
    public boolean getEpsilonGreedy(){
	return epsilonGreedy; 
    }
    public boolean getBoltzmann(){
	return boltzmann; 
    }

    public IAction getChoice( ActionList l){
	if(l.size()==0) return null;
	IState s=l.getState(); 
	TileAbleEnvironment universe=(TileAbleEnvironment)s.getEnvironment(); 
	// Determining the best action
	IAction meilleure=l.get(0); 	
	ListOfTiles current=universe.getTiles(s,meilleure); 
	bestSoFar=current; 
	double maxqsap=current.sumTheta(); 
	for(int i=1;i<l.size();i++){
	    IAction a=l.get(i); 
	    current=universe.getTiles(s,a); 
	    double qsap=current.sumTheta(); 
	    if(qsap>maxqsap) {
		maxqsap=qsap;  
		meilleure=a;
		bestSoFar=current; 
	    }
	}
	// Meilleure is now the best action
	IAction candidat=null; 
	if(rouletteWheel)candidat=rouletteWheelChoice(l); 
	if(epsilonGreedy) candidat=epsilonGreedyChoice(l); 
	if(boltzmann) candidat=boltzmannChoice(l); 
	if(!candidat.equals(meilleure)) ely.eraseAll(); 
	else ely.multiplyAll(gamma*lambda); 
	bestSoFar=universe.getTiles(s,candidat); 
	return candidat; 
    }


    /** Epsilon greedy choice 
     * @param l the list of all possible actions + the current state*/
    private IAction epsilonGreedyChoice(ActionList l){
	if(l.size()==0) return null;
	IState s=l.getState(); 
	TileAbleEnvironment universe=(TileAbleEnvironment)s.getEnvironment(); 
	if(generator.nextDouble()<this.epsilon) {
	    IAction rnd=l.get(generator.nextInt(l.size())); 
	    return rnd; 
	}
	IAction meilleure=l.get(0); 	
	ListOfTiles current=universe.getTiles(s,meilleure); 

	double maxqsap=current.sumTheta(); 
	for(int i=1;i<l.size();i++){
	    IAction a=l.get(i); 
	    current=universe.getTiles(s,a); 
	    double qsap=current.sumTheta(); 
	    if(qsap>maxqsap) {
		maxqsap=qsap;  
		meilleure=a;
	    }
	}
	return meilleure; 
    }

    /** Roulette Wheel choice 
     * @param l : current ActionList*/
    private IAction rouletteWheelChoice(ActionList l){
	if(l.size()==0) return null; 
	IState s=l.getState();
	TileAbleEnvironment universe=(TileAbleEnvironment)s.getEnvironment(); 
	double sum=0; 
	double tab[]=new double[l.size()]; 
	for(int i=0;i<l.size();i++) {
	    IAction a=l.get(i); 
	    ListOfTiles current=universe.getTiles(s,a); 
	    sum+=Math.abs(current.sumTheta())+1;
	    tab[i]=sum;
	} 
	double choix=generator.nextDouble()*sum; 
	for(int i=0;i<l.size();i++)
	    if(choix<=tab[i]) return l.get(i); 
	// debug
	System.err.println(choix+" "+sum); 
	System.err.println("Wrong"); 
	System.exit(-1); 
	return null;
	
    }

    /** Boltzmann choice 
     * @param  l ActionList */
    private IAction boltzmannChoice(ActionList l){
      	if(l.size()==0) return null; 
	IState s=l.getState();
	TileAbleEnvironment universe=(TileAbleEnvironment)s.getEnvironment(); 
	double sum=0;  
	double tab[]=new double[l.size()];  
	for(int i=0;i<l.size();i++) { 
	    IAction a=l.get(i);  
	    ListOfTiles current=universe.getTiles(s,a);  
	    sum+=Math.exp(current.sumTheta()/this.tau); 
	    tab[i]=sum; 
	}  
	double choix=generator.nextDouble()*sum;  
	for(int i=0;i<l.size();i++) 
	    if(choix<=tab[i]) {
		//	counter[i]++; 
		//	System.out.println(counter[0]+"---"+counter[1]+"---"+counter[2]); 
		return l.get(i);  
	    }
	// debug 
	System.err.println("Wrong");  
	System.exit(-1);  
	return null; 
	
    }





}