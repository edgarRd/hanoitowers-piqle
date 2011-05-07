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
 *    RectangularTile.java
 *    Copyright (C) 2005 Francesco De Comite
 */

public class RectangularTile extends Tile{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Coordinates of the tile into the tiling*/
    private int x,y; 
    
    public RectangularTile(Tiling p,int tx,int ty){
	super(p); 
	this.x=tx; 
	this.y=ty; 
    }

    protected int getTileX(){return this.x;}
    protected int getTileY(){return this.y;}

    public String toString(){
	return "Pavage "+this.pavage+" X : "+this.x+" Y : "+this.y+" theta "+this.theta; 
    }
}