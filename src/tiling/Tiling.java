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
 *    Tiling.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */

import java.io.Serializable;
import java.util.ArrayList;

import environment.IAction;
import environment.IState;

/** A Tiling is a set of Tiles, with a method for finding a Tile associated with a (state,action) couple. Specific implementations must be done separately for each problem, provided this problem implements the TileAbleEnvironment interface.
 */

abstract public class Tiling implements Serializable{

    /** The list of all already known tiles (we compare tiles with the default equals method, so each tile must be created at most once) 
	TODO DEBUG : USELESS (never modified, overwritten by dejaVU in subclasses
*/
    // protected ArrayList alreadySeen=null; 

    protected ArrayList<Tile> dejaVu=null;

    protected ArrayList<Tile> getAlreadySeen(){return this.dejaVu;}

    /** Find the tile associated with (s,a) */
    abstract protected Tile computeTile(IState s,IAction a); 
    
    /** Return the tile associated with (s,a). In case this tile was not already seen, add it to the 
	list of already seen tiles.
TODO DEBUG : USELESS (overwritten in all sub classes)
    */
    public Tile getTile(IState s, IAction a){
	Tile t=computeTile(s,a); 
	if(!dejaVu.contains(t)) dejaVu.add(t); 
	return t; 
    }
}