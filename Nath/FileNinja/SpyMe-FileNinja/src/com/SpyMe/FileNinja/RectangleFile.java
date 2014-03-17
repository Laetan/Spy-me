package com.SpyMe.FileNinja;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

/** Gère la position, le mouvement, etc de touts les items en mouvement
 * 
 * @author Nathanaël
 * @version 0.1
 */
public class RectangleFile extends Rectangle implements Poolable{


	private static final long serialVersionUID = 1L;
	public int fileType;
	/*
	 * 0 :: red
	 * 1 :: blue
	 * 2 :: green
	 */
	private float x0;
	private float y0;
	boolean caught;
	private float xStore;
	
	public RectangleFile(){
		fileType = -1;
		width = 64;
		height = 64;
		caught = false;
		xStore = 0;
		y0 = 0;
	}
	
	public void init(int type){
		fileType = type;
		x0 = x = MathUtils.random(0, 480-64);
		y0 = y = 0;
	}
	
	public void update(float delta){
		if(!caught){
			if(fileType == 1)
			{
				y += 100 * delta;
			}
			else if(fileType == 0)
			{
				y += 100 * delta;
				x = x0 + 50*MathUtils.cosDeg(y);
				x = x < 0 ? 0 : x;
				x = x > 480 - 64 ? 480 - 64 : x;
			}
			else if(fileType == 2)
			{
				y += 400 * delta;
			}
		}
		else
		{
			y += (800 - 70 - y0) * 5 * delta;
			x += (xStore-x0) * 5 * delta;
		}
		
	}
	
	public void catchFile(int store){
		caught = true;
		xStore = store * 100 + 20;
		x0 = x;
	}
	
	
	@Override
	public void reset() {

		fileType = -1;
		caught = false;
		xStore = 0;
	}

}
