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
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    LoneAgent.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javax.swing.JFileChooser;

import util.ExtensionFileFilter;
import algorithms.ISelector;
import environment.IEnvironment;
import environment.IEnvironmentSingle;

/** The basic behavior of an Agent is : 
 <ul>
<li> According to the current state of the environment, choose the action</li>
<li> Apply this action, get the reward</li>
</ul>

Every Agent can call its underlying <i>algorithm</i>, and ask it to choose the action. 

 @author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 
*/


public class LoneAgent extends AbstractAgent{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Place the agent  in the environment.*/
    public LoneAgent(IEnvironmentSingle s, ISelector al){ 
    super(s,al);
    // For the multi-agent case (ICML Octopus)
    if(s!=null)
    	this.currentState=s.defaultInitialState();  
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
	resultat.currentState=((IEnvironmentSingle) s).defaultInitialState(); 
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
	IEnvironmentSingle s=(IEnvironmentSingle) resultat.getEnvironment(); 
	resultat.currentState=s.defaultInitialState(); 
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
	    { System.err.println("You choose to open this file: " 
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
	resultat.currentState=((IEnvironmentSingle) s).defaultInitialState(); 
	return resultat; 
	}

	
	
}
