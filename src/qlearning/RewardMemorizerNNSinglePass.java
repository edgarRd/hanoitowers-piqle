package qlearning; 
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
 *    RewardMemorizerNN.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */


import neuralnetwork.NeuralNetwork;
import dataset.Sample;
import environment.IAction;
import environment.IState;


/** Samples are not memorized : each time a new experience is available, the neural network is asked to train. The sample is then forgotten.

@author Francesco De Comite 
 @version $Revision: 1.0 $ 

 */



public class RewardMemorizerNNSinglePass extends RewardMemorizerNN{

  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public RewardMemorizerNNSinglePass() {
		super();
	}

	/**
	 * @param dvc
	 */
	public RewardMemorizerNNSinglePass(IDefaultValueChooser dvc) {
		super(dvc);
	}

	public void put(IState s,IAction a,IState sp,double qsa){
		int prosize=a.nnCodingSize(); 
    	if (memory==null) {//No neural Network yet
	    // Build it
	    int archi[]=new int[3]; 
	    archi[0]=s.nnCodingSize()+prosize; 
	    archi[1]=1+archi[0]/5; 
	    archi[2]=1; 
	    memory=new NeuralNetwork(archi); 
	    memory.setEpoch(1);
	    memory.initNetwork();  
	}
	double inputs[]=new double[s.nnCodingSize()+prosize];
	double outputs[]=new double[1]; 
	if (rescale)
	    outputs[0]=inverseLogistic(qsa); 
	else
	    outputs[0]=qsa; 
	System.arraycopy(s.nnCoding(),0,inputs,0,s.nnCodingSize()); 
	System.arraycopy(a.nnCoding(),0,inputs,s.nnCodingSize(),prosize); 
	try{
	    memory.learnFromOneExample(new Sample(inputs,outputs)); 
	}
	catch(Exception e){System.err.println("RewardMemorizerNNSinglePass"+e); System.exit(-1);}
	
	return; 
    }
}
