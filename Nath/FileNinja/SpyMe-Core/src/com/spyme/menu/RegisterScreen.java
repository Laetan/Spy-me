package com.spyme.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.spyme.core.Spyme;

public class RegisterScreen implements Screen{

//****************************************************************************************
//****************************************************************************************
	Spyme game;
	OrthographicCamera cam;
	Texture [] textures;
	private MyTextInputListener listener;
	private BitmapFont font ;
	private BitmapFont font2 ;
	private String message="";
	private String message1="";
	private String message2="";
	public static String messageErreur="";
	public static String coRepServeur;
	private int display;
	
//****************************************************************************************
//****************************************************************************************	
	public RegisterScreen(Spyme gam){
		Gdx.graphics.setContinuousRendering(true);
		font = new BitmapFont();
		font2 = new BitmapFont();
		font.setColor(Color.BLACK);
		game = gam;
		textures = new Texture[5];
		textures[0] = new Texture(Gdx.files.internal("inscription.png"));
		textures[1] = new Texture(Gdx.files.internal("annuler.png"));
		textures[2] = new Texture(Gdx.files.internal("annuler2.png"));
		textures[3] = new Texture(Gdx.files.internal("valider.png"));
		textures[4] = new Texture(Gdx.files.internal("valider2.png"));
		cam = new OrthographicCamera();
		cam.setToOrtho(true, game.width, game.height);
		cam.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.input.setInputProcessor(new RegisterHandler());
		listener=new MyTextInputListener();
	}
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(textures[0],game.width/6,game.height/3,game.width/1.5f,game.height/2.5f);
		game.batch.draw(textures[4],game.width/5,game.height/3.f,game.width/4,game.width/6);
		game.batch.draw(textures[2],4*game.width/5-game.width/4,game.height/3.f,game.width/4,game.width/6);
		font.draw(game.batch, message, game.width/2.6f, game.height/1.6f);
		font2.draw(game.batch, messageErreur, game.width/10, game.height/1.35f);
		font.draw(game.batch, message2, game.width/2.6f, game.height/1.82f);
		font.draw(game.batch, message1, game.width/2.6f, game.height/2.12f);
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

	private class RegisterHandler implements InputProcessor{

		public boolean keyDown(int keycode) {
			return false;
		}

		public boolean keyUp(int keycode) {
			return false;
		}

		public boolean keyTyped(char character) {
			return false;
		}

		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			if(screenX>180&&screenX<385){
				if(screenY>325&&screenY<350){
					Gdx.input.getTextInput(listener, "pseudo", "");
					display=0;
				}else if(screenY>380&&screenY<410){
					Gdx.input.getTextInput(listener, "mdp", "");
					display=1;
				}else if(screenY>430&&screenY<465){
					Gdx.input.getTextInput(listener, "verification mdp", "");
					display=2;
				}
				
			}
			if(screenY>480&&screenY<550){
				if(screenX>120&&screenX<210){
					 if(samePw(message1,message2)==1)
				        {  
						 Spyme.state=1;
						 httpHandler http=new httpHandler();
						 String url="http://192.168.5.76:8888/inscription?pseudo="+game.convertSpaces(message)+"&password="+game.convertSpaces(message2);
						 http.get(url);
						 while(RegisterScreen.coRepServeur == null){System.out.println(RegisterScreen.coRepServeur);}
						System.out.println(RegisterScreen.coRepServeur);
						//if(RegisterScreen.coRepServeur != null){
						game.checkIns(coRepServeur, message);
						RegisterScreen.coRepServeur=null;}
						 //valider
				}else if(screenX>290&&screenX<480){
					game.setScreen(new LogInScreen(game));
				}
			}
			//}
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

        public void input (String text) {
      	  if(display==0){
      		  message=text;
      	  } else if(display==1){
				message2=text;
      	  } else{
      		  message1=text;
		   }
        }
        
		   public void canceled () {
		   }
		}
	
//****************************************************************************************
//****************************************************************************************
	
    public int samePw(String s1, String s2){
    	if(s1.equals(s2)){
    		return 1;
    	}else{
    		messageErreur="Veuillez saisir deux fois le même password.";
    		return 0;
    	}
    }
}
