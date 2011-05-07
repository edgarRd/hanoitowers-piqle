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
 *    MountainCar.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */
 
 
import java.awt.Dimension;
import javax.swing.JFrame; 
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent; 

import javax.swing.BoxLayout;
 
 
import java.util.Random; 
import environment.*; 

/**
   A car has to climb up a mountain, and to succeed, it needs to drive a little bit backward, in order to have enough speed to win the slope.
   The problem is described in <a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node89.html">Sutton & Barto, page 214</a>
@author Francesco De Comite
@version $Revision: 1.0 $ 

*/



public class MountainCar extends AbstractEnvironmentSingle{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Random generateur=new Random(); 

    /** Bounds for position*/
    protected double bpleft=-1.5; 
    protected double bpright=0.45; 
    /** Bounds for speed */
    protected double bsleft=-0.07; 
    protected double bsright=0.07; 

    protected MountainCarState defaultInitialState; 

    public MountainCar(){
	defaultInitialState=new MountainCarState(-0.5,0.0,this); 
    } 

    public IState defaultInitialState(){
	return new MountainCarState(-0.5,0.0,this); 
    }

    public MountainCarState randomState(){
	double pos=1.7*generateur.nextDouble()-1.2; 
	double speed=0.14*generateur.nextDouble()-0.07; 
	return new MountainCarState(pos,speed,this); 
    }

    public ActionList getActionList(IState s){
	ActionList loa=new ActionList(s); 
	loa.add(ActionMountainCar.FORWARD); 
	loa.add(ActionMountainCar.BACKWARD); 
	loa.add(ActionMountainCar.STILL); 
	return loa; 
    } 

     public IState successorState(IState s,IAction a){
	 MountainCarState em=(MountainCarState)s; 
	 ActionMountainCar am=(ActionMountainCar)a; 
	 double speedt1= em.getSpeed()+(0.001*am.getType())+(-0.0025*Math.cos(3.0*em.getPosition())); 
	 if(speedt1<bsleft) speedt1=bsleft; 
	 if(speedt1>bsright) speedt1=bsright; 
	 double post1=em.getPosition()+speedt1; 
	 if(post1<=bpleft) {post1=bpleft; speedt1=0.0;}
	 if(post1>=bpright){post1=bpright;speedt1=0.0;} 
	 return new MountainCarState(post1,speedt1,this); 
	 
     }

    /** 0 in case of success, -1 for all other moves */
    public double getReward(IState s1,IState s2,IAction a){
	if(!s2.isFinal()) return -1; 
	return 0; 
    }

    public boolean isFinal(IState s){
	MountainCarState em=(MountainCarState)s; 
	return (Math.abs(em.getPosition()-bpright)<1e-8); 
	//return (em.getPosition()>=0.45); 
    }

    public int whoWins(IState s){
	if(isFinal(s)) return -1; 
	else return 0; 
    }// whoWins
    
    
    
     // Graphical methods

    protected MountainCarDesign designArea; 
    
    public void setGraphics(){
	graf=new JFrame(); 
	graf.setSize(new Dimension(500/2,500/2)); 
	// TODO revoir le comportement du programme en cas de quittage
	graf.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
		    System.out.println("On quitte");
                    System.exit(0);
                }
            });             
	graf.getContentPane().setLayout(new BoxLayout(graf.getContentPane(),BoxLayout.Y_AXIS)); 
	designArea=new MountainCarDesign(500/2,500/2); 
	graf.getContentPane().add(designArea); 
	graf.setVisible(true); 
     }  
   public void sendState(IState e){
	 MountainCarState ev=(MountainCarState)e; 
	 this.designArea.plot(ev.getPosition(),ev.getSpeed());

     }
    public void clearGraphics(){this.designArea.reinit(); }
    
    
}
