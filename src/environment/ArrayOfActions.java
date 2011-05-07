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
 *    ArrayOfActions.java
 *    16 nov. 06
 *
 */
package environment;

import java.io.Serializable;

/**
 * For ElementaryAgentsWithNeighbours : maintain a list of the agent neighbours
 * last actions. 
 * An ArrayOfActions MUST define all the actions coding related methods
 * (hashCode,equals, nnCoding,nnCodingSize...)
 * Nothing in this interface, just to remember to redefine those methods.
 * At this time (November 16th), only implemented in already existing and 
 * suitable ComposedActionArrayList class.
 * @author Francesco De Comite
 *
 */
public interface ArrayOfActions extends Serializable, Cloneable {

}
