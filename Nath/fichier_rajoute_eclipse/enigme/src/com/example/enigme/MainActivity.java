

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
    
    //regarde si l'utilisateur existe, si le mdp correspond à l'utilisateur
    //lors de la connexion
    public void checkUser(String s0, TextView t)
    {
    	//si le pseudonyme n'existe pas
    	if (s0=="1")
    	{
    		t.setText("Le pseudonyme est incorrect, êtes-vous sûr d'être inscrit?");
    	}
    	//si le password est incorrect
    	else if (s0=="2")
    	{
    		t.setText("Le password est incorrect");
    	}
    	//si c'est le pseudo et le password corresponde à une ligne de la db
    	else
    	{
    		setContentView(R.layout.design_try0);
            setButtonClickListener();
    	}
    }
    
    //verifie si le pseudonyme est dispo lors de l'inscirption
    public void checkIns(String s1, TextView t)
    {
    	if(s1=="0")
    	{
    		t.setText("ce pseudonyme est déjà utiliser par un autre joueur");
    		
    	}
    	else
    	{
    		setContentView(R.layout.design_try0);
            setButtonClickListener();
    	}
    }
    
    //vérifie que l'utilisateur a bien rentrer deux fois le meme
    //password lors de l'inscription
    public int samePw(String s1, String s2, TextView t)
    {
    	if(s1.equals(s2))
    	{
    		return 1;

    	}
    	else
    	{
    		t.setText("Veuillez saisir deux fois le même password.");
    		return 0;
    	}
    }
    
    //converti les espaces en "+" pour pouvoir envoyer la réponse au serveur
    //met la réponse en minuscules
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
	        String coRepServeur = handlerCo1.post("http://192.168.5.171:8888/connexion?id="+convertSpaces(coEd1.getText().toString())+"&password="+convertSpaces(coEd2.getText().toString()));
	        checkUser(coRepServeur, coTw);
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
		        String insRepServeur = handlerIns1.post("http://192.168.5.171:8888/inscription?id="+convertSpaces(insEd1.getText().toString())+"&password="+convertSpaces(insEd2.getText().toString()));
		        checkIns(insRepServeur, insTw);
		      
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
	        int nbP=5;
	        String id="bouh";
	        String sCheck=handler.post("http://192.168.5.171:8888/deconnexion?nbPoints="+nbP+"&id="+id);
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
		        String txt = handler.post("http://192.168.5.171:8888/enigme");
		        t.setText(txt);
		          } 
		        }); 
		        
		        //fonction relative au bouton "valider (réponse"
		        button2.setOnClickListener(new View.OnClickListener() {
			          public void onClick(View v) { 
			        	  
			        EditText ed1=(EditText)findViewById(R.id.editText1);
			        httpHandler handler2 = new httpHandler();
			        String txt2 = handler2.post("http://192.168.5.171:8888/answer?text="+convertSpaces(ed1.getText().toString()));
			        TextView t2= (TextView)findViewById(R.id.textView2);
			        t2.setText(txt2);
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