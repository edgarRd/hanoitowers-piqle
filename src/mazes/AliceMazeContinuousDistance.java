package mazes; 
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
 *    AliceMazeContinuousDistance.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */






import environment.*;
 
 
/** A rectangular maze, with walls (and no treasure !) to simulate Alice Robot's wandering. See <a href="http://www.epfl.ch/"> this site</a> for details about Alice...*

In this version,the robot can see at which distance the first wall is in any direction.

@author Francesco De Comite
 @version $Revision: 1.0 $ 



 */


public class AliceMazeContinuousDistance extends AliceMazeDistance{

 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public AliceMazeContinuousDistance(int lo,int la){
	super(lo,la); 
    }
/** Set the initial state to any free position of the maze. */
    public void randomInitialState(){
	int i=generateur.nextInt(longueur); 
	int j=generateur.nextInt(largeur);
	while(!forme[i][j].isFree()){
	    i=generateur.nextInt(longueur); 
	    j=generateur.nextInt(largeur);
	}
	defaultCurrentState=new AliceContinuousDistanceState(i,j,generateur.nextInt(4),this); 
	oldState=defaultCurrentState; 
    }


/** Build a maze according to a two-dimensional array description */
    public void setDesign(int [][] coding){
	boolean found=false; 
	for(int i=0;i<largeur;i++)
	    for(int j=0;j<longueur;j++){
		forme[i][j]=composantLabyrinthe.makeComponent(coding[i][j]); 
		if(coding[i][j]==2){
		    this.xtre=i; 
		    this.ytre=j;
		}
	    }
	for(int i=0;i<largeur;i++)
	    for(int j=0;j<longueur;j++)
		if((!found)&&(forme[i][j].isFree()))
		    {
			//System.out.println(i+" "+j); 
			defaultCurrentState=new AliceContinuousDistanceState(i,j,0,this); 
			oldState=defaultCurrentState; 
			found=true; 
		    }
    }
    public IState successorState(IState s,IAction a){
	AliceContinuousDistanceState el=(AliceContinuousDistanceState) s;
	ActionAlice al=(ActionAlice)a;
	oldState=el; 
	if(al.equals(ActionAlice.FORWARD))
	    switch(el.getOrientation()){
	    case 0 : return new AliceContinuousDistanceState(el.getX()-1,el.getY(),el.getOrientation(),this); 
	    case 1 : return new AliceContinuousDistanceState(el.getX(),el.getY()+1,el.getOrientation(),this); 
	    case 2 : return new AliceContinuousDistanceState(el.getX()+1,el.getY(),el.getOrientation(),this); 
	    case 3 : return new AliceContinuousDistanceState(el.getX(),el.getY()-1,el.getOrientation(),this); 
	    default : System.exit(-1); 
	    }
	if(al.equals(ActionAlice.BACKWARD))
	    switch(el.getOrientation()){
	    case 0 : return new AliceContinuousDistanceState(el.getX()+1,el.getY(),el.getOrientation(),this); 
	    case 1 : return new AliceContinuousDistanceState(el.getX(),el.getY()-1,el.getOrientation(),this); 
	    case 2 : return new AliceContinuousDistanceState(el.getX()-1,el.getY(),el.getOrientation(),this); 
	    case 3 : return new AliceContinuousDistanceState(el.getX(),el.getY()+1,el.getOrientation(),this); 
	    default : System.exit(-1); 
	    }
	if(al.equals(ActionAlice.RIGHT))
	    return new AliceContinuousDistanceState(el.getX(),el.getY(),(el.getOrientation()+1)%4,this); 
	if(al.equals(ActionAlice.LEFT))
	    return new AliceContinuousDistanceState(el.getX(),el.getY(),(el.getOrientation()+3)%4,this); 
	return null; 
    }// etatSuivant

    public ActionList getActionList(IState s){
	AliceContinuousDistanceState el=(AliceContinuousDistanceState) s;
	ActionList l=new ActionList(el);  
	l.add(ActionAlice.LEFT); 
	l.add(ActionAlice.RIGHT); 
	if(el.getWSM(0)!=1) l.add(ActionAlice.FORWARD); 
	if(el.getWSM(2)!=1) l.add(ActionAlice.BACKWARD); 
	return l; 
    }//getListeActions

}