package maabac; 

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

import java.awt.Dimension;
import javax.swing.JFrame; 
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent; 

import javax.swing.BoxLayout;

import environment.AbstractComposedAction;
import environment.IState; 
import environment.IAction; 


/** A N-segments Arm */

public class ArmN  extends AbstractMember{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Arm angles */
    protected double angles[]; 
    private double maxDist; 

    public ArmN(int segments){
	this.taille=2*segments; 
	this.angles=new double[segments];
	this.xTarget=Math.sqrt(2.0); 
	this.yTarget=Math.sqrt(2.0); 
	this.defaultInitialState=new MaabacState(this,taille); 
	for(int i=0;i<taille;i++)
	    this.defaultInitialState.init(i,false); 
	int c[]=new int[taille]; 
	int m[]=new int[taille]; 
	for(int i=0;i<taille;i++){
	    c[i]=this.defaultInitialState.getValue(i); 
	    m[i]=this.defaultInitialState.getMaxValue(i);
	}
	this.x=0;
	this.y=0;
	for(int i=0;i<segments;i++){
	    this.angles[i]=Math.PI*(1+((c[2*i]/(m[2*i]+0.0))-(c[2*i+1]/(m[2*i+1]+0.0))))/2; 
	    this.x+=Math.cos(angles[i]); 
	    this.y+=Math.sin(angles[i]); 
	}
	this.defaultInitialState.setDist(this.computeDist()); 
	this.defaultInitialState.setDir(this.computeDir()); 
	this.maxDist=2.0*segments*1.0; 
	this.defaultInitialState.setDdist(this.computeDdist()/this.maxDist); 
	this.defaultInitialState.setDdir(this.computeDdir()); 
    }

    public ArmN(int segments,double x,double y,double r){
	this(segments); 
	this.xTarget=x; 
	this.yTarget=y; 
	this.rTarget=r; 
	this.maxDist=2.0*segments*1.0; 
	this.defaultInitialState.setDdist(this.computeDdist()/this.maxDist); 
	this.defaultInitialState.setDdir(this.computeDdir()); 
	this.defaultInitialState.setDist(this.computeDist()); 
	this.defaultInitialState.setDir(this.computeDir()); 	
    }

  public void setState(int c[],int m[]){
	super.setState(c,m); 
	this.x=0;
	this.y=0;
	for(int i=0;i<taille/2;i++){
	    this.angles[i]=Math.PI*(1+((c[2*i]/(m[2*i]+0.0))-(c[2*i+1]/(m[2*i+1]+0.0))))/2; 
	    this.x+=Math.cos(angles[i]); 
	    this.y+=Math.sin(angles[i]); 
	}
    }

     public IState successorState(IState s,IAction a){
	MaabacState ms=(MaabacState)s; 
	MaabacState nouveau=(MaabacState)ms.copy(); 
	AbstractComposedAction ca=(AbstractComposedAction)a; 
	for(int i=0;i<this.taille;i++){
		// TODO replace getAction(i) with getAction(Iagent a) ????
	    nouveau.changeValue(i,(MuscleAction)(ca.getAction(i))); 
	}
	this.x=0;
	this.y=0;
	for(int i=0;i<taille/2;i++){
	    this.angles[i]=Math.PI*(1+((ms.getValue(2*i)/(ms.getMaxValue(2*i)+0.0))
				       -(ms.getValue(2*i+1)/(ms.getMaxValue(2*i+1)+0.0))))/2; 
	    this.x+=Math.cos(angles[i]); 
	    this.y+=Math.sin(angles[i]); 
	}
	nouveau.setDist(this.computeDist()); 
	nouveau.setDir(this.computeDir()); 
	this.maxDist=this.taille*1.0; 
	nouveau.setDdist(this.computeDdist()/this.maxDist); 
	nouveau.setDdir(this.computeDdir()); 
	return nouveau; 
    }// etatSuivant

 // Graphical Interface

    protected ArmNDesign designArea; 
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
	graf.setSize(new Dimension((taille+2)*50,(taille+2)*50)); 
	// TODO revoir le comportement du programme en cas de quittage
	graf.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
		    System.out.println("On quitte");
                    System.exit(0);
                }
            });             
	graf.getContentPane().setLayout(new BoxLayout(graf.getContentPane(),BoxLayout.Y_AXIS)); 
	designArea=new ArmNDesign((taille+2)*50,(taille+2)*50); 
	graf.getContentPane().add(designArea); 
	graf.setVisible(true); 
    }  
    public void sendState(IState e){
	if(!this.graphicsEnabled) return; 
	if(!this.isVisible) return; 
	this.designArea.plot(this.angles,xTarget,yTarget,rTarget);
	
    }
    public void clearGraphics(){this.designArea.reinit(); }
    
    public void closeGraphics(){
	if(!this.graphicsEnabled) return; 
	if(!this.isVisible) return; 
	this.isVisible=false; 
	graf.setVisible(false); 
    }
}
    