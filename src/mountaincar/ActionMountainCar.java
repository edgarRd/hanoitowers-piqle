package mountaincar;
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
 *    ActionMountainCar.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import environment.IAction;



/** There are only three actions : forward, backward, still.

@author Francesco De Comite 
 @version $Revision: 1.0 $ 

*/
public class ActionMountainCar implements IAction{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int typeMove; 

    public ActionMountainCar(int i){
	this.typeMove=i; 
    }
    
    public ActionMountainCar(){}; 

    public static ActionMountainCar FORWARD  = new ActionMountainCar(1); 
    public static ActionMountainCar BACKWARD = new ActionMountainCar(-1); 
    public static ActionMountainCar STILL    = new ActionMountainCar(0); 

    public int getType(){
	return this.typeMove;
    }

/** Clone */
    public IAction copy(){
	switch(this.typeMove){
	case 0 : return STILL; 
	case 1: return FORWARD; 
	case -1: return BACKWARD; 
	default: return null; 
	}
    }

    public int hashCode(){return this.typeMove+1; }

     public boolean equals(Object o){
	if (!(o instanceof ActionMountainCar)) return false; 
	 ActionMountainCar a=(ActionMountainCar)o; 
	 return (this.typeMove==a.typeMove); 
	 }

    /** One among 3 coding */
    public int nnCodingSize(){return 3; }

  
    public double[] nnCoding(){
	double code[]=new double[3]; 
	code[typeMove+1]=1.0; 
	return code; 
    }

    public String toString(){
	return this.typeMove+""; 
    }

} 

