package environment; 
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
 *    ElementaryMultiAgentEnvironment.java
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

/** This kind of environments is only able to produce a list of possible 
 * actions : all other computations are made inside a MultiAgentEnvironment */

abstract public class ElementaryMultiAgentEnvironment implements IEnvironmentSingle{

    /**  Gives the list of possible actions from a given state. The only
     useful method in this class.*/
    abstract public ActionList getActionList(IState s); 

    /**  No meaning here */
    public IState successorState(IState s,IAction a){
	return null;
    }

    /** No meaning here */ 
    public ITwoPlayerState defaultInitialTwoPlayerState(){return null;}
    
    
    /** No meaning here */
    public IState defaultInitialState(){return null;}

    /** No meaning here */
    public double getReward(IState s1,IState s2,IAction a){return 0.0; }

    /** No meaning here */
    public boolean isFinal(IState s){return false;}

    /** No meaning here */
    public int whoWins(IState s){return -1;};
}