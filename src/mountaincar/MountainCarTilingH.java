package mountaincar;
/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    MountainCarTilingH.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */

import java.util.Iterator; 
import tiling.ListOfTiles; 
import tiling.SetOfTilings; 
import tiling.HyperRectangularTiling; 

import tiling.HyperRectangularSparseTiling; 

import environment.IState; 
import environment.IAction; 
import environment.TileAbleEnvironment; 


public class MountainCarTilingH extends MountainCar implements TileAbleEnvironment{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private static Random generateur=new Random(); 
    /** One set of tilings for each possible action */
    protected SetOfTilings arrayTilings[]=new SetOfTilings[3]; 

     protected void addTiling(HyperRectangularTiling t,int level){
	arrayTilings[level].addTiling(t); 
    }

    /** Constructor : define all the tilings */
    public MountainCarTilingH(){
	double bl[]={bpleft,bsleft};
	double bu[]={bpright,bsright}; 
	double sh[]=new double[2]; 
	boolean pvalid[]={true,true};
	int nbdiv[]={8,8}; 
	for(int i=0;i<3;i++){
	    arrayTilings[i]=new SetOfTilings(); 
	    for(int j=0;j<10;j++){
		sh[0]=0.2*generateur.nextDouble(); 
		sh[1]=0.2*generateur.nextDouble(); 
		this.addTiling(new HyperRectangularSparseTiling(bl,bu,sh,nbdiv,pvalid),i); 
	    }
	}
    }// Constructor

    public ListOfTiles getTiles(IState s,IAction a){
	ActionMountainCar ac=(ActionMountainCar)a;
	MountainCarState ec=(MountainCarState)s;
	double x[]=new double[2]; 
        x[0]=ec.getPosition(); 
	x[1]=ec.getSpeed(); 
	int index=ac.getType()+1; 
	Iterator c=arrayTilings[index].iterator(); 
	ListOfTiles resu=new ListOfTiles(); 
	while(c.hasNext()){
	    HyperRectangularSparseTiling t=(HyperRectangularSparseTiling)c.next(); 
	    resu.add(t.getTile(x)); 
	}
	return resu; 
    }








}