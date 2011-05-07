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
 *    TicTacToeState.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import environment.*; 




/** A state is just the configuration of the playing board.

 @author Francesco De Comite 
 @version $Revision: 1.0 $ 


*/

public class TicTacToeState extends AbstractTwoPlayerState{

 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/** An array containing the actual position. Cell can contain : 
<ul>
<li> -1 : Player 1</li>
<li> 0 : free cell.</li> 
<li> 1 : Player 2</li>
</ul>

@author Francesco De Comite 
 @version $Revision: 1.0 $ 
 */
    /** An array coding the board.*/
    protected int[][] config=new int[3][3]; 

    /** Number of turns already played. */
    private int nbCoups=0; 

   
    protected int getNbCoups(){return nbCoups; }

    public int[][] getConfig(){
	return config; 
    }

    
    
    protected void incNbCoups(){nbCoups++;}

    protected void setConfig(TicTacToeAction a,boolean turn){
	config[a.getLine()][a.getColumn()]=(turn ?  1:-1); 
    }
	

   
    public TicTacToeState(IEnvironment ct){
	super(ct); 
    }

    public IState copy(){
	TicTacToeState neuf=new TicTacToeState(myEnvironment); 
	neuf.turn=this.turn; 
	neuf.nbCoups=this.nbCoups; 
	for(int i=0;i<3;i++)
	    for(int j=0;j<3;j++) neuf.config[i][j]=this.config[i][j]; 
	return neuf; 
    }

    /** Decoding array for printing.*/
    private char symbol(int i,int j){
	if (config[i][j]==0) return ' ' ;
	if(config[i][j]==1) return 'X'; 
	else return '0'; 
    }

    
    private boolean strictEquals(TicTacToeState e){
	boolean b=true; 
	for(int i=0;i<3;i++){
	    for(int j=0; j<3;j++){
		if(this.config[i][j]!=e.config[i][j]) {
		    b=false;
		    break;  
		}
	    }
	    if(!b) break; 
	}
	return b; 
    }

     public boolean equals(Object o){
        if (!(o instanceof TicTacToeState)) return false;
	else return this.strictEquals((TicTacToeState)o);
     } 
    
    public int hashCode(){
	int sum=0; 
	for(int i=0;i<3;i++)
	    for(int j=0;j<3;j++)
		sum=3*sum+config[i][j]+1; 
	return sum;
    }	

  
    public String toString(){
	String s=""; 
	for(int i=0;i<3;i++){
	    s+="|"; 
	    for(int j=0;j<3;j++)
		s+=symbol(i,j)+"|"; 
	    s+="\n"; 
	}
	return s; 

}

   

    /** Coding each cell with a One among Three coding scheme. */
    public int nnCodingSize(){return 27;}
    
    public double[] nnCoding(){
	double code[]=new double[27]; 
	for(int i=0;i<3;i++)
	    for(int j=0;j<3;j++)
		code[9*i+3*j+config[i][j]+1]=1.0; 
	return code; 
    }


    

  
}
