package neuralnetwork; 
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
 *    NeuralNetwork.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */




import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import dataset.Dataset;
import dataset.Sample;

/** 
    Feed-forward neural network using  the backpropagation algorithm.
    Inputs and outputs are vectors, whom values are <code>double</code>s between 0 and 1. 

    @see Sample  

    @author Francesco De Comite 
    @version $Revision: 1.0 $ 


    */

public class NeuralNetwork implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Width of input. */
    private int nbInputs;
    /** Width of output. */
    private int nbOutputs;
    /** Array memorizing the size of each layer.*/
    private int sizeOfLayers[];
    /** Number of layers.*/
    private int nbLayers; 
   
    private double learningRate=0.3;
    /** Weights <br>
	theWeights[i][j][k]= weigth on the link between the jth cell of layer i to the kth cell of layer i+1

*/
    private double theWeights[][][]; 
    
    private int epoch=1;
  
    private static Random generator=new Random(); 

    /** To trace quadratic error. */
    private double totalError; 
    private double error[]; 

    /** Get the total quadratic error.*/
    public double getSE(){return totalError;}

    /** Get quadratic error at output i.*/
    public double getSE(int i){return error[i]; }

    /** Mean Squared Error.*/
    public double getMSE(){return totalError/nbOutputs;}


    /** Read one weight.*/
    public double getWeight(int i,int j,int k){
	return theWeights[i][j][k]; 
    }

    public int getSizeOfLayer(int i){
	return sizeOfLayers[i];
    }
    

    /** Initialisation.<br> 
	@param descriptLayers : array containing the size of each layer.
	<ul>
	<li> Layer 0 : inputs</li>
	<li> Last layer : outputs</li>
	</ul>
	Don't forget to add 1 to the actual number of cells in a layer (threshold!)
*/
    public NeuralNetwork(int descriptLayers[]){
	nbLayers=descriptLayers.length; 
	nbInputs=descriptLayers[0]; 
	nbOutputs=descriptLayers[nbLayers-1]; 
	sizeOfLayers=new int[nbLayers]; 
	theWeights=new double[nbLayers-1][][]; 
	for(int i=0;i<nbLayers-1;i++)
	    sizeOfLayers[i]=descriptLayers[i]+1; // don't forget the threshold..
	sizeOfLayers[nbLayers-1]=descriptLayers[nbLayers-1]; // except for output

	for(int i=0;i<nbLayers-1;i++){
	    theWeights[i]=new double[sizeOfLayers[i]][]; 
	    for(int j=0;j<sizeOfLayers[i];j++)
		theWeights[i][j]=new double[sizeOfLayers[i+1]]; 
	}
    }

    
    /** Sigmoid */
    private static double sigmoid(double r){
	return 1.0/(1.0+Math.exp(-r)); 
    }

    /** Random initialisation of weights. */
    public void initNetwork(){
	for(int i=0;i<nbLayers-1;i++)
	    for(int j=0;j<sizeOfLayers[i];j++)
		for(int k=0;k<sizeOfLayers[i+1];k++)
		    theWeights[i][j][k]=generator.nextDouble()*2.0-1.0; 
    }

    /** Use the network to classify a sample. 
     @param input input data..
     @return Array of computed values
     @throws UncompatibleSizeException 
    */ 
    public double[] classify(double[] input) throws UncompatibleSizeException{
	if(input.length!=nbInputs) throw new UncompatibleSizeException("Classify : expected size :  "+nbInputs+" real size : "+ input.length); 
	double send[],receive[]=null;
	send=new double[input.length+1]; 
	System.arraycopy(input,0,send,0,input.length); 
	send[input.length]=1; 
	for(int i=1;i<nbLayers;i++){
	    receive=new double[sizeOfLayers[i]]; 
	    receive[sizeOfLayers[i]-1]=1; // fake cell : Threshold 
	    int fin; 
	    if(i!=nbLayers-1) fin=sizeOfLayers[i]-1; 
	    else fin=sizeOfLayers[i]; 
	    for(int j=0;j<fin;j++){
		receive[j]=0.0; 
		for(int k=0;k<sizeOfLayers[i-1];k++)
		    receive[j]+=send[k]*theWeights[i-1][k][j]; 
		receive[j]=sigmoid(receive[j]);
	    }
	    send=new double[receive.length];
	    System.arraycopy(receive,0,send,0,receive.length); 	 
	}

	return receive; 
    }


    public double[] classify(Sample s) throws UncompatibleSizeException{
	double resu[]=classify(s.getInputs()); 
	return resu; 
    }
    

    public void learnFromOneExample(Sample s) throws UncompatibleSizeException{
	learnFromOneExample(s.getInputs(),s.getOutputs()); 
    }

    /**  
     @param InputSample Array of input data 
     @param OutputSample Aray of expected output data
     @throws UncompatibleSizeException 

    */
    public void learnFromOneExample(double InputSample[],double OutputSample[]) throws UncompatibleSizeException{
	if(InputSample.length!=nbInputs) throw new UncompatibleSizeException("LearnFromOneExample Expected input size: "+nbInputs+" Actual input size: "+InputSample.length); 
	if(OutputSample.length!=nbOutputs) throw new UncompatibleSizeException("learnFromOneExample Expected output size: "+nbOutputs+" Actual output size : "+OutputSample.length); 
	double receive[][]=new double[nbLayers][]; 
	double delta[][]=new double[nbLayers][]; 
	receive[0]=new double[sizeOfLayers[0]]; 
	receive[0][sizeOfLayers[0]-1]=1; 
	if(error==null) error=new double[nbOutputs]; 
	totalError=0.0;

	System.arraycopy(InputSample,0,receive[0],0,sizeOfLayers[0]-1); 
	
	// Next layer
	for(int i=1;i<nbLayers;i++){
	    receive[i]=new double[sizeOfLayers[i]]; 
	    if(i!=nbLayers-1)
		receive[i][sizeOfLayers[i]-1]=1; 
	    else receive[i][sizeOfLayers[i]-1]=0; 
	    delta[i]=new double[sizeOfLayers[i]]; 
	    int fin; 
	    if(i==nbLayers-1) fin=sizeOfLayers[i]; 
	    else fin=sizeOfLayers[i]-1; 
	    for(int j=0;j<fin;j++){
		receive[i][j]=0.0; 
		for(int k=0;k<sizeOfLayers[i-1];k++)
		    receive[i][j]+=receive[i-1][k]*theWeights[i-1][k][j]; 
		receive[i][j]=sigmoid(receive[i][j]); 
	    }
	}
	// Quadratic error computation.
	    for(int i=0;i<OutputSample.length;i++) {
		error[i]+=0.5*(receive[nbLayers-1][i]-OutputSample[i])*(receive[nbLayers-1][i]-OutputSample[i]); 
		totalError+=0.5*(receive[nbLayers-1][i]-OutputSample[i])*(receive[nbLayers-1][i]-OutputSample[i]); 
	    }


	// Computation of deltas
	// Beginning with output layer

	for(int i=0;i<sizeOfLayers[nbLayers-1];i++){
	    double ok=receive[nbLayers-1][i]; 
	    delta[nbLayers-1][i]=ok*(1.0-ok)*(OutputSample[i]-ok); 
	}

	// Intermediate layers
	
	for(int i=nbLayers-2;i>0;i--){
	    for(int j=0;j<sizeOfLayers[i];j++){
		delta[i][j]=0.0; 
		for(int k=0;k<sizeOfLayers[i+1];k++){
		    delta[i][j]+=theWeights[i][j][k]*delta[i+1][k]; 
		} 
		delta[i][j]*=receive[i][j]*(1.0-receive[i][j]); 
	    }
	}

	// Modify weights
	for(int i=0;i<nbLayers-1;i++){
	    for(int j=0;j<sizeOfLayers[i];j++){
		for(int k=0;k<sizeOfLayers[i+1];k++){
		    theWeights[i][j][k]+=learningRate*delta[i+1][k]*receive[i][j]; 
		   
		}
	    }
	}// for i

    }
   

    /**Successively enters Samples from the Dataset into the NN
    @param d Dataset
    @throws UncompatibleSizeException
    */
    public void learnFromDatasetNonStochastic(Dataset d) throws  UncompatibleSizeException{
	for(int i=0;i<epoch;i++){
	    totalError=0; 
	    error=new double[nbOutputs]; 
	    Iterator it=d.iterator(); 
	    while(it.hasNext()){
		Sample u=(Sample)it.next(); 
		learnFromOneExample(u); 
	    }
	}
    }

    /** Choose epoch.*/
    public void setEpoch(int e){epoch=e;}
	

   
    public String toString(){
	String s=""; 
	s+="Neural Network with "+nbLayers+" layers"; 
	for(int i=0;i<nbLayers-1;i++)s+="\n Layer "+i+" : "+(sizeOfLayers[i]-1)+" neurones"; 
	s+="\n Layers "+(nbLayers-1)+" : "+sizeOfLayers[nbLayers-1]+" neurones at output"; 
	return s; 
    }

    /** Debug : look at the weigths */
    public void printWeights(){
	for(int i=0;i<nbLayers-1;i++){
	    System.out.println("From layer  "+i+" to layer "+(i+1)); 
	    for(int j=0;j<sizeOfLayers[i];j++)
		for(int k=0;k<sizeOfLayers[i+1];k++)
		    System.out.println("Cell "+j+" to cell "+k+" : "+theWeights[i][j][k]); 
	}
    }

    /** A sample function to learn. */
    public double debugFunction(double value[]){
	return (Math.cos(value[0]*15.0)+1.0)/2.0; 
    }

    /** Samples are randomly entered into the network.
    */
    public void learnFromDatasetStochastic(Dataset d) throws  UncompatibleSizeException{

	for(int j=0;j<epoch;j++){
	    totalError=0; 
	    error=new double[nbOutputs]; 
	    ArrayList<Integer> grosSac=new ArrayList<Integer>(); 
	    for(int i=0;i<d.numInstances();i++) grosSac.add(new Integer(i));
	    while(!grosSac.isEmpty()){
		Integer retour=(Integer)grosSac.remove(generator.nextInt(grosSac.size()));
		int pp=retour.intValue(); 
		Sample u=d.getSampleAt(pp); 
		learnFromOneExample(u); 
	    }
	}
	
    }// learnFromDatasetStochastic
    
    /** Testing the class */
    public static void main(String argv[]) throws Exception{
	int archi[]={1,3,4,1};
	NeuralNetwork myNetwork=new NeuralNetwork(archi); 
	Dataset myInstances=new Dataset();  
	myNetwork.initNetwork();  

	double u[]=new double[1]; 
	double v[]=new double[1]; 
	// Build the dataset 
	for(int i=0;i<100;i++){
	    u[0]=generator.nextDouble(); 
	    v[0]=myNetwork.debugFunction(u);
	    myInstances.add(new Sample(u,v));
	  }
	// Learn 
	for(int i=0;i<10000;i++){
	    myNetwork.learnFromDatasetStochastic(myInstances); 
	    if(i%1000==0) System.err.println(i); 
	    System.out.println(i+" "+myNetwork.getSE()); 
	}
	

	// verify
// 	for(int i=0;i<100;i++){
// 	    u[0]=i/100.0; 
// 	    v[0]=myNetwork.debugFunction(u); 
// 	    Sample s=new Sample(u,v); 
// 	    System.out.println(u[0]+" "+v[0]+" "+myNetwork.classify(s.getInputs())[0]); 
// 	}

	//	System.out.println(myNetwork); 
	//myNetwork.printWeights(); 

    
}
}

	
	
