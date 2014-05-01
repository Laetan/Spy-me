package com.spyme.fileninja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

/** Gère la position et autres infos des fichiers récuperer par le joueur
 * 
 * @author Nathanaël
 * @version 0.1
 *
 */
public final class StoredFile extends Rectangle implements Poolable{
	
	
	private static final long serialVersionUID = 1L;
	private static final int sheight = Gdx.graphics.getHeight();
	int stored;
	int fileType;
	boolean moving;
	boolean toDispose;
	boolean display;
	int x0;
	
	public StoredFile(){
		fileType = -1;
		width = 64;
		height = 64;
		moving = false;
		toDispose = false;
		display = false;
	}


	public void init(int type, int place){
		stored = 0;
		fileType = type;
		y = sheight - 70;
		x = 100*place+20;
	}

	public void add(){
		stored++;
		display = true;
//		if(stored >= 10) dispose();
	}
	
	public void move(int place){
		x0 = 100 * place +20;
		moving = true;
	}
	
	public void dispose(){
		toDispose = true;
	}
	
	public void displayFile(){
		display = true;
	}
	
	public boolean update(float delta){
		
		if(moving)
		{
			x -= (x-x0) *4* delta;
			if(x==x0) moving = false;
		}
		
		if(toDispose)
		{
			
			y+=150 * delta;
			if(y > sheight) return true;

		}
		return false;
	}
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		fileType = -1;
		moving = false;
		toDispose = false;
		display = false;
	}
	
}
