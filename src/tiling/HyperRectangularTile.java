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
 *    alo0ng with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    HyperRectangularTile.java
 *    Copyright (C) 2005 Francesco De Comite
 */

/** A tile which is a rectangle/cube in N dimensions */

public class HyperRectangularTile extends Tile{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static private int nbtiles=0; 
    /** Coordinates of the tile into the tiling*/
    protected int x[]; 
    
    public HyperRectangularTile(Tiling p,int tx[]){
	super(p); 
	//	if(nbtiles%100==0) System.err.println("-->"+nbtiles);
	nbtiles++;
	this.x=new int[tx.length]; 
	System.arraycopy(tx,0,this.x,0,tx.length); 
    }

    /** The ith value of coordinate vector */
    protected int getTileVal(int i){return this.x[i];}

    public String toString(){
	String s="Pavage "+this.pavage+" "; 
	for(int i=0;i<this.x.length;i++) s+=this.x[i]+" "; 
	return s; 
    }
}