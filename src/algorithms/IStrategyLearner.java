package algorithms;

import environment.IAction;
import environment.IState;

import java.io.Serializable; 

public interface IStrategyLearner extends Serializable{

	/** Learn 
	 @param s1 The state the agent is in before the action is performed.
	 * @param s2 The state the agent goes to when the action is performed. 
	 * @param a The action the agent took.
	 * @param reward The reward obtained for this move.
	 */
	public void learn(IState s1, IState s2, IAction a, double reward);

	/** There might be some things to do at the beginning of each episode... */
	public void newEpisode();

}