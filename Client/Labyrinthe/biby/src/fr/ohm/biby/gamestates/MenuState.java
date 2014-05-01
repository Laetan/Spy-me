package fr.ohm.biby.gamestates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ohm.biby.biby;
import fr.ohm.biby.manager.GameStateManager;

public class MenuState extends GameState{

	private SpriteBatch sb;
	private BitmapFont titleFont;
	public static BitmapFont font;
	private final String title="Gagné";
	public static String[] menuItems;
	
	/**
	 * MenuState's constructor
	 * @param gsm: choose a state (menu-play-instructions)
	 */
	public MenuState(GameStateManager gsm){
		super(gsm);
	}

	/**
	 * initialize the elements Title and Menu
	 */
	public void init() {
		biby.cam.position.set(biby.WIDTH/2,biby.HEIGHT/2,0);
		biby.cam.update();
		biby.playState=0;
		sb=new SpriteBatch();
		titleFont=new BitmapFont();
		titleFont.setScale(3.5f);
		titleFont.setColor(Color.WHITE);
		font= new BitmapFont();
		font.setScale(2.5f);
		menuItems=new String[]{"Rejouer","Quitter"};
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
		sb.setProjectionMatrix(biby.cam.combined);
		sb.begin();
		//draw title
		float width=titleFont.getBounds(title).width;
		titleFont.draw(sb,title,(biby.WIDTH-width)/2,0.66f*biby.HEIGHT);
		//draw menu
		for(int i=0;i<menuItems.length;i++){
			width=font.getBounds(menuItems[i]).width;
			font.draw(sb, menuItems[i],(biby.WIDTH-width)/2 ,biby.HEIGHT/2-70*i);
		}
		sb.end();
	}
	
	public void dispose() {
		
	}

	public void handleInput() {
		
	}

}
