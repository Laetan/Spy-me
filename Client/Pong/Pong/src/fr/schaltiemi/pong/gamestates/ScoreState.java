package fr.schaltiemi.pong.gamestates;

import fr.schaltiemi.pong.manager.GameStateManager;

public class ScoreState extends GameState{
	
	/**
	 * ScoreState's constructor
	 * @param gsm: state chosen (menu-play-blackscreen-results)
	 */
	public ScoreState(GameStateManager gsm){
		super(gsm);
	}

	public void init() {
	}

	/**
	 * delay between ball
	 */
	public void update(float dt) {
		for(int i=0;i<100000;i++){}
		gsm.setState(GameStateManager.PLAY);
	}

	public void draw() {}

	public void handleInput() {}

	public void dispose() {}

}