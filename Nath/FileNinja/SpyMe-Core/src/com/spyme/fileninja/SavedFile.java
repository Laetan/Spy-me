package com.spyme.fileninja;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public final class SavedFile extends Rectangle{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int fileType;
	public int place;
	public int height;
	private float x0;
	public boolean dispose;
	
	public SavedFile(int ft,int p){
		fileType=ft;
		place = p;
		y = 300;
		x0 = x = 100 - p*80;
		dispose = false;
		switch(fileType){
		case 0:
		case 1:
		case 2:
		case 3:
			height = 64;
			width = 64;
			break;
		case 4:
		case 5:
		case 6:
			height = 128;
			width = 64;
			break;
		}
	}
	
	public void update(float delta){
		if(!dispose)
			x+=MathUtils.ceil((x0-x)*2*delta);
			
	}
	
	public boolean move(){
		x0 = x + 80;
		if(x0>100)
			dispose = true;
		
		return dispose;
	}
}
