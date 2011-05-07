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
 *    AliceMaze.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */






import environment.*;

 
/** A rectangular maze, with walls (and no treasure !) to simulate 
 * Alice Robot's wandering. See <a href="http://www.epfl.ch/"> this site</a> 
 * for details about Alice...*

@author Francesco De Comite 
 @version $Revision: 1.0 $ 



 */

public class AliceMaze extends Maze{

 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


public AliceMaze(int lo,int la){
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
	defaultCurrentState=new AliceState(i,j,generateur.nextInt(4),this); 
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
			defaultCurrentState=new AliceState(i,j,0,this); 
			oldState=defaultCurrentState; 
			found=true; 
		    }
    }
    public IState successorState(IState s,IAction a){
	AliceState el=(AliceState) s;
	ActionAlice al=(ActionAlice)a;
	oldState=el; 
	if(a.equals(ActionAlice.FORWARD))
	    switch(el.getOrientation()){
	    case 0 : return new AliceState(el.getX()-1,el.getY(),el.getOrientation(),this); 
	    case 1 : return new AliceState(el.getX(),el.getY()+1,el.getOrientation(),this); 
	    case 2 : return new AliceState(el.getX()+1,el.getY(),el.getOrientation(),this); 
	    case 3 : return new AliceState(el.getX(),el.getY()-1,el.getOrientation(),this); 
	    default : System.exit(-1); 
	    }
	if(a.equals(ActionAlice.BACKWARD))
	    switch(el.getOrientation()){
	    case 0 : return new AliceState(el.getX()+1,el.getY(),el.getOrientation(),this); 
	    case 1 : return new AliceState(el.getX(),el.getY()-1,el.getOrientation(),this); 
	    case 2 : return new AliceState(el.getX()-1,el.getY(),el.getOrientation(),this); 
	    case 3 : return new AliceState(el.getX(),el.getY()+1,el.getOrientation(),this); 
	    default : System.exit(-1); 
	    }
	if(a.equals(ActionAlice.RIGHT))
	    return new AliceState(el.getX(),el.getY(),(el.getOrientation()+1)%4,this); 
	if(a.equals(ActionAlice.LEFT))
	    return new AliceState(el.getX(),el.getY(),(el.getOrientation()+3)%4,this); 
	return null; 
    }// etatSuivant

    public ActionList getActionList(IState s){
	AliceState el=(AliceState) s;
	ActionList l=new ActionList(el);  
	l.add(ActionAlice.LEFT); 
	l.add(ActionAlice.RIGHT); 
	if(el.getWSM(0)==0) l.add(ActionAlice.FORWARD); 
	if(el.getWSM(2)==0) l.add(ActionAlice.BACKWARD); 
	return l; 
    }//getListeActions

    /** No notion of treasure in this maze */
    protected double distanceToTreasure(MazeState s){return 0; }

    /** Endless episodes */
    public boolean isFinal(IState e){return false;}

    public double getReward(IState s1,IState s2,IAction a){
	if(a.equals(ActionAlice.FORWARD)) return 1;  
	if(a.equals(ActionAlice.BACKWARD)) return -10;  
	else return -5; 
	//if(a.equals(ActionAlice.BACKWARD)) return -1; 

}

    /** Endless episodes */
    public boolean isEnd  (MazeState s){return false;}


    public int whoWins(IState s){return 0;}


  public void sendState(IState e){
 	AliceState ev=(AliceState)e;
	if(oldState!=null)
	    cases[oldState.getX()][oldState.getY()].setIcon(Bouton.UNKNOWN); 
	switch(ev.orientation){
	case 0 : cases[ev.getX()][ev.getY()].setIcon(Bouton.NMY); break; 
	case 1 : cases[ev.getX()][ev.getY()].setIcon(Bouton.EMY); break;    
	case 2 : cases[ev.getX()][ev.getY()].setIcon(Bouton.SMY); break; 
	case 3 : cases[ev.getX()][ev.getY()].setIcon(Bouton.WMY); break; 
	default:System.out.println("default") ; break; 
	}
 	//cases[ev.getX()][ev.getY()].setIcon(Bouton.ROBOT); 
	
 	//graf.getContentPane().repaint(); 
		for(int i=0;i<5000;i++)
	    for(int j=0;j<500;j++)
		{int zozo=(int)Math.sqrt((double)i); }

 }

}
