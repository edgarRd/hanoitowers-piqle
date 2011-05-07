package jenga; 
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
 *    ActionJenga.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import environment.IAction;

/** 
There are at most three different moves :  
    <ul>
    <li> Taking the central block of a full level (MOVECENTER)</li>
    <li> Taking a lateral block of a full level (MOVESIDE) </li>
    <li> Taking a lateral block from a level with only two adjacents blocks (LEAVEONE)</li>
    </ul>
 @author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 


*/

public class ActionJenga implements IAction{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final int 
	/** Take the central block (from a full level)*/
	move1=0,
	/** Take a lateral block from a full level. */
	move2=1,
	/** Take a lateral block in a level of two adjacent blocks. */
	move3=2; 
    
    /** Type of action.*/
    private int typeMove; 
    
    public ActionJenga(int i){
	typeMove=i; 
    }
    
    public ActionJenga(){}; 

   
    public static ActionJenga MOVECENTER=new ActionJenga(0); 
    
    public static ActionJenga MOVESIDE=new ActionJenga(1);
    
    public static ActionJenga LEAVEONE=new ActionJenga(2); 
	
  /** Clone */
    public  IAction copy(){
	switch(this.typeMove){
	case 0 : return MOVECENTER; 
	case 1: return MOVESIDE; 
	case 2: return LEAVEONE; 
	default: return null; 
	}
    }

   
    public String toString(){
	switch(typeMove){
	case 0 : return "Move central block."; 
	case 1: return "Take a lateral block from a full level."; 
	case 2: return "Take a lateral block in a level of two adjacent blocks."; 
	default : return "Wrong action." ; 
		      }
    }
    
   
    public int hashCode(){
	return typeMove; 
    }

  
     public boolean equals(Object o){
	if (!(o instanceof ActionJenga)) return false; 
	 ActionJenga a=(ActionJenga)o; 
	 return (this.typeMove==a.typeMove); 
	 }

   

    /** One among 3 coding */
    public int nnCodingSize(){return 3; }

  
    public double[] nnCoding(){
	double code[]=new double[3]; 
	code[typeMove]=1.0; 
	return code; 
    }

} 
