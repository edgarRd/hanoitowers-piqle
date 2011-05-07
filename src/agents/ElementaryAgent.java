package agents; 

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
 *    ElementaryAgent.java
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

/** An agent part of a AbstractSwarm, i.e. a multi agent community. 
 * The only differences with a normal agent are : 
 * - the method filterState 
 * - The notion of internal reward */

import algorithms.ISelector;
import environment.Filter;
import environment.IAction;
import environment.IEnvironment;
import environment.IEnvironmentSingle; 
import environment.IState;


public class ElementaryAgent extends LoneAgent{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Filter fil; 
	protected double internalReward=0.0; 
	
	/** A number identifying an ElementaryAgent inside an AbstractSwarm */
	public int rank; 
 /** Place the agent  in the environment.*/
    public ElementaryAgent(IEnvironment s, ISelector al,Filter f,IState st){ 
	super((IEnvironmentSingle) s,al); 
	this.fil=f; 
	this.currentState=this.fil.filterState(st,this.universe); 
    }
    
    public ElementaryAgent(IEnvironment s, ISelector al,Filter f){ 
    	super((IEnvironmentSingle) s,al); 
    	this.fil=f;  
        }

    public void setCurrentState(IState s){
    	this.oldState=this.currentState.copy();	
    	this.currentState=this.fil.filterState(s,this.universe); 
	
    }
    
    protected IAction applyAction(IAction a){
	System.err.println("No need to call this function. Exit"); 
	System.exit(0);
	return null;
    }

    public Filter getFilter(){return this.fil;}

 /** Place the agent.*/
    public void setInitialState(IState s){
	this.currentState=this.fil.filterState(s,this.universe); 
	this.oldState=this.currentState.copy();	
    }

  

    /** Each elementary agent adds some part of reward to the global reward 
     * (MAABAC)*/
    public double getInternalReward(){
	return this.internalReward; 
    }
    
    /** In some cases (to be defined), environment may deliver individual rewards to 
     * elementary agents */
    
    public void setInternalReward(double r){
    	this.internalReward=r;
    }
   

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

    
}