

package com.example.enigme;
import java.security.Policy;

import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends Activity {
	


	private int nbPoints=0;
	private int id_enigme=0;
	private String id_temp="";
	private String pseudo;
	private int niveau=0;
	
	//////////////////////////////////////////////////////////////////
	private boolean mIsBound = false;
	private MusicService mServ;
	private ServiceConnection Scon =new ServiceConnection(){

		public void onServiceConnected(ComponentName name, IBinder
	     binder) {
		mServ =((MusicService.ServiceBinder)binder).getService();
		}

		public void onServiceDisconnected(ComponentName name) {
			mServ = null;
		}
		};

		void doBindService(){
	 		bindService(new Intent(this,MusicService.class),
					Scon,Context.BIND_AUTO_CREATE);
			mIsBound = true;
		}

		void doUnbindService()
		{
			if(mIsBound)
			{
				unbindService(Scon);
	      		mIsBound = false;
			}
		}
	   
	////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	

        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);
        /* musique jeu
        doBindService();
        Intent music=new Intent();
        music.setClass(this, MusicService.class);
        startService(music);
        */
        setButtonClickListenerConnexion();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /***fonction relative aux echanges client-serveur
     * check le bon déroulement des opérations
     * affiche des messages dans les TextView correspondant aux codes d'erreur envoyé par le serveur
     * prend en 1er argument: le code envoyé par le serveur
     * prend en 2eme argument, le TextView où afficher l'éventuel message d'erreur
     * renvoie un entier pour dire si tout est ok (1) ou non (0)
     */

    public int decodeServ(int code, TextView t){
    	Button button2 = (Button)findViewById(R.id.button2);
    	switch (code){
    	case 100:
    		return 1;
    	case 101:
    		t.setText("Ce pseudonyme est déjà utilisé par un autre joueur.");
    		return 0;
    	case 102:
    		t.setText("Il y a des champs non remplis. Veuillez saisir votre pseudonyme et password?");
    		return 0;
    	case 103:
    		t.setText("Le pseudonyme ou le password est incorrect. Etes vous sûr d'être inscrit?");
    		return 0;
    	case 104:
    		t.setText("Gagné");
    		button2.setEnabled(false);//le bouton valider est désactiver
    		return 1;
    	case 105:
    		t.setText("Perdu");
    		return 0;
    	case 106:
    		t.setText("Vous avez résolu toutes les énigmes de ce niveau. Plus d'énigmes disponibles");
    		return 0;
    	default:
    		t.setText("unknown error");
    		return 0;
    	}
    }
    
    /****fonction relative à la connexion****
     * check la validité du pseudonyme et du password
     * passe au menu si tout est ok, affiche un message d'erreur sinon, enregistre les info relatives à l'utilisateur
     * prend en 1er argument: la réponse renvoyée par le serveur (s0)
     * 2eme argument: l'objet TextView où seront affichées les eventuelles erreurs
     * 3eme argument: l'EditText permettant de récupérer le pseudonyme
     * ne renvoie rien
    */
    public void checkUser(String answer, TextView t, EditText ed){
    	Integer code;
    	if(answer.length()>3){
    	String[] tokens=answer.split("/");
    	id_temp=tokens[1];
		niveau=Integer.valueOf(tokens[3]);
		nbPoints=Integer.valueOf(tokens[2]);
		pseudo=convertSpaces(ed.getText().toString());
		setContentView(R.layout.design_try0);
        setButtonClickListener();
    	}
    	else{
    		code=Integer.valueOf(answer);
    		decodeServ(code,t);
    	}
    	}
    
    /****fonction relative à l'inscription****
     * check la disponibilité du pseudonyme
     * passe au menu si tout est ok, affiche un message d'erreur sinon. Enregistre le pseudonyme.
     * prend en 1er argument: la réponse renvoyée par le serveur (s1)
     * 2eme argument: l'objet TextView où seront affichées les eventuelles erreurs
     * 3eme argument: l'objet EditText pour récupérer le pseudonyme
     * ne renvoie rien
    */
    public void checkIns(String answer, TextView t, EditText ed){
    	
    	Integer code;
    	if(answer.length()>3){
    	String[] tokens=answer.split("/");
    	id_temp=tokens[1];
		pseudo=convertSpaces(ed.getText().toString());
		setContentView(R.layout.design_try0);
        setButtonClickListener();
    	}
    	else{
    		code=Integer.valueOf(answer);
    		decodeServ(code,t);
    	}
    	}

    
    /****fonction relative à l'inscription****
     * check que l'utilisateur entre bien 2 fois le même password lors de l'inscription
     * affiche un message d'erreur si ce n'est pas le cas
     * prend en 1er et 2eme argument: les deux password entrés
     * 3eme argument: l'objet TextView où seront affichées les eventuelles erreurs
     * renvoie un entier 1 si ok 0 sinon
    */
    public int samePw(String s1, String s2, TextView t){
    	if(s1.equals(s2)){
    		return 1;
    	}
    	else{
    		t.setText("Veuillez saisir deux fois le même password.");
    		return 0;
    	}
    }
    /****fonction relative à l'énigme****
     * affiche l'enigme
     * prend en 1er argument: la réponse du serveur
     * 2eme argument: l'objet TextView où sera affiché Gagné ou Perdu
     * ne renvoie rien
    */
    public void checkEnigme(String answer,TextView t)
    {
    	if(answer.length()>3){
    		String[] tokens=answer.split("/");
    		id_enigme=Integer.valueOf(tokens[1]);
    		t.setText(tokens[2]);
    	}
    	else{
    		Integer code= Integer.valueOf(answer);
    		decodeServ(code,t);
    	}
    	
    	
    }
    
    
    /****fonction relative à l'énigme****
     * regarde si la réponse donnée correspond bien à une réponse valide
     * affiche gagné ou perdu
     * prend en 1er argument: la réponse du serveur
     * 2eme argument: l'objet TextView où sera affiché Gagné ou Perdu
     * ne renvoie rien
    */
    public void checkAnswerEnigme(int code,TextView t)
    {
    	if(decodeServ(code,t)==1){
    		nbPoints+=10/(niveau+1);
    	}
    }
    /****fonction relative à l'enigme****
     * change les espaces en "+" pour pouvoir envoyer la réponse au serveur
     * passe la réponse en minuscules
     * prend en argument: la string à convertir
     * renvoie la string convertie
    */
    public String convertSpaces(String s1){
    	String s2="";
    	for(int i=0;i<s1.length();i++)
    		if(s1.charAt(i)==' ')
    			s2=s2+'+';
    		else
    			s2=s2+s1.charAt(i);   	
    	return s2.toLowerCase();
    }
    
/*---------------------------------------------------------------------
---connexion-----------------------------------------------------------
---------------------------------------------------------------------*/
    private void setButtonClickListenerConnexion() { 
	 	Button coButton1=(Button)findViewById(R.id.coButton1);
        Button coButton2 = (Button)findViewById(R.id.coButton2);
        Button coButton3 = (Button)findViewById(R.id.coButton3);

        
        //fonction relative au bouton "annuler"
        coButton1.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) { 
        	/*  doUnbindService();
        	  mServ.stopMusic();*/
        	  finish();
          } 
        }); 
        
        //fonction relative au bouton "valider"
        coButton2.setOnClickListener(new View.OnClickListener() {
	          public void onClick(View v) { 
	        EditText coEd1=(EditText)findViewById(R.id.coEditText1);
	        EditText coEd2=(EditText)findViewById(R.id.coEditText2);
	        TextView coTw=(TextView)findViewById(R.id.coTextView3);
	        httpHandler handlerCo1 = new httpHandler();
	        String coRepServeur = handlerCo1.post("http://192.168.5.199:8888/connexion?pseudo="+convertSpaces(coEd1.getText().toString())+"&password="+convertSpaces(coEd2.getText().toString()));
	       // String coRepServeur = handlerCo1.doInBackground("http://192.168.5.199:8888/connexion?pseudo="+convertSpaces(coEd1.getText().toString())+"&password="+convertSpaces(coEd2.getText().toString()));
	        checkUser(coRepServeur, coTw, coEd1);
	          } 
	        }); 
        //fonction relative au bouton "inscription"
        coButton3.setOnClickListener(new View.OnClickListener() {
	          public void onClick(View v) {

	        	  setContentView(R.layout.inscription);
	        	  setButtonClickListenerInscription();
		
	          } 
	        });	
          } 
/*---------------------------------------------------------------------
---inscription---------------------------------------------------------
---------------------------------------------------------------------*/
    private void setButtonClickListenerInscription() { 
	 	Button insButton1=(Button)findViewById(R.id.insButton1);
        Button insButton2 = (Button)findViewById(R.id.insButton2);

        
        //fonction relative au bouton "annuler"
        insButton1.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) { 
        	  setContentView(R.layout.connexion);
        	  setButtonClickListenerConnexion();
          } 
        }); 
        
        //fonction relative au bouton "valider"
        insButton2.setOnClickListener(new View.OnClickListener() {
	          public void onClick(View v) { 
	        TextView insTw=(TextView)findViewById(R.id.insTextView);
	        EditText insEd1=(EditText)findViewById(R.id.insEditText1);
	        EditText insEd2=(EditText)findViewById(R.id.insEditText2);
	        EditText insEd3=(EditText)findViewById(R.id.insEditText3);
	        if(samePw(insEd2.getText().toString(),insEd3.getText().toString(),insTw)==1)
	        {
		        httpHandler handlerIns1 = new httpHandler();
		        String insRepServeur = (handlerIns1.post("http://192.168.5.199:8888/inscription?pseudo="+convertSpaces(insEd1.getText().toString())+"&password="+convertSpaces(insEd2.getText().toString())));
		        checkIns(insRepServeur, insTw, insEd1);
		      
	        }

	          } 
	        }); 
 
          } 
/*---------------------------------------------------------------------
 ---menu---------------------------------------------------------------
---------------------------------------------------------------------*/
   
    //détection d'évènements sur les bouttons du menu
	 private void setButtonClickListener() { 		 
	        Button but1 = (Button)findViewById(R.id.butm1);
	        Button but3=(Button)findViewById(R.id.butm3);
	        Button but5 = (Button)findViewById(R.id.butm5);
	        //fonction relative au boutton "continuer"
	        but1.setOnClickListener(new View.OnClickListener() {
	          public void onClick(View v) { 
	        	  setContentView(R.layout.enigme02);
	        	  setButtonClickListenerEnigme();
	          } 
	        });
	        //fonction relative au boutton "options"
	        but3.setOnClickListener(new View.OnClickListener() {
		          public void onClick(View v) { 
		        	  setContentView(R.layout.options);
		        	  setButtonClickListenerOptions();
			
		          } 
		        });
	        //fonction relative au boutton "deconnexion"
	        but5.setOnClickListener(new View.OnClickListener() {
		          public void onClick(View v) { 
		        	  setContentView(R.layout.deconnexion);
		        	  setButtonClickListenerDeco();
			
		          } 
		        });
	 }
/*---------------------------------------------------------------------
 ---options---------------------------------------------------------
 ---------------------------------------------------------------------*/
	
	 //detection d'évènements sur les boutons d'"options"
	 private void setButtonClickListenerOptions() { 
		 	
	        CheckBox bop1 = (CheckBox)findViewById(R.id.opCheckBox1);
	        CheckBox bop2 = (CheckBox)findViewById(R.id.opCheckBox2);
	        CheckBox bop3 = (CheckBox)findViewById(R.id.opCheckBox3);
	        CheckBox bop4 = (CheckBox)findViewById(R.id.opCheckBox4);
	        Button bReinit=(Button)findViewById(R.id.opButton1);
	        ImageButton retour=(ImageButton)findViewById(R.id.opRetour);
	        //fonction relative au bouton "oui" de Activer son
	        bop1.setOnClickListener(new View.OnClickListener() {
	          public void onClick(View v) { 
	        	  CheckBox bop2 = (CheckBox)findViewById(R.id.opCheckBox2);
	        	  bop2.setChecked(false);
	        	  //mServ.resumeMusic();//musique
	          } 
	        }); 
	        //fonction relative au bouton "non" de Activer son
	        bop2.setOnClickListener(new View.OnClickListener() {
	          public void onClick(View v) { 
	        	  CheckBox bop1 = (CheckBox)findViewById(R.id.opCheckBox1);
	        	  bop1.setChecked(false);  
	        	  //mServ.pauseMusic();//musique
	          } 
	        }); 
	        //fonction relative au bouton "oui" des effets
	        bop3.setOnClickListener(new View.OnClickListener() {
	          public void onClick(View v) { 
	        	  CheckBox bop4 = (CheckBox)findViewById(R.id.opCheckBox4);
	        	  bop4.setChecked(false);  
	          } 
	        }); 
	        //fonction relative au bouton "non" des effets
	        bop4.setOnClickListener(new View.OnClickListener() {
	          public void onClick(View v) { 
	        	  CheckBox bop3 = (CheckBox)findViewById(R.id.opCheckBox3);
	        	  bop3.setChecked(false);  
	          } 
	        });
	        //fonction relative au bouton retour
	        retour.setOnClickListener(new View.OnClickListener() {
	          public void onClick(View v) { 
	        	  setContentView(R.layout.design_try0);
	        	  setButtonClickListener(); 
	          } 
	        });

	        //fonction relative au bouton "réinitialiser"
	        bReinit.setOnClickListener(new View.OnClickListener() {
		          public void onClick(View v) { 
		        httpHandler handler2 = new httpHandler();
		        String txt2 =(handler2.post("http://192.168.5.199:8888/reinitialisation?pseudo="+pseudo));
	        	  setContentView(R.layout.design_try0);
	        	  setButtonClickListener(); 
		          } 
		        }); 
	 
	          } 
	     		      
	 /*---------------------------------------------------------------------
	 ---deconnexion---------------------------------------------------------
	 ---------------------------------------------------------------------*/
		
		 //detection d'évènements sur les boutons de déco
		 private void setButtonClickListenerDeco() { 
			 	
		        Button bdeco1 = (Button)findViewById(R.id.bdeco1);
		        Button bdeco2 = (Button)findViewById(R.id.bdeco2);
		        //fonction relative au bouton "oui"
		        bdeco1.setOnClickListener(new View.OnClickListener() {
		          public void onClick(View v) { 
		        httpHandler handler = new httpHandler();

		        String sCheck=handler.post("http://192.168.5.199:8888/deconnexion?pseudo="+pseudo);
		      /*  mServ.stopMusic();
		        doUnbindService();//stop musique*/
		         finish();
		          } 
		        }); 
		        
		        //fonction relative au bouton "non"
		        bdeco2.setOnClickListener(new View.OnClickListener() {
			          public void onClick(View v) { 
			        	  
			        	  setContentView(R.layout.design_try0);
			        	  setButtonClickListener();
			          } 
			        }); 
		 
		          } 
		     		    
	 /*---------------------------------------------------------------------
	 ---enigme--------------------------------------------------------------
	 ---------------------------------------------------------------------*/
		
		 //detection d'évènements sur les bouttons de l'enigme
		 private void setButtonClickListenerEnigme() { 
			 	ImageButton button3=(ImageButton)findViewById(R.id.retourm);
			 	
		        Button button = (Button)findViewById(R.id.button1);
		        Button button2 = (Button)findViewById(R.id.button2);
		        //fonction relative au bouton "enigme"
		        button.setOnClickListener(new View.OnClickListener() {
		          public void onClick(View v) { 
		        Button button = (Button)findViewById(R.id.button1);
		        Button button2 = (Button)findViewById(R.id.button2);
		        button2.setEnabled(true);//autorise l'appui du boutton valider
		        TextView t2= (TextView)findViewById(R.id.textView2);
		        EditText ed1=(EditText)findViewById(R.id.editText1);
		        TextView t= (TextView)findViewById(R.id.textView1);
		        button.setText("enigme suivante");
		        httpHandler handler = new httpHandler();
		        String txt = handler.post("http://192.168.5.199:8888/enigme?idTemp="+id_temp);
		        t2.setText("");
		        ed1.setText("");
		        checkEnigme(txt,t);
		          } 
		        }); 
		        
		        //fonction relative au bouton "valider (réponse)"
		        button2.setOnClickListener(new View.OnClickListener() {
			          public void onClick(View v) { 
					TextView t2= (TextView)findViewById(R.id.textView2);	  
			        EditText ed1=(EditText)findViewById(R.id.editText1);
			        httpHandler handler2 = new httpHandler();
			        Integer txt2 = Integer.valueOf(handler2.post("http://192.168.5.199:8888/answer?id_enigme="+id_enigme+"&idTemp="+id_temp+"&answer="+convertSpaces(ed1.getText().toString())));
			        checkAnswerEnigme(txt2,t2);
			          } 
			        }); 
		        //fonction relative au boutton retour
		        button3.setOnClickListener(new View.OnClickListener() {
			          public void onClick(View v) {
		
			        	  setContentView(R.layout.design_try0);
			        	  setButtonClickListener();
				
			          } 
			        });	
		          } 
	      
}