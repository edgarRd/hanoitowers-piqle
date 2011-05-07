package qlearning; 
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
 *    Eligibles.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import environment.IAction;
import environment.IState;

import java.io.Serializable; 


/** Memorizing eligibility traces.

@see algorithms.AbstractQLambdaSelector

 @author Francesco De Comite
 @version $Revision: 1.0 $ 


*/

public class Eligibles implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<ActionStatePair,Double> map = new HashMap<ActionStatePair,Double>();
    /** Incremente (state,action) eligibility value. */
    public void increment(IState s,IAction a){
	Double dv=map.get(new ActionStatePair(a,s)); 
	double value; 
	if (dv==null) value=0; 
	else
	    value=dv.doubleValue(); 
	map.put(new ActionStatePair(a,s),new Double(1+value)); 
    }

    /** Read eligibility value.*/
    public double get(IState s, IAction a){
	Double dv=map.get(new ActionStatePair(a,s)); 
	double value; 
	if (dv==null) value=0; 
	else
	    value=dv.doubleValue(); 
	return value; 
    }


    /** Store eligibility value. */ 
    public void set(IState s,IAction a, double value){
	map.put(new ActionStatePair(a,s),new Double(value)); 
    }

    /** Store eligibility value.*/
    public void put(ActionStatePair us,double value){
	map.put(us,new Double(value)); 
    }

    /** Read eligibility value.*/
    public double get(ActionStatePair us){
	Double d=map.get(us); 
	return d.doubleValue(); 
    }

    /** Iterator on (state,action) pairs. */
    public Iterator getIterator(){
	Set keys=map.keySet(); 
	return keys.iterator(); 
    }

}
