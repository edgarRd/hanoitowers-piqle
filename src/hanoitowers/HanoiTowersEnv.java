package hanoitowers;

import environment.AbstractEnvironmentSingle;
import environment.ActionList;
import environment.IAction;
import environment.IState;

import java.util.LinkedList;

public class HanoiTowersEnv extends AbstractEnvironmentSingle {
	
	protected static final long serialVersionUID = 1L;
	protected final int DEF_NUM_DISKS = 4;
	protected int _num_disks = 0;
	
	protected HanoiTowersState oldState = null;
	protected HanoiTowersState defaultCurrentState = null;
	
	public HanoiTowersEnv() {
     	this._num_disks = DEF_NUM_DISKS;
     	
     	defaultCurrentState = this.setInitialState();
	}
	
	public HanoiTowersEnv(int num_disks) {
		this._num_disks = (num_disks > 0) ? num_disks : DEF_NUM_DISKS;
		
		defaultCurrentState = this.setInitialState();
	}
	
	public HanoiTowersState setInitialState() {
		LinkedList<Integer> pole1 = new LinkedList<Integer>();
		
		for (int i = this.getTotalDisks(); i > 0; i--)
			pole1.addFirst(i);
		
		return new HanoiTowersState(pole1, new LinkedList<Integer>(), new LinkedList<Integer>(), this);
	}
	
	public IState getInitialState() {
		return setInitialState();
	}

	protected int getTotalDisks() { return this._num_disks; }
	
	@Override
	public IState defaultInitialState() { return defaultCurrentState; }

	@Override
	public ActionList getActionList(IState s) {
		ActionList aList = new ActionList(s);
		HanoiTowersState state = (HanoiTowersState) s;
		
		LinkedList<Integer> pole1 = state.getPole1();
		LinkedList<Integer> pole2 = state.getPole2();
		LinkedList<Integer> pole3 = state.getPole3();
		
		int topA, topB;
		
		// --- Tests for pole 1
		
		topA = (pole1.isEmpty()) ? 1000 : pole1.getFirst();
		topB = (pole2.isEmpty()) ? 1000 : pole2.getFirst();
		//System.out.println("topA: "+ topA +", topB: "+ topB);
		if (topA < topB)
			aList.add(new HanoiTowersAction(1, 2));
		
		topB = (pole3.isEmpty()) ? 1000 : pole3.getFirst();
		//System.out.println("topA: "+ topA +", topB: "+ topB);
		if (topA < topB)
			aList.add(new HanoiTowersAction(1, 3));
		
		// --- Tests for pole 2
		
		topA = (pole2.isEmpty()) ? 1000 : pole2.getFirst();
		topB = (pole1.isEmpty()) ? 1000 : pole1.getFirst();
		//System.out.println("topA: "+ topA +", topB: "+ topB);
		if (topA < topB)
			aList.add(new HanoiTowersAction(2, 1));
		
		topB = (pole3.isEmpty()) ? 1000 : pole3.getFirst();
		//System.out.println("topA: "+ topA +", topB: "+ topB);
		if (topA < topB)
			aList.add(new HanoiTowersAction(2, 3));
		
		// --- Tests for pole 3
		
		topA = (pole3.isEmpty()) ? 1000 : pole3.getFirst();
		topB = (pole1.isEmpty()) ? 1000 : pole1.getFirst();
		//System.out.println("topA: "+ topA +", topB: "+ topB);
		if (topA < topB)
			aList.add(new HanoiTowersAction(3, 1));
		
		topB = (pole2.isEmpty()) ? 1000 : pole2.getFirst();
		//System.out.println("topA: "+ topA +", topB: "+ topB);
		if (topA < topB)
			aList.add(new HanoiTowersAction(3, 2));
		
		// System.out.println(aList.size());
		
		return aList;
	}

	@Override
	public double getReward(IState s1, IState s2, IAction a) {
		if (s2.isFinal())
			return 100;
		else
			return -5;
	}

	@Override
	public boolean isFinal(IState s) {
		HanoiTowersState current = (HanoiTowersState) s;
		boolean res = true;
		
		res &= current.getPole1().isEmpty();
		res &= current.getPole2().isEmpty();
		
		if (res)
			for (int i=this._num_disks; i > 0; i--) {
				res &= ( ((Integer)current.getPole3().get(i-1)) == i ); 
			}
		
		return res;
	}

	@Override
	public IState successorState(IState s, IAction a) {
		HanoiTowersAction action = (HanoiTowersAction) a;
		HanoiTowersState state = (HanoiTowersState) s;
		HanoiTowersState next_state = (HanoiTowersState) state.copy();
		
		Integer movable_disk = 0;
		
		// --- Get disk to move.
		
		switch (action.getPoleSrc()) {
		case 1:
			movable_disk = (Integer) next_state.getPole1().pop();
			break;
		case 2:
			movable_disk = (Integer) next_state.getPole2().pop();
			break;
		case 3:
			movable_disk = (Integer) next_state.getPole3().pop();
			break;
		default:
			break;
		}
		
		// --- Move the disk to the desired pole.
		
		switch (action.getPoleDest()) {
		case 1:
			next_state.getPole1().addFirst(movable_disk);
			break;
		case 2:
			next_state.getPole2().addFirst(movable_disk);
			break;
		case 3:
			next_state.getPole3().addFirst(movable_disk);
			break;
		default:
			break;
		}
		
		return next_state;
	}

	@Override
	public int whoWins(IState s) {
		if (s.isFinal())
			return 1;
		else
			return 0;
	}

}
