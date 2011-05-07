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
 *    IAction.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */



import java.io.Serializable;


    /** Minimal behavior of a generic Action.*/

    public interface IAction extends Cloneable,Serializable{

	/** Clone an Action. */
    	// TODO change profile to public IAction ? 
	public Object copy(); 

	// For use with Neural Networks
	
	/** Size of an Action's coding (for NN). */
	public int nnCodingSize(); 

	/** Action's coding (for NN). */
	public double[] nnCoding(); 


	
	/** Q-Learning memorizing techniques use hashcoding : it is necessary to redefine it for each problem/game */
	public int hashCode(); 


	/** Q-Learning memorizing techniques use equality: it is necessary to redefine it for each problem/game */
	public boolean equals(Object o); 


   
}
  
