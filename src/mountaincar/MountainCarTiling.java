package mountaincar;
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
 *    MountainCarTiling.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */

import java.util.Iterator; 
import tiling.ListOfTiles; 
import tiling.SetOfTilings; 
import tiling.RectangularTiling; 


import environment.IState; 
import environment.IAction; 
import environment.TileAbleEnvironment; 

import java.util.Random; 

/** Sutton's original definition of the mountain car task with tiling. Tiles are basic 2-dim rectangles.
    See @MountainCarTilingH for a more general version (hyperRectangles with only certain dimensions taken into account)
*/
public class MountainCarTiling extends MountainCar implements TileAbleEnvironment{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static Random generateur=new Random(); 
    /** One set of tilings for each possible action */
    protected SetOfTilings arrayTilings[]=new SetOfTilings[3]; 

    protected void addTiling(RectangularTiling t,int level){
	arrayTilings[level].addTiling(t); 
    }

    /** Constructor : define all the tilings */
    public MountainCarTiling(){
	for(int i=0;i<3;i++){
	    arrayTilings[i]=new SetOfTilings(); 
	    for(int j=0;j<10;j++)
		this.addTiling(new RectangularTiling(bpleft,bpright,
						     bsleft,bsright,
						     0.2*generateur.nextDouble(),
						     0.2*generateur.nextDouble(),8,8),i); 
	}
    }// Constructor

    public ListOfTiles getTiles(IState s,IAction a){
	ActionMountainCar ac=(ActionMountainCar)a;
	MountainCarState ec=(MountainCarState)s; 
	double x=ec.getPosition(); 
	double y=ec.getSpeed(); 
	int index=ac.getType()+1; 
	Iterator c=arrayTilings[index].iterator(); 
	ListOfTiles resu=new ListOfTiles(); 
	while(c.hasNext()){
	    RectangularTiling t=(RectangularTiling)c.next(); 
	    resu.add(t.getTile(x,y)); 
	}
	return resu; 
    }








}