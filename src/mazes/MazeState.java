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
 *    MazeState.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */



import environment.AbstractState; 
import environment.IState; 
import environment.IEnvironment; 



/** A maze state consists of : 
    <ul>
    <li> Its coordinates x,y</li>
    <li> Its type : free, wall, <i>treasure</i> : coded into the associated <code>Environment</code></li>
    </ul>

@author Francesco De Comite 
 @version $Revision: 1.0 $ 

*/


public class MazeState extends AbstractState{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int x,y ; 
   

    /** 
	@param z first coordinate
	@param t second coordinate
	@param univers the maze
    */
    public MazeState(int z,int t,IEnvironment univers){
	super(univers); 
	this.x=z; 
	this.y=t;
    }

   
    public IState copy(){
	return new MazeState(x,y,myEnvironment); 
    }

  
    /**
     * 
     * @return true if this state is a wall
     */    
    public boolean isWall(){
	return ((Maze)myEnvironment).isWall(this); 
    }

    public int getX(){return x;}
    public int getY(){return y;}

    public String toString(){return "row "+x+" column "+y;}

    public int hashCode(){
	return this.x+this.y*((Maze)myEnvironment).getLargeur(); 
    }
    
    public boolean equals(Object o){
	if(!(o instanceof MazeState)) return false; 
	MazeState el=(MazeState)o; 
	return((el.x==this.x)&&(el.y==this.y)); 
    }

   
    /** Maze is a one player problem  */
    public boolean getTurn(){return false;}

    public int nnCodingSize(){
	return 20; 
    }
    
    public double[] nnCoding(){
	double code[]=new double[20]; 
	code[x%10]=1.0; 
	code[y%10+10]=1.0; 
	return code; 
    }


    



    
    
}
