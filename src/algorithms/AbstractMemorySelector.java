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
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    AbstractMemorySelector.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import java.util.Iterator;
import java.util.Random; 

import qlearning.IRewardStore;
import dataset.Dataset;
import environment.ActionList;
import environment.IAction;
import environment.IState;


/**
 * The base of all Q-Learning-like algorithms :
 * 
 * <ul>
 * <li> Provides a structure to memorize or compute the Q(s,a) </li>
 * <li> Contains all the parameters used in the Q-Learning update rules </li>
 * <li> Contains all the parameters used to control convergence</li>
 * </ul>
 * 
 * 
 * 
 * @author Francesco De Comite (decomite at lifl.fr)
 * @version $Revision: 1.0 $
 * 
 */
/* October 4th 2006 : return to the old version, where boltzmann, epsilon-greedy
 *  and rouletteWheel are declared inside this class : 
 *  too much problems in defining and monitoring the parameters of each
 *  choosing strategy (epsilon, tau, modifying epsilon...) (fd)
 * 
 */
abstract public class AbstractMemorySelector implements ISelector {

	/** Memorizing or computing Q(s,a) */
	protected IRewardStore memory;

	/** The parameter for Boltzmann Action selection Strategy */
	protected double tau=0.5; 

	/** Learning rate */
	protected double alpha = 0.9;

	/** discount rate */
	protected double gamma = 0.9;
	
	protected double epsilon=0.5; 
	
	private Random generator=new Random();


	/**
	 * Factor by which we multiply alpha at each learning step (geometric decay)<br>
	 * <i> Note : geometric decay does no insure convergence.</i>
	 */
	protected double decayAlpha = 0.999999999;

	/** Number of learning steps */
	protected double count = 1.0;

	/**
	 * Power of decay when alpha=1/(count^alphaDecayPower)
	 * 
	 * @see <a href="http://www.cs.tau.ac.il/~evend/papers/ql-jmlr.ps">Learning
	 *      rates for Q-Learning</a>
	 */
	protected double alphaDecayPower = 0.8;

	public void setAlpha(double a) {
		this.alpha = a;
	}

	public void setGamma(double g) {
		this.gamma = g;
	}
	public void setDecay(double d) {
		this.decayAlpha = d;
	}

	public void setAlphaDecayPower(double a) {
		this.alphaDecayPower = a;
	}

	public double getAlpha() {
		return alpha;
	}

	public double getGamma() {
		return gamma;
	}

	public double getDecay() {
		return this.decayAlpha;
	}

	public double getAlphaDecayPower() {
		return this.alphaDecayPower;
	}

	/**
	 * Alpha decay methods
	 * <ul>
	 * <li> Geometric : use decayAlpha </li>
	 * <li> Exponential : use alphaDecayPower (default)</li>
	 * <ul>
	 */
	protected boolean geometricDecay = false;

	public void setGeometricAlphaDecay() {
		geometricDecay = true;
	}

	public void setExponentialAlphaDecay() {
		geometricDecay = false;
	}

	/**
	 * How convergence is controlled ?
	 * <ul>
	 * <li> true : alpha decays geometrically </li>
	 * <li> false : alpha decays exponentially</li>
	 * </ul>
	 */
	public boolean getGeometricDecay() {
		return geometricDecay;
	}

	/**
	 * How to implement randomness ?
	 * <ul>
	 * <li>epsilon-greedy</li>
	 * <li>Roulette wheel selection</li>
	 * <li>Boltzmann </li>
	 * <ul>
	 * Roulette wheel or Boltzmann selection makes epsilon useless.
	 */
	protected boolean rouletteWheel = false;

	protected boolean epsilonGreedy = true;

	protected boolean boltzmann = false;
	


	public void setRouletteWheel() {
		rouletteWheel = true;
		epsilonGreedy = false;
		boltzmann = false;
	}

	/** Set the epsilon-greedy policy */
	public void setEpsilonGreedy() {
		epsilonGreedy = true;
		rouletteWheel = false;
		boltzmann = false;
	}

	/** Set Boltzmann selection */
	public void setBoltzmann() {
		epsilonGreedy = false;
		rouletteWheel = false;
		boltzmann = true;
	}

	public boolean getRouletteWheel() {
		return rouletteWheel; 
	}

	public boolean getEpsilonGreedy() {
		return epsilonGreedy;
	}

	public boolean getBoltzmann() {
		return boltzmann;
	}

	/** Finding Q(s,a) */
	public double getValue(IState s, IAction a) {
		return memory.get(s, a);
	}

	/** Nothing to reset at this level. */
	public void newEpisode() {
	};

	/**
	 * Learning from experience.
	 * 
	 * @param s1
	 *            the start state.
	 * @param s2
	 *            the arrival state.
	 * @param a
	 *            the chosen action.
	 * 
	 * <a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node65.html">Sutton &
	 * Barto p 149 Q-Learning</a>
	 * @param reward
	 *            immediate reward.
	 */
	public void learn(IState s1, IState s2, IAction a, double reward) {
		if (geometricDecay)
			alpha *= decayAlpha;
		else {
			alpha = 1 / Math.pow(count + 1.0, this.alphaDecayPower);
		}

		count++;
		double qsa = memory.get(s1, a);
		ActionList la = s2.getActionList();
		if (la.size() != 0) {
			Iterator<IAction> iterator = la.iterator();
			double maxqsap = memory.get(s2, iterator.next());
			while (iterator.hasNext()) {
				IAction aprime = iterator.next();
				double qsap = memory.get(s2, aprime);
				if (qsap > maxqsap)
					maxqsap = qsap;
			}
			qsa += alpha * (reward + gamma * maxqsap - qsa);
			memory.put(s1, a, s2, qsa);
		} else {
			memory.put(s1, a, s2, qsa + alpha * (reward - qsa));
		}
	}

	/** Choose one of the legal moves */
	public IAction getChoice(ActionList l) {
		if (rouletteWheel)
			return rouletteWheelChoice(l);
		if (epsilonGreedy)
			return epsilonGreedyChoice(l);
		if (boltzmann)
			return boltzmannChoice(l);
		return null;
	}
	 /** Roulette Wheel selection of the next action : the probability for an action to be chosen is relative to its Q(s,a) value.

    TODO DEBUG : not valid if Q(s,a) can be negative !!!*/

	private IAction rouletteWheelChoice(ActionList l){
		if(l.size()==0) return null; 
		IState s=l.getState();
		double sum=0; 
		for(int i=0;i<l.size();i++) sum+=memory.get(s,l.get(i))+1; 
		double choix=generator.nextDouble()*sum; 
		int indice=0;
		double partialSum=memory.get(s,l.get(indice))+1; 
		while(choix>partialSum){
			  indice++; 
			  partialSum+=1+memory.get(s,l.get(indice)); 
		}
		return l.get(indice);	
	}
	
	/** Epsilon-greedy choice of next action */
	private IAction epsilonGreedyChoice(ActionList l){
		if(l.size()==0) return null;
		IState s=l.getState(); 	
		IAction meilleure=l.get(0); 
		double maxqsap=memory.get(s,meilleure);
		// TODO : might use an iterator
		for(int i=1;i<l.size();i++){
		    IAction a=l.get(i); 
		    double qsap=memory.get(s,a);
		    if(qsap>maxqsap) {
			maxqsap=qsap;  
			meilleure=a;
		    }
		}
		// TODO Beginning the method with this test should speed up the program
		if(generator.nextDouble()>this.epsilon) return meilleure; 
		else 
		    return l.get(generator.nextInt(l.size())); 
	}

	/** Chooses an action according to the Boltzmann protocol */
	private IAction boltzmannChoice(ActionList l){
		if(l.size()==0) return null; 
		IState s=l.getState();
		double sum=0; 
		double tab[]=new double[l.size()]; 
		for(int i=0;i<l.size();i++) {
		    sum+=Math.exp(memory.get(s,l.get(i))/this.tau); 
		    tab[i]=sum; 
		}    
		double choix=generator.nextDouble()*sum; 
		for(int i=0;i<l.size();i++){
		    if(choix<=tab[i]) return l.get(i);
		}
		System.err.println(choix+ " "+"Wrong");
		System.exit(-1); 
		return null;
	}
	/** Auxiliary/debug method : find the best action from a state. */
	public IAction bestAction(IState s) {
		ActionList l = s.getActionList();
		Iterator<IAction> iterator = l.iterator();
		if(l.size()==0) return null;
		IAction meilleure = iterator.next();
		double maxqsap = memory.get(s, meilleure);
		while (iterator.hasNext()) {
			IAction a = iterator.next();
			double qsap = memory.get(s, a);
			if (qsap > maxqsap) {
				maxqsap = qsap;
				meilleure = a;
			}
		}
		return meilleure;
	}

	public String toString() {
		return memory.toString();
	}

	public Dataset extractDataset() {
		return memory.extractDataset();
	}

	
	/**
	 * @return the tau used in Boltzmann Action selection strategy
	 */
	public double getTau() {
		return tau;
	}

	/**
	 * @param tau the tau to set in the Boltzmann Action selection strategy
	 */
	public void setTau(double tau) {
		this.tau = tau;
	}

	/**
	 * @return the epsilon
	 */
	public double getEpsilon() {
		return epsilon;
	}

	/**
	 * @param epsilon the epsilon to set
	 */
	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

}
