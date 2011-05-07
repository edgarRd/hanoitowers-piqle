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
 *    HyperRectangularSparseTiling.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */


import java.util.Iterator;

/** An extension of HyperRectangularTiling where only certain dimensions are used. The class memorizes which dimensions must be taken into account. */

public class HyperRectangularSparseTiling extends HyperRectangularTiling{
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static private int totTiles=0; 
    static private int nbTilings=0; 

    /** The boolean vector indicating which dimensions are to be considered */
    protected boolean valid[]; 

    /** Defines a new tiling.
	@param pblow lower bounds
	@param pbup upper bounds
	@param pshift shift 
	@param pnbDivisions number of division along each axis
	@param pvalid true : this dimension is valid -- false : don't use this dimension
    */
    public HyperRectangularSparseTiling(double pblow[],double pbup[],double pshift[],int pnbDivisions[],boolean pvalid[]){
	super(pblow,pbup,pshift,pnbDivisions); 
	this.valid=new boolean[this.dimension]; 
	System.arraycopy(pvalid,0,this.valid,0,dimension); 
	int nbtiles=1; 
	for(int i=0;i<this.dimension;i++) 
	    if(this.valid[i]) {
		nbtiles*=this.nbDivisions[i];
	    }
		
	totTiles+=nbtiles; 
	System.err.println("nb tiles :"+nbtiles+ " total "+totTiles+" "+(++nbTilings)); 
    }


    public Tile computeTile(double coord[]){
	int tileNumber[]=new int[this.dimension]; 
	for(int i=0;i<dimension;i++){
	    if (this.valid[i]){
	    double vmax=this.origin[i]+this.width[i]; 
	    while(coord[i]>vmax){tileNumber[i]++;vmax+=this.width[i];}
	    }
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
		if((this.valid[i])&&(tx[i]!=t.getTileVal(i))){ok=false;break;}
	    }
	    if (ok) return t;
	} 
	return null; 
    }
}
