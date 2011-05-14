import agents.*;
import algorithms.*;
import referees.*;
import hanoitowers.*;

public class TestHanoiTowers {

	public static void main(String[] args) {
		
		HanoiTowersEnv environment = new HanoiTowersEnv(4);
		environment.setInitialState();
		
		QLearningSelector sql = new QLearningSelector();
		double epsilon = 0.4;

		sql.setEpsilon(epsilon);
		sql.setAlphaDecayPower(0.5);
		
		IAgent agent = new HanoiTowersAgent(environment, sql);
		
		OnePlayerReferee referee = new OnePlayerReferee(agent);
		referee.setMaxIter(1000);
		double total_reward = 0;
		
		for (int i=0; i < 50000; i++) {
			
			referee.episode(environment.getInitialState());
			total_reward = referee.getRewardForEpisode();
			
			int actions_performed = ((HanoiTowersAgent)agent).getActionsPerformed();
			
			System.out.println(i+" "+total_reward+" "+ epsilon+ " "+actions_performed);

			epsilon *= 0.9999;
			sql.setEpsilon(epsilon);
		}
	}
}
