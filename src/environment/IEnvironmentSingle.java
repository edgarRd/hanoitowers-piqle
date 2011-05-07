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
 *    IEnvironmentSingle.java
 *    4 oct. 06
 *
 */
package environment;

/**
 * @author Francesco De Comite
 *
 * The definition of Environment for non-adversial environments :
 * the next state depends only of the preceeding one and the action taken.
 * Also holds for multi-agent (cooperative) schemes
 * The difference with environment for two players is mainly that there
 * is no notion of winner.
 */
public interface IEnvironmentSingle extends IEnvironment {
	
	public IState defaultInitialState(); 

}
