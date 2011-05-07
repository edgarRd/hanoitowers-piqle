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
 *    Maze.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */


import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.JFrame; 
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent; 



import environment.*;
import java.util.Random; 
 

/** Standard rectangular maze : each position is either free, a wall, a treasure.

Reward is return when one reaches a treasure. 

It is up to you to add new types of positions, non-rectangular shapes ...

@author Francesco De Comite
 @version $Revision: 1.0 $ 



*/



public class Maze extends AbstractEnvironmentSingle{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The maze's dimensions*/
    protected int longueur; 
    protected int largeur; 

    /** Treasure's position */
    protected int xtre; 
    protected int ytre; 

    protected Bouton cases[][];

    protected  MazeState oldState=null; 

    /** Array describing the maze's shape */
    protected composantLabyrinthe[][] forme; 
    
    protected Random generateur=new Random(); 

  
    protected MazeState defaultCurrentState; 

    public Maze(int lo,int la){
	this.longueur=lo; 
	this.largeur=la; 
	forme=new composantLabyrinthe[largeur][longueur]; 

    }

    public IState defaultInitialState(){return defaultCurrentState;}

    
    /** Set the initial state to any free position of the maze. */
    public void randomInitialState(){
	int i=generateur.nextInt(longueur); 
	int j=generateur.nextInt(largeur);
	while(!forme[i][j].isFree()){
	    i=generateur.nextInt(longueur); 
	    j=generateur.nextInt(largeur);
	}
	defaultCurrentState=new MazeState(i,j,this); 
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
		if((!found)&&(forme[i][j].isFree()))
		    {
			defaultCurrentState=new MazeState(i,j,this); 
			oldState=defaultCurrentState; 
			found=true; 
		    }
	    }
	

    }

    protected double distanceToTreasure(MazeState s){
	return Math.sqrt(((s.getX()-this.xtre)*(s.getX()-this.xtre))+((s.getY()-this.ytre)*(s.getY()-this.ytre))); 
    }


    public String toString(){
	String s=""; 
	for(int i=0;i<largeur;i++){
	    s+="|"; 
	    for(int j=0;j<longueur;j++)
		s+=forme[i][j]+"|"; 
	    s+="\n"; 
	    for(int k=0;k<largeur;k++) s+="--"; 
	    s+="\n"; 
	}
	return s; 
    }

    public boolean isFinal(IState e){
	MazeState s=(MazeState)e; 
	return forme[s.getX()][s.getY()].isEnd(); 
    }

     public double getReward(IState s1,IState s2,IAction a){
	 MazeState xx=(MazeState)s2; 
	 if(forme[xx.getX()][xx.getY()].isEnd()) return 1; 
	 // TODO original setting --> return 0
	 return -1; 
     }

    	

    public IState successorState(IState s,IAction a){
	MazeState el=(MazeState) s;
	MazeAction al=(MazeAction)a;
	oldState=el; 
	return new MazeState((el.getX()+al.getX()),
				  (el.getY()+al.getY()),this);
    }

    
    public ActionList getActionList(IState s){
	MazeState el=(MazeState) s;
	ActionList l=new ActionList(el);  
	for (int i=0;i<9;i++) {
	    MazeAction a=MazeAction.direction[i]; //???? useless
	    int newX=el.getX()+a.getX(); 
	    int newY=el.getY()+a.getY(); 
	    if((newX<0)||(newX>=largeur)) continue; 
	    if((newY<0)||(newY>=longueur)) continue; 
	    if(forme[newX][newY].isWall()) continue; 
	    l.add(MazeAction.direction[i]); 
	}
	return l; 

    }
    
    /** @return true is there is a wall at this place.*/
    public boolean isWall(MazeState s){
	return forme[s.getX()][s.getY()].isWall(); 
    }
    /** @return true if there is a treasure at this place. */
     public boolean isEnd  (MazeState s){
         return forme[s.getX()][s.getY()].isEnd(); 
     }

    /** @return true if the state is a free cell. */
     public boolean isFree(MazeState s){
            return forme[s.getX()][s.getY()].isFree(); 
     }

    /** @return type of the position 
	<ul>
	<li> -1 : unknown (bug) </li>
	<li> 0: free</li>
	<li> 1 : wall </li>
	<li> 2 : treasure </li>
	</ul>
    */
    protected int getType(int abs,int coord){
	if((abs<0)||(abs>=longueur)) return -1; 
	if((coord<0)||(coord>=largeur)) return -1; 
	if(forme[abs][coord].isFree()) return 0; 
	if(forme[abs][coord].isWall()) return 1;
	if(forme[abs][coord].isEnd()) return 2;
	return 1214; //never reached
    }
	
	

  
    /** Access first dimension */
    public int getLargeur(){return this.largeur;}
    /** Access second dimension */
    public int getLongueur(){return this.longueur;}

  
    public int whoWins(IState s){
	 MazeState xx=(MazeState)s; 
	 if(forme[xx.getX()][xx.getY()].isEnd()) return -1; 
	 return 0; 
    }
 /* Graphical methods */
    
    
    protected JFrame graf; 
   
    public void setGraphics(){
	graf=new JFrame(); 
	graf.setSize(new Dimension(30*longueur,30*largeur)); 
	// TODO revoir le comportement du programme en cas de quittage
	graf.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
		    System.out.println("On quitte");
                    System.exit(0);
                }
            });             
	graf.getContentPane().setLayout(new GridLayout(longueur,largeur));
 
	cases=new Bouton[longueur][]; 
	for(int i=0;i<longueur;i++){
	    cases[i]=new Bouton[largeur];
	    for(int j=0;j<largeur;j++){
		cases[i][j]=new Bouton(9+forme[i][j].getValue());
	    }
	    	
	    
	}
		for(int i=0;i<longueur;i++)
	    for(int j=0;j<largeur;j++)
		graf.getContentPane().add(cases[i][j]); 
	graf.setVisible(true); 

} 
    public void closeGraphics(){}
    public void sendState(IState e){
 	MazeState ev=(MazeState)e;
	if(oldState!=null)
	       cases[oldState.getX()][oldState.getY()].setIcon(Bouton.UNKNOWN); 

 	cases[ev.getX()][ev.getY()].setIcon(Bouton.ROBOT); 
	
 	//graf.getContentPane().repaint(); 
		for(int i=0;i<5000;i++)
	    for(int j=0;j<500;j++)
		{int zozo=(int)Math.sqrt((double)i); }

 }
    public void clearGraphics(){
	for(int i=0;i<longueur;i++){
	    for(int j=0;j<largeur;j++){
		cases[i][j].setIcon(9+forme[i][j].getValue());
	    }
	}
	graf.getContentPane().repaint(); 

    }







}    
