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
 *    AcrobotCLS2.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */

import environment.*;

/** An attempt to implement the CLSquare version of the acrobot described in 
<a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node110.html"> Sutton and Barto page 270</a> 

The main modification is the rewriting of the <code>successorState</code> method...

@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 
*/



public class AcrobotCLS2 extends Acrobot{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double dt=this.delta_t/10.0; 

    private double[] rk4(double[] startx,double torque){
	double dx0[]; 
	double dx1[];
	double dx2[]; 
	double dx3[]; 
	double x1[]=new double[4]; 
	double x2[]=new double[4]; 
	double x3[]=new double[4]; 
	double resu[]=new double[4];

	dx0=f(startx,torque); 
	for(int i=0;i<4;i++) x1[i]=startx[i]+dx0[i]*dt/2; 
	//System.out.println("dx0:"+dx0[0]+" "+dx0[1]+" "+dx0[2]+" "+dx0[3]); 

	dx1=f(x1,torque); 
	for(int i=0;i<4;i++) x2[i]=startx[i]+dx1[i]*dt/2; 
	dx2=f(x2,torque); 
	for(int i=0;i<4;i++) x3[i]=startx[i]+dx2[i]*dt;

	dx3=f(x3,torque); 
	for(int i=0;i<4;i++)
	    resu[i]=startx[i]+dt/6.0*(dx0[i]+2*(dx1[i]+dx2[i])+dx3[i]); 

	
	//System.out.println(resu[0]+" "+resu[1]+" "+resu[2]+" "+resu[3]); 
	return resu; 

    }//rk4

    private double[] f(double[] x,double torque){
	double theta1=x[0]; 
	double theta2=x[2]; 
	double angularSpeed1=x[1]; 
	double angularSpeed2=x[3]; 
	double resu[]=new double[4];

	double d1; 
	double d2; 
	double phi2; 
	double phi1; 
	double accel2; 
	double accel1; 
	d1=m1*lc1Square+m2*(l1Square+lc2Square+2*l1*lc2*Math.cos(theta2))+I1+I2; 
	d2=m2*(lc2Square+l1*lc2*Math.cos(theta2))+I2; 
	phi2=m2*lc2*g*Math.cos(theta1+theta2-Math.PI/2); 
	phi1=-m2*l1*lc2*angularSpeed2*Math.sin(theta2)*(angularSpeed2+2*angularSpeed1)+(m1*lc1+m2*l1)*g*Math.cos(theta1-(Math.PI/2))+phi2; 
	accel2=(torque+phi1*(d2/d1)-m2*l1*lc2*angularSpeed1*angularSpeed1*Math.sin(theta2)-phi2); 
	accel2=accel2/(m2*lc2Square+I2-(d2*d2/d1)); 
	accel1=-(d2*accel2+phi1)/d1;

	resu[0]=angularSpeed1; 
	resu[2]=angularSpeed2;
	resu[1]=accel1; 
	resu[3]=accel2; 

	return resu; 
	

    }//f
   

    public IState successorState(IState s,IAction a){
	AcrobotState ea=(AcrobotState)s; 
	double theta1=ea.getTheta1(); 
	double theta2=ea.getTheta2(); 
	double angularSpeed1=ea.getAngularSpeed1(); 
	double angularSpeed2=ea.getAngularSpeed2(); 
	ActionAcrobot aa=(ActionAcrobot)a; 
	double torque=(double)aa.getTorque(); 

	if(angularSpeed1<-maxSpeed1) angularSpeed1=-maxSpeed1; 
	if(angularSpeed1>maxSpeed1) angularSpeed1=maxSpeed1; 
	if(angularSpeed2<-maxSpeed2) angularSpeed2=-maxSpeed2; 
	if(angularSpeed2>maxSpeed2) angularSpeed2=maxSpeed2; 

	double temp[]={theta1,angularSpeed1,theta2,angularSpeed2};
	for(int t=0;t<10;t++) temp=rk4(temp,torque); 
	
	while(temp[0]<-Math.PI) temp[0]+=2*Math.PI; 
	while(temp[0]>Math.PI) temp[0]-=2*Math.PI; 
	while(temp[2]<-Math.PI) temp[2]+=2*Math.PI; 
	while(temp[2]>Math.PI) temp[2]-=2*Math.PI; 

	return new AcrobotState(temp[0],temp[2],temp[1],temp[3],this);  
 }

}