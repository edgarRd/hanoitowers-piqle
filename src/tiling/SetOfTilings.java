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
 *    SetOfTilings.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import environment.IAction;
import environment.IState;

/** Gathers all the tilings associated with a problem. Provides methods to find tiles corresponding to a specifice (state,action) couple. */
public class SetOfTilings implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Tiling>tilings=new ArrayList<Tiling>(); 

    public void addTiling(Tiling t){
	this.tilings.add(t); 
    }

    public Iterator iterator(){
	return this.tilings.iterator(); 
    }

    public int size(){
	return this.tilings.size(); 
    }

    public ListOfTiles getTiles(IState s, IAction a){
	ListOfTiles result=new ListOfTiles(); 
	Iterator c=this.tilings.iterator(); 
	while(c.hasNext()){
	    Tiling t=(Tiling)c.next(); 
	    result.add(t.getTile(s,a)); 
	}
	return result; 
    }
}