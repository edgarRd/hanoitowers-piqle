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
 *    ListOfTiles.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


/** A ListOfTiles gathers all the tiles corresponding to a (state,action) couple */



public class ListOfTiles implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Tile> container=new ArrayList<Tile>(); 

    public Iterator iterator(){return this.container.iterator(); }

    public void add(Tile t){
	this.container.add(t); 
    }

    public double sumTheta(){
	Iterator c=this.container.iterator(); 
	double sum=0.0; 
	while(c.hasNext()){
	    Tile t=(Tile)c.next(); 
	    sum+=t.getTheta(); 
	}
	return sum; 
    }

    public String toString(){
	String s="------------------------------------\n"; 
	Iterator c=this.container.iterator(); 
	while(c.hasNext()){
	    Tile t=(Tile)c.next(); 
	    s+="\t"+t+"\n"; 
	}
	return s+"*****************************************"; 
    }
}

    
    