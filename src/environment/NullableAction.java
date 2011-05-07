/**
 *	This program is free software; you can redistribute it and/or modify
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
 *
 *
 *    Copyright (C) 2006 Francesco De Comite
 *    NullableAction.java
 *    20 nov. 06
 *
 */
package environment;

/**
 * @author decomite
 *
 */
public class NullableAction implements IAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** An Action representing 'no Action'? We need it for : 
	 * <ul>
	 * <li> nnCodingSize in case of NullAction</li>
	 * <li> Initial action in initial state for elementary multi-agents.w/li>
	 * </ul>
	 */
	static public NullableAction nullAction=null;
	/* (non-Javadoc)
	 * @see environment.IAction#copy()
	 */
	public Object copy(){
		System.err.println("This method from NullableAction MUST be overridden");
		System.exit(0);
		return null;
	}

	/* (non-Javadoc)
	 * @see environment.IAction#isNullAction()
	 */
	public boolean isNullAction(){
		System.err.println("This method from NullableAction MUST be overridden");
		System.exit(0);
		return false;
	}

	/* (non-Javadoc)
	 * @see environment.IAction#nnCoding()
	 */
	public double[] nnCoding(){
	System.err.println("This method from NullableAction MUST be overridden");
	System.exit(0);
	return null;
}

	
	/** Size of the NN coding scheme for an action */
	public int nnCodingSize() {
		System.err.println("This method from NullableAction MUST be overridden");
		System.exit(0);
		return 0;
	}

	/* 
	 * Must be overridden
	 */
	static public int staticNNCodingSize(){
		System.err.println("This method from NullableAction MUST be overridden");
		System.exit(0);
		return 0;
	}

	/**
	 * @return the nullAction
	 */
	public static NullableAction getNullAction() {
		if(nullAction!=null)
			return nullAction;
		System.err.println("You forgot to define nullAction");
		System.exit(0);
		return null;
	}

	/**
	 * @param nullAction the nullAction to set
	 */
	public static void setNullAction(NullableAction nullAction) {
		NullableAction.nullAction = nullAction;
	}
}
