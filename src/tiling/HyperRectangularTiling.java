package tiling; 
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
 *    HyperRectangularTiling.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */


import java.util.ArrayList;
import java.util.Iterator;

import environment.IAction;
import environment.IState;

/** A 'standard' hyper-rectangular partition of space, covering all the space for p dimensions, with n_1*n_2...*n_p squares, overlapping the complete space, and shifted by the amount of less than one square in each dimension.


*/

public class HyperRectangularTiling extends Tiling{
   
    /** Memorize already built tiles */
    // protected ArrayList dejaVu; 

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Number of dinmensions */
    protected int dimension;
    /** Upper and lower bounds in each dimension*/
    protected double blow[],bup[]; 
   
    /** Width of a tile in each dimension*/
    protected double width[]; 
   
    /** Shift of the tiling (less than one square) */
    protected double shift[];
    
    /** Origin of the tiling (after adding one tile, and shift the entire tiling) */
    protected double origin[];

    /** Number of tiles on each side */
    protected int nbDivisions[];

    /** Defines a new tiling.
	@param pblow lower bounds
	@param pbup upper bounds
	@param pshift shift 
	@param pnbDivisions number of division along each axis
    */
    public HyperRectangularTiling(double pblow[],double pbup[],double pshift[],int pnbDivisions[]){
	this.dimension=pblow.length; 
	this.blow=new double[dimension]; 
	this.bup=new double[dimension]; 
	this.shift=new double[dimension]; 
	this.nbDivisions=new int[dimension]; 
	System.arraycopy(pblow,0,this.blow,0,dimension);
	System.arraycopy(pbup,0,this.bup,0,dimension);
	System.arraycopy(pshift,0,this.shift,0,dimension);
	System.arraycopy(pnbDivisions,0,this.nbDivisions,0,dimension);
	for(int i=0;i<dimension;i++) this.nbDivisions[i]++; 
	this.width=new double[dimension];
	for(int i=0;i<dimension;i++)
	    this.width[i]=(this.bup[i]-this.blow[i])/(this.nbDivisions[i]-1); 
	this.origin=new double[dimension]; 
	for(int i=0;i<dimension;i++)
	    this.origin[i]=this.blow[i]-this.width[i]+(this.shift[i]*this.width[i]); 
	this.dejaVu=new ArrayList<Tile>(); 
    }//Constructor

    public Tile computeTile(double coord[]){
	int tileNumber[]=new int[this.dimension]; 
	for(int i=0;i<dimension;i++){
	    double vmax=this.origin[i]+this.width[i]; 
	    while(coord[i]>vmax){tileNumber[i]++;vmax+=this.width[i];}
	}
	HyperRectangularTile t=internalEquals(tileNumber); 
	if(t==null){
	    t=new HyperRectangularTile(this,tileNumber); 
	    dejaVu.add(t); 
	}
	return t; 
    }//computeTile

    protected HyperRectangularTile internalEquals(int tx[]){
	Iterator c=this.dejaVu.iterator(); 
	while(c.hasNext()){
	    HyperRectangularTile t=(HyperRectangularTile)c.next(); 
	    boolean ok=true; 
	    for(int i=0;i<this.dimension;i++){
		if(tx[i]!=t.getTileVal(i)){ok=false;break;}
	    }
	    if (ok) return t;
	} 
	return null; 
    }

    /** We don't use this way of acquiring a tile in rectangular tiling  .... */
    protected Tile computeTile(IState s,IAction a){return null;}

    /** Useless wrapping */
    public Tile getTile(double x[]){
	return computeTile(x); 
    }

}

   

