package fr.schaltiemi.pong.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.schaltiemi.pong.Pong;
import fr.schaltiemi.pong.manager.GameKeys;
import fr.schaltiemi.pong.manager.GameStateManager;


public class GameOverState extends GameState{
	
	private SpriteBatch sb;
	private BitmapFont titleFont;
	private BitmapFont font;
	private String title;
	private int currentItem;
	private String[] menuItems;
	
	/**
	 * GameOverState's constructor
	 * @param gsm: game state manager
	 */
	public GameOverState(GameStateManager gsm){
		super(gsm);
	}

	/**
	 * calls once the GameOverState is initialized,
	 * display which player wins
	 * Menu - quit
	 */
	public void init() {
		Pong.playState=4;
		winner();
		//reset the scores
		Pong.scoreJ1=0;
		Pong.scoreJ2=0;
		sb=new SpriteBatch();
		initFonts();
	}

	/**
	 * check which player wins
	 */
	public void winner(){
		if(Pong.scoreJ1==3){
			title="PLAYER 1 WINS";
		}else{
			title="PLAYER 2 WINS";
		}
	}
	
	/**
	 * initialize the fonts
	 */
	public void initFonts(){
		titleFont= new BitmapFont();
		titleFont.setScale(3.5f);
		titleFont.setColor(Color.WHITE);
		font=new BitmapFont();
		font.setScale(2.5f);
		menuItems=new String[]{"Menu","Quitter"};
	}
	/**
	 * update the screen
	 */
	public void update(float dt) {
		handleInput();
		
	}

	/**
	 * draw the different elements
	 */
	public void draw() {
		sb.setProjectionMatrix(Pong.cam.combined);
		sb.begin();
		//draw title
		float width=titleFont.getBounds(title).width;
		titleFont.draw(sb,title,(Pong.WIDTH-width)/2,0.66f*Pong.HEIGHT);
		//draw menu
		for(int i=0;i<menuItems.length;i++){
			width=font.getBounds(menuItems[i]).width;
			font.draw(sb, menuItems[i],(Pong.WIDTH-width)/2 ,Pong.HEIGHT/2-70*i);
		}
		sb.end();
	}
	
	
	
	//for computer, useless on the phone

	/**
	 * listen to the keyboard
	 */
	public void handleInput() {
		if(GameKeys.isPressed(GameKeys.UP)){
			if(currentItem>0)currentItem--;
		}
		if(GameKeys.isPressed(GameKeys.DOWN)){
			if(currentItem< menuItems.length-1){
				currentItem++;
			}
		}
		if(GameKeys.isPressed(GameKeys.ENTER)){
			select();
		}
	}

	/**
	 * when enter is pressed, execute the action related to the chosen element
	 */
	private void select(){
		if(currentItem==0){
			gsm.setState(GameStateManager.MENU);
		}
		else if(currentItem==1){
			Gdx.app.exit();
		}
	}
	
	public void dispose() {}

}


