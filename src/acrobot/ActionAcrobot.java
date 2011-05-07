package acrobot; 

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
 *    ActionAcrobot.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */

import environment.*; 

/** Acrobot can only exert positive, negative or null torque. The magnitude of torque is constant 

@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 

*/

public class ActionAcrobot implements IAction{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
    private int torque; 
	
    public ActionAcrobot(int i){
	this.torque=i; 
    }
    public ActionAcrobot(){}
    
    public static ActionAcrobot NEGATIVE=new ActionAcrobot(-1); 
    
    public static ActionAcrobot NHUL=new ActionAcrobot(0);
    
    public static ActionAcrobot POSITIVE=new ActionAcrobot(1); 
    
    /** Clone */
    public IAction copy(){
	switch(this.torque){
	case -1 : return NEGATIVE; //return new ActionAcrobot(-1); 
	case 0: return NHUL; //return new ActionAcrobot(0); 
	case 1: return POSITIVE; //return new ActionAcrobot(1); 
	default: return null; 
	}
    }
    
    
    public String toString(){
	switch(this.torque){
	case -1 : return "Negative"; 
	case 0: return "Nul"; 
	case 1: return "Positive"; 
	default : return "Wrong action." ; 
	}
    }
    
    
    public int hashCode(){
	return this.torque; 
    }
    
    public int getTorque(){return this.torque;}

  
     public boolean equals(Object o){
	if (!(o instanceof ActionAcrobot)) return false; 
	 ActionAcrobot a=(ActionAcrobot)o; 
	 return (this.torque==a.torque);
	 // return (a==this); 
	 }

   

    /** One among 3 coding */
    public int nnCodingSize(){return 3; }

  
    public double[] nnCoding(){
	double code[]=new double[3]; 
	code[this.torque+1]=1.0; 
	return code; 
    }

} 
