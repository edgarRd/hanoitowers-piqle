
/**
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

/*    ElementaryMuscleEnvironmentV2.java
 *    22 nov. 06
 * 	  
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

package maabacVersion2;

import environment.ActionList;
import environment.ElementaryMultiAgentEnvironment;
import environment.IState;

/**
 * @author Francesco De Comite
 *
 * 22 nov. 06
 */
public class ElementaryMuscleEnvironmentV2 extends
		ElementaryMultiAgentEnvironment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ActionList getActionList(IState s){
		ActionList loa=new ActionList(s); 
		loa.add(MuscleNullableAction.STILL); 
		loa.add(MuscleNullableAction.CONTRACT);
		loa.add(MuscleNullableAction.DECONTRACT);
		return loa;
	    }
}
