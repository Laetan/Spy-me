package fr.schaltiemi.pong.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.schaltiemi.pong.Pong;
import fr.schaltiemi.pong.manager.GameKeys;
import fr.schaltiemi.pong.manager.GameStateManager;

public class MenuState extends GameState{
	
	private SpriteBatch sb;
	private BitmapFont titleFont;
	public static BitmapFont font;
	private final String title="Spong";
	private int currentItem;
	public static String[] menuItems;
	
	/**
	 * MenuState's constructor
	 * @param gsm: choose a state (menu-play-blackscreen-gameover)
	 */
	public MenuState(GameStateManager gsm){
		super(gsm);
	}

	/**
	 * initialize the elements Title and Menu
	 */
	public void init() {
		Pong.playState=2;
		sb=new SpriteBatch();
		titleFont=new BitmapFont();
		titleFont.setScale(3.5f);
		titleFont.setColor(Color.WHITE);
		font= new BitmapFont();
		font.setScale(2.5f);
		menuItems=new String[]{"Facile","Moyen","Difficile","Quitter"};
	}

	/**
	 * update the screen
	 */
	public void update(float dt) {
		handleInput();
	}

	/**
	 * draw the elements on the screen
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

	
	
	
	
	//only for computer, useless for the phone
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
	 * change the ball speed/maxspeed, the IA speed related to the level chosen
	 * @param ts: new ballSpeed
	 * @param ias: new IA paddle speed
	 * @param bms: new ball maximum speed
	 */
	private void setConfig(int ts, int ias, int bms){
		Pong.tempSpeed=ts;
		Pong.IASpeed=ias;
		Pong.ballMaxSpeed=bms;
	}
	/**
	 * when enter is pressed, execute the instruction associated to the selection
	 */
	private void select(){
		if(currentItem==0){
			setConfig(13,11,23);
			gsm.setState(GameStateManager.PLAY);
		}
		else if(currentItem==1){
			setConfig(18,15,28);
			gsm.setState(GameStateManager.PLAY);
		}
		else if(currentItem==2){
			setConfig(18,15,43);
			gsm.setState(GameStateManager.PLAY);
		}
		else if(currentItem==3){
			Gdx.app.exit();
		}
	}
	public void dispose() {
		
	}

}
