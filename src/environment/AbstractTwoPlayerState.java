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
 *    AbstractTwoPlayerState.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */




/** Wrapping Interface State and define some common behaviors.

@author Francesco De Comite
 @version $Revision: 1.0 $ 

*/

abstract public class AbstractTwoPlayerState implements ITwoPlayerState{

    /** The game's rules */
    protected IEnvironment myEnvironment; 
    /** Tells which player can play.*/
    protected boolean turn; 

    
    public AbstractTwoPlayerState(IEnvironment ct){
	this.myEnvironment=ct; 
    }

    public boolean isFinal(){
	return this.myEnvironment.isFinal(this); 
    }

    public void setEnvironment(IEnvironment ct){this.myEnvironment=ct;}
    
    
    public ActionList getActionList(){
	return myEnvironment.getActionList(this); 
    }
	
       
    public IState modify(IAction a){
	return this.myEnvironment.successorState(this,a); 
    }

    public IEnvironment getEnvironment(){return myEnvironment;}

    public double getReward(IState old,IAction a){
	return this.myEnvironment.getReward(old,this,a); 
}

    abstract public IState copy(); 
 
  

    public int getWinner(){
	return myEnvironment.whoWins(this); 
    }


   

    public  boolean getTurn(){return this.turn;} 

     public void toggleTurn(){this.turn=!this.turn; }

    public void setTurn(boolean b){ this.turn=b;}

    
    abstract public int nnCodingSize(); 


    abstract public double[] nnCoding(); 

   
  

}




    
