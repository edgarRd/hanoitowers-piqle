package maabac; 
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
 *    ElementaryMuscleTiling.java
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

import environment.ElementaryMultiAgentEnvironment; 
import environment.IState; 
import environment.IAction;
import environment.ActionList; 

import java.util.Iterator; 
import tiling.ListOfTiles; 
import tiling.SetOfTilings; 
import tiling.HyperRectangularTiling; 
import tiling.HyperRectangularSparseTiling; 
import environment.TileAbleEnvironment; 

import java.util.Random; 

/** Actions are contract, decontract, let the muscle still. Tile coding is used.*/

public class ElementaryMuscleTiling extends ElementaryMultiAgentEnvironment implements TileAbleEnvironment{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Random generateur=new Random(); 

      public ActionList getActionList(IState s){
	ActionList loa=new ActionList(s); 
	loa.add(MuscleAction.STILL); 
	loa.add(MuscleAction.CONTRACT);
	loa.add(MuscleAction.DECONTRACT);
	return loa;
    }

       /** One set of tilings for each possible action */
    protected SetOfTilings arrayTilings[]=new SetOfTilings[3]; 

     protected void addTiling(HyperRectangularTiling t,int level){
	arrayTilings[level].addTiling(t); 
    }

    
    /** Constructor : define all the tilings */
    public ElementaryMuscleTiling(){
	double bl[]={0,0,0}; 
	double bu[]={1,1,2.0*Math.PI}; 
	double sh[]=new double[3];
	boolean pvalid[]={true,true,true};
	int nbdiv[]={15,15,15}; 
	for(int i=0;i<3;i++){
	    arrayTilings[i]=new SetOfTilings();
	    for(int j=0;j<10;j++){	
		for(int k=0;k<3;k++) sh[k]=0.2*generateur.nextDouble(); 
		this.addTiling(new HyperRectangularSparseTiling(bl,bu,sh,nbdiv,pvalid),i); 
	    }
	}

    }//Constructor

public ListOfTiles getTiles(IState s,IAction a){
	MuscleAction ac=(MuscleAction)a;
	Contraction ec=(Contraction)s;
	double x[]=new double[3]; 
        x[0]=(ec.getLevel()+0.0)/ec.getMaxLevel(); 
	x[1]=ec.getDdist(); 
	x[2]=ec.getDdir(); 
	int index=ac.getValue()+1; 
	Iterator c=arrayTilings[index].iterator(); 
	ListOfTiles resu=new ListOfTiles(); 
	while(c.hasNext()){
	    HyperRectangularSparseTiling t=(HyperRectangularSparseTiling)c.next(); 
	    resu.add(t.getTile(x)); 
	}
	return resu; 
    }

}




