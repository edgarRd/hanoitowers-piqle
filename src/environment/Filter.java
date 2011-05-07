package environment; 
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
 *    Filter.java
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

/** Two methods to change the view an elementary agent can have over 
 * a multi agent state, and perhaps the actions of its neighbour agents */


import java.io.Serializable;

abstract public class Filter implements Cloneable,Serializable{
   abstract public IState filterState(IState s,IEnvironment c); 
}