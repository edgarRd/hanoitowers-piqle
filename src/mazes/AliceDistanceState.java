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
 *    ALiceDistanceState.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */




import environment.IEnvironment; 



/** A view on a maze, as it can be perceived by a mobile robot, like Alice : 
    <ul>
<li>  Its coordinates x,y (for the supervisor LabAlice, but not used by the state itself)</li>
<li> What it sees in the 4 orthogonal directions (forward, left, backward and right), 
at a maximal distance of two squares </li>
<li> Its orientation (N,E,S,W) (for the supervisor, not used by the state itself)</li>
</ul>

@author Francesco De Comite 
 @version $Revision: 1.0 $ 

*/

public class AliceDistanceState extends AliceState{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AliceDistanceState(int z,int t,int direction,IEnvironment univers){
    super(z,t,direction,univers); 
	AliceMazeDistance galaxy=(AliceMazeDistance)myEnvironment; 
	whatSurroundsMe[NORTH]=getValueNorth(galaxy,z,t); 
	whatSurroundsMe[EAST]=getValueEast(galaxy,z,t); 
	whatSurroundsMe[SOUTH]=getValueSouth(galaxy,z,t); 
	whatSurroundsMe[WEST]=getValueWest(galaxy,z,t); 
	this.orientation=direction %4; 
}

    private int getValueNorth(AliceMazeDistance galaxy,int x,int y){
	if(x==0) return 1; 
	if(galaxy.getType(x-1,y)==1) return 1; 
	// [x-1,y] is free
	if(x==1) return 2; // wall at two squares
	if(galaxy.getType(x-2,y)==1) return 2; 
	return 0; 
    }//getValueNorth

    private int getValueSouth(AliceMazeDistance galaxy,int x,int y){ 
	if(galaxy.getType(x+1,y)==-1) return 1; 
	if(galaxy.getType(x+1,y)==1) return 1; 
	// [x+1,y] is free
	if(galaxy.getType(x+2,y)==-1) return 2; 
	if(galaxy.getType(x+2,y)==1) return 2; 
	return 0; 
    }//getValueSouth

 private int getValueEast(AliceMazeDistance galaxy,int x,int y){ 
	if(galaxy.getType(x,y+1)==-1) return 1; 
	if(galaxy.getType(x,y+1)==1) return 1; 
	// [x,y+1] is free
	if(galaxy.getType(x,y+2)==-1) return 2; 
	if(galaxy.getType(x,y+2)==1) return 2; 
	return 0; 
    }//getValueEast

 private int getValueWest(AliceMazeDistance galaxy,int x,int y){ 
	if(galaxy.getType(x,y-1)==-1) return 1; 
	if(galaxy.getType(x,y-1)==1) return 1; 
	// [x,y-1] is free
	if(galaxy.getType(x,y-2)==-1) return 2; 
	if(galaxy.getType(x,y-2)==1) return 2; 
	return 0; 
    }//getValueWest



    protected String strval(int i){
	if(i==0) return "Free"; 
	if(i==1) return "Wall_1"; 
	return "Wall_2"; 
    }
 public boolean equals(Object o){
	if(!(o instanceof AliceDistanceState)) return false; 
	AliceDistanceState ea=(AliceDistanceState)o;  
	for(int i=0;i<4;i++)
	    if(this.whatSurroundsMe[(i+this.orientation)%4]!=ea.whatSurroundsMe[(i+ea.orientation)%4]) return false; 
	return true; 
    }


}
