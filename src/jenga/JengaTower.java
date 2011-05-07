package jenga; 
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
 *    JengaTower.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */



import environment.*; 
/**
   Jenga is an game of skill : the player must take blocks out of a tower without making it fall down. But assuming that his skill is perfect, it turns out to be a Nim-like game, with a winning strategy for one of the players (depending of the highness of the tower). <br>
<a href="http://www.cs.tau.ac.il/~zwick/papers/jenga-SODA.pdf"> This paper</a> analyses the game, and describe the strategy. <br>

The interest of the game in the perspective of Reinforcement Learning, is to see whether the algorithms found this strategy, and whether it is understandable (and quickly found !). 


 @author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 

*/

public class JengaTower implements IEnvironmentTwoPlayers{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Size of the tower (commercial version) */
    private int towerSize=18; 
    /** Number of full levels */
    private int X=17; 
    /** Number of II- or -II levels */
    private int Y=0; 
    /** Number of blocks at top level. */
    private int Z=0; 

    protected int getTowerSize(){return towerSize;}
    protected int getX(){return X; }
    protected int getY(){return Y;}
    protected int getZ(){return Z; }

    protected StateJenga defaultInitialState; 

    public JengaTower(){
	defaultInitialState=new StateJenga(this); 
    }

    public JengaTower(int taille){
	this.towerSize=taille; 
	defaultInitialState=new StateJenga(this); 
    }

      
    public ActionList getActionList(IState s){
	StateJenga ej=(StateJenga)s; 
	ActionList loa=new ActionList(ej); 
	if(ej.getFullLevels()!=0){
	    loa.add(ActionJenga.MOVECENTER); 
	    loa.add(ActionJenga.MOVESIDE); 
	}
	if(ej.getTwoAdjacentBlocksLevels()!=0)
	    loa.add(ActionJenga.LEAVEONE); 
	return loa; 
    }

	
    public IState successorState(IState s,IAction a){
	StateJenga ej=(StateJenga)s; 
	StateJenga neuf=(StateJenga)ej.copy(); 
	neuf.toggleTurn();
	neuf.incNbTurns(); 
	if (a.equals(ActionJenga.MOVECENTER)){
	    neuf.setFullLevels(ej.getFullLevels()-1); 
	}
	if(a.equals(ActionJenga.MOVESIDE)){
	    neuf.setFullLevels(ej.getFullLevels()-1); 
	    neuf.setTwoAdjacentBlocksLevels(ej.getTwoAdjacentBlocksLevels()+1); 
	}
	if(a.equals(ActionJenga.LEAVEONE)){
	    neuf.setTwoAdjacentBlocksLevels(ej.getTwoAdjacentBlocksLevels()-1); 
	}
	 neuf.setTopBlocks(ej.getTopBlocks()+1); 
	 if(neuf.getTopBlocks()==3){
	     neuf.setFullLevels(neuf.getFullLevels()+1);
	     neuf.setTopBlocks(0); 
	 }    
	 return neuf; 
    }// etat suivant


    public ITwoPlayerState defaultInitialTwoPlayerState(){
	return new StateJenga(this); 
    }


    public double getReward(IState s1,IState s2,IAction a){
	if(!s2.isFinal()) return 0; 
	ITwoPlayerState ss2=(ITwoPlayerState)s2;
	double reward=0;  
	if(!ss2.getTurn()&&(whoWins(s2)==1)) reward=-1; 
	if(!ss2.getTurn()&&(whoWins(s2)==-1)) reward=1;
	if(ss2.getTurn()&&(whoWins(s2)==-1)) reward=-1; 
	if(ss2.getTurn()&&(whoWins(s2)==1)) reward=1;
	return reward; 
    }//getReward


    public boolean isFinal(IState s){
	StateJenga ej=(StateJenga)s; 
	return ((ej.getFullLevels()==0)&&(ej.getTwoAdjacentBlocksLevels()==0)); 
    }// isFinal 



	/** Who won ? 
	    
	    <ul>
	    <li> Player One : -1</li>
	    <li> Tie : 0 </li>
	    <li> Player Two  : 1 </li>
	    </ul>
	   No tie game at Jenga.
	    */
    public int whoWins(IState s){
	if(((StateJenga)s).getNbTurns()%2==0) return 1; 
	else return -1; 
    }

   
	    
    }
