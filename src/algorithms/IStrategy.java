package algorithms;

import environment.ActionList;
import environment.IAction;
import java.io.Serializable; 

public interface IStrategy extends Serializable{

	/** Choose an action in the list of legal moves 
	 * @param l ActionList to deal with (i.e. Isate and associated actions
	 * No need for the start state, it is included into the ActionList
	 * */
	public IAction getChoice(ActionList l);

}