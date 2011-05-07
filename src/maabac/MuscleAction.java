package maabac;
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
 *    MuscleAction.java
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

import environment.IAction;

/** Elementary action : increment, decrement or do not change the contraction level of a Muscle.*/

public class MuscleAction implements IAction{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int typeMove; 

    public MuscleAction(int i){
	this.typeMove=i; 
    }
    
    public MuscleAction(){}; 

    public static MuscleAction CONTRACT=new MuscleAction(1); 
    public static MuscleAction DECONTRACT=new MuscleAction(-1); 
    public static MuscleAction STILL=new MuscleAction(0); 

    public int getValue(){
	return this.typeMove;
    }

/** Clone */
    public IAction copy(){
	switch(this.typeMove){
	case 0 : return STILL; 
	case 1: return CONTRACT; 
	case -1: return DECONTRACT; 
	default: return null; 
	}
    }

    public int hashCode(){return this.typeMove+1; }

     public boolean equals(Object o){
	if (!(o instanceof MuscleAction)) return false; 
	 MuscleAction a=(MuscleAction)o; 
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

