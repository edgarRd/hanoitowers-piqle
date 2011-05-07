
/**
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

/*    ContractionV2.java
 *    22 nov. 06
 * 	  
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

package maabacVersion2;



import environment.AbstractState;
import environment.IEnvironment;
import environment.IState;

/**
 * A ContractionV2 is a local vision on the general state by a single muscle.
 * This will be used as the base state class in the extended state for a muscle
 * (field perception)
 * @author Francesco De Comite
 *
 * 22 nov. 06
 */
public class ContractionV2 extends AbstractState {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ct
	 */
	public ContractionV2(IEnvironment ct) {
		super(ct);
	}


	
	
	protected int value; 
    protected int maxValue; 
    protected int dist; 
    protected int dir; 
    /** The direction towards the target (radians) */
    protected double ddir; 
    /** The distance to the target (between 0 and 1) */
    protected double ddist; 

   

    public ContractionV2(IEnvironment ct,int val,int maxv,int d1,int d2,
		       double ddist,double ddir){
	super(ct); 
	this.value=val; 
	this.maxValue=maxv; 
	this.dist=d1; 
	this.dir=d2; 
	this.ddir=ddir; 
	this.ddist=ddist; 
    }

    public int getLevel(){return this.value;}
    public int getMaxLevel(){return this.maxValue;}
    public int getDir(){return this.dir;}
    public int getDist(){return this.dist;}
    public double getDdir(){return this.ddir;}
    public double getDdist(){return this.ddist;}

    public IState copy(){
	ContractionV2 c=new ContractionV2(myEnvironment,this.value,this.maxValue,
				      this.dist,this.dir,this.ddist,this.ddir); 
	return c; 
    }
    public boolean equals(Object o){
    	if(!(o instanceof ContractionV2)) return false; 
    	ContractionV2 ec=(ContractionV2)o;
    	if(this.value!=ec.value) return false; 
    	if(this.maxValue!=ec.maxValue) return false; 
    	// TODO Debug
    	if(this.dir!=ec.dir) return false; 
    	if(this.dist!=ec.dist) return false; 
    	return true; 
        }

        public int hashCode(){
    	return (this.value*7+5*this.maxValue)%23;
    	//return (this.value*7+5*this.maxValue+11*this.dist+13*this.dir)%23;
        }

        public String toString(){
    	return this.value+" "+this.dist+" "+this.dir; 
        }

        public int nnCodingSize(){return 5;}; 
        
        public double[] nnCoding(){
    	double code[]=new double[5]; 
    	code[0]=this.value/(this.maxValue+0.0); 
    	// a one among 4 coding might be more appropriate...
    	code[1]=this.dist/4.0; 
    	code[2]=this.dir/4.0; 
    	code[3]=this.ddist; 
    	code[4]=this.ddir/(2*Math.PI); 
    	return code;
        }
   
   
  
}
