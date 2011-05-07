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


import environment.TileAbleEnvironment; 
import environment.IAction;
import environment.IState;

import java.util.Random; 


/** An attempt to implement the CLSquare version of the acrobot described in 
<a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node110.html"> Sutton and Barto page 270</a> 

with tiles

@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 
*/



public class AcrobotCLS2Tiling extends AcrobotCLS2 implements TileAbleEnvironment{

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
    public AcrobotCLS2Tiling(){
	double bi[]={0,0,-this.maxSpeed1,-this.maxSpeed2}; 
	double bs[]={2*Math.PI,2*Math.PI,this.maxSpeed1,this.maxSpeed2}; 
	double sh[]=new double[4]; 
	int nbdiv[]={15,15,15,15}; 
	boolean pvalid[]={true,true,true,true}; 
	/* For each possible action */
	for(int i=0;i<3;i++){
	    arrayTilings[i]=new SetOfTilings(); 
	    for(int l=0;l<4;l++) pvalid[l]=true; 
	    /* The twelve 4-dim tilings */
	    for(int j=0;j<12;j++){
		for(int k=0;k<4;k++) sh[k]=0.2*generateur.nextDouble(); 
		this.addTiling(new HyperRectangularSparseTiling(bi,bs,sh,nbdiv,pvalid),i); 
	    }// 12
	    //System.err.println("end 12 "); 
	    /* The four 3-dim tilings */
	    for(int dis=0;dis<4;dis++){
		/* Ignoring one of the dimensions */
		for(int p=0;p<4;p++){
		    if(p==dis) pvalid[p]=false; 
		    else pvalid[p]=true; 
		}
		/* Three shifts for each 3-dim rectangle */
		for(int j=0;j<3;j++){
		    for(int k=0;k<4;k++) sh[k]=0.2*generateur.nextDouble(); 
		    //System.err.println(pvalid[0]+" "+pvalid[1]+" "+pvalid[2]+" "+pvalid[3]); 
		    this.addTiling(new HyperRectangularSparseTiling(bi,bs,sh,nbdiv,pvalid),i); 
		    //    System.err.println("3d "); 
		}
	    }//dis
	    /* The 6 2-dim tilings */
	    for(int dis1=0;dis1<3;dis1++){
		for(int dis2=dis1+1;dis2<4;dis2++){
		    for(int p=0;p<4;p++){
			if((p==dis1)||(p==dis2)) pvalid[p]=false; 
			else pvalid[p]=true; 
		    }   
		    
		    
		    for(int j=0;j<2;j++){
			for(int k=0;k<4;k++) sh[k]=0.2*generateur.nextDouble(); 
			this.addTiling(new HyperRectangularSparseTiling(bi,bs,sh,nbdiv,pvalid),i); 
		    }
		} //dis2
	    }// dis1
	    /* the four 1-dim tiling */
	    for(int dis=0;dis<4;dis++){
		/** Ignoring 3 dimensions */
		for(int p=0;p<4;p++)
		    if(p==dis) pvalid[p]=true; 
		    else pvalid[p]=false; 
		
		for(int j=0;j<3;j++){
		    for(int k=0;k<4;k++) sh[k]=0.2*generateur.nextDouble(); 
		    this.addTiling(new HyperRectangularSparseTiling(bi,bs,sh,nbdiv,pvalid),i);
		}
	    }// dis
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