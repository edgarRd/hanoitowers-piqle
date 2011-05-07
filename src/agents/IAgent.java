package agents;

import algorithms.IStrategy;
import dataset.Dataset;
import environment.IAction;
import environment.IEnvironment;
import environment.IState;

import java.io.Serializable;

public interface IAgent extends Serializable{

	/** Access the algorithm, for exemple to change its settings. */
	public IStrategy getAlgorithm();

	public IEnvironment getEnvironment();

	/** Put the agent in learning phase (default). */
	public void enableLearning();

	/** Stop learning. */
	public void freezeLearning();

	/** Get the state where the agent is.*/
	public IState getCurrentState();

	/** Place the agent.*/
	public void setInitialState(IState s);

	/** Read the last reward. */
	public double getLastReward();

	/** Acts */
	public IAction act();

	/** Try to understand the states and/or actions values found by the algorithm. */
	public void explainValues();

	/** From the states and actions encountered during the exploration, extracts a dataset in a
	 format suitable for the Neural Network treatment. */
	public Dataset extractDataset();

	/** Save an Agent into a file : by reading it again, you can continue the learning.*/
	public void saveAgent();

	/** Same as above, but the file name is given. */
	public void saveAgent(String fileName);

	/** Start a new episode, ask the algorithm to do the same. */
	public void newEpisode();
	
	public  IAction getLastAction(); 
	
	public void setLastAction(IAction lastAction);

}