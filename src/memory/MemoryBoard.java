package memory; 
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
 *    MemoryBoard.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import environment.*; 

import java.util.Random; 
import java.util.ArrayList; 


/**This version of the Memory game tries to maximize the probability to win the game. It is different than the version studied by Zwick & Patterson, where they try to maximize the number of cards.

@see MemoryBoardZP 

@author Francesco De Comite
 @version $Revision: 1.0 $ 
 */
public class MemoryBoard implements IEnvironmentTwoPlayers{
    
   

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Number of cards. */
    protected int nbCartes=20; 

    /** Rectangular shape of the game. */
    protected int largeur =4; 
    protected int longueur=5; 

    /** Cards values */
    protected int[][] tableau; 

    protected MemoryState defaultInitialState; 

    /** When each player passes successively, the game ends.*/
    protected int nbPasses=0; 

    /** @return the number of cards.*/
    public int getSize(){return nbCartes;}

   
    public MemoryBoard(){
	defaultInitialState=new MemoryState(this); 
	this.initialize(); 
    }

    /** Choose the size of the game : one of the parameter must be even ! */
    public MemoryBoard(int la,int lo){
	this.nbCartes=la*lo; 
	this.largeur=la; 
	this.longueur=lo; 
	defaultInitialState=new MemoryState(this); 
	this.initialize(); 
    }

     public ActionList getActionList(IState s){
	MemoryState em=(MemoryState)s; 
	ActionList loa=new ActionList(em); 
	if(em.known()>=2) loa.add(ActionMemory.MOVEZERO); 
	if((em.known()>=1)&&(em.unknown()>=1))
	    loa.add(ActionMemory.MOVEONE); 
	if(em.unknown()>=2)
	    loa.add(ActionMemory.MOVETWO); 
	return loa; 
     }

   
    protected void initialize(){
	Random generateur=new Random(); 
	ArrayList<Integer> bigBag=new ArrayList<Integer>(); 
	tableau=new int[this.largeur][]; 
	for(int i=0;i<this.largeur;i++) tableau[i]=new int[this.longueur]; 
	for(int i=0;i<this.nbCartes/2;i++) 
	    {
		bigBag.add(new Integer(i)); 
		bigBag.add(new Integer(i)); 
	    }
	for(int i=0;i<this.largeur;i++)
	    for(int j=0;j<this.longueur;j++){
		int value=
		    ((Integer)(bigBag.remove(generateur.nextInt(bigBag.size())))).intValue(); 
		tableau[i][j]=value; 
	    }
    }

    
    protected void fill(ArrayList<Position> liste){
	for(int i=0;i<largeur;i++)
	    for(int j=0;j<longueur;j++)
		liste.add(new Position(i,j)); 
    }

    
    protected int getValue(Position p){
	return tableau[p.getX()][p.getY()]; 
    }
		     
		     

    
    public IState successorState(IState s,IAction a){
	Random generateur=new Random(); 
	MemoryState em=(MemoryState)s; 
	MemoryState neuf=(MemoryState)em.copy(); 
	neuf.toggleTurn(); 
	neuf.incNbTurns(); 
	int pick;
	Position p; 
	int value; 
	if(a.equals(ActionMemory.MOVEZERO)) {
	    nbPasses++; 
	    return neuf; 
	}
	nbPasses=0; 
	while(true){ // flip the first card : if the other is known, take both out
	    if(neuf.unknown()==0) return neuf; 
	    pick=generateur.nextInt(neuf.unknown()); 
	    p=neuf.getPosition(pick); 
	    value=this.getValue(p);
	    if(!neuf.knowIt(value)){
		neuf.makeKnown(pick,value,p); 
		break;
	    }
	    else {
		neuf.incNbPairsFound(); 
		neuf.removeFromUnknown(pick); 
		neuf.removeFromKnown(value);
	    }
	}
	if(a.equals(ActionMemory.MOVEONE)) return neuf; 
	// Second card : flip an unknown card
	while(true){
	     if(neuf.unknown()==0) return neuf; 
	     int pick2=generateur.nextInt(neuf.unknown()); 
	     Position p2=neuf.getPosition(pick2); 
	     int value2=this.getValue(p2);
	     if(!neuf.knowIt(value2)){
		 neuf.makeKnown(pick2,value2,p2); 
		 break;
	     }
	      else {
		  neuf.incNbPairsFound(); 
		  neuf.removeFromUnknown(pick2); 
		  neuf.removeFromKnown(value2);
	    }
	}
	return neuf; 
	    
    }// successorState

     public ITwoPlayerState defaultInitialTwoPlayerState(){
	return new MemoryState(this); 
    }

    
	
    public double getReward(IState s1,IState s2,IAction a){
	if(!s2.isFinal()) return 0; 
	MemoryState ss2=(MemoryState)s2;
	double reward=0;
	if(!ss2.getTurn()&&(whoWins(s2)==1)) reward=-1; 
	if(!ss2.getTurn()&&(whoWins(s2)==-1)) reward=1;
	if(ss2.getTurn()&&(whoWins(s2)==-1)) reward=-1; 
	if(ss2.getTurn()&&(whoWins(s2)==1)) reward=1;
	if(whoWins(s2)==0)  reward=-1; 
	   
	return -reward; 
    }

    
    public boolean isFinal(IState s){
	MemoryState em=(MemoryState)s; 
	return ((em.unknown()==0)||(nbPasses==2)); 
    }

    
    public int whoWins(IState s){
	MemoryState em=(MemoryState)s; 
	if (em.firstFound()>em.secondFound()) return -1; 
	if (em.firstFound()<em.secondFound()) return 1; 
	return 0; 
    }

    public String toString(){
	String s=""; 
	for(int i=0;i<largeur;i++)
	    {
		for(int j=0;j<longueur-1;j++)
		    s+=tableau[i][j]+"|"; 
		s+=tableau[i][longueur-1]+"\n"; 
	    }
	return s; 
}


    }
