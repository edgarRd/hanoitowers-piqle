package algorithms;

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
 *    PengSelector.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import java.util.Iterator;

import qlearning.ActionStatePair;
import qlearning.IDefaultValueChooser;
import environment.ActionList;
import environment.IAction;
import environment.IState;

/**
 * Peng's implementation of Q(lambda) <br>
 * <a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node78.html"> Sutton
 * and Barto chap 7.6 page 182</a><br>
 * 
 * Reference : <a
 * href="ftp://ftp.ccs.neu.edu/pub/people/rjw/qlambda-ml-96.ps">Peng et Williams
 * 1996</a>
 * 
 * @author Francesco De Comite (decomite at lifl.fr)
 * @version $Revision: 1.0 $
 * 
 * 
 * 
 */

public class PengSelector extends AbstractQLambdaSelector {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PengSelector(double l) {
		super(l);
	}

	/**
	 * @param l  Q(lambda)'s lambda
	 * @param dvc how to generate randomness
	 */
	public PengSelector(double l, IDefaultValueChooser dvc) {
		super(l, dvc);
	}

	/**
	 * Learning from experience.
	 * 
	 * @param s1
	 *            the start state.
	 * @param s2
	 *            the arrival state.
	 * @param a
	 *            the chosen action. <br>
	 *            <a
	 *            href="ftp://ftp.ccs.neu.edu/pub/people/rjw/qlambda-ml-96.ps">Peng
	 *            et Williams 1996</a><br>
	 * 
	 * The implemented algorithm is described in the above mentionned paper, at
	 * page 5
	 * @param reward
	 *            immediate reward.
	 * 
	 */
	public void learn(IState s1, IState s2, IAction a, double reward) {
		if (geometricDecay)
			alpha *= decayAlpha;
		else
			alpha = 1 / Math.pow(count + 0.0, this.alphaDecayPower);
		count++;
		double maxqsap = 0; // Vhat(x_{t+1})
		double maxq = 0; // Vhat(x_t)

		ActionList la = s2.getActionList();
		if (la.size() != 0) {
			maxqsap = memory.get(s2, la.get(0));
			for (int i = 1; i < la.size(); i++) {
				IAction aprime = la.get(i);
				double qsap = memory.get(s2, aprime);
				if (qsap > maxqsap)
					maxqsap = qsap;
			}// Peng et William : Vhat(x_{t+1})

		}
		// Peng et William : Compute Vhat(x_t)
		ActionList lb = s1.getActionList();
		if (lb.size() != 0) {
			maxq = memory.get(s1, lb.get(0));
			for (int i = 1; i < lb.size(); i++) {
				IAction aprime = lb.get(i);
				double qsap = memory.get(s1, aprime);
				if (qsap > maxq)
					maxq = qsap;
			}
		}// V_hat(x_t) is comuted
		// Eligibilities
		double qsa = memory.get(s1, a);
		double et = reward + gamma * maxqsap - maxq;
		double etprime = reward + gamma * maxqsap - qsa;
		Iterator parcours = eligibles.getIterator();
		while (parcours.hasNext()) {
			ActionStatePair courante = (ActionStatePair) parcours.next();
			double valeur = eligibles.get(courante);
			double old = memory.get(courante.getState(), courante.getAction());
			valeur *= lambda * gamma;
			memory.put(courante.getState(), courante.getAction(), null, old
					+ alpha * valeur * et);
			eligibles.put(courante, valeur);
		}

		memory.put(s1, a, s2, qsa + alpha * etprime);
		if (!replace)
			eligibles.increment(s1, a);
		else
			eligibles.set(s1, a, 1);

	}// apprend

	public void setRouletteWheel() {
		System.err
				.println("Warning : Roulette wheel selection not implemented in Peng(lambda)");
		super.setRouletteWheel();

	}

	public void setBoltzmann() {
		System.err
				.println("Warning : Boltzmann selection not implemented in Peng(lambda)");
		super.setBoltzmann();
	}

	public IAction getChoice(IState state, ActionList l) {
		IAction pro = super.getChoice(l);
		if (!pro.equals(meilleure(state,l)))
			this.reset();
		return pro;
	}

	public IAction meilleure(IState state,ActionList l) {
		if (l.size() == 0)
			return null;
		IState s = state;
		IAction m = l.get(0);
		double maxqsap = memory.get(s, m);
		for (int i = 1; i < l.size(); i++) {
			IAction a = l.get(i);
			double qsap = memory.get(s, a);
			if (qsap > maxqsap) {
				maxqsap = qsap;
				m = a;
			}
		}
		return m;

	}

}
