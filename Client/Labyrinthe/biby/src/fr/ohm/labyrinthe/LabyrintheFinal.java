package fr.ohm.labyrinthe;

import java.util.Vector;
import fr.ohm.labyrinthe.elements.Grid;
import fr.ohm.labyrinthe.elements.Vertice;

	public class LabyrintheFinal {
		
		private  Vector<Vertice> VList;
		private Grid grid;
		private int refx=0;
		private int refy=0;
		private int dX;
		private int dY;
		private int aX;
		private int aY;
		private Vertice begV;
		
		/**
		 * constructor
		 */
		public LabyrintheFinal(){
			VList=new Vector<Vertice>();
			grid=new Grid(9,9,9);
			grid.createGrid();
			for(int i=0;i<grid.getVerticesList().size();i++){
				VList.add(grid.getVerticesList().elementAt(i));
			}
			begV=grid.getTab()[8];
			initializeAD();
		}
		
		/**
		 * initialize the arrival and departure
		 */
		public void initializeAD(){
			dX=grid.getTab()[8].getPosX();
			dY=grid.getTab()[8].getPosY();
			aX=grid.getTab()[0].getPosX();
			aY=grid.getTab()[0].getPosY();
		}
		
		/**
		 * display the list
		 */
		public void displayList(){
			grid.displayGrid();
			for(int i=0;i<VList.size();i++){
				VList.elementAt(i).displayVertice();
			}
		}
		
		
		/**
		 * fill the Labyrinth
		 */
		public void fillLabyrinthe(){
			Grid gridTemp;
			Vertice vTemp;
			grid.displayVector();
			for(int i=0;i<2;i++){
				gridTemp=new Grid(9,9,9,grid.getNextLabVal(),grid.getNextLabVal2(),grid.getNextLabBeg());
				gridTemp.createGrid();
				vTemp=grid.getVerticesList().elementAt(0);
				superpose(gridTemp,grid.getNextLabBeg());
				shiftGrid(gridTemp,grid.getNextLabBeg(),vTemp);
				aX=gridTemp.getTab()[0].getPosX();
				aY=gridTemp.getTab()[0].getPosY();
				linkVertices(vTemp,gridTemp.getTab()[8],grid.getNextLabBeg());
				for(int j=0;j<gridTemp.getVerticesList().size();j++){
					VList.add(gridTemp.getVerticesList().elementAt(j));
				}
				grid=gridTemp;
			}
		}
		
		public void superpose(Grid g, int dir){
			System.out.println("on affiche les references: refx: "+refx+ "et ref y: "+refy+"\n");
			for(int i=0;i<g.getVerticesList().size();i++){
				if(dir==2||dir==3)
				g.getVerticesList().elementAt(i).setPosX(g.getVerticesList().elementAt(i).getPosX()+refx);
				if(dir==0||dir==1)
				g.getVerticesList().elementAt(i).setPosY(g.getVerticesList().elementAt(i).getPosY()+refy);
			}
		}
		
		/**
		 * shift the grid added
		 * @param g:
		 * @param direction
		 * @param v
		 */
		public void shiftGrid(Grid g, int direction, Vertice v){
			if(direction==0){
				shiftUp(g,v);
			}else if(direction==1){
				shiftDown(g,v);
			}else if(direction==2){
				shiftLeft(g,v);
			}else if(direction==3){
				shiftRight(g,v);
			}
		}
		
		/**
		 * shift a grid up
		 * @param g: grid to shift
		 * @param v: reference vertice
		 */
		public void shiftUp(Grid g, Vertice v){
			Vertice vTemp; 
			for(int i=0;i<g.getVerticesList().size();i++){
				vTemp=g.getVerticesList().elementAt(i);
				vTemp.setPosX(v.getPosX()-9+vTemp.getPosX());
			}
			refx-=9;
		}
		/**
		 * shift a grid down
		 * @param g: grid to shift
		 * @param v: reference vertice
		 */
		public void shiftDown(Grid g, Vertice v){
			Vertice vTemp; 
			for(int i=0;i<g.getVerticesList().size();i++){
				vTemp=g.getVerticesList().elementAt(i);
				vTemp.setPosX(v.getPosX()+1+vTemp.getPosX());
			}
			refx+=9;
		}
		
		/**
		 * shift a grid left
		 * @param g: grid to shift
		 * @param v: reference vertice
		 */
		public void shiftLeft(Grid g, Vertice v){
			Vertice vTemp; 
			for(int i=0;i<g.getVerticesList().size();i++){
				vTemp=g.getVerticesList().elementAt(i);
				vTemp.setPosY(v.getPosY()-9+vTemp.getPosY());
			}
			refy-=9;
		}
		
		/**
		 * shift a grid right
		 * @param g: grid to shift
		 * @param v: reference vertice
		 */
		public void shiftRight(Grid g, Vertice v){
			Vertice vTemp; 
			for(int i=0;i<g.getVerticesList().size();i++){
				vTemp=g.getVerticesList().elementAt(i);
				vTemp.setPosY(v.getPosY()+1+vTemp.getPosY());
			}
			refy+=9;
		}

		/**
		 * link two vertices
		 * @param v1: first vertice
		 * @param v2: second vertice
		 * @param dir: direction to link
		 */
	public void linkVertices(Vertice v1, Vertice v2, int dir){
		if(dir==1){
			v1.setDirections(3, 1, v2);
			v2.setDirections(2,1, v1);
		}else if(dir==0){
			v1.setDirections(2, 1, v2);
			v2.setDirections(3,1, v1);
		}else if(dir==3){
			v1.setDirections(1, 1, v2);
			v2.setDirections(0,1, v1);
		}else if(dir==2){
			v1.setDirections(0, 1, v2);
			v2.setDirections(1,1, v1);
		}
	}

	//************************************************************************************************************
	//*****************************************************getters************************************************
	//************************************************************************************************************
	public Vector<Vertice> getVList() {
		return VList;
	}
			
	public int getdX() {
		return dX;
	}

	public int getdY() {
		return dY;
	}

	public int getaX() {
		return aX;
	}

	public int getaY() {
		return aY;
	}
	
	public Vertice getbegV(){
		return  begV;
	}
	
	}

