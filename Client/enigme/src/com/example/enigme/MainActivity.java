

package com.example.enigme;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends Activity {

	private int nbPoints=0;
	private int id_enigme=0;
	private String pseudo="";
	private int niveau=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);
        setButtonClickListenerConnexion();
       /* setContentView(R.layout.design_try0);
        setButtonClickListener();  */
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
    	switch (code){
    	case 100:
    		return 1;
    	case 101:
    		t.setText("Ce pseudonyme est déjà utilisé par un autre joueur.");
    		return 0;
    	case 102:
    		t.setText("Le pseudonyme n'existe pas, êtes-vous sûr d'être inscrit?");
    		return 0;
    	case 103:
    		t.setText("Le password est incorrect");
    		return 0;
    	case 104:
    		t.setText("Gagné");
    		return 1;
    	case 105:
    		t.setText("Perdu");
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
    	String[] tokens=answer.split("/");
    	Integer code= Integer.valueOf(tokens[0]);
    	if (decodeServ(code,t)==1){
    		niveau=Integer.valueOf(tokens[1]);
    		nbPoints=Integer.valueOf(tokens[2]);
    		pseudo=pseudo+ed.getText();
    		setContentView(R.layout.design_try0);
            setButtonClickListener();
    	}}
    
    /****fonction relative à l'inscription****
     * check la disponibilité du pseudonyme
     * passe au menu si tout est ok, affiche un message d'erreur sinon. Enregistre le pseudonyme.
     * prend en 1er argument: la réponse renvoyée par le serveur (s1)
     * 2eme argument: l'objet TextView où seront affichées les eventuelles erreurs
     * 3eme argument: l'objet EditText pour récupérer le pseudonyme
     * ne renvoie rien
    */
    public void checkIns(int code, TextView t, EditText ed){
    	if(decodeServ(code,t)==1){
    		pseudo=pseudo+ed.getText();
    		setContentView(R.layout.design_try0);
            setButtonClickListener();
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
    	String[] tokens=answer.split("/");
    	Integer code= Integer.valueOf(tokens[0]);
    	id_enigme=Integer.valueOf(tokens[1]);
    	if(decodeServ(code,t)==1){
    		t.setText(tokens[2]);
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
	        String coRepServeur = handlerCo1.post("http://spyme.waxo.org/connexion?pseudo="+convertSpaces(coEd1.getText().toString())+"&password="+convertSpaces(coEd2.getText().toString()));
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
		        Integer insRepServeur = Integer.valueOf(handlerIns1.post("http://spyme.waxo.org/inscription?pseudo="+convertSpaces(insEd1.getText().toString())+"&password="+convertSpaces(insEd2.getText().toString())));
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
	        Button but5 = (Button)findViewById(R.id.butm5);
	        //fonction relative au boutton "continuer"
	        but1.setOnClickListener(new View.OnClickListener() {
	          public void onClick(View v) { 
	        	  setContentView(R.layout.enigme02);
	        	  setButtonClickListenerEnigme();
		
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
	        //valeurs pourries à changer, utilisées pour test.

	        String sCheck=handler.post("http://spyme.waxo.org/deconnexion?nbPoints="+nbPoints);
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
		        TextView t= (TextView)findViewById(R.id.textView1);
		        httpHandler handler = new httpHandler();
		        String txt = handler.post("http://spyme.waxo.org/enigme?pseudo="+pseudo);
		        checkEnigme(txt,t);
		          } 
		        }); 
		        
		        //fonction relative au bouton "valider (réponse)"
		        button2.setOnClickListener(new View.OnClickListener() {
			          public void onClick(View v) { 
					TextView t2= (TextView)findViewById(R.id.textView2);	  
			        EditText ed1=(EditText)findViewById(R.id.editText1);
			        httpHandler handler2 = new httpHandler();
			        Integer txt2 = Integer.valueOf(handler2.post("http://spyme.waxo.org/answer?id_enigme="+id_enigme+"&pseudo="+pseudo+"&answer="+convertSpaces(ed1.getText().toString())));
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