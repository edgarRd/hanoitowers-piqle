package memory; 
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
 *    ActionMemory.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */
import environment.*; 

/** Following <a href="http://www.cs.tau.ac.il/%7Ezwick/papers/memory-game.ps.gz">Zwick and Patterson  analysis</a>, there are only three different useful actions : 
    <ul>
    <li> Flip two already known cards. </li>
    <li> Flip first an unknown card, then a known one. </li>
    <li> Flip two unknown cards.</li>
    </ul>

    In the last two cases, if, after the first flip, we can match this card with a already known one, both cards are taken out, and the player plays again.

   @author Francesco De Comite 
 @version $Revision: 1.0 $ 
*/


public class ActionMemory implements IAction{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
    /** The coding. */
    private int typeMove; 

    public ActionMemory(int i){
	typeMove=i; 
    }

    public ActionMemory(){}; 

   
     /** Flip two known cards. */
    public static ActionMemory MOVEZERO=new ActionMemory(0); 
    /**  Flip an unknown card, then a known one.*/
    public static ActionMemory MOVEONE=new ActionMemory(1);
    /** Flip two unknown cards. */
    public static ActionMemory MOVETWO=new ActionMemory(2); 
	
  /** Clone */
    public IAction copy(){
	switch(this.typeMove){
	case 0 : return MOVEZERO; 
	case 1: return MOVEONE; 
	case 2: return MOVETWO; 
	default: return null; 
	}
    }

   
    public String toString(){
	switch(typeMove){
	case 0 : return "Flip two known cards"; 
	case 1: return "Flip an unknown card, then a known one"; 
	case 2: return "Flip two unknown cards"; 
	default : return "bug" ; // never reached
	}
    }
    
    
    public int hashCode(){
	return typeMove; 
    }

   
     public boolean equals(Object o){
	if (!(o instanceof ActionMemory)) return false; 
	 ActionMemory a=(ActionMemory)o; 
	 return (this.typeMove==a.typeMove); 
	 }


  
    public int nnCodingSize(){return 3; }

    /**One among three coding. */
    public double[] nnCoding(){
	double code[]=new double[3]; 
	code[typeMove]=1.0; 
	return code; 
    }
 

    
   
}

