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
 *    ALiceState.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */



import environment.IState; 
import environment.IEnvironment; 


/** A view on a maze, as it can be perceived by a mobile robot, like Alice : 
    <ul>
<li>  Its coordinates x,y (for the supervisor LabAlice, but not used by the state itself)</li>
<li> What it sees in the 4 orthogonal directions (forward, left, backward and right) </li>
<li> Its orientation (N,E,S,W) (for the supervisor, not used by the state itself)</li>
</ul>

@author Francesco De Comite 
 @version $Revision: 1.0 $ 

*/


public class AliceState extends MazeState{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final int
	NORTH=0,
	EAST=1,
	SOUTH=2,
	WEST=3; 

    /** Objective view of the neighbourood : 0-> North 1->East ...
	The robot view of its environment will be a shift of those values */
    protected int whatSurroundsMe[]=new int[4]; 
    
    /** Direction towards the robot looks (used by the maze supervisor, not available in state's calculations) 
	0-> North, 1-> East ... 
    */
    protected int orientation; 

    public int getOrientation(){return orientation;}

    public AliceState(int z,int t,int direction,IEnvironment univers){
	super(z,t,univers); 
	AliceMaze galaxy=(AliceMaze)myEnvironment; 
	whatSurroundsMe[NORTH]=galaxy.getType(x-1,y); 
	whatSurroundsMe[EAST]=galaxy.getType(x,y+1); 
	whatSurroundsMe[SOUTH]=galaxy.getType(x+1,y); 
	whatSurroundsMe[WEST]=galaxy.getType(x,y-1); 
	for(int i=0;i<4;i++)
	    if(whatSurroundsMe[i]==-1) whatSurroundsMe[i]=1; 
	this.orientation=direction %4; 
    }

    public int getWSM(int i){
	return whatSurroundsMe[(i+this.orientation)%4]; 
    }

  public IState copy(){
      return new AliceState(x,y,orientation,myEnvironment); 
    }   

 public int hashCode(){
	int sum=0; 
	for(int i=0;i<4;i++)
	    sum=3*sum+whatSurroundsMe[(i+orientation)%4]; 
	return sum%53; 
    }

    public boolean equals(Object o){
	if(!(o instanceof AliceState)) return false; 
	AliceState ea=(AliceState)o;  
	for(int i=0;i<4;i++)
	    if(this.whatSurroundsMe[(i+this.orientation)%4]!=ea.whatSurroundsMe[(i+ea.orientation)%4]) return false; 
	return true; 
    }

    protected double distanceToTreasure(){
	return 0; 
    }

 

    protected String strval(int i){
	if(i==0) return "Free"; 
	return "Wall"; 
    }

    protected String orString(int i){
	switch(this.orientation){
	case 0: return "North"; 
	case 1 : return "East"; 
	case 2 : return "South"; 
	case 3 : return "West"; 
	default : System.exit(-1); 
	}
	return null; 
    }

   
    public String toString(){
	String s=""; 
	for(int i=0;i<4;i++)s+=strval(whatSurroundsMe[(i+orientation)%4])+" "; 
	s+=" "+"row :"+x+" column :"+y+" orientation : "+orString(orientation); 
	return s; 

    }// toString
    
    
   public int nnCodingSize(){ return 12;}
    
    public double[] nnCoding(){   	
    	double code[]=new double[12]; 
    	for(int i=0;i<4;i++)
    		code[3*i+whatSurroundsMe[i]]=1; 
    	return code;
    }
}