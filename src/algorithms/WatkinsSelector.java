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
 *    alo0ng with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    WatkinsSelector.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */





import java.util.Iterator;

import qlearning.ActionStatePair;
import environment.ActionList;
import environment.IAction;
import environment.IState;
import qlearning.IDefaultValueChooser;

/** 

<a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node78.html">Q(lambda), version Watkins </a>


 @author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 
*/


public class WatkinsSelector extends AbstractQLambdaSelector{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Memorize the next action to perform. */
    protected IAction aprime=null;

    /** The number of learning steps achieved. */
    protected double count=1.0; 
  

   public WatkinsSelector(double lambda){
	super(lambda); 
	//memory=new RewardMemorizer(); Already done in QLearningSelector
    }
   public WatkinsSelector(double lambda,IDefaultValueChooser dvc){
	   super(lambda,dvc);
   }

    /** Learning from experience.
	@param s1 the start state.
     * @param s2 the arrival state.
     * @param a the chosen action.
<br>
       <a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node78.html">Algorithm as described is Sutton & Barto, page 184, figure 7.14</a>
<br>
     * @param reward immediate reward.
	
    */

  public void learn(IState s1,IState s2, IAction a,double reward){
      IAction aetoile;
      double delta;  
     
      if(geometricDecay)
	  alpha*=decayAlpha;
      else
	  alpha=1/Math.pow(count+1.0,this.alphaDecayPower);
      
      count++;
      if(lambda!=0){ 
	  if(!replace)
	      eligibles.increment(s1,a); 
	  else
	      eligibles.set(s1,a,1); 
      }
      if(s2.isFinal()) {
	  delta=reward-memory.get(s1,a); 
	  aprime=null; 
	  aetoile=null; 
      }
      else {
      aprime=this.getInternalChoice(s2.getActionList()); 
      aetoile=this.bestAction(s2); 
      delta=reward+gamma*memory.get(s2,aetoile)-memory.get(s1,a); 
      }
      // update all state-action value estimates and eligibility traces
      
     
      // Eligibility
      // A first loop to update  Q(s,a)
      	Iterator parcours=eligibles.getIterator(); 
	while(parcours.hasNext()){
	 ActionStatePair courante=(ActionStatePair)parcours.next();
	 double valeur=eligibles.get(courante); 
	 double old=memory.get(courante.getState(),courante.getAction()); 
	 memory.put(courante.getState(),courante.getAction(),s2,old+alpha*delta*valeur); 
	}
	if((aetoile!=null)&&(aetoile.equals(aprime))){
	    parcours=eligibles.getIterator(); 
	    while(parcours.hasNext()){
		ActionStatePair courante=(ActionStatePair)parcours.next(); 
		double valeur=eligibles.get(courante);
		eligibles.put(courante,valeur*gamma*lambda); 
	    }
	}
	// reset eligibility traces
	else this.reset();  
 }// apprend


   
 private IAction getInternalChoice(ActionList l){
     return super.getChoice(l);
 }
    

    public IAction getChoice(IState state, ActionList l){
	if(aprime!=null) return aprime; 
	super.getChoice(l);
	return null;
    } 

    
   
    public void reset(){
	super.reset(); 
	aprime=null;
    }

    public void newEpisode(){
	aprime=null;
    }
	
}
