package hanoitowers;

import environment.IAction;

public class HanoiTowersAction implements IAction {
	
	private static final long serialVersionUID = 1L;
	protected int _pole_src, _pole_dest;
	
	public HanoiTowersAction(int pole_src, int pole_dest) {
		this._pole_src = pole_src;
		this._pole_dest = pole_dest;
	}
	
	public int getPoleSrc() { return this._pole_src; }
	
	public int getPoleDest() { return this._pole_dest; }

	public IAction copy() {
		return new HanoiTowersAction(this._pole_src, this._pole_dest);
	}
	
	@Override
	public String toString() {
		return "From Pole: "+ this.getPoleSrc() + " --> "+ this.getPoleDest();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof HanoiTowersAction)) return false;
		HanoiTowersAction action = (HanoiTowersAction) o;
		
		if (action.getPoleSrc() == this.getPoleSrc() &&
			action.getPoleDest() == this.getPoleDest()) 
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode() {
		return this.getPoleDest()+this.getPoleSrc();
	}

	public double[] nnCoding() {
		double[] code = new double[5];
		code[this.getPoleSrc()+this.getPoleDest()] = 1.0;
		return code;
	}

	public int nnCodingSize() {
		return 5;
	}

}
