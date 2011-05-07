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
 *    TicTacToeAction.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */
import environment.*; 



/** Action=cell's coordinates.

@author Francesco De Comite
 @version $Revision: 1.0 $ 

 */

public class TicTacToeAction implements IAction {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Coordinates */
    private int ligne,colonne; 

 
    public TicTacToeAction(int i,int j){
	ligne=i; 
	colonne=j; 
    }  

  
    public int getLine(){return ligne; }
    
    
    public int getColumn(){return colonne; }

    public String toString(){
	return ligne+" "+colonne; 
    }

    public int hashCode(){
	return 3*ligne+colonne; 
    }

    public boolean equals(Object o){
	if (!(o instanceof TicTacToeAction)) return false; 
	 TicTacToeAction a=(TicTacToeAction)o; 
	 return ((ligne==a.ligne)&&(a.colonne==colonne)); 
	 }

    public IAction copy(){
	return new TicTacToeAction(ligne,colonne); 
    }

    /** Coding One among Three, twice. */
    public int nnCodingSize(){return 6;}
	
    public double[] nnCoding(){
	double code[]=new double[6]; 
	code[ligne]=1.0; 
	code[3+colonne]=1.0; 
	return code;
    }

    
  
}
