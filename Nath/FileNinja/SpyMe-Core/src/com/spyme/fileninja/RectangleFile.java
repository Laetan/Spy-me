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
	 * 0 :: buffer
	 * 1 :: file
	 * 2 :: mail
	 * 3 :: music
	 * 4 :: yt
	 * 5 :: zip
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
	private float accx, accy, vitx, vity;
	
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
		if(type == 0)
			traj = 4;
		else
			traj = MathUtils.random(0, 3);
		sens = MathUtils.round(MathUtils.random(0,1));
		x0 = x = MathUtils.random(0, swidth-64);
		y0 = y = -height;
		accx = accy = vitx = 0;
		vity = speed * 100;

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
			case 4:
				randomTraj(delta);
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
	
	private void randomTraj(float delta){
		accx = MathUtils.random(-1000,1000);
		accy = MathUtils.random(-1000,1000);
		if(vitx<150*delta && vitx>-150*delta && vity>-200*delta && vity<200*delta){
			accx*=2;
			accy*=2;
		}
		
		vitx += accx*delta;
		vity += accy*delta;
		
		y += vity*delta;
		if(y <0 && vity<0){
			y -= 2*vity*delta;
			vity*=-1;
		}
		x += vitx*delta;
		if(x <0 || x> swidth-width){
			x -= 2*vitx*delta;
			vitx*=-1;
		}
	}
	
	@Override
	public void reset() {

		fileType = -1;
		caught = false;
		xStore = 0;
	}

}
