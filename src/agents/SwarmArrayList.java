/**
 *	This program is free software; you can redistribute it and/or modify
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
 *
 *
 *    Copyright (C) 2006 Francesco De Comite
 *    SwarmArrayList.java
 *    14 nov. 06
 *
 */
package agents;

import java.util.Iterator;

import algorithms.IStrategyLearner;
import environment.ComposedActionArrayList;
import environment.IAction;
import environment.IEnvironmentSingle;
import environment.IState;


/**
 * @author decomite
 *
 */
public class SwarmArrayList extends AbstractSwarm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param s
	 */
	public SwarmArrayList(IEnvironmentSingle s) {
		super(s);
		ca=new ComposedActionArrayList();
	}
	
	
	  /** <ul>
	<li>Apply the action, get the reward.</li>
	<li>If learning is enabled, learn from states, action, and reward. </li>
	<ul>
	Since we know that ca is an ArrayList, rewriting the method
	results in faster program (ca.getAction(i))
	*/
    protected IAction applyAction(IAction a){ 
	oldState = currentState.copy(); 
	currentState=currentState.modify(a); 
	double r=currentState.getReward(oldState,a);
	reward=r; 
	
	Iterator<ElementaryAgent> iterator=listOfAgents.iterator();
    while(iterator.hasNext()){
    	ElementaryAgent ag=iterator.next();
    	ag.setCurrentState(currentState);
    }

	if(this.learningEnabled){
	    for(int i=0;i<listOfAgents.size();i++){
		ElementaryAgent ag=(ElementaryAgent)listOfAgents.get(i); 
		/*((IStrategyLearner) ag.getAlgorithm()).learn(ag.getFilter().filterState(old,ag.getEnvironment()),
					  ag.getFilter().filterState(currentState,ag.getEnvironment()),
					  //ca.getAction(ag),
					  (IAction)ca.getAction(i),
					  r+ag.getInternalReward());*/
		((IStrategyLearner) ag.getAlgorithm()).learn(ag.getOldState(),
				ag.getCurrentState(),
				ag.getLastAction(),
				r+ag.getInternalReward());
	    	}
	    }
	   
	return a; 
    }
    
    public IAction choose(){
    	// We must avoid building huge arraylists
    	ca=new ComposedActionArrayList();
    	return super.choose();
    }

}
