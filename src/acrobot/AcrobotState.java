package acrobot; 
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
 *    AcrobotState.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */

import environment.*; 

/** As in <a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node110.html"> Sutton and Barto</a>, Acrobot states are defined by two angles and two speeds. But tiling is not used, states are equals if they lie not to far from each other. See the code for method <code>equals</code>
 @author Francesco De Comite (decomite at lifl.fr)
  @version $Revision: 1.0 $ 
*/

public class AcrobotState extends AbstractState{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Angles and speed (see <a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node110.html"> Sutton and Barto</a>) */
    private double theta1,theta2,angularSpeed1,angularSpeed2;  

    public AcrobotState(double t1,double t2,double a1,double a2,IEnvironment ct){
	super(ct); 
	this.theta1=t1; 
	this.theta2=t2; 
	this.angularSpeed1=a1; 
	this.angularSpeed2=a2; 
    }
    
   
    public IState copy(){
	return new AcrobotState(this.theta1,this.theta2,this.angularSpeed1,
			this.angularSpeed2,myEnvironment); 
    }
  
    public int nnCodingSize(){return 4;}


    public double[] nnCoding(){
	double code[]=new double[4]; 
	code[0]=this.theta1/(2*Math.PI); 
	code[1]=this.theta2/(2*Math.PI); 
	code[2]=this.angularSpeed1/(2*Math.PI); 
	code[3]=this.angularSpeed2/(2*Math.PI); 
	return code; 
    }

    /** Q-Learning memorizing techniques use hashcoding : it is necessary to redefine it for each problem/game */
    public int hashCode(){
	int hc=0; 
	hc+=(int)Math.round(Math.abs(theta1)); 
	hc+=3*(int)Math.round(Math.abs(theta2)); 
	hc+=5*(int)Math.round(Math.abs(angularSpeed1)); 
	hc+=7*(int)Math.round(Math.abs(angularSpeed2)); 
	return hc%31; 
    }
    /** Q-Learning memorizing techniques use equality: it is necessary to redefine it for each problem/game */
    public boolean equalsXXX(Object o){
	if(!(o instanceof AcrobotState)) return false; 
	AcrobotState ev=(AcrobotState)o; 
	double dif1=Math.abs(this.theta1-ev.theta1); 
	if(dif1>0.8) return false; //0.5
	double dif2=Math.abs(this.theta2-ev.theta2); 
	if(dif2>0.8) return false; //0.5
	double dif3=Math.abs(this.angularSpeed1-ev.angularSpeed1); 
	if(dif3>0.5) return false;  //0.1
	double dif4=Math.abs(this.angularSpeed2-ev.angularSpeed2); 
	if(dif4>0.5) return false; //0.1

	return true;
    }

public boolean equals(Object o){
	if(!(o instanceof AcrobotState)) return false; 
	AcrobotState ev=(AcrobotState)o; 
	double dif1=Math.abs(this.theta1-ev.theta1)/(2*Math.PI); 
	if(dif1>0.1) return false; 
	double dif2=Math.abs(this.theta2-ev.theta2)/(2*Math.PI); 
	if(dif2>0.1) return false; 
	double dif3=Math.abs(this.angularSpeed1-ev.angularSpeed1)/(2*((Acrobot)myEnvironment).maxSpeed1); 
	if(dif3>0.1) return false;  
	double dif4=Math.abs(this.angularSpeed2-ev.angularSpeed2)/(2*((Acrobot)myEnvironment).maxSpeed2); 
	if(dif4>0.1) return false; 

	return true;
    }



    public String toString(){
	return theta1+" "+theta2+" "+angularSpeed1+" "+angularSpeed2; 
    }

    protected double getTheta1(){return this.theta1;}
    protected double getTheta2(){return this.theta2;}
    protected double getAngularSpeed1(){return this.angularSpeed1;}
    protected double getAngularSpeed2(){return this.angularSpeed2;}

   
}