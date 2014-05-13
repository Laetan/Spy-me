package com.spyme.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;

public class EnigmaScreen implements Screen{

	public final Spyme game;
	OrthographicCamera cam;
	String[] enigma;
	String answer;
	int id_enigma;
	int l_enigma;
	int state;
	BitmapFont font;
	boolean keyPressed, synchro;
	Texture button;
	Texture quitButton;
	
	public EnigmaScreen(Spyme gam){
		game = gam;
		font = new BitmapFont();
		cam = new OrthographicCamera();
		button = new Texture(Gdx.files.internal("valider2.png"));
		quitButton = new Texture(Gdx.files.internal("quit.png"));
		cam.setToOrtho(false, game.width, game.height);
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		cam.update();
		game.batch.setProjectionMatrix(cam.combined);
		Gdx.input.setCatchBackKey(true);
		id_enigma = -1;
		enigma = new String[8];
		enigma[0] = "";
		answer = "";
		state = -1;
		keyPressed = synchro = false;
		font.setColor(Color.BLACK);
		getEnigma();
		Gdx.input.setInputProcessor(new InputHandler());
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(synchro){checkRep();}
		
		game.batch.begin();
			game.batch.draw(quitButton, 10, 10);
			if(id_enigma == -1){
				font.setScale(2);
				font.draw(game.batch, "Loading...", game.width/2 - 50, game.height/2-10);
			}
			else{
				font.setScale(1);
				int i = 0;
				for(String s : enigma){
					if(s!=null)font.draw(game.batch, s, game.width/2 - 3 * s.length(), game.height- 20*(++i) );
				}
				font.draw(game.batch, answer, game.width/2 - 3*answer.length(), game.height/2);

				if(answer.length()>0)
					game.batch.draw(button, game.width/2-128, 20);
			}
		

		game.batch.end();
	}

	private void getEnigma(){		
		game.httpReq("enigme?idTemp="+game.player.id_temp);
		synchro = true;
	}
	
	private void checkRep(){
		
		if(game.repServeur != null){
			synchro = false;
			if(enigma[0] == "")
				decodEnigma();
			else
				decodAnswer();
		}
	}
	
	private void decodEnigma(){
		int j = 0;
		if(game.repServeur.length()==3){
			game.decodeServ();
		}
		else{
			String[] tokens=game.repServeur.split("/");
			id_enigma=Integer.valueOf(tokens[1]);
			enigma[0] = tokens[2];
			System.out.println(enigma[0]);
			state = 0;
			
			while(true){
				l_enigma = enigma[j].length();
				System.out.println(l_enigma);
				if(l_enigma>40){
					for(int i = 30;i<l_enigma;i++){
						if(enigma[j].charAt(i)==' '){
							enigma[j+1]=enigma[j].substring(i+1);
							enigma[j]=enigma[j].substring(0, i);
							break;
						}
					}
					j++;
				}
				else
					break;
			}
			for(String s:enigma)
				System.out.println(s);
		}
	}
	
	private void decodAnswer(){
		if(game.decodeServ()==1)
		{
			System.out.println(game.repServeur);
			if(Integer.parseInt(game.repServeur) == 104){
				answer = "Correct !";
				System.out.println("Correct");
				state = 1;
	        	game.player.score +=10/(game.player.level+1);
	        	game.httpReq("updatepoints?idTemp="+game.player.id_temp+"&nbPoints="+game.player.score);
			}
			else{
				answer = "Nope !";
				System.out.println("Nope");
				state = 2;
			}
		}
	}
	
	private void sendAnswer(){

		String ans = convertSpaces(answer);
        game.httpReq("answer?id_enigme="+id_enigma+"&idTemp="+game.player.id_temp+"&answer="+ans);
        synchro = true;
        
	}
	
	private void redo(){
		id_enigma = -1;
		enigma[0]="";
		answer = "";
		getEnigma();
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		font.dispose();
		button.dispose();
		quitButton.dispose();
	}

	public String convertSpaces(String s1){
    	String s2="";
    	for(int i=0;i<s1.length();i++)
    		if(s1.charAt(i)==' ')
    			s2=s2+'+';
    		else
    			s2=s2+s1.charAt(i);   	
    	return s2.toLowerCase();
    }
	
	private class InputHandler implements InputProcessor{

		@Override
		public boolean keyDown(int keycode) {
			// TODO Auto-generated method stub
			if(keycode == Keys.BACK)
				Gdx.input.setOnscreenKeyboardVisible(false);
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			keyPressed = false;
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			if(!keyPressed && state == 0){
				answer = answer + character;
				keyPressed = true;
			}
			
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			Vector3 touchPos = new Vector3(screenX, screenY, 0);
			cam.unproject(touchPos);
			
			if(touchPos.x>10 && touchPos.x < 42 && touchPos.y > 10 && touchPos.y < 42){
				game.setScreen(new TestScreen(game));
				dispose();
			}
			
			if(state == 0){
				Gdx.input.setOnscreenKeyboardVisible(true);
				System.out.println(touchPos.x+" : "+touchPos.y);
				if(answer.length() > 0 && touchPos.x > game.width/2-128 && touchPos.x < game.width/2+128 && touchPos.y > 20 && touchPos.y < 148){
					sendAnswer();
				}
			}else if(state == 1){
				
				//Jeu Gagné. Switch Screen
				redo();
				
			}else if(state == 2){
				answer = "";
				state = 0;
			}
			
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
}
