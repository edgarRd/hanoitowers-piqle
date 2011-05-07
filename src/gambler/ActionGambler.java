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
 *    ActionGambler.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */




import environment.IAction;

	
/** An action is a bet (lesser or equal than the gambler's capital)


 @author Francesco De Comite 
 @version $Revision: 1.0 $ 


 */

public class ActionGambler implements IAction{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The bet.*/
    private int amount; 

    public ActionGambler(int i){
	this.amount=i;
    }

    public int getValue(){return this.amount;}

    public int hashCode(){return this.amount%23;}
    
    public boolean equals(Object o){
	if(!(o instanceof ActionGambler)) return false; 
	ActionGambler ag=(ActionGambler)o; 
	return (this.amount==ag.amount); 
    }

    public IAction copy(){
	return new ActionGambler(this.amount); 
    }
    
    public String toString(){
	return "Bet "+this.amount; 
    }

    public double[] nnCoding(){
	double code[]=new double[1]; 
	code[0]=this.amount/100.0; 
	return code; 
    }

    public int nnCodingSize(){return 1;}




    
}
