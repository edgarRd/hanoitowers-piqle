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
 *    MemoryState.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */



import environment.*; 
import java.util.HashMap; 
import java.util.ArrayList; 


/** A state for the Memory game gathers a lot of information : 
<ul>
<li> The list of known cards (with links to find their position on the board).</li>
<li> The list of the positions of unknown cards.</li>
<li> The number of turns. </li>
<li> The score of each player.</li>
</ul>




@author Francesco De Comite
 @version $Revision: 1.0 $ 


*/
public class MemoryState extends AbstractTwoPlayerState{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The list containing the known cards.*/
    private HashMap<Integer,Position> knownCards=new HashMap<Integer,Position>(); 
    /** The list of the positions of unknown cards. */
    private ArrayList<Position>unknownCards=new ArrayList<Position>(); 
    
    
    /** Actual score of each player. */
    protected int nbPairsFound[]=new int[2];  

    /** Number of turns already played. */
    protected int nbTurns=0; 

    protected void incNbTurns(){nbTurns++;}

    protected int getNbTurns(){return nbTurns; }


    /** Score of actual player. */
    protected int getNbPairsFound(){
	if(this.getTurn()) return nbPairsFound[0];
	else return nbPairsFound[1]; 
	   }

   
    protected void incNbPairsFound(){nbPairsFound[(this.turn? 0:1)]++;}

  
    public MemoryState(IEnvironment ct){
	super(ct); 
	MemoryBoard mb=(MemoryBoard)ct; 
	knownCards=new HashMap<Integer,Position>(); 
	unknownCards=new ArrayList<Position>(); 
	mb.fill(unknownCards); 
    }

    /** Clone */
    public IState copy(){
	MemoryState neuf=new MemoryState(myEnvironment); 
	neuf.knownCards=new HashMap<Integer,Position>(knownCards); 
	neuf.unknownCards=new ArrayList<Position>(unknownCards); 
	neuf.nbPairsFound[0]=this.nbPairsFound[0];
	neuf.nbPairsFound[1]=this.nbPairsFound[1];
	neuf.nbTurns=this.nbTurns; 
	neuf.turn=this.turn; 
	return neuf; 
    }

    /** Number of unknown cards. */
    protected int unknown(){return this.unknownCards.size();}
    
    /** Number of known cards. */
    protected int known(){
	return this.knownCards.size(); 
    }

    /** Take the unknown card at rank i. */
    protected Position getPosition(int i){
	return (Position)this.unknownCards.get(i); 
    }

    /** We have already seen the twin card.*/
    protected boolean knowIt(int value){
	return this.knownCards.containsKey(new Integer(value)); 
    }

    /** An unknown card becomes known. */
    protected void makeKnown(int pick,int value,Position p){
	this.unknownCards.remove(pick); 
	this.knownCards.put(new Integer(value),p); 
    }

 
    protected void removeFromUnknown(int pick){
	this.unknownCards.remove(pick); 
    }


    protected void removeFromKnown(int value){
	this.knownCards.remove(new Integer(value)); 
    }
    

   

 

    public String toString(){
	return this.unknownCards.size()+" "+this.knownCards.size()+"("+nbPairsFound[0]
	+","+nbPairsFound[1]+")"; 
    }//toString


      public boolean equals(Object o) {
	if(!(o instanceof MemoryState)) return false; 
	MemoryState em=(MemoryState)o; 
	if(em.unknownCards.size()!=this.unknownCards.size()) return false; 
	if(em.knownCards.size()!=this.knownCards.size()) return false;
	if(em.nbPairsFound[0]!=this.nbPairsFound[0]) return false; 
	if(em.nbPairsFound[1]!=this.nbPairsFound[1]) return false; 
	if(em.nbTurns!=this.nbTurns) return false; 
	return true; 
    }

    public int hashCode(){
	return (7*this.unknownCards.size()+this.knownCards.size())%13;
	    }
    
  

    public int nnCodingSize(){return 30;}
    
    /** Coding : number of known cards, number of unknown cards. 
	Adjust or change for more accurate coding ....
    */
    public double[] nnCoding(){
	double code[]=new double[30]; 
	code[this.knownCards.size()%15]=1.0; 
	code[15+(this.unknownCards.size()%15)]=1.0;
	return code; 
    }
    
    /** First player's score.*/
    protected int firstFound(){return nbPairsFound[0];}

    /** Second player's score.*/
    protected int secondFound(){return nbPairsFound[1];}

  
    



 
    
}
