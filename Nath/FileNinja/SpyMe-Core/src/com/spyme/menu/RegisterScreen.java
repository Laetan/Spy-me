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
	Texture  texture;
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
		texture = new Texture(Gdx.files.internal("inscription_t.png"));
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
		game.batch.draw(texture,0,0,game.width,game.height);
		font.draw(game.batch, message, game.width/2.3f, game.height/1.56f);
		font2.draw(game.batch, messageErreur, game.width/5, game.height/1.35f);
		font.draw(game.batch, message2, game.width/2.3f, game.height/1.83f);
		font.draw(game.batch, message1, game.width/2.3f, game.height/2.23f);
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
			if(screenX>0.42f*game.width&&screenX<0.83f*game.width){
				if(screenY>0.34f*game.height&&screenY<0.46f*game.height){
					Gdx.input.getTextInput(listener, "pseudo", "");
					display=0;
				}else if(screenY>0.47f*game.height&&screenY<0.555f*game.height){
					Gdx.input.getTextInput(listener, "mdp", "");
					display=1;
				}else if(screenY>0.56f*game.height&&screenY<0.65f*game.height){
					Gdx.input.getTextInput(listener, "verification mdp", "");
					display=2;
				}
				
			}
			if(screenY>0.65f*game.height&&screenY<0.73f*game.height){
				if(screenX>0.16f*game.width&&screenX<0.39f*game.width){
					 if(samePw(message1,message2)==1)
				        {  
						 Spyme.state=1;
						 String url="inscription?pseudo="+game.convertSpaces(message)+"&password="+game.convertSpaces(message2);
						 game.httpReq(url);
						 while(game.repServeur == null){System.out.println(game.repServeur);}

						//if(RegisterScreen.coRepServeur != null){
						 checkIns(game.repServeur, message);
						RegisterScreen.coRepServeur=null;}
						 //valider
				}else if(screenX>0.57f*game.width&&screenX<0.82f*game.width){
					game.setScreen(new LogInScreen(game));
				}
			}
			//}
			System.out.println(screenX+" "+screenY);
			return false;
		}

	    public void checkIns(String answer, String psd){
	    	
	    	Integer code;
	    	if(answer.length()>3){
		    	String[] tokens=answer.split("/");
		    	game.player.id_temp=tokens[1];
				game.player.pseudo=game.convertSpaces(psd);
				game.setScreen(new MenuScreen(game));
	    	}
	    	else{
	    		code=Integer.valueOf(answer);
		    	game.decodeServ(code,"");
		    	}
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