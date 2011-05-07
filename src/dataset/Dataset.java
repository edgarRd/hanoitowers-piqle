package dataset; 
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
 *    Dataset.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */






import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/** <code>Datasets</code> are used by Neural Networks. They gathered together <code>Samples</code>, which are simply two vectors : one with the Neural Network input values, the other with the output values.<br>

@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 


*/

public class Dataset implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Samples are put in an ArrayList */
    private ArrayList<Sample> lesExemples=new ArrayList<Sample>(); 
    
    /** Add a Sample */
    public void add(Sample u){
	Sample v=copy(u); 
	lesExemples.add(v); 
    }

    /** Size of input vector.*/
    public int getInputSize(){
	return getSampleAt(0).getInputs().length; 
    }

    /** Size of output vector. */
    public int getOutputSize(){
	return getSampleAt(0).getOutputs().length; 
    }

    /** Enables the enumeration of all elements. */
    public Iterator iterator(){
	return lesExemples.iterator(); 
    }

    /** Number of collected samples.*/
    public int numInstances(){
	return lesExemples.size(); 
    }

    /** Add a sample at a certain rank.*/
    public void add(int index,Sample u){
	Sample v=copy(u); 
	lesExemples.add(index,v); 
    }
    
    /** Remove sample at index i */
    public void remove(int index){
	lesExemples.remove(index); 
    }

    /** Read sample at rank i */
    public Sample getSampleAt(int i){
	return (Sample)lesExemples.get(i); 
    }

    /** Copy a sample before to put it into the ArrayList. */
    private static Sample copy(Sample u){
	double v1[]=new double[u.getInputs().length]; 
	double v2[]=new double[u.getOutputs().length];
	System.arraycopy(u.getInputs(),0,v1,0,v1.length); 
	System.arraycopy(u.getOutputs(),0,v2,0,v2.length); 
	return new Sample(v1,v2); 
    }

    public String toString(){
	String s=""; 
	Iterator enumerer=lesExemples.iterator(); 
	while(enumerer.hasNext()){
	    Sample u=(Sample)enumerer.next(); 
	    s+=u+"\n"; 
	}
	return s; 
    }
}
