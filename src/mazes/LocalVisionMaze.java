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
 *    LocalVisionMaze.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */






import environment.*;
 

/** Standard rectangular maze : each position is either free, a wall, a treasure.

Reward is return when one reaches a treasure. 

It is up to you to add new types of positions, non-rectangular shapes ...

States are only local visions of the maze (the value of the 8 surrounding cases)
The actual position in the maze is also memorized, but serves only for LocalVisionMaze definition of possible moves and
computing the following state.
Coordinates are neither used for hashCode or equals

@author Francesco De Comite 
 @version $Revision: 1.0 $ 
*/

public class LocalVisionMaze extends Maze{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LocalVisionMaze(int lo,int la){
	super(lo,la); 
    }

  /** Set the initial state to any free position of the maze. */
    public void randomInitialState(){
	int i=generateur.nextInt(longueur); 
	int j=generateur.nextInt(largeur);
	while(!forme[i][j].isFree()){
	    i=generateur.nextInt(longueur); 
	    j=generateur.nextInt(largeur);
	}
	oldState=defaultCurrentState; 
	defaultCurrentState=new LocalMazeState(i,j,this); 
    }

    /** Build a maze according to a two-dimensional array description */
    public void setDesign(int [][] coding){
	boolean found=false; 
	for(int i=0;i<largeur;i++)
	    for(int j=0;j<longueur;j++){
		forme[i][j]=composantLabyrinthe.makeComponent(coding[i][j]); 
		if(coding[i][j]==2){
		    this.xtre=i; 
		    this.ytre=j;
		}
	    }
	for(int i=0;i<largeur;i++)
	    for(int j=0;j<longueur;j++)
		if((!found)&&(forme[i][j].isFree()))
		    {
			//System.out.println(i+" "+j); 
			defaultCurrentState=new LocalMazeState(i,j,this); 
			oldState=defaultCurrentState; 
			found=true; 
		    }
    }
	

    

 public IState successorState(IState s,IAction a){
	LocalMazeState el=(LocalMazeState) s;
	MazeAction al=(MazeAction)a;
	oldState=el; 
	return new LocalMazeState((el.getX()+al.getX()),
				  (el.getY()+al.getY()),this);
    }

 public ActionList getActionList(IState s){
	LocalMazeState el=(LocalMazeState) s;
	ActionList l=new ActionList(el);  
	for (int i=0;i<9;i++) {
	    MazeAction a=MazeAction.direction[i]; 
	    int newX=el.getX()+a.getX(); 
	    int newY=el.getY()+a.getY(); 
	    if((newX<0)||(newX>=largeur)) continue; 
	    if((newY<0)||(newY>=longueur)) continue; 
	    if(forme[newX][newY].isWall()) continue; 
	    l.add(MazeAction.direction[i]); 
	}
	return l; 

    }



}
