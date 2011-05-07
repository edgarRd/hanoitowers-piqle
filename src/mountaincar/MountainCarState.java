package mountaincar; 

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
 *    MountainCarState.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */


import environment.*; 

/** A State is composed of a position and a speed. Both are bounded. Continuous values are kept for neural network learning, but discretized for hashCode and equals.

 @author Francesco De Comite 
 @version $Revision: 1.0 $ 

    
*/
 
public class MountainCarState extends AbstractState{

    private double position; 
    private double speed; 

    public MountainCarState(double p, double s,AbstractEnvironmentSingle ct){
	super(ct); 
	this.position=p; 
	this.speed=s;
    }

    public MountainCarState(IEnvironment ct){
	super(ct); 
    }
    
    

    public double getPosition(){
	return this.position;
    }
    
    public double getSpeed(){
	return this.speed;
    }

    public IState copy(){
	MountainCarState em=new MountainCarState(this.myEnvironment); 
	em.position=this.position; 
	em.speed=this.speed; 
	return em; 
    }


    public double[] nnCoding(){
	double code[]=new double[2]; 
	code[0]=(this.position+1.2)/1.7; 
	code[1]=(this.speed+0.07)/0.14; 
	return code; 
    }

    public int nnCodingSize(){
	return 2;
    }

   

    /** Need to discretize states.*/
    public int hashCode(){
	return ((7*((int)(100*this.position)+12)) +(5*((int)(100*this.speed)+7)))%23; 
    }

    /** Need to discretize equality */
    public boolean equals(Object o){
       
	if(!(o instanceof MountainCarState)) return false; 
	MountainCarState ej=(MountainCarState)o;
	double difpos=Math.abs(this.position-ej.position); 
	double difspeed=Math.abs(this.speed-ej.speed); 
	if(difpos>0.01) return false; 
	if(difspeed>0.01)return false; 
	return true;
    }

    public String toString(){
	return this.position+" "+this.speed; 
    }


}
