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
 *    EligibleTiles.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */

/** Maintains the list of all tiles encountered during an episode. provides methods to modify eligibility of those tiles. 
 */

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class EligibleTiles implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Tiles are defined by their genuine hashcode and equals methods.*/
    private HashMap<Tile,Double> store=new HashMap<Tile,Double>(); 

    /** Add a tile to the list of eligible tiles, if it is not already present.In any case, increments it eligibility by 1 */

    public void put(Tile t){
	if(store.containsKey(t))
	    store.put(t,1+this.get(t)); 
	else 
	    store.put(t,new Double(1.0)); 
    }

    /** Return the eligibility of a Tile, zero if not present (may not occur) */
    public double get(Tile t){
	if(!store.containsKey(t)) return 0; 
	return ((Double)store.get(t)).doubleValue(); 
    }
    
    /** Erase eligibility traces */
    public void eraseAll(){ store=new HashMap<Tile,Double>(); }

    /** Add one to any eligibility value */
    public void incrementAll(){
	Set keys=store.keySet(); 
	Iterator c=keys.iterator(); 
	while(c.hasNext()){
	    Tile t=(Tile)c.next(); 
	    double u=this.get(t); 
	    store.put(t,new Double(u+1)); 
	}
    }

    /** Multiply each eligibility by a common factor */
    public void multiplyAll(double factor){
	boolean bool=false; 
	Set keys=store.keySet(); 
	Iterator c=keys.iterator(); 
	while(c.hasNext()){
	    Tile t=(Tile)c.next(); 
	    double u=this.get(t); 
	    if(u*factor>10000000) {System.out.println("************* "+u+" "+factor);bool=true;}
	    store.put(t,new Double(u*factor)); 
	}
	if(bool) System.exit(0); 

    }

    /** Modify theta for each eligible tile */
    public void modifyTheta(double factor){
	//	System.out.println("facteur :"+factor); 
	Set keys=store.keySet(); 
	Iterator c=keys.iterator(); 
	while(c.hasNext()){
	    Tile t=(Tile)c.next(); 
	    double u=t.getTheta();
	    double e=this.get(t); 
	    t.setTheta(u+factor*e); 
	}
    }// modifyTheta

    
}
	
	