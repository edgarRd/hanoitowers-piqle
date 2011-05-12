import agents.*;
import algorithms.*;
import referees.*;
import hanoitowers.*;

public class TestHanoiTowers {

	public static void main(String[] args) {
		
		HanoiTowersEnv environment = new HanoiTowersEnv(3);
		environment.setInitialState();
		
		QLearningSelector rand = new QLearningSelector();
		
		double epsilon = 0.4;

		rand.setEpsilon(epsilon);
		rand.setAlphaDecayPower(0.5);
		
		IAgent agent = new LoneAgent(environment, rand);
		
		OnePlayerReferee referee = new OnePlayerReferee(agent);
		referee.setMaxIter(1000);
		double total_reward = 0;
		
		for (int i=0; i < 10000; i++) {
			
			referee.episode(environment.getInitialState());
			total_reward = referee.getRewardForEpisode();
			
			System.out.println(i+" "+total_reward+" "+ epsilon);

			epsilon *= 0.9999;
			rand.setEpsilon(epsilon);
		}
	}
}
