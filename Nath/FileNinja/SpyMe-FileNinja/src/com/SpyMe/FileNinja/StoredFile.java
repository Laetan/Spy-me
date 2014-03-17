package com.SpyMe.FileNinja;

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
		stored = 0;
		moving = false;
		toDispose = false;
		display = false;
	}


	public void init(int type, int place){
		fileType = type;
		y = 800 - 70;
		x = 100*place;
	}

	public void add(){
		stored++;
		display = true;
	}
	
	public void move(int place){
		x0 = 100 * place +20;
		if(x0 != x) moving = true;
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
			x -= (x-x0) * delta/4;
			if(x==x0) moving = false;
		}
		
		if(toDispose)
		{
			y+=150 * delta;
			if(y > 800) return true;
		}
		return false;
	}
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		fileType = -1;
		stored = 1;
		moving = false;
		toDispose = false;
		display = false;
	}
	
}
