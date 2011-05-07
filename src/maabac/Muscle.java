package maabac; 

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
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    Muscle.java
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

/** A Muscle is an elementary agent in the MAABAC frame. In addition to the standard definition of an ElementaryAgent, it computes a (negative) reward correlated to its level of contraction */

import agents.ElementaryAgent; 
import environment.IEnvironmentSingle;
import environment.IState; 
import environment.Filter;

import algorithms.ISelector;


import maabac.Muscle; 

public class Muscle extends ElementaryAgent{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected double kappa=1.0; 

     public Muscle(IEnvironmentSingle s, ISelector al,Filter f,IState st){ 
	 super(s,al,f,st); 
    }
    
    public double internalReward(){
	@SuppressWarnings("unused")
	int pro=((Contraction)this.currentState).getLevel(); 
	@SuppressWarnings("unused")
	int max=((Contraction)this.currentState).getMaxLevel(); 
	//return -kappa*(pro/(max+0.0))*(pro/(max+0.0)); 
	return 0;
    }
}