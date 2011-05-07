package agents; 

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
 *    alo0ng with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    TwoPlayerAgent.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */



import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;

import util.ExtensionFileFilter;
import algorithms.ISelector;
import environment.IAction;
import environment.IEnvironment;
import environment.IEnvironmentTwoPlayers;
import environment.IState;
import environment.ITwoPlayerState;

/** Adversarial agent must wait the answer of its opponent before learning 
    (afterstates, <a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node68.html">Sutton & Barto page 156</a>)
@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 
 
 */

public class TwoPlayerAgent extends AbstractAgent{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    /** Remember the last state. */
   // protected IState oldState; 
   

      /** Place this agent somewhere in the environment, set its choice algorithm. */
    public TwoPlayerAgent(IEnvironmentTwoPlayers  s, ISelector al){ 
	super(s,al); 
	this.currentState=s.defaultInitialTwoPlayerState();
    }

    /** In a two players game, the agent waits unitl
     * the opponent plays before to learn.
     */
    protected IAction applyAction(IAction a){
	oldState=currentState.copy(); 
	this.currentState=currentState.modify(a); 
	return a; 
    } 

  

    /** The referee  tells the agent the new state of the environment after 
     * its opponent played.
     @param s The state of the environment after the opponent played.
*/
    public void getInformed(ITwoPlayerState s){
	if(oldState!=null){
	    double r=s.getReward(oldState,lastAction); 
	    reward=r; 
	    if(this.learningEnabled){
		algorithm.learn(oldState,s,lastAction,r); 
	    }
	this.oldState=currentState; 
	
	}this.currentState=s;
    }

   
    /** Same as above, technical difference when the play comes to an end : we need to change the <i>turn indicator</i> back.*/
    public void getInformedFinal(IState s){ 
	ITwoPlayerState s1=(ITwoPlayerState) s.copy();
	s1.toggleTurn(); 
	getInformed(s1); 
    }
    
    /** Read an agent's description from a file. */
	public static IAgent readAgent(String fichier, IEnvironment s) {
	File fichierALire=new File(fichier+".agt"); 
	ObjectInputStream entree;
	LoneAgent resultat=null; 
	try{
	    entree=new ObjectInputStream(new FileInputStream(fichierALire)); 
	    resultat=(LoneAgent)entree.readObject(); 
	    entree.close(); 
	}
	catch(Exception e){ System.err.println("Problem when reading agent file. "+e.getMessage()); }
	resultat.currentState=((IEnvironmentTwoPlayers) s).defaultInitialTwoPlayerState(); 
	return resultat; 
	}// readAgent
	
	
	
	/** Read an agent's description from a file, but find itself the universe the agent was into */
	public static IAgent readAgent(String fichier) {
	File fichierALire=new File(fichier+".agt"); 
	ObjectInputStream entree;
	LoneAgent resultat=null; 
	try{
	    entree=new ObjectInputStream(new FileInputStream(fichierALire)); 
	    resultat=(LoneAgent)entree.readObject(); 
	    entree.close(); 
	}
	catch(Exception e){ System.err.println("Problem when reading agent file. "+e.getMessage()); }
	IEnvironmentTwoPlayers s=(IEnvironmentTwoPlayers) resultat.getEnvironment(); 
	resultat.currentState=s.defaultInitialTwoPlayerState(); 
	return resultat; 
	}// readAgent

	/** Same as above, but the file name is not given.*/
	public static IAgent readAgent(IEnvironment s) {
	JFileChooser chooser=new JFileChooser();
	chooser.setCurrentDirectory(new File(".")); 
	String extension="agt";
	File fichierALire; 
	ObjectInputStream entree;
	ExtensionFileFilter filter = new ExtensionFileFilter(); 
	LoneAgent resultat=null; 
	filter.addExtension(extension);  
	filter.setDescription("Agent file"); 
	chooser.setFileFilter(filter); 
	int returnVal = chooser.showOpenDialog(null); 
	if(returnVal == JFileChooser.APPROVE_OPTION) 
	    { System.err.println("You chose to open this file: " 
				 + chooser.getSelectedFile().getName()); 
	    
	    fichierALire=chooser.getSelectedFile(); 
	    }
	else 
	    {
		return null; 
	
	    }
	try{
	entree=new ObjectInputStream(new FileInputStream(fichierALire)); 
	resultat=(LoneAgent)entree.readObject(); 
	entree.close(); 
	}
	catch(Exception e){ System.err.println("Problem when reading agent file. "+e.getMessage()); }
	resultat.currentState=((IEnvironmentTwoPlayers) s).defaultInitialTwoPlayerState(); 
	return resultat; 
	}

    
}
