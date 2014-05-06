package com.spyme.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.spyme.core.Spyme;

public class LogInScreen implements Screen{

//**********************************************attributes********************************
//****************************************************************************************
	Spyme game;
	OrthographicCamera cam;
	Texture [] textures;
	public static String coRepServeur;
	private BitmapFont font ;
	private BitmapFont font2 ;
	private MyTextInputListener listener;
	private String message="";
	private String message2="";
	public static String messageErreur="";
    private int display;
    
//****************************************************************************************
//****************************************************************************************	
	
 /**
  * initialize the different elements   
  * @param gam: instance of the game
  */
    public LogInScreen(Spyme gam){
		Gdx.graphics.setContinuousRendering(true);
		game = gam;
		font = new BitmapFont();
		font2 = new BitmapFont();
		display=0;
		textures = new Texture[4];
		textures[0] = new Texture(Gdx.files.internal("connexion.png"));
		textures[1] = new Texture(Gdx.files.internal("valider2.png"));
		textures[2] = new Texture(Gdx.files.internal("annuler2.png"));
		textures[3] = new Texture(Gdx.files.internal("incription_co.png"));
		cam = new OrthographicCamera();
		cam.setToOrtho(true, game.width, game.height);
		cam.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.input.setInputProcessor(new LogInHandler());
		listener=new MyTextInputListener();
	}
    
    /**
     * draw the different elements
     */
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(textures[0],game.width/10,game.height/3,game.width/1.3f,game.height/2.5f);
		game.batch.draw(textures[1],game.width/5,game.height/3.1f,game.width/4,game.width/5);
		game.batch.draw(textures[2],4*game.width/5-game.width/4,game.height/3.1f,game.width/4,game.width/5);
		game.batch.draw(textures[3],game.width/10,game.height/6.5f,game.width/1.2f,game.width/5);
		font.setColor(Color.BLACK);
		font.draw(game.batch, message, game.width/2.75f, game.height/1.68f);
		font2.draw(game.batch, messageErreur, game.width/10, game.height/1.3f);
		font.draw(game.batch, message2, game.width/2.75f, game.height/2);
		game.batch.end();
	}
	

	public void resize(int width, int height) {
	}

	public void show() {
	}

	public void hide() {
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
	}

//****************************************************************************************
//****************************************************************************************
	
	private class LogInHandler implements InputProcessor{

		public boolean keyDown(int keycode) {
			return false;
		}

		public boolean keyUp(int keycode) {
			return false;
		}

		public boolean keyTyped(char character) {
			return false;
		}
		
		/**
		 * called when the user touch the screen
		 * trigger the events
		 */
		public boolean touchDown(int screenX, int screenY, int pointer,int button) {
			if(screenX<game.width/2.75f+300&&screenX>game.width/2.75f){
				if(screenY<game.height/1.68f-20&&screenY>game.height/1.68f-70){
					Gdx.input.getTextInput(listener, "mdp", "");
					display=1;
				}
				else if(screenY<game.height/1.68f-90&&screenY>game.height/1.68f-140){
					Gdx.input.getTextInput(listener, "pseudonyme", "");
					display=0;
				}
			}
			if(screenY<game.height/1.48f&&screenY>game.height/1.7f){
				if(screenX>4*game.width/5-game.width/4&&screenX<4*game.width/5){
					Gdx.app.exit();
				}
				else if(screenX>game.width/5&&screenX<game.width/5+game.width/4){
					Spyme.state=0;
					httpHandler http=new httpHandler();
					String url="http://192.168.5.76:8888/connexion?pseudo="+game.convertSpaces(message)+"&password="+game.convertSpaces(message2);
					http.get(url);
					while(coRepServeur == null){System.out.print("");}
					game.checkUser(coRepServeur,message);
					coRepServeur=null;
					 }
			}
			else if(screenY<game.height/1.3f+game.width/10&&screenY>game.height/1.32f){
				game.setScreen(new RegisterScreen(game));
				}
			return false;
		}

		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		public boolean touchDragged(int screenX, int screenY, int pointer) {
			return false;
		}

		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		public boolean scrolled(int amount) {
			return false;
		}
	}
	
	//****************************************************************************************
	//****************************************************************************************
	public class MyTextInputListener implements TextInputListener {

		/**
		 * called when an input is needed
		 */
		public void input (String text) {
        	  if(display==1)
        		  message2=text;
        	  else
				message=text;
				display++;
		   }

		   public void canceled () {
		   }
		}

	
}
