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
 *    LocalMazeState.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */




import environment.IState; 
import environment.IEnvironment;  

/** A local view of a maze state consists of : 
    <ul>
    <li> Its coordinates x,y (for the supervisor environment LocalVisionMaze, but not used by the state itself)</li>
    <li> What it sees in the 8 directions (wall, free, treasure)</li>
    <li> Its type : free, wall, <i>treasure</i> : coded into the associated <code>Contraintes</code></li>
    </ul>

@author Francesco De Comite
 @version $Revision: 1.0 $ 

*/


public class LocalMazeState extends MazeState{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 0-> NW 1-> North 3-> East ... */
    private int[] whatISee=new int[8]; 
    private int[] dirl={-1,-1,-1,0,0,1,1,1}; 
    private int[] dirc={-1,0,1,-1,1,-1,0,1}; 
    
    public LocalMazeState(int z,int t,IEnvironment univers){
	super(z,t,univers);
	LocalVisionMaze galaxy=(LocalVisionMaze)myEnvironment; 
	whatISee[0]=galaxy.getType(x-1,y-1); 
	whatISee[1]=galaxy.getType(x-1,y); 
	whatISee[2]=galaxy.getType(x-1,y+1); 
	whatISee[3]=galaxy.getType(x,y-1); 
	whatISee[4]=galaxy.getType(x,y+1); 
	whatISee[5]=galaxy.getType(x+1,y-1); 
	whatISee[6]=galaxy.getType(x+1,y); 
	whatISee[7]=galaxy.getType(x+1,y+1);
	for(int i=0;i<8;i++)
	    if(whatISee[i]==-1) whatISee[i]=1; 
    }

     public IState copy(){
	return new LocalMazeState(x,y,myEnvironment); 
    }

    public int hashCode(){
	int sum=0; 
	for(int i=0;i<8;i++)
	    sum=3*sum+whatISee[i];
	return sum%53; 
    }

    public boolean equals(Object o){
	if(!(o instanceof LocalMazeState)) return false; 
	LocalMazeState el=(LocalMazeState)o; 
	for(int i=0;i<8;i++){
	    if(this.whatISee[i]!=el.whatISee[i]) return false; 
	}
	if(Math.abs(this.distanceToTreasure()-el.distanceToTreasure())<1e-6)
	    return true; 
	else return false; 
    }

    private double distanceToTreasure(){
	return ((Maze)myEnvironment).distanceToTreasure(this); 
    }
    
    /** one among three for each neighbors, plus distance to treasure */
    public int nnCodingSize(){ return 25;}
    
    public double[] nnCoding(){   	
    	double code[]=new double[25]; 
    	LocalVisionMaze galaxy=(LocalVisionMaze)myEnvironment; 
    	for(int i=0;i<8;i++)
    		code[3*i+whatISee[i]]=1; 
    	code[24]=this.distanceToTreasure()/(galaxy.getLargeur()*galaxy.getLongueur());
    	return code;
    }

  
}


    
		
	

    

    







