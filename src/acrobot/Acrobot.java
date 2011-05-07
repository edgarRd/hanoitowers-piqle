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
 *    Acrobot.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */


import java.awt.Dimension;
import javax.swing.JFrame; 
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent; 

import javax.swing.BoxLayout;

import environment.*;
import java.util.Random; 

/** See <a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node110.html"> Sutton and Barto page 270</a> for a description. All the settings are (quite) strictly following this definition.

 @author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 
*/

public class Acrobot extends AbstractEnvironmentSingle{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Random generateur=new Random(); 

 /** Diverse settings, to eventually be able to modify them ... */
    protected double maxSpeed1=4*Math.PI; 
    protected double maxSpeed2=9*Math.PI; 
    protected double m1=1; 
    protected double m2=1; 
    protected double l1=1; 
    protected double l2=1; 
    protected double l1Square=l1*l1; 
    protected double l2Square=l2*l2; 
    protected double lc1=0.5; 
    protected double lc2=0.5; 
    protected double lc1Square=lc1*lc1; 
    protected double lc2Square=lc2*lc2; 
    protected double I1=1; 
    protected double I2=1; 
    protected double g=9.8; 
    protected double delta_t=0.05; 

 /**  Gives the list of possible actions from a given state. */
    public ActionList getActionList(IState s){
	AcrobotState el=(AcrobotState) s;
	ActionList l=new ActionList(el);  
	l.add(ActionAcrobot.NEGATIVE); 
	l.add(ActionAcrobot.NHUL); 
	l.add(ActionAcrobot.POSITIVE); 
	return l; 
    }
    
    /**  Computes the next state, given a start state and an action. */
   public IState successorState(IState s,IAction a){
	AcrobotState ea=(AcrobotState)s; 
	double theta1=ea.getTheta1(); 
	double theta2=ea.getTheta2(); 
	double angularSpeed1=ea.getAngularSpeed1(); 
	double angularSpeed2=ea.getAngularSpeed2(); 
	ActionAcrobot aa=(ActionAcrobot)a; 
	double torque=(double)aa.getTorque(); 
	double d1; 
	double d2; 
	double phi2; 
	double phi1; 
	double accel2; 
	double accel1; 
	for(int i=0;i<4;i++){
	// beginning of loop  for CLSquare ???
	d1=m1*lc1Square+m2*(l1Square+lc2Square+2*l1*lc2*Math.cos(theta2))+I1+I2; 
	d2=m2*(lc2Square+l1*lc2*Math.cos(theta2))+I2; 
	phi2=m2*lc2*g*Math.cos(theta1+theta2-Math.PI/2); 
	phi1=-m2*l1*lc2*angularSpeed2*Math.sin(theta2)*(angularSpeed2+2*angularSpeed1)+(m1*lc1+m2*l1)*g*Math.cos(theta1-(Math.PI/2))+phi2; 
	accel2=(torque+phi1*(d2/d1)-m2*l1*lc2*angularSpeed1*angularSpeed1*Math.sin(theta2)-phi2); 
	accel2=accel2/(m2*lc2Square+I2-(d2*d2/d1)); 
	 accel1=-(d2*accel2+phi1)/d1; 
	 // for(int i=0;i<4;i++){ // Adam White's beginning of loop
	    angularSpeed1+=accel1*delta_t;
	    if(angularSpeed1<-maxSpeed1) angularSpeed1=-maxSpeed1; 
	    if(angularSpeed1>maxSpeed1) angularSpeed1=maxSpeed1; 
	    
	    theta1=theta1+angularSpeed1*delta_t; 
	    
	     angularSpeed2+=accel2*delta_t;
	    if(angularSpeed2<-maxSpeed2) angularSpeed2=-maxSpeed2; 
	    if(angularSpeed2>maxSpeed2) angularSpeed2=maxSpeed2; 
	    
	    theta2=theta2+angularSpeed2*delta_t; 
	}//for
	while(theta1<-Math.PI) theta1+=2*Math.PI; 
	while(theta1>Math.PI) theta1-=2*Math.PI; 
	while(theta2<-Math.PI) theta2+=2*Math.PI; 
	while(theta2>Math.PI) theta2-=2*Math.PI; 

	return new AcrobotState(theta1,theta2,angularSpeed1,angularSpeed2,this); 
    }//etatSuivant

    public IState defaultInitialState(){
	return new AcrobotState(0,0,
			       0,0,
			       //   2*maxSpeed1*generateur.nextDouble()-maxSpeed1,
			       //2*maxSpeed2*generateur.nextDouble()-maxSpeed2,
			       this); 
    }
    public IState randomState(){
	return new AcrobotState(4*Math.PI*generateur.nextDouble()-2*Math.PI,
			       4*Math.PI*generateur.nextDouble()-2*Math.PI,
			       //0,0,
			       2*maxSpeed1*generateur.nextDouble()-maxSpeed1,
			       2*maxSpeed2*generateur.nextDouble()-maxSpeed2,
			       this); 
			       
	//	return defaultInitialState(); 
}
    
    public double getReward(IState s1,IState s2,IAction a){
	if (isFinal(s2)) return 0; 
	return -1;
    }
    
    public boolean isFinal(IState s){
	AcrobotState ea=(AcrobotState)s; 
	double theta1=ea.getTheta1(); 
	double theta2=ea.getTheta2(); 
	double position=-(l1*Math.cos(theta1)+l2*Math.cos(theta2)); 
	return (position>1); 
    }

    public int whoWins(IState s){return -1;}
    
    // Graphical methods

    protected AcrobotDesign designArea; 
    protected boolean graphicsEnabled=false; 
    protected boolean isVisible=false; 
    
    public void setGraphics(){
	if(this.graphicsEnabled){
	    if(!this.isVisible) graf.setVisible(true);
	    return;
	}
	this.graphicsEnabled=true; 
	this.isVisible=true; 
	graf=new JFrame(); 
	graf.setSize(new Dimension(300,300)); 
	// TODO revoir le comportement du programme en cas de quittage
	graf.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
		    System.out.println("On quitte");
                    System.exit(0);
                }
            });             
	graf.getContentPane().setLayout(new BoxLayout(graf.getContentPane(),BoxLayout.Y_AXIS)); 
	designArea=new AcrobotDesign(300,300); 
	graf.getContentPane().add(designArea); 
	graf.setVisible(true); 
     }  
   public void sendState(IState e){
       if(!this.graphicsEnabled) return; 
       if(!this.isVisible) return; 
       AcrobotState ev=(AcrobotState)e; 
       this.designArea.plot(ev.getTheta1(),ev.getTheta2());

     }
    public void clearGraphics(){this.designArea.reinit(); }

    public void closeGraphics(){
	if(!this.graphicsEnabled) return; 
	if(!this.isVisible) return; 
	this.isVisible=false; 
	graf.setVisible(false); 
    }


    

}




















