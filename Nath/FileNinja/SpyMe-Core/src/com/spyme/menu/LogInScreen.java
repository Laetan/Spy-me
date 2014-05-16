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
	Texture texture;
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
		texture=new Texture(Gdx.files.internal("connexion_t.png"));
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
		game.batch.draw(texture,0,0,game.width,game.height);
		font.setColor(Color.BLACK);
		font.draw(game.batch, message, game.width/2.3f, game.height/1.7f);
		font2.draw(game.batch, messageErreur, game.width/5, game.height/1.17f);
		font.draw(game.batch, message2, game.width/2.3f, game.height/2.04f);
		
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

    public void checkUser(String answer, String psd){
    	Integer code;
    	if(answer.length()>3){
    	String[] tokens=answer.split("/");
    	game.player.id_temp=tokens[1];
		game.player.level=Integer.valueOf(tokens[3]);
		game.player.score=Integer.valueOf(tokens[2]);
		game.player.pseudo=game.convertSpaces(psd);
		game.setScreen(new MenuScreen(game));
    	}
    	else{
    		code=Integer.valueOf(answer);
    		game.decodeServ(code,"");
    		}
    	}

	public void dispose() {
		
			texture.dispose();
		

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
			if(screenX<0.83f*game.width&&screenX>0.41f*game.width){
				if(screenY<0.507f*game.height&&screenY>0.39f*game.height){
					Gdx.input.getTextInput(listener, "pseudonyme", "");
					display=0;
				}
				else if(screenY<0.59f*game.height&&screenY>0.51f*game.height){
					Gdx.input.getTextInput(listener, "mot de passe", "");
					display=1;
				}
			}
			if(screenY<0.7f*game.height&&screenY>0.637f*game.height){
				if(screenX>0.57f*game.width&&screenX<0.83f*game.width){
					Gdx.app.exit();
				}
				else if(screenX>0.187f*game.width&&screenX<0.41f*game.width){
					Spyme.state=0;
					String url="connexion?pseudo="+game.convertSpaces(message)+"&password="+game.convertSpaces(message2);
					game.httpReq(url);
					while(game.repServeur == null){System.out.print("");}
					checkUser(game.repServeur,message);
					coRepServeur=null;
					 }
			}
			else if(screenY<0.9f*game.height&&screenY>0.81f*game.height){
				game.setScreen(new RegisterScreen(game));
				}
			System.out.println(screenX+" "+screenY);
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
