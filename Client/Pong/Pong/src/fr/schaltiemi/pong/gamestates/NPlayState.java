package fr.schaltiemi.pong.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.schaltiemi.pong.Pong;
import fr.schaltiemi.pong.manager.GameKeys;
import fr.schaltiemi.pong.manager.GameStateManager;

public class NPlayState extends GameState{
	
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
	public NPlayState(GameStateManager gsm){
		super(gsm);
	}

	/**
	 * initialize the elements Title and Menu
	 */
	public void init() {
		Pong.playState=1;
		sb=new SpriteBatch();
		setFonts();
	}

	/**
	 * initialize fonts properties 
	 */
	public void setFonts(){
		titleFont=new BitmapFont();
		titleFont.setScale(3.5f);
		titleFont.setColor(Color.WHITE);
		font= new BitmapFont();
		font.setScale(2.5f);
		menuItems=new String[]{"Un joueur","Deux joueurs","Quitter"};
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
		titleFont.draw(sb,title,(Pong.WIDTH-width)/2,Pong.HEIGHT-Pong.HEIGHT/3);
		
		//draw menu
		for(int i=0;i<menuItems.length;i++){
			width=font.getBounds(menuItems[i]).width;
			font.draw(sb, menuItems[i],(Pong.WIDTH-width)/2 ,Pong.HEIGHT-Pong.HEIGHT/2-70*i);
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
	 * when enter is pressed, execute the instruction associated to the selection
	 */
	private void select(){
		if(currentItem==0){
			gsm.setState(GameStateManager.MENU);
			Pong.isIA=true;
		}
		else if(currentItem==1){
			gsm.setState(GameStateManager.MENU);
			Pong.isIA=false;
		}
		else if(currentItem==2){
			Gdx.app.exit();
		}
	}
	public void dispose() {
		
	}
}