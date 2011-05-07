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
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    ComposedActionArrayList.java
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import agents.ElementaryAgent;

/** A container of elementary Agent actions, using an ArrayList
 * to store and retrieve elementary actions*/

public class ComposedActionArrayList extends AbstractComposedAction 
	implements ArrayOfActions{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int primes[]={2,3,5,7,11,13,17,19,23,29,31,37};
	
	private Collection<IAction> list; 
	//protected Collection<IAction> list = new ArrayList<IAction>();
	public ComposedActionArrayList(){
		this.list=new ArrayList<IAction>();
	}
	@Override
	public IAction getAction(int i) {
		return ((ArrayList<IAction>)list).get(i);
	}

	@Override
	public IAction getAction(ElementaryAgent a){
			return ((ArrayList<IAction>)list).get(a.getRank());
	}


	@Override
	public void addElementaryAction(ElementaryAgent agent, IAction action) {
		((ArrayList<IAction>)list).add(agent.getRank(),action);
	}
	/* (non-Javadoc)
	 * @see environment.AbstractComposedAction#copy()
	 * copy is only used when the object is considered as an ArrayOfActions :
	 * it can then rely strongly of the underlying ArrayList structure.
	 */
	@Override
	public IAction copy() {
		ComposedActionArrayList nouveau=new ComposedActionArrayList();
		ArrayList<IAction> thisList=(ArrayList<IAction>)this.list;
		ArrayList<IAction> newList=(ArrayList<IAction>)nouveau.list; 
		for(int i=0;i<thisList.size();i++){
				IAction ActionToCopy=(IAction)thisList.get(i);
				if(ActionToCopy!=null)
					newList.add(i,(IAction)ActionToCopy.copy());	
		}
		return nouveau; 
		
	}
	/* (non-Javadoc)
	 * @see environment.AbstractComposedAction#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof ComposedActionArrayList)) return false; 
		ArrayList<IAction> caalo=(ArrayList<IAction>)((ComposedActionArrayList)o).list;
		ArrayList<IAction> thisList=(ArrayList<IAction>)this.list;
		for(int i=0;i<this.list.size();i++)
		{
				if(!(thisList.get(i).equals(caalo.get(i))))
					return false; 
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see environment.AbstractComposedAction#hashCode()
	 */
	@Override
	public int hashCode() {
		ArrayList<IAction> thisList=(ArrayList<IAction>)this.list;
		int hash=0;
		for(int i=0;i<thisList.size();i++)
				hash=primes[i%primes.length]*hash+thisList.get(i).hashCode();
		return hash%23;
		
	}
	/* (non-Javadoc)
	 * @see environment.AbstractComposedAction#nnCoding()
	 */
	@Override
	public double[] nnCoding() {
		// TODO define
		return super.nnCoding();
	}
	/* (non-Javadoc)
	 * @see environment.AbstractComposedAction#nnCodingSize()
	 */
	@Override
	public int nnCodingSize() {
		//TODO : define
		return super.nnCodingSize();
	}
}