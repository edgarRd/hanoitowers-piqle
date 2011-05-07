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
 *    AliceMazeCOntinuousDistanceState.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */




import environment.IEnvironment; 



/** A view on a maze, as it can be perceived by a mobile robot, like Alice : 
    <ul>
<li>  Its coordinates x,y (for the supervisor LabAlice, but not used by the state itself)</li>
<li> What it sees in the 4 orthogonal directions (forward, left, backward and 
right), as far as the first wall it sees</li>
<li> Its orientation (N,E,S,W) (for the supervisor, not used by the state itself)</li>
</ul>

@author Francesco De Comite
 @version $Revision: 1.0 $ 

*/

public class AliceContinuousDistanceState extends AliceDistanceState{

 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The bigger maze's dimension */
	private int maxDim; 
public AliceContinuousDistanceState(int z,int t,int direction,IEnvironment univers){
	super(z,t,direction,univers); 
	AliceMazeContinuousDistance galaxy=(AliceMazeContinuousDistance)myEnvironment; 
	if(galaxy.getLargeur()>galaxy.getLongueur()) maxDim=galaxy.getLargeur(); 
	else maxDim=galaxy.getLongueur();
	whatSurroundsMe[NORTH]=getValueNorth(galaxy,z,t); 
	whatSurroundsMe[EAST]=getValueEast(galaxy,z,t); 
	whatSurroundsMe[SOUTH]=getValueSouth(galaxy,z,t); 
	whatSurroundsMe[WEST]=getValueWest(galaxy,z,t); 
	this.orientation=direction %4; 
}

    private int getValueNorth(AliceMazeContinuousDistance galaxy,int x,int y){
	int distance=1; 
	while((x-distance>=0)&&(galaxy.getType(x-distance,y)==0)) distance++; 
	return distance; 
    }//getValueNorth

    private int getValueSouth(AliceMazeContinuousDistance galaxy,int x,int y){ 
	int distance=1; 
	while((x+distance<galaxy.getLongueur())&&(galaxy.getType(x+distance,y)==0)) 
		distance++; 
       return distance; 
    }//getValueSouth

 private int getValueEast(AliceMazeContinuousDistance galaxy,int x,int y){ 
     int distance=1; 
	while((y+distance<galaxy.getLargeur())&&(galaxy.getType(x,y+distance)==0)) distance++; 
	return distance; 
    }//getValueEast

 private int getValueWest(AliceMazeContinuousDistance galaxy,int x,int y){ 
     	int distance=1; 
	while((y-distance>=0)&&(galaxy.getType(x,y-distance)==0)) distance++; 
	return distance; 
    }//getValueWest



    public String toString(){
	String s=""; 
	for(int i=0;i<4;i++)s+=whatSurroundsMe[(i+orientation)%4]+" "; 
	s+=" "+"row :"+x+" column :"+y+" orientation : "+orString(orientation); 
	return s; 

    }// toString

    protected String strval(int i){
	return null;
    }
 public boolean equals(Object o){
	if(!(o instanceof AliceContinuousDistanceState)) return false; 
	AliceContinuousDistanceState ea=(AliceContinuousDistanceState)o;  
	for(int i=0;i<4;i++)
	    if(this.whatSurroundsMe[(i+this.orientation)%4]!=ea.whatSurroundsMe[(i+ea.orientation)%4]) return false; 
	return true; 
    }
 public int nnCodingSize(){ return 4;}
 
 public double[] nnCoding(){   	
 	double code[]=new double[4];
 	for(int i=0;i<4;i++)
 		code[i]=whatSurroundsMe[i]/(maxDim+0.0); 
 	return code;
 }

}
