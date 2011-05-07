package acrobot; 


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
 *    AcrobotCLS2Tiling.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */
import java.util.Iterator; 
import tiling.ListOfTiles; 
import tiling.SetOfTilings; 
import tiling.HyperRectangularSparseTiling; 



import java.util.Random; 

import environment.*;

/** An attempt to implement the CLSquare version of the acrobot described in 
<a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node110.html"> Sutton and Barto page 270</a> 

with a more simple tile coding (n 4-dimensionnal tilings)

@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 
*/



public class AcrobotCLS2TilingSimple extends AcrobotCLS2 implements TileAbleEnvironment{

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Random generateur=new Random(); 
    /** One set of tilings for each possible action */
    protected SetOfTilings arrayTilings[]=new SetOfTilings[3]; 

   protected void addTiling(HyperRectangularSparseTiling t,int level){
	arrayTilings[level].addTiling(t); 
    }
    public AcrobotCLS2TilingSimple(){
	double bi[]={0,0,-this.maxSpeed1,-this.maxSpeed2}; 
	double bs[]={2*Math.PI,2*Math.PI,this.maxSpeed1,this.maxSpeed2}; 
	double sh[]=new double[4]; 
	int nbdiv[]={13,7,11,13}; 
	boolean pvalid[]={true,true,true,true}; 
	/* For each possible action */
	for(int i=0;i<3;i++){
	    arrayTilings[i]=new SetOfTilings(); 
	    for(int l=0;l<4;l++) pvalid[l]=true; 
	    /* The 48  4-dim tilings */
	    for(int j=0;j<48;j++){
		for(int k=0;k<4;k++) sh[k]=0.2*generateur.nextDouble(); 
		this.addTiling(new HyperRectangularSparseTiling(bi,bs,sh,nbdiv,pvalid),i); 
	    }// 48
	}// for each action
    }// constructor


    public ListOfTiles getTiles(IState s,IAction a){
	ActionAcrobot aa=(ActionAcrobot)a; 
	AcrobotState ea=(AcrobotState)s; 
	double x[]=new double[4]; 
	x[0]=ea.getTheta1(); 
	x[1]=ea.getTheta2(); 
	x[2]=ea.getAngularSpeed1(); 
	x[3]=ea.getAngularSpeed2(); 
	int index=aa.getTorque()+1; 
	Iterator c=arrayTilings[index].iterator(); 
	ListOfTiles resu=new ListOfTiles(); 
	while(c.hasNext()){
	    HyperRectangularSparseTiling t=(HyperRectangularSparseTiling)c.next(); 
	    resu.add(t.getTile(x)); 
	}
	return resu;   
    }// getTile

}