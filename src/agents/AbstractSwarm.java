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
 *    alo0ng with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    AbstractSwarm.java
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

import java.util.ArrayList;
import java.util.Iterator;

import algorithms.IStrategyLearner;
import dataset.Dataset;
import environment.AbstractComposedAction;
import environment.ComposedActionArrayList;
import environment.IAction;
import environment.IEnvironmentSingle;
import environment.IState;

/** An AbstractSwarm is composed of several Agents, each one with its own ISelector.
 */

abstract public class AbstractSwarm extends LoneAgent{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The number of ElementaryAgents composing the AbstractSwarm */
	private int nbAgents=0;
	/** The list of all elementary Agents */
	protected ArrayList<ElementaryAgent> listOfAgents=new ArrayList<ElementaryAgent>();
	
	/** For storing the list of elementary actions. It is up to the subclasses
	 * to initialize and maintain a finite size for ca... */
	protected AbstractComposedAction ca; 

    /** Constructor : no algorithm for a AbstractSwarm : each agent has its own one*/
    public AbstractSwarm(IEnvironmentSingle s){
	super(s,null); 
	lastAction=new ComposedActionArrayList();
    }

    /** Add an agent to a swarm */
    public void add(ElementaryAgent a){
	this.listOfAgents.add(a); 
	a.setRank(nbAgents++); 
    }

    public void setInitialState(IState s){
	super.setInitialState(s);
	Iterator<ElementaryAgent> iterator=listOfAgents.iterator();
	int indexdebug=0;
    while(iterator.hasNext()){
    	indexdebug++;
    	ElementaryAgent aa=iterator.next();
    	aa.setInitialState(s); 
    	//TODO : define lastAction ......
    }
	
    }

    public void newEpisode(){
    	this.lastAction=new ComposedActionArrayList();
    Iterator<ElementaryAgent> iterator=listOfAgents.iterator();
    while(iterator.hasNext()){
    	ElementaryAgent aa=iterator.next();
    	((IStrategyLearner) aa.getAlgorithm()).newEpisode(); 
    }

    }

    /** Asks each agent to choose its own action, collects those actions into 
     * a composed one. */
    public IAction choose(){
	 Iterator<ElementaryAgent> iterator=listOfAgents.iterator();
	    while(iterator.hasNext()){
	    	ElementaryAgent aa=iterator.next();
	    	IAction action=aa.choose();
	    	ca.addElementaryAction(aa, action); 
	    	((ComposedActionArrayList)lastAction).addElementaryAction(aa, action);
	    }
	return ca; 
    }

    /** <ul>
	<li>Apply the action, get the reward.</li>
	<li>If learning is enabled, learn from states, action, and reward. </li>
	<ul>*/
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
	    Iterator<ElementaryAgent> iterator2=listOfAgents.iterator();
	    while(iterator2.hasNext()){
	    		ElementaryAgent ag=iterator2.next(); 
	    	/*	((IStrategyLearner) ag.getAlgorithm()).learn(ag.getFilter().filterState(old,ag.getEnvironment()),
						  ag.getFilter().filterState(currentState,ag.getEnvironment()),
						  ag.getLastAction(),
						  //ca.getAction(ag),
						  r+ag.getInternalReward());*/
	    		
	    		((IStrategyLearner) ag.getAlgorithm()).learn(ag.getOldState(),
	    					ag.getCurrentState(),
	    					ag.getLastAction(),
	    					r+ag.getInternalReward());
	    }
		
		
	}
	return a; 
    }
	
    /** TODO : find a correct definition of dataset in this case... */
     public Dataset extractDataset(){
	return null; 
    }

	/**
	 * @return the nbAgents
	 */
	public int getNbAgents() {
		return nbAgents;
	}

	/**
	 * @param nbAgents the nbAgents to set
	 */
	public void setNbAgents(int nbAgents) {
		this.nbAgents = nbAgents;
	}

}
    
    