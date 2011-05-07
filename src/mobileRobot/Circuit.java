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
 *    Circuit.java
 *    Copyright (C) 2005,USTL  Francesco De Comite
 *
 */

import environment.*;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import javax.swing.JFrame; 
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent; 


/** A circuit is a portion of plane between two closed curves : the agent 
 * (a racing car, or a mobile robot ...) has to drive as long as possible
 * without hiting the walls, while running as far as possible.
 * By modifying the reward function, one could change the final robot's goal.

@author Francesco De Comite 
 @version $Revision: 1.0 $ 
*/

abstract public class Circuit extends AbstractEnvironmentSingle{
    protected Polyline inside,outside; 
    protected MobileState preceedingState=null; 

    /** An upper bound of the distance between the car and the external wall */
    protected double maxSize(){return this.outside.maxSize();}


	/**  Gives the list of possible actions from a given state. */
    public ActionList getActionList(IState s){
	ActionList l=new ActionList(s);
	MobileState ev=(MobileState)s; 
	int speed=ev.getSpeed(); 
	double angle=ev.getAngle(); 
	// TODO parametrize the loop boundaries with the number
	// of possible steering wheel positions (and not -3,4)
	for(int i=-3; i<4;i++){
	    if(speed>0) l.add(new MobileAction(-1,angle,i)); // slow down
	    if(speed<MobileState.maxSpeed) l.add(new MobileAction(1,angle,i));  // speed up
	    if(speed!=0) l.add(new MobileAction(0,angle,i)); // constant speed
	}
	return l; 
    }// getListeActions

	/**  Computes the next state, given a start state and an action. */
    public IState successorState(IState s,IAction a){
	MobileState ev=(MobileState)s; 
	MobileAction av=(MobileAction)a; 
	//	System.out.println(ev.getX()+" "+ev.getY()+" "+ev.getAngle()+" "+ev.getDistance(0)+" "+ev.getDistance(1)+" "+ev.getDistance(2)); 
	preceedingState=(MobileState)ev.copy(); 	
	MobileState newEV=new MobileState(ev,av); 
	return newEV; 
    }//etatSuivant


    /** Must be define in each instantiation */
    abstract public IState defaultInitialState(); 

    
    /** The number of pixels of the linear retina */
    protected int retinaSize=3;

    public int getRetinaSize(){return this.retinaSize;}
    public void setRetinaSize(int i){this.retinaSize=i;}

    /** Angle increment */
    protected double angle=10.0; 
    public void setAngle(double v){this.angle=v;}
    public double getAngle(){return this.angle;}

/** Compute the view of a state (how far are we from the walls ?) */
    protected double[] getView(MobileState ev){
	double v[]=new double[retinaSize]; 
	for(int i=0;i<retinaSize; i++)
	    v[i]=getViewInOneDir(ev,(-angle*((retinaSize-1.0)/2.0))+angle*i);
	return v; 
    }

    /** How far are we from a wall in a given direction ?*/
    abstract protected double getViewInOneDir(MobileState ev,double smallDev);
	
    public double getReward(IState s1,IState s2,IAction a){
	MobileState vs=(MobileState)s2;	
	if(inside.inside(vs)||outside.outside(vs))  return -1000; 
	if(vs.getSpeed()==0) return -10; 
	return Math.pow(vs.getSpeed(),1.123); 
    }//getReward


    public boolean isFinal(IState s){
	MobileState vs=(MobileState)s; 
	if (inside.inside(vs)) {return true;}
	if (outside.outside(vs)) {return true;}
	return false;
    }// isFinal
	

	/** Who won ?  
	   
	    <li> Single-player game : </li>
	    <ul>
	    <li> Win : -1  : pass the line in the right direction, after having completed the turn</li>
	    <li> Null: 0    </li>
	    <li> Loose: 1  : out of track</li>
	    </ul>
	    </ul> */
    public int whoWins(IState s){
	MobileState vs=(MobileState)s; 
	if (inside.inside(vs)) return 1; 
	if (outside.outside(vs)) return 1; 
	return -1; 
    }// whoWins

    
    /* Graphical methods */

    protected VisualizeArena designArea; 

     public void setGraphics(){
	graf=new JFrame(); 
	graf.setSize(new Dimension(400,400)); 
	// TODO revoir le comportement du programme en cas de quittage
	graf.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
		    System.out.println("On quitte");
                    System.exit(0);
                }
            });             
	graf.getContentPane().setLayout(new BoxLayout(graf.getContentPane(),BoxLayout.Y_AXIS)); 
	designArea=new VisualizeArena(this.inside,this.outside); 
	graf.getContentPane().add(designArea); 
	graf.setVisible(true); 

     }  

     public void sendState(IState e){
	 MobileState ev=(MobileState)e; 
	 this.designArea.addPoint((int)(ev.getX()+this.outside.getXTrans()),(int)(ev.getY()+this.outside.getYTrans()));

     }
    public void clearGraphics(){this.designArea.reinit(); }
	    
}
