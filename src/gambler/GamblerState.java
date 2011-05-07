package gambler; 
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
 *    GamblerState.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import environment.*; 

/** A state is just the gambler's capital.

 @author Francesco De Comite
 @version $Revision: 1.0 $ 

 */


public class GamblerState extends AbstractState{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The purse.*/
    private int capital; 

    public GamblerState(IEnvironment ct, int i){
	super(ct); 
	this.capital=i;
    }

    public IState copy(){
	GamblerState neuf=new GamblerState(myEnvironment,this.capital); 
	return neuf; 
    }

    public int getValue(){return this.capital;}
    
    public String toString(){
	return this.capital+""; 
    }

    public int hashCode(){
	return this.capital%23;
    }

    public boolean equals(Object o){
	if(!(o instanceof GamblerState)) return false; 
	GamblerState eg=(GamblerState)o; 
	return (this.capital==eg.capital);
    }
 
    public boolean getTurn(){return false;}
	
    public double[] nnCoding(){
	double code[]=new double[1]; 
	code[0]=this.capital/100.0; 
	return code;
    }

    public int nnCodingSize(){return 1;}

    
    
     }

	
