package hanoitowers;

import agents.LoneAgent;
import algorithms.ISelector;
import environment.IEnvironmentSingle;
import environment.IAction;

public class HanoiTowersAgent extends LoneAgent {

    private int _actions_performed = 0;

    public HanoiTowersAgent(IEnvironmentSingle s, ISelector a) {
        super(s,a);
    }
    
    public int getActionsPerformed() { return this._actions_performed; }
    
    @Override
    public void newEpisode() {
        this._actions_performed = 0;
        super.newEpisode();
    }
    
    @Override
    protected IAction applyAction(IAction a) {
        this._actions_performed++;
        return super.applyAction(a);
    }
}