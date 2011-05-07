package hanoitowers;

import environment.AbstractState; 
import environment.IState; 
import environment.IEnvironment;

import java.util.LinkedList; 

public class HanoiTowersState extends AbstractState {

    private static final long serialVersionUID = 1L;
    private LinkedList<Integer> _pole1, _pole2, _pole3;
    
    public HanoiTowersState(LinkedList<Integer> pole1, 
        LinkedList<Integer> pole2, 
        LinkedList<Integer> pole3, 
        IEnvironment universe) {
        
        super(universe);
        this._pole1 = pole1;
        this._pole2 = pole2;
        this._pole3 = pole3;
    }

    public LinkedList<Integer> getPole1() { return this._pole1; }
    public LinkedList<Integer> getPole2() { return this._pole2; }
    public LinkedList<Integer> getPole3() { return this._pole3; }

	@Override
	public IState copy() {
		
		LinkedList<Integer> pole1 = copyPole(this._pole1);
		LinkedList<Integer> pole2 = copyPole(this._pole2);
		LinkedList<Integer> pole3 = copyPole(this._pole3);
		
		return new HanoiTowersState(pole1, pole2, pole3, myEnvironment);
	}
	
	private LinkedList<Integer> copyPole(LinkedList<Integer> src) {
		
		LinkedList<Integer> newPole = new LinkedList<Integer>();
		for (Integer i : src) {
			newPole.add(i);
		}
		
		return newPole;
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		
		buf.append(printPole(this._pole1));
		buf.append("------\n");
		buf.append(printPole(this._pole2));
		buf.append("------\n");
		buf.append(printPole(this._pole3));
		buf.append("------\n");
		
		return buf.toString();
	}
	
	private String printPole(LinkedList<Integer> pole) {
		
		String str = "";
		
		for (Integer i : pole) {
			str = " | "+i + str;
		}
		
		if (pole.isEmpty())
			str += "[]\n";
		else
			str += "\n";
		
		return str;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean res = true;
		
		if (o instanceof HanoiTowersState) {
		
			HanoiTowersState hstate = (HanoiTowersState) o;
	
			res &= this._pole1.equals(hstate.getPole1());
			res &= this._pole2.equals(hstate.getPole2());
			res &= this._pole3.equals(hstate.getPole3());
			
			return res;
			
		} else
			return false;
	}
	
	@Override
	public int hashCode() {
		int sp1=0, sp2=0, sp3=0;
		
		for (Integer i : this._pole1) {
			sp1 += i;
		}
		for (Integer i : this._pole2) {
			sp2 += i;
		}
		for (Integer i : this._pole3) {
			sp3 += i;
		}
		
		return sp1*1 + sp2*2 + sp3*3;
	}
	
	@Override
	public double[] nnCoding() {
		double code[]=new double[8]; 
		
		String byteStr = Integer.toBinaryString(this.hashCode());
		
		for (int i=0; i < byteStr.length(); i++) {
			if (byteStr.charAt(i) == '1')
				code[i] = 1.0;
			else
				code[i] = 0.0;
		}
		
		return code; 
	}
	@Override
	public int nnCodingSize() {
		return 8;
	}
}