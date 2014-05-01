package fr.ohm.biby.gamestates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ohm.biby.biby;
import fr.ohm.biby.manager.GameKeys;
import fr.ohm.biby.manager.GameStateManager;

public class Instruction extends GameState{
	
	private SpriteBatch sb;
	private BitmapFont titleFont;
	public static BitmapFont font;
	public static BitmapFont beginFont;
	private final String title="Labyrinthe";
	private String []instru;
	private String commencer=">> Touchez l'écran pour commencer <<";
	
	/**
	 * MenuState's constructor
	 * @param gsm: choose a state (menu-play-instructions)
	 */
	public Instruction(GameStateManager gsm){
		super(gsm);
	}

	/**
	 * initialize the elements Title, the instructions, and the play button
	 */
	public void init() {
		instru=new String[]{"Bienvenue dans le jeu du labyrinthe", 
				"Trouvez l'arrivée et vous serez récompensé",
				"Pour y arriver vous devrez vous mefier",
				"Une direction empreintée est une direction choisie",
				"Les retours en arrière sont permis",
				"Mais vous ne pourrez pas vous arrêter au milieu de nos allées",
				"Jusqu'au bout vous serez dirigés"};
		biby.playState=2;
		sb=new SpriteBatch();
		titleFont=new BitmapFont();
		beginFont=new BitmapFont();
		beginFont.setColor(Color.WHITE);
		beginFont.setScale(1.8f);
		titleFont.setScale(3f);
		titleFont.setColor(Color.WHITE);
		font= new BitmapFont();
		font.setScale(1f);
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
		titleFont.draw(sb,title,(biby.WIDTH-width)/2,0.88f*biby.HEIGHT);
		//draw instructions
		for(int i=0;i<instru.length;i++){
			width=font.getBounds(instru[i]).width;
			font.draw(sb, instru[i],(biby.WIDTH-width)/2 ,0.7f*biby.HEIGHT-50*i);
		}		
		//draw play button
		width=beginFont.getBounds(commencer).width;
		beginFont.draw(sb,commencer,(biby.WIDTH-width)/2,0.1f*biby.HEIGHT);
		sb.end();
	}

	//only for computer, useless for the phone
	/**
	 * listen to the keyboard
	 */
	public void handleInput() {
		if(GameKeys.isPressed(GameKeys.ENTER)){
			gsm.setState(GameStateManager.PLAY);
		}
	}

	public void dispose() {
		
	}

}
