package fr.schaltiemi.pong.gamestates;

import fr.schaltiemi.pong.manager.GameStateManager;

public abstract class GameState {
	
	protected GameStateManager gsm;
	
	protected GameState(GameStateManager gsm){
		this.gsm=gsm;
		init();
	}
	
	public abstract void init();
	//2 methods called into the render
	public abstract void update(float dt);
	public abstract void draw();
	//called in the update to do stuff with the game keys
	public abstract void handleInput();
	//called when we want to switch to another game state
	public abstract void dispose();
}
