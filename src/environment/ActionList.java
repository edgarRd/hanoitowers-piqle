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
 *    ActionList.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */




import java.util.ArrayList;
import java.util.Iterator;

/** A place to put actions : used when looking for the list of possible moves. 

@author Francesco De Comite 
 @version $Revision: 1.0 $ 


*/

public class ActionList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** The true list of Actions.*/
	private ArrayList<IAction> actionList=new ArrayList<IAction>();
	/** The state from which the IActions are beginning */
	private IState state; 
	
	public ActionList(IState s){
			this.state=s; 
	}
	
   /** Adds an action.*/
    public boolean add(IAction a){
	if(!this.actionList.contains(a))
	    this.actionList.add(a);
	return true; 
    }
    
    
    public Iterator<IAction> iterator(){
    		return this.actionList.iterator();
    }
   
    public int size(){return this.actionList.size();}

    /** The starting state.*/
    public IState getState(){return state;}

    /** Get Action at rank i */
    public IAction get(int i){ 
	if(actionList.size()!=0)
	    return this.actionList.get(i);
	else return null; 
    }

    /* (non-Javadoc)
	 * @see environment.Set<IAction>#toString()
	 */
    public String toString(){
	String s=""; 
	for(IAction action:actionList)
	    s+=action+"\n"; 
	return s; 
    }
}