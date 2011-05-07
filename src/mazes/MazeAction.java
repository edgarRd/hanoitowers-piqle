package mazes; 

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
 *    MazeAction.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */


import environment.IAction; 





/** In mazes, an action is a move in 1 among 9 directions (real moves and <i>stood still</i>) : we code it with two integers : 
 <ul>
 <li> Horizontal move : -1, 0, 1</li>
 <li> Vertical move : -1,0,1</li>
</ul>


@author Francesco De Comite
 @version $Revision: 1.0 $ 

*/
public class MazeAction implements IAction{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The two directions */   
    private int x,y; 
    /** For printing informations */
    // TODO : use this array in toString ?
    //private static String sens[]={"NW","N","NE","W","Still","E","SW","S","SE"}; 

    /** There are only 9 moves */
    // Why is this array not used ? 
    protected static MazeAction direction[]=new MazeAction[9]; 

    /** Fill the array with all the possible moves */
    static {for(int i=0;i<3;i++)
	for(int j=0;j<3;j++)
	    direction[3*i+j]=new MazeAction(i-1,j-1);
    }




    public MazeAction(int z, int t){
	x=z; 
	y=t; 
    }

    /** Access horizontal part of move */
    public int getX(){return x;}
    /** Access vertical part of move */
    public int getY(){return y;}

    public String toString(){return " x : "+x+" y :"+y;}

    public int hashCode(){
	return 10*x+y; 
    }

     public boolean equals(Object o){
	if(!(o instanceof MazeAction)) return false; 
	MazeAction el=(MazeAction)o; 
	return((el.x==this.x)&&(el.y==this.y)); 
    }

  

    public IAction copy(){return new MazeAction(x,y); }

    public int nnCodingSize(){
	return 9; 
    }

    public double[] nnCoding(){
	double code[]=new double[9];
	code[3*(x+1)+y+1]=1.0; 
	return code;
    }

    public int getValue(){
	return 3*(x+1)+y+1;
    }


   
}
