package com.spyme.fileninja;

import com.badlogic.gdx.Gdx;
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
	private final float speed = 1;
	public int fileType;
	
	/*
	 * 0 :: red
	 * 1 :: blue
	 * 2 :: green
	 * 3 :: music
	 * 4 :: par
	 * 5 :: zir
	 * 6 :: virus
	 */
	private static final int swidth = Gdx.graphics.getWidth();
	private static final int sheight = Gdx.graphics.getHeight();
	private float x0;
	private float y0;
	private int traj;
	private int sens;
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
		traj = MathUtils.random(0, 3);
		sens = MathUtils.round(MathUtils.random(0,1));
		x0 = x = MathUtils.random(0, swidth-64);
		y0 = y = 0;
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
		if(!caught){
			switch(traj)
			{
			case 0:
				y += speed * 100 * delta;
				x = (x0 + 50*MathUtils.cosDeg(y)) % 430;
				break;
			case 1:
				y += speed * 100 * delta;
				break;
			case 2:
				y += speed * 400 * delta;
				break;
			case 3:
				y += speed * 100 * delta;
				x += speed * sens * 300 * delta;
				sens = (x < 0 || x > swidth - 64 ) ? sens*-1 : sens;
				break;
			default:
					break;
			}	
		}
		else
		{
			y += (sheight - 70 - y0) * 5 * delta;
			x += (xStore-x0) * 5 * delta;
		}
		
	}
	
	public void catchFile(int store){
		caught = true;
		xStore = store * 100 + 20;
		x0 = x;
		y0 = y;
	}
	
	
	@Override
	public void reset() {

		fileType = -1;
		caught = false;
		xStore = 0;
	}

}
