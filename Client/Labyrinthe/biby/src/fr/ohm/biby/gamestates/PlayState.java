package fr.ohm.biby.gamestates;

import java.util.Vector;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.ohm.biby.biby;
import fr.ohm.biby.entities.Arrival;
import fr.ohm.biby.entities.Ball;
import fr.ohm.biby.entities.Departure;
import fr.ohm.biby.entities.Edges;
import fr.ohm.biby.entities.Vtc;
import fr.ohm.biby.manager.GameInputProcessor;
import fr.ohm.biby.manager.GameStateManager;
import fr.ohm.labyrinthe.LabyrintheFinal;
import fr.ohm.labyrinthe.elements.Vertice;

public class PlayState extends GameState{
	
	private SpriteBatch sb;
	public static Ball ball;
	private Arrival arrival;
	private Departure departure;
	private static Vector<Vertice> VerticesList;
	private static Vtc[] tab;
	private static LabyrintheFinal laby;
	private static Vector<Edges> edges;
	public static Vertice tempV;
	public static int shift=biby.WIDTH/14;

	static{
		VerticesList=new Vector<Vertice>();
		laby=new LabyrintheFinal();
		//laby.fillLabyrinthe();
		for(int i=0;i<laby.getVList().size();i++){
			VerticesList.add(laby.getVList().elementAt(i));
		}
		tab=new Vtc[VerticesList.size()];
		edges=new Vector<Edges>();
	}
	
	/***********************************************************************************************************/
	
	/**
	 * PlayState's constructor
	 * @param gsm: chosen state (menu-play-instructions)
	 */
	public PlayState(GameStateManager gsm){
		super(gsm);
	}

	/**
	 * function called once when the playState is created
	 */
	public void init() {
		biby.playState=1;
		sb= new SpriteBatch();
		initializeElements();
	}

	/**
	 * initialize the ball(player's position), departure, arrival, vertices, edges
	 */
	public void initializeElements(){
		tempV=laby.getbegV();
		ball= new Ball(biby.WIDTH/7,biby.WIDTH/7,laby.getdX()*biby.WIDTH/5,laby.getdY()*biby.WIDTH/5);
		departure= new Departure(biby.WIDTH/7,biby.WIDTH/7,laby.getdX()*biby.WIDTH/5,laby.getdY()*biby.WIDTH/5);
		arrival= new Arrival(biby.WIDTH/7,biby.WIDTH/7,laby.getaX()*biby.WIDTH/5,laby.getaY()*biby.WIDTH/5);
		biby.cam.position.set(laby.getdX()*biby.WIDTH/5, laby.getdY()*biby.WIDTH/5, 0);
		biby.cam.update();
		for(int i=0;i<tab.length;i++){
			tab[i]=new Vtc(biby.WIDTH/20,biby.WIDTH/20,VerticesList.elementAt(i).getPosX()*biby.WIDTH/5,
					VerticesList.elementAt(i).getPosY()*biby.WIDTH/5);
			initializeEdges(VerticesList.elementAt(i));
		}
	}
	
	/**
	 * initialize the edges at the four directions of a vertice
	 * @param v: vertice
	 */
	public void initializeEdges(Vertice v){
		for(int i=0;i<4;i++){
			
			if(v.getvDir()[i]!=null){
				edges.add(new Edges(v.getPosX()*biby.WIDTH/5+biby.WIDTH/40,
						v.getPosY()*biby.WIDTH/5+biby.WIDTH/40,
						v.getvDir()[i].getPosX()*biby.WIDTH/5+biby.WIDTH/40
						,v.getvDir()[i].getPosY()*biby.WIDTH/5+biby.WIDTH/40));
			}
		}
	}

	/**
	 * update the screen
	 */
	public void update(float dt) {

		sb.setProjectionMatrix(biby.cam.combined);
		handleInput();
		ball.update(dt);
		isGameWon();
	}
	
	/**
	 * draw the different elements
	 */
	public void draw() {
		//draw edges
		for(int i=0;i<edges.size();i++){
			edges.elementAt(i).draw(sb);
		}
		//draw vertices
		for(int i=0;i<tab.length;i++){
			tab[i].draw(sb);
		}
		//arrival-departure-player
		arrival.draw(sb);
		departure.draw(sb);
		ball.draw(sb);
	}

	/**
	 * listen to the keyboard keys left/right/up/down
	 */
	public void handleInput() {
		if(GameInputProcessor.l&&tempV.getvDir()[2]!=null&&ball.getMoving()==false){
			left_dir();
		}
		else if(GameInputProcessor.r&&tempV.getvDir()[3]!=null&&ball.getMoving()==false){
			right_dir();
		}
		else if(GameInputProcessor.u&&tempV.getvDir()[1]!=null&&ball.getMoving()==false){
			up_dir();
		}
		else if(GameInputProcessor.d&&tempV.getvDir()[0]!=null&&ball.getMoving()==false){
			down_dir();
		}
	}

	//called when going left
	public void left_dir(){
		ball.setLeft(true);
		do{
			tempV=tempV.getvDir()[2];
			ball.setPosXmin(tempV.getPosX()*biby.WIDTH/5);
		}while(tempV.getvDir()[2]!=null);
	}
	
	//called when going right
	public void right_dir(){
		ball.setRight(true);
		do{
			tempV=tempV.getvDir()[3];
			ball.setPosXmax(tempV.getPosX()*biby.WIDTH/5);
		}while(tempV.getvDir()[3]!=null);
	}
	
	//called when going up
	public void up_dir(){
		ball.setUp(true);
		do{
			tempV=tempV.getvDir()[1];
			ball.setPosYmax(tempV.getPosY()*biby.WIDTH/5);
		}while(tempV.getvDir()[1]!=null);
	}
	
	//called when going down
	public void down_dir(){
		ball.setDown(true);
		do{
			tempV=tempV.getvDir()[0];
			ball.setPosYmin(tempV.getPosY()*biby.WIDTH/5);
		}while(tempV.getvDir()[0]!=null);
	}
	/**
	 * check if the player is at the same position than the arrival
	 */
	public void isGameWon(){
		if((ball.getX()>laby.getaX()*biby.WIDTH/5-10)&&(ball.getX()<laby.getaX()*biby.WIDTH/5+10)
				&&(ball.getY()>laby.getaY()*biby.WIDTH/5-10)&&(ball.getY()<laby.getaY()*biby.WIDTH/5+10)){
			gsm.setState(GameStateManager.MENU);
		}
	}
	
	public void dispose() {
	}

}
