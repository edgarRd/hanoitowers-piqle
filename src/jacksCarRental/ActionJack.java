package jacksCarRental; 

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
 *    ActionJack.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import environment.*;  

public class ActionJack implements IAction{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The number of cars to move from location 1 to location 2
	(negative values mean moving cars from location 2 to location 1)
    */
    private int moveCars; 

    public ActionJack(int i){
	moveCars=i;
    }

    public int getNumber(){return moveCars;}
    
    public IAction copy(){
	return new ActionJack(this.moveCars); 
    }

   

    public String toString(){
	if(this.moveCars<0) return "Bring "+Math.abs(this.moveCars)+" cars form  2 to 1"; 
	if (this.moveCars>0) return "Bring "+this.moveCars+" cars from 1 vto 2";
	return "No car move"; 
    }

    public int hashCode(){
	return moveCars;
    }

     public boolean equals(Object o){
	if (!(o instanceof ActionJack)) return false; 
	 ActionJack a=(ActionJack)o; 
	 return (this.moveCars==a.moveCars); 
	 }
    
   
    
     /** One among k coding + one bit for the sign */
    public int nnCodingSize(){return 21; }

      
    public double[] nnCoding(){
	double code[]=new double[21]; 
	code[Math.abs(moveCars)]=1.0;
	if (moveCars<0) code[20]=1.0; 
	return code; 
    }

   


  

}
