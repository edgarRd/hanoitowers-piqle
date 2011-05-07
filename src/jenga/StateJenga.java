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
 *    StateJenga.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */


import environment.*; 

/** 
    Following the analysis of <a href="http://www.cs.tau.ac.il/~zwick/papers/jenga-SODA.pdf"> this paper</a>, a state can be coded with three integers. <i> In fact, there is still a more compact coding sufficient to found the optimal strategy...</i>

 @author Francesco De Comite 
 @version $Revision: 1.0 $ 

    
*/

public class StateJenga extends AbstractTwoPlayerState{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;




	/** Size of the game (the tower : 18 usually)*/
    private int towerSize; 
    
    
    
     
    /** Number of full levels */
    private int fullLevels; // X
    /** Number of levels with only two adjacents blocks*/
    private int twoAdjacentBlocksLevels;   // Y
    /** Number of blocks at the top of the tower.*/
    private int topBlocks;  //Z

    protected int nbTurns=0; 

    protected void incNbTurns(){nbTurns++;}

    protected int getNbTurns(){return nbTurns;}
    protected int getFullLevels(){return fullLevels; }
    protected int getTwoAdjacentBlocksLevels(){return twoAdjacentBlocksLevels; }
    protected int getTopBlocks(){return topBlocks;}
	
    protected void setFullLevels(int i){
	this.fullLevels=i; 
    }
    protected void setTwoAdjacentBlocksLevels(int i){
	this.twoAdjacentBlocksLevels=i;  
    }

    protected void setTopBlocks(int i){
	this.topBlocks=i;
    }
    
	
    /** Standard tower */
    public StateJenga(IEnvironment ct){
	super(ct); 
	JengaTower jt=(JengaTower)ct; 
	this.towerSize=jt.getTowerSize(); 
	this.fullLevels=jt.getX(); 
	this.twoAdjacentBlocksLevels=jt.getY(); 
	this.topBlocks=jt.getZ(); 
    }

    
    /** Clone */
    public IState copy(){
	StateJenga neuf=new StateJenga(myEnvironment); 
	neuf.towerSize=this.towerSize; 
	neuf.fullLevels=this.fullLevels; 
	neuf.twoAdjacentBlocksLevels=this.twoAdjacentBlocksLevels; 
	neuf.topBlocks=this.topBlocks; 
	neuf.turn=this.turn; 
	neuf.nbTurns=this.nbTurns; 
	return neuf; 
    }
    
   

    
  public String toString(){
	String s   ; 
	s =fullLevels+" "+twoAdjacentBlocksLevels+" "+topBlocks; 
	return s; 
    }

    

    public boolean equals(Object o) {
	if(!(o instanceof StateJenga)) return false; 
	StateJenga ej=(StateJenga)o; 
	return ((ej.fullLevels==this.fullLevels)&&
		(ej.twoAdjacentBlocksLevels==this.twoAdjacentBlocksLevels)&&
		(ej.topBlocks==this.topBlocks));
    }

    

   
    public int hashCode(){
	int sum=(fullLevels*13)+(twoAdjacentBlocksLevels*7)+topBlocks; 
	return sum%23;
    }

    

    public int nnCodingSize(){return 21;}

    /** Coding scheme : 
	<ul>
	<li> topBlocks with 3 neurons (1 among 3)</li>
	<li> 9 bits for each of the two others quantities (base 3 coding)</li>
	</ul>
    */

    public double[] nnCoding(){
	double code[]=new double[21]; 
	code[18+(topBlocks%3)]=1.0; 
	code[(fullLevels%3)]=1.0; 
	code[9+(twoAdjacentBlocksLevels%3)]=1.0;
	code[3+((fullLevels/3)%3)]=1.0; 
	code[12+((twoAdjacentBlocksLevels/3)%3)]=1.0;
	code[6+(fullLevels/9)%3]=1.0; 
	code[15+(twoAdjacentBlocksLevels/9)%3]=1.0;
	return code; 
    }

  
    }



	
 
