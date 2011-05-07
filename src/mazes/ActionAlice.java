package mazes; 

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
 *    ActionAlice.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */

import environment.IAction; 


// TODO : doublon with AliceAction ???
/**
   A modelisation of Alice Robot. The four actions are : 
<ul>
<li> run forward</li>
<li> run backward</li>
<li> Turn left 90 degrees</li>
<li> Turn right 90 degrees</li>
</ul>



@author Francesco De Comite 
 @version $Revision: 1.0 $ 

*/

public class ActionAlice implements IAction{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int
    /** run forward */
	forward=0,
    /** run bacward */
	backward=1,
    /** turn left */
	left=2,
    /** turn right */
	right=3; 

    /** Type of action.*/
    private int typeMove; 
    

    public ActionAlice(int i){
	typeMove=i; 
    }

    public ActionAlice(){}

    public static ActionAlice FORWARD=new ActionAlice(forward); 
    public static ActionAlice BACKWARD=new ActionAlice(backward); 
    public static ActionAlice LEFT=new ActionAlice(left); 
    public static ActionAlice RIGHT=new ActionAlice(right); 

     /** Clone */
    public IAction copy(){
	switch(this.typeMove){
	case 0 : return FORWARD; 
	case 1: return BACKWARD; 
	case 2: return LEFT; 
	case 3 : return RIGHT; 
	default: return null; 
	}
    }
    
 public String toString(){
	switch(typeMove){
	case 0 : return "FORWARD"; 
	case 1: return "BACKWARD"; 
	case 2: return "LEFT"; 
	case 3 : return "RIGHT"; 
	default : return "Wrong action." ; 
		      }
    }
    
   
    public int hashCode(){
	return typeMove; 
    }

  
     public boolean equals(Object o){
	if (!(o instanceof ActionAlice)) return false; 
	 ActionAlice a=(ActionAlice)o; 
	 return (this.typeMove==a.typeMove); 
	 }

      /** One among 4 coding */
    public int nnCodingSize(){return 4; }

  
    public double[] nnCoding(){
	double code[]=new double[4]; 
	code[typeMove]=1.0; 
	return code; 
    }
} 