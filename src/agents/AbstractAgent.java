package agents;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


import javax.swing.JFileChooser;

import util.ExtensionFileFilter;
import algorithms.ISelector;
import algorithms.IStrategy;
import dataset.Dataset;
import environment.ActionList;
import environment.IAction;
import environment.IEnvironment;
import environment.IState;

public class AbstractAgent implements IAgent{

	/**
	 * Test Comment : erase
	 */
	private static final long serialVersionUID = 1L;
	/** The current state of the agent. */
	protected IState currentState = null;
	/** The algorithm which chooses the action among the liste of possible ones.xx*/
	protected ISelector algorithm;
	/** The universe in which the agent lives (to allow the referee to communicate with graphical interface) */
	protected IEnvironment universe;
	/** The last action performed by this agent */
	protected IAction lastAction=null;
	
	protected IState oldState=null;

	public IStrategy getAlgorithm() {
	return this.algorithm; 
	}

	/** Controlling whether the agent is in a learning phase or not. */
	protected boolean learningEnabled = true;

	public IEnvironment getEnvironment() {return this.universe;}

	/** Last reward. */
	protected double reward = 0;

	public void enableLearning() {learningEnabled=true;}

	public void freezeLearning() {learningEnabled=false;}

	public IState getOldState(){return this.oldState; }
	

	public IState getCurrentState() {
	return this.currentState; 
	}

	public void setInitialState(IState s) {
		this.oldState=s;
		this.currentState=s; 
	}

	/** Ask the algorithm to choose the next action. */
	/* TODO : It might be that this chosen action will not be the one finally performed (02/2007)*/
	
	public IAction choose() {
	ActionList l=getActionList();
	IAction prov=algorithm.getChoice(l); 
	
	this.lastAction=(IAction)prov.copy();
	return prov;
	}

	protected ActionList getActionList() {
		ActionList prodebug=currentState.getActionList();
		return prodebug;
		//return currentState.getActionList();
		
	}

	/** <ul>
	<li>Apply the action, get the reward.</li>
	<li>If learning is enabled, learn from states, action, and reward. </li>
	<ul>*/
	protected IAction applyAction(IAction a) { 
	oldState = currentState;
	currentState=currentState.modify(a);	
	double r=currentState.getReward(oldState,a);
	reward=r; 
	if(this.learningEnabled)
	    algorithm.learn(oldState,currentState,a,r);
	return a; 
	}

	public double getLastReward() {return reward;}

	public IAction act() {
		return this.applyAction(this.choose());
	}

	public void explainValues() {
	System.out.println(algorithm); 
	}

	public Dataset extractDataset() {
	return algorithm.extractDataset(); 
	}

	public void saveAgent() {
	JFileChooser chooser=new JFileChooser();
	chooser.setCurrentDirectory(new File(".")); 
	File sauvegarde; 
	ObjectOutputStream sortie;
	String ext[]={"agt"};
	ExtensionFileFilter filter = new ExtensionFileFilter(ext); 
	filter.setDescription("Agent file"); 
	chooser.setFileFilter(filter); 
	int returnVal = chooser.showSaveDialog(null); 
	if(returnVal == JFileChooser.APPROVE_OPTION) 
	    { System.out.println("You chose to open this file: " 
				 + chooser.getSelectedFile().getName()); 
	    
	    sauvegarde=chooser.getSelectedFile(); 
	    }
	else 
	    {
		sauvegarde=new File("raymond.agt"); 
	
	    }
	try{
	sortie=new ObjectOutputStream(new FileOutputStream(sauvegarde)); 
	sortie.writeObject(this); 
	sortie.close(); 
	}
	catch(Exception e){ System.err.println("Problem when trying to save agent "+e.getMessage()); 
	}
	
	}

	public void saveAgent(String fileName) {
	File sauvegarde; 
	ObjectOutputStream sortie;
	sauvegarde=new File(fileName+".agt"); 
	try{
	    sortie=new ObjectOutputStream(new FileOutputStream(sauvegarde)); 
	    sortie.writeObject(this); 
	    sortie.close(); 
	}
	catch(Exception e){ System.err.println("Problem when trying to save agent "+e.getMessage()+"***"+e); 
	}
	
	}

	public static IAgent readAgent(IEnvironment s){return null;}
	public static IAgent readAgent(String fichier, IEnvironment s){return null;}
	public static IAgent readAgent(String fichier) {return null;}
	
	public AbstractAgent(IEnvironment s, ISelector al) {
		this.algorithm=al; 
		this.universe=s; 
	}

	public void newEpisode() {
	reward=0.0;
	this.lastAction=null;
	//DEBUG
	this.oldState=null;
	if(this.algorithm!=null)
	    this.algorithm.newEpisode(); 
	}

	/**
	 * @return the lastAction
	 */
	public final IAction getLastAction() {
		return lastAction;
	}

	/**
	 * @param lastAction the lastAction to set
	 */
	public final void setLastAction(IAction lastAction) {
		this.lastAction = lastAction;
	}
	
	
		
}