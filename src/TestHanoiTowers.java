import agents.*;
import algorithms.*;
import referees.*;
import hanoitowers.*;

public class TestHanoiTowers {

	public static void main(String[] args) {
		
		HanoiTowersEnv environment = new HanoiTowersEnv(3);
		environment.setInitialState();
		
		QLearningSelector rand = new QLearningSelector();
		rand.setGamma(0.7);
		rand.setAlpha(0.7);
		
		IAgent agent = new LoneAgent(environment, rand);
		
		OnePlayerReferee referee = new OnePlayerReferee(agent);
		referee.setMaxIter(100);
		double total_reward = 0;
		
		for (int i=0; i < 30; i++) {
			
			referee.episode(environment.getInitialState());
			total_reward = referee.getRewardForEpisode();
			
			System.out.println(i+" "+total_reward);
		}
	}
}
