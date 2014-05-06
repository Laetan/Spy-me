package com.spyme.core;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spyme.menu.LogInScreen;
import com.spyme.menu.MenuScreen;
import com.spyme.menu.RegisterScreen;
import com.spyme.menu.httpHandler;

public class Spyme extends Game{
	
//****************************************************************************************
//****************************************************************************************
	public SpriteBatch batch;
	public BitmapFont font;
	public int width;
	public int height;
	public int nbPoints=0;
	public int id_enigme=0;
	public String id_temp="";
	public String pseudo;
	public int niveau=0;
	public static int state;
//****************************************************************************************
//****************************************************************************************

	public void create() {
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		batch = new SpriteBatch();
		font = new BitmapFont(true);
		this.setScreen(new LogInScreen(this));
	}
	
	public void render()
	{
		super.render();
	}
	
	public void dispose()
	{
		batch.dispose();
		font.dispose();
	}

//****************************************************************************************
//****************************************************************************************
	
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
    		s="Gagné";
    		return 1;
    	case 105:
    		s="Perdu";
    		return 0;
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
	
    public void checkUser(String answer, String psd){
    	Integer code;
    	if(answer.length()>3){
    	String[] tokens=answer.split("/");
    	id_temp=tokens[1];
		niveau=Integer.valueOf(tokens[3]);
		nbPoints=Integer.valueOf(tokens[2]);
		pseudo=convertSpaces(psd);
		this.setScreen(new MenuScreen(this));
    	}
    	else{
    		code=Integer.valueOf(answer);
    		decodeServ(code,"");
    		}
    	}
    
    public void checkIns(String answer, String psd){
    	
    	Integer code;
    	if(answer.length()>3){
    	String[] tokens=answer.split("/");
    	id_temp=tokens[1];
		pseudo=convertSpaces(psd);
		this.setScreen(new MenuScreen(this));
		//setContentView(R.layout.design_try0);
    	}
    	else{
    		code=Integer.valueOf(answer);
	    	decodeServ(code,"");
	    	}
    	}


    
    public void checkEnigme(String answer,String result)
    {
    	if(answer.length()>3){
    		String[] tokens=answer.split("/");
    		id_enigme=Integer.valueOf(tokens[1]);
    		result=tokens[2];
    	}
    	else{
    		Integer code= Integer.valueOf(answer);
    		decodeServ(code,result);
    	}
    }
    
    public void checkAnswerEnigme(int code,String errorM)
    {
    	if(decodeServ(code,errorM)==1){
    		httpHandler handler2 = new httpHandler();
    		nbPoints+=10/(niveau+1);
    		System.out.println(nbPoints);
    		
	        String url="http://192.168.5.65:8888/updatepoints?idTemp="+id_temp+"&nbPoints="+nbPoints;
			//String repServeur = handler2.get(url);
    	}
    }
}

