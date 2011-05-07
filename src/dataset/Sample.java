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
 *    Sample.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */





import java.io.Serializable;

/** A sample is used by Neural Networks. <br>
    A sample is just composed of two vectors : 
    <ul>
    <li> The input vector</li>
    <li> The output vector</li>
    </ul>

@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 



*/
public class Sample implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Input vector */  
    private double input[]; 
    /** Output vector */
    private double output[]; 

    public Sample(){}; 

    /** Build a Sample from two arrays */
    public Sample(double i[],double o[]){
	input=new double[i.length]; 
	output=new double[o.length]; 
	System.arraycopy(i,0,input,0,i.length); 
	System.arraycopy(o,0,output,0,o.length); 
    }

    /** Read the input vector. */
    public double[] getInputs(){
	return input; 
    }

    /** Read the output vector.*/
    public double[] getOutputs(){
	return output;
    }

    /** Write the input vector. */
    public void setInputs(double i[]){input=i;}
    /** Write the output vector. */
    public void setOutputs(double o[]){output=o;}

    public String toString(){
	String s=""; 
	for(int i=0;i<input.length;i++) s+=" "+input[i]; 
	s+=" "; 
	for(int i=0;i<output.length;i++) s+=" "+output[i]; 
	return s; 

    }
}
