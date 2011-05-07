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
 *    ComposedActionHashMap.java
 *    14 nov. 06
 *
 */
package environment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import agents.ElementaryAgent;

public class ComposedActionHashMap extends AbstractComposedAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		private HashMap<ElementaryAgent,IAction> list=new HashMap<ElementaryAgent,IAction>();
	
	
	/* (non-Javadoc)
	 * @see environment.AbstractComposedAction#addElementaryAction(agents.ElementaryAgent, environment.IAction)
	 */
	@Override
	public void addElementaryAction(ElementaryAgent agent, IAction action) {
		this.list.put(agent,action);

	}

	/* (non-Javadoc)
	 * @see environment.AbstractComposedAction#getAction(agents.ElementaryAgent)
	 */
	@Override
	public IAction getAction(ElementaryAgent a) {
		return this.list.get(a);
	}

	/* (non-Javadoc)
	 * @see environment.AbstractComposedAction#getAction(int)
	 */
	@Override
	public IAction getAction(int i) {
		// TODO Not to be used, might not be very efficient
		Set key=list.keySet();
		Iterator cursor=key.iterator();
		while(cursor.hasNext()){
			ElementaryAgent a=(ElementaryAgent)cursor.next();
			if(a.getRank()==i)
					return (IAction)this.list.get(a); 
		}
		// Never reach this point
		return null;
		}
	
	public IAction copy() {
		ComposedActionHashMap nouveau=new ComposedActionHashMap();
		nouveau.list=new HashMap<ElementaryAgent,IAction>(this.list);
		return nouveau;
		
	}
		
	}


