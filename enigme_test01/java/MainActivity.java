//bon j'arrive pas encore à mettre les accents comme il faut... si vous avez une idée
// pour le moment on fait une requete au serveur pour obtenir la question et la réponse
//jcrois que Maxime voulait qu'on envoie l'enigme format JSON au client 
//et qu'ensuite le client se débrouille tout seul, bref a suivre....


package com.example.enigme;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButtonClickListener();
        

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

	 private void setButtonClickListener() { 
	        Button button = (Button)findViewById(R.id.button1);
	        Button button2 = (Button)findViewById(R.id.button2);
	        button.setOnClickListener(new View.OnClickListener() {
	          public void onClick(View v) { 

	        TextView t= (TextView)findViewById(R.id.textView1);
	        httpHandler handler = new httpHandler();
	        //vu en r�union avec Maxime: on entre un texte dans l'edit text et �a nous retourne sa factorielle dans le textView
	       // String txt = handler.post("http://192.168.5.4:8888/facto?number="+ed1.getText());
	       
	        //il faut mettre l'adresse ip du serveur ici. Si tu es sur windows et que ton serveur est sur ton pc
	        //tu vas dans invite de commandes, tu tapes: ipconfig, et tu rel�ves l'adresse IPv4
	        // attention! dans AndroisManifest.xml tu dois rajouter la ligne suivante
	        //<uses-permission android:name="android.permission.INTERNET" />
	        //entre ton <uses-sdk [...] et <application
	        String txt = handler.post("http://192.168.5.64:8888/enigme");
	        t.setText(txt);
	          
		
	          } 
	        }); 
	        
	        
	        button2.setOnClickListener(new View.OnClickListener() {
		          public void onClick(View v) { 
		        	  
		        EditText ed1=(EditText)findViewById(R.id.editText1);
		        httpHandler handler2 = new httpHandler();
		        String txt2 = handler2.post("http://192.168.5.64:8888/answer?text="+convertSpaces(ed1.getText().toString()));
		        TextView t2= (TextView)findViewById(R.id.textView2);
		        t2.setText(txt2);
		          
			
		          } 
		        }); 
	      }
    
}