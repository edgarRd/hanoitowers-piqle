package tictactoe; 
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
 *    TicTacToeBoard.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import environment.*; 

/**
   The rules of Tic-tac-Toe.

@author Francesco De Comite 
 @version $Revision: 1.0 $ 

*/


public class TicTacToeBoard implements IEnvironmentTwoPlayers{


       
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ActionList getActionList(IState s){
	TicTacToeState el=(TicTacToeState) s;
	ActionList l=new ActionList(el); 
	int config[][]=el.getConfig(); 
	for(int i=0;i<3;i++)
	    for(int j=0;j<3;j++)
		if(config[i][j]==0) l.add(new TicTacToeAction(i,j));
	return l; 
    }// getActionList


    public IState successorState(IState s,IAction a){
	TicTacToeState st=(TicTacToeState)s; 
	TicTacToeState neuf=(TicTacToeState)st.copy(); 
	neuf.toggleTurn(); 
	neuf.setConfig((TicTacToeAction)a,st.getTurn());
	neuf.incNbCoups(); 
	return neuf; 

    }//etatSuivant

    public ITwoPlayerState defaultInitialTwoPlayerState(){
    return new TicTacToeState(this); }


    public double getReward(IState s1,IState s2,IAction a)
    {
	if(!s2.isFinal()) return 0; 
	// Tie is not good
	if(whoWins(s2)==0) return -0.5; 
	double pro=0.0; 
	if(!((TicTacToeState)s2).getTurn()) pro=-whoWins(s2); 
	else pro=whoWins(s2); 
	// Adjust reward to make winning more attractive
	if(pro<0) pro*=2; 
	return pro;    }

	
    public boolean isFinal(IState s){
	TicTacToeState et=(TicTacToeState)s; 
	if(et.getNbCoups()==9) return true; 
	int config[][]=et.getConfig(); 
	for(int i=0;i<3;i++){
	if((config[i][0]==config[i][1])&&
	   (config[i][2]==config[i][1])&&
	   (config[i][0]!=0)) return true; 
	if((config[0][i]==config[1][i])&&
	   (config[2][i]==config[1][i])&&
	   (config[0][i]!=0)) return true;
	}
	if((config[0][0]==config[1][1])&&
	   (config[1][1]==config[2][2])&&
	   (config[0][0]!=0)) return true; 
	if((config[0][2]==config[1][1])&&
	   (config[1][1]==config[2][0])&&
	   (config[0][2]!=0)) return true;
	return false; 
    }//isFinal 


    public int whoWins(IState s){
	TicTacToeState es=(TicTacToeState)s; 
	int config[][]=es.getConfig(); 
	for(int i=0;i<3;i++){
	    if((config[i][0]==config[i][1])&&
	       (config[i][2]==config[i][1])&&
	       (config[i][0]!=0)) return config[i][0]; 
	    if((config[0][i]==config[1][i])&&
	       (config[2][i]==config[1][i])&&
	       (config[0][i]!=0)) return config[0][i];
	}
	if((config[0][0]==config[1][1])&&
	   (config[1][1]==config[2][2])&&
	   (config[0][0]!=0)) return config[0][0]; 
	if((config[0][2]==config[1][1])&&
	   (config[1][1]==config[2][0])&&
	   (config[0][2]!=0)) return config[0][2];
	return 0;  }// whoWins
	    
 

}
