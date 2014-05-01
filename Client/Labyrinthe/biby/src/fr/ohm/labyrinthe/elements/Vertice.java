package fr.ohm.labyrinthe.elements;

public class Vertice {
	
	private int[] dir;
	private Vertice[] vDir;
	private int posX;
	private int posY;
	
	public Vertice(int posX, int posY){
		this.posX=posX;
		this.posY=posY;
		dir =new int[4]; 	//initialise bien à 0 tous les elem
		vDir= new Vertice[4]; //initialise bien à null tous les elem
	}
	
	//display vDir[]
	public void display(){
		for(int i=0;i<vDir.length;i++){
			if(vDir[i]==null){
				System.out.println("bouh");
			}
		}
	}

	public int getPosX() {
		return posX;
	}


	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getPosY() {
		return posY;
	}

	public void setDirections(int direction, int val, Vertice v){
		dir[direction]=val;
		vDir[direction]=v;
	}
	
	public void displayVertice(){
		System.out.println("position x: "+posX+" 	position y: "+posY);
		for(int i=0;i<4;i++){
			if(vDir[i]!=null)
			System.out.println("direction "+i+" : "+dir[i]+"     vertice liée: "+vDir[i].posX+" "+vDir[i].posY);
		}
	}
	
	//return the direction of <3 (Clem)
	//return the direction of the edge+
	public int dirEE(){
		for(int i=0;i<4;i++){
			if(dir[i]==2){
				return i;
			}
		}
		return -1;
	}
	
	//return the direction of the edge-
	public int dirES(){
		for(int i=0;i<4;i++){
			if(dir[i]==3){
				return i;
			}
		}
		return -1;
	}
	
	//check if there is a vertice at the posX posY given in parameters
	public boolean isEqual(int posX, int posY){
		return this.posX==posX && this.posY==posY;
	}
	
	//check if the vertice already have 3 links
	public boolean is3Links(){
		int compte=0;
		for(int i=0;i<4;i++){
			if(dir[i]!=0){
				compte++;
			}
		}
		if(compte>=3){
			return true;
		}
		return false;
	}
	
	//check if on which axis (vertical ro horizontal) the vertice doesn't have any links
	public int dirNotUsed(){
		if(dir[0]==0&&dir[1]==0){
			return 0;
		}else{
			return 2;
		}
	}
	
	public boolean checkValidity(){
		if(vDir[0]!=null&&vDir[0].getPosY()>this.posY){
			return false;
		}
		if(vDir[1]!=null&&vDir[1].getPosY()<this.posY){
			return false;
		}
		if(vDir[2]!=null&&vDir[2].getPosX()>this.posX){
			return false;
		}
		if(vDir[3]!=null&&vDir[3].getPosX()<this.posX){
			return false;
		}
		return true;
	}

	public Vertice[] getvDir() {
		return vDir;
	}
	


}
