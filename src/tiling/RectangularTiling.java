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
 *    RectangularTiling.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */


import java.util.ArrayList;
import java.util.Iterator;

import environment.IAction;
import environment.IState;

/** A 'standard' rectangular partition of space, covering all the space for two dimensions, with n*m squares, overlapping the complete space, and shifted by the amount of less than one square 


*/

public class RectangularTiling extends Tiling{

    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Upper and lower bound of X interval */
    private double bxlow,bxup; 
    /** Upper and lower bound of Y interval */
    private double bylow,byup;
    
    /** X-width of a tile */
    private double xWidth; 
    /**Y-Width of a tile */
    private double yWidth; 

    /** Shift of the tiling (less than one square) */
    private double shiftx,shifty; 
    
    /** Origin of the tiling (after adding one tile, and shift the entire tiling) */
    private double xOrigin, yOrigin; 

    /** Number of tiles on each side */
    private int nbDivisionsX,nbDivisionsY; 

    /** Defines a new tiling.
	@param bix lower bound of x axis
	@param bsx upper bound of x axis
	@param biy lower bound of y axis
	@param bsy upper bound of y axis
	@param sx shift along x axis
	@param sy shift along y axis
	@param nbsqX number of division along X axis
	@param nbsqY number of division along Y axis
    */
    public RectangularTiling(double bix,double bsx,double biy,double bsy,double sx,double sy,int nbsqX,int nbsqY){
	this.bxlow=bix; 
	this.bxup=bsx; 
	this.bylow=biy; 
	this.byup=bsy; 
	this.shiftx=sx; 
	this.shifty=sy; 
	this.nbDivisionsX=nbsqX+1; 
	this.nbDivisionsY=nbsqY+1; 
	this.xWidth=(this.bxup-this.bxlow)/(this.nbDivisionsX-1); 
	this.yWidth=(this.byup-this.bylow)/(this.nbDivisionsY-1);
	this.xOrigin=this.bxlow-this.xWidth+(this.shiftx*this.xWidth); 
	this.yOrigin=this.bylow-this.yWidth+(this.shifty*this.yWidth); 
	this.dejaVu=new ArrayList<Tile>(); 
    }//Constructor

    public Tile computeTile(double x,double y){
	int tileX=0; 
	int tileY=0; 
	double xmax=this.xOrigin+this.xWidth; 
	while(x>xmax){tileX++; xmax+=xWidth;}
	double ymax=this.yOrigin+this.yWidth; 
	while(y>ymax){tileY++; ymax+=yWidth;}
	RectangularTile t=internalEquals(tileX,tileY); 
	if(t==null){
	    t=new RectangularTile(this,tileX,tileY); 
	    dejaVu.add(t); 
	}
	return t; 
    }//computeTile

    private RectangularTile internalEquals(int tx,int ty){
	Iterator c=this.dejaVu.iterator(); 
	while(c.hasNext()){
	    RectangularTile t=(RectangularTile)c.next(); 
	    if((tx==t.getTileX())&&(ty==t.getTileY())) return t; 
	}
	return null; 
    }

    /** We don't use this way of acquiring a tile in rectangular tiling  .... */
    protected Tile computeTile(IState s,IAction a){return null;}

    /** Useless wrapping */
    public Tile getTile(double x,double y){
	return computeTile(x,y); 
    }

   

}