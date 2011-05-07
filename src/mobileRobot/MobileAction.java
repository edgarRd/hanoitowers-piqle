package mobileRobot; 
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
 *    ActionVoiture.java
 *    Copyright (C) 2005,USTL  Francesco De Comite
 *
 */

import environment.*; 
/** A car action consists of two possibilities : 
<ul>
<li> Changing speed </li>
<li> Changing direction</li>
</ul>

Hence an action consists of a new speed and a new angle, relatively to the X-axis.

@author Francesco De Comite
 @version $Revision: 1.0 $ 
*/

public class MobileAction implements  IAction{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int speed; 
    private int volant; 
    private double angle; 

    public MobileAction(int s,double a,int v){
	this.speed=s;
	this.volant=v; 
	this.angle=a+10.0*v; 
	while(this.angle>360) this.angle-=360; 
	while (this.angle<0) angle+=360;
    }

    public int getSpeed(){return this.speed;}
    public double getAngle(){return this.angle;}
    public int getVolant(){return this.volant;}; 

    public String toString(){
	return transfo(this.volant)+" "+texte(this.speed); 
    }
    
    public int hashCode(){
	return 3*volant+5*speed; 
    }// hashCode

    public boolean equals(Object o){
	if(!(o instanceof MobileAction)) return false; 
	MobileAction av=(MobileAction)o; 
	if (this.speed!=av.speed) return false; 
	if(this.volant!=av.volant) return false; 
	return true; 
    }//equals

    public int nnCodingSize(){return 2;}

    public double[] nnCoding(){
	double[] code=new double[2]; 
	code[0]=(double)this.volant; 
	code[1]=this.speed; 
	return code;
    }
    
  
    private static String transfo(int vol){
	switch(vol){
	case -3: return "All left"; 
	case -2 : return "left median"; 
	case -1 : return "slightly left"; 
	case 0 : return "forward"; 
	case 1 : return "slightly right"; 
	case 2 : return "median right"; 
	case 3 : return "All right"; 
	default : return "unknown"; 
	}
    }


    private static String texte(int sp){
	if(sp==-1) return "Brake"; 
	if (sp==1) return "Accelerate";
	return "Keep speed"; 

    }

 //TODO this is the only place where we need angle to be a field...
    public IAction copy(){
	return new MobileAction(this.speed,this.angle,this.volant); 
    }


}//MobileAction