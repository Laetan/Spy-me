package com.spyme.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.spyme.core.Spyme;


public class httpHandler {
	
public static String text="";

/**
 * called when there's a http request
 * @param posturl: url
 */
public void get(String posturl){
	HttpRequest httppost = new HttpRequest(HttpMethods.GET);
	httppost.setUrl(posturl);
	Gdx.net.sendHttpRequest (httppost, new Net.HttpResponseListener() {
		/**
		 * called when the server gives an answer
		 */
		public void handleHttpResponse(com.badlogic.gdx.Net.HttpResponse httpResponse) {
			httpHandler.text=httpResponse.getResultAsString();
			if(Spyme.state==0){
				LogInScreen.coRepServeur=httpHandler.text;
			}else if(Spyme.state==1){
				RegisterScreen.coRepServeur=httpHandler.text;
			}else if(Spyme.state==2){
				ExitScreen.coRepServeur=httpHandler.text;
			}else if(Spyme.state==3){
				OptionScreen.coRepServeur=httpHandler.text;
			}
		}
		public void failed(Throwable t) {
			System.out.println("erreur");
			}
		}); 
	}
}

