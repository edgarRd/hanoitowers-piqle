
/**
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

/*    MuscleNullableAction.java
 *    22 nov. 06
 * 	  
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

package maabacVersion2;

import environment.IAction;
import environment.NullableAction;

/**
 * @author Francesco De Comite
 *
 * 22 nov. 06
 */
public class MuscleNullableAction extends NullableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int typeMove; 

    public MuscleNullableAction(int i){
	this.typeMove=i; 
    }
    
    public MuscleNullableAction(){}; 
    
    public static MuscleNullableAction CONTRACT=new MuscleNullableAction(1); 
    public static MuscleNullableAction DECONTRACT=new MuscleNullableAction(-1); 
    public static MuscleNullableAction STILL=new MuscleNullableAction(0);
    public static MuscleNullableAction nullAction=new MuscleNullableAction(2);
    
    public int getValue(){
	return this.typeMove;
    }

/** Clone */
    public IAction copy(){
	switch(this.typeMove){
	case 0 : return STILL; 
	case 1: return CONTRACT; 
	case -1: return DECONTRACT; 
	case 2: return nullAction;
	default: return null; 
	}
    }
   public static NullableAction getNullAction() {
    	return nullAction;
    }
    public int hashCode(){return this.typeMove+1; }

     public boolean equals(Object o){
	if (!(o instanceof MuscleNullableAction)) return false; 
	 MuscleNullableAction a=(MuscleNullableAction)o; 
	 return (this.typeMove==a.typeMove); 
	 }

    /** One among 3 coding */
    public int nnCodingSize(){return 4; }

  
    public double[] nnCoding(){
	double code[]=new double[4]; 
	code[typeMove+1]=1.0; 
	return code; 
    }

    public String toString(){
	return this.typeMove+""; 
    }

	public boolean isNullAction(){
			return this.typeMove==2;
	}
	
}
