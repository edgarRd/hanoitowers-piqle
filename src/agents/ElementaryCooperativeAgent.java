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
*    ElementaryCooperativeAgent.java
*    21 nov. 06
*
*/
package agents;

import java.util.ArrayList;
import java.util.Iterator;

//import maabacVersion2.MuscleNullableAction;

import algorithms.ISelector;
import environment.ExtendedStateWithActions;
import environment.NullableAction;
import environment.Filter;
import environment.IEnvironment;
import environment.IState;

/**
* We want to have agents (in a multi-agent frame) that are able to know
* what was the last action of their neighbours.
* Each such agent maintains a (stationary) list of its neighbours, 
* and builds its state (its perception of the universe) using the global 
* environment's state (through a filter), and the last actions of its 
* neighbours.
* As we must also define the initial state, we need the notion of null action 
* (when no agents has yet moved). 
* 
*   
* @author decomite
*
*/


abstract public class ElementaryCooperativeAgent<F extends ExtendedStateWithActions,
	E extends NullableAction> 
extends ElementaryAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected ArrayList<ElementaryCooperativeAgent> neighbours
		=new ArrayList<ElementaryCooperativeAgent>();
	protected ArrayList<E> oldActions=new ArrayList<E>();
	/** True if all neighbours are entered and the initial state build */
	protected boolean completed=false;

	/**
	 * @param s  the local environment (which defines possible actions)
	 * @param al the selector 
	 * @param f  the filter
	 */
	public ElementaryCooperativeAgent(IEnvironment s, ISelector al,
			Filter f) {
		super(s, al, f);
		// Next line : the type is not yet instantiated
	//	lastAction=E.getNullAction();
		// Initial state can only be build when all neighbours are entered
	}
	
	
	protected F buildState(IEnvironment universe,IState s,ArrayList<E> old){
		System.out.println("ElementaryCooperativeAgent.buildState() :");
		System.out.println("You MUST override this method : exiting");
		System.exit(0);
		return null;}
	
	/** By calling this method, the user indicates that all neighbours 
	 * have been entered : we can build the initial composed state
	 *
	 */

	public void buildInitialComposedState(IState st){
		this.completed=true; 
		F f = buildState(this.universe,
			              this.fil.filterState(st,this.universe),
						  this.oldActions);
		this.currentState=f;
		this.oldState=this.currentState.copy();	
	}
	
	public void setInitialState(IState s){
			setCurrentState(s);
	}
	
	 
	public void setCurrentState(IState s){
		if(this.completed){
			this.oldState=this.currentState.copy();
			this.oldActions=new ArrayList<E>();
			Iterator<ElementaryCooperativeAgent>cursor=this.neighbours.iterator();
			while(cursor.hasNext()){
					ElementaryCooperativeAgent ag=cursor.next(); 
					this.oldActions.add((E)ag.getLastAction());
			}
			F f = (F) buildState(this.universe,
		              this.fil.filterState(s,this.universe),
					  this.oldActions);
	this.currentState=f;
		}
		else{
			System.err.println("Can't set state before list of neighbours completed\n" +
					"Must call buidInitialComposedState");
			System.exit(0);
			return;
		}
	}
	
 
	public NullableAction getNullAction()
	{
		return E.getNullAction();
	}
	
	public void newEpisode() {
		super.newEpisode(); 
		this.lastAction=E.getNullAction();
	}
	
	public void addNeighbour(ElementaryCooperativeAgent n)
	{
		this.neighbours.add(n);
		oldActions.add((E)n.getLastAction());
	}

}
