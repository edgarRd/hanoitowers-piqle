package qlearning;
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
*    ConstantValueChooser.java
*    10 oct. 06
*
*/


/**
* @author Francesco De Comite
*
*/
public class ConstantValueChooser implements IDefaultValueChooser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double constantValue=0;
	
	public ConstantValueChooser(double val){
		this.constantValue=val;
	}
	public double getValue() {
		return this.constantValue; 
	}

}
