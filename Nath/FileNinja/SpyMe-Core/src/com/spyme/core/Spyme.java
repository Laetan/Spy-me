package com.spyme.core;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spyme.fileninja.StartScreen;
import com.spyme.menu.*;

@SuppressWarnings("unused")
public class Spyme extends Game{
	
	public SpriteBatch batch;
	public BitmapFont font;
	public int width;
	public int height;
	public HttpHandler httpHandler;
	public int id_temp;
	public Player player;
	public static int state;
	public String repServeur;
	
	@Override
	public void create() {
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		batch = new SpriteBatch();
//		font = new BitmapFont(true);
		httpHandler = new HttpHandler(this);
		player = new Player();
		font = new BitmapFont(Gdx.files.internal("font.fnt"),true);

		this.setScreen(new EnigmaScreen(this));
	}
	@Override
	public void render()
	{
		super.render();
		
	}
	
	@Override
	public void dispose()
	{
		batch.dispose();
		font.dispose();
	}

	
	private void errorPop(String err){
		//Fonction d'affichage des error serveur (popup)
	}
	
	public int decodeServ(){
		
		if(repServeur.length()>3){
			String[] code = repServeur.split("/");
			return decodeServ(Integer.parseInt(code[0]),"");
		}
		else
			return decodeServ(Integer.parseInt(repServeur),"");
	}
	public int decodeServ(int code, String s){
    	switch (code){
    	case 100:
    		return 1;
    	case 101:
    		RegisterScreen.messageErreur="Ce pseudonyme est déjà utilisé.";
    		return 0;
    	case 102:
    		RegisterScreen.messageErreur="Il y a des champs non remplis";
    		return 0;
    	case 103:
    		LogInScreen.messageErreur="Le pseudonyme ou le password est incorrect";
    		return 0;
    	case 104:
    		return 1;
    	case 105:
    		return 1;
    	case 106:
    		s="Vous avez résolu toutes les énigmes de ce niveau. Plus d'énigmes disponibles";
    		return 0;
    	default:
    		s="unknown error";
    		return 0;
    	}
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

	public void httpReq(String url){
		repServeur = null;
		System.out.println(url);
		httpHandler.get("http://192.168.1.24:8888/"+url);
	}
    
	public class Player{
		public String pseudo;
		public String id_temp;
		public int score;
		public int level;
		
		public Player(){
			id_temp = pseudo = "";
			score = level = 0;
			id_temp = "-1317385271246848";
		}
	}
}

