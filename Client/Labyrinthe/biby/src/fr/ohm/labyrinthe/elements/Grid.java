package fr.ohm.labyrinthe.elements;

import java.util.Scanner;
import java.util.Vector;

public class Grid {
	
	/**
	 * attributs de Grid
	 * width: largeur de la grille de jeu
	 * height: hauteur de la grille de jeu
	 * grid: grille de jeu
	 */
	private int width;
	private int height;
	private char[][] grid;
	private Vertice[] tab;
	private int[] liaison;
	private Scanner keyboard;
	private char fillLetter;
	private  Vector<Vertice> VerticesList;
	private int nextLabBeg; //pour s'assurer que les lab ne se superposent pas
	private int nextLabVal;
	private int nextLabVal2;
	private boolean newE;

	/**
	 * constructeur de la classe Grid
	 * @param width: largeur de la grille
	 * @param height: hauteur de la grille
	 */
	public Grid(int width, int height, int nbElements){
		VerticesList=new Vector<Vertice>();
		tab=new Vertice[nbElements];
		fillLetter='a';
		nextLabBeg=4;
		this.width=width;
		this.height=height;
		this.grid= new char[width][height];
		newE=false;
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				grid[i][j]='0';
			}
		}
	}
	
	public Grid(int width, int height, int nbElements, int begX, int begY, int dir){
		this(width,height,nbElements);
		tab[nbElements-1]=new Vertice(begX,begY);
		grid[begX][begY]='1';
		this.nextLabBeg=dir;
		newE=true;
	}
	


	/**
	 * retourne le nombre de points sur une meme ligne
	 * @param colonne: la colonne en question
	 * @return un entier
	 */
	public int numberElementY(int ligne){
		int compte=0;
		for(int i=0;i<width;i++){
			if(grid[i][ligne]=='1'){
				compte ++;
			}
		}
		return compte;
	}
	
	/**
	 * retourne le nombre de points sur une meme colonne
	 * @param ligne: la ligne en question
	 * @return un entier
	 */
	public int numberElementX(int colonne){
		int compte=0;
		for(int i=0;i<height;i++){
			if(grid[colonne][i]=='1'){
				compte ++;
			}
		}
		return compte;
	}
	
	/**
	 * rempli la grille de jeu
	 */
	public void fillGrid(){
		int posX=0, posY=0,size;
		tab[0]=genArriv();
		if(newE){
			size=8;
		}else{
			size=9;
		}
		for(int i=1;i<size;i++){
				do{
					posY=(int)(Math.random()*(height));
					posX=(int)(Math.random()*(width));
				}while (grid[posX][posY]!='0'||numberElementY(posY)>=1||numberElementX(posX)>=1);
				grid[posX][posY]='1';
				tab[i]=new Vertice(posX,posY);
			}	
		}	
	

	/**
	 * génère la vertice d'arrivée
	 * @return la vertice d'arrivée
	 */
	public Vertice genArriv(){
		int posX=0, posY=0, rand;
		do{
			rand=(int)(Math.random()*4);
		}while(rand==this.oppositeP(nextLabBeg));
		switch(rand){
		case 0:
			posX=0;
			posY=(int)(Math.random()*(height));
			setValueNextLab(width-1,posY,0);
			break;
		case 1:
			posX=width-1;
			posY=(int)(Math.random()*(height));
			setValueNextLab(0,posY,1);
			break;
		case 2:
			posY=0;
			posX=(int)(Math.random()*(width));
			setValueNextLab(posX,height-1,2);
			break;
		case 3:
			posY=height-1;
			posX=(int)(Math.random()*(width));
			setValueNextLab(posX,0,3);
			break;
		}
		grid[posX][posY]='1';
		return new Vertice(posX,posY);
	}




	public void setValueNextLab(int val1, int val2, int next){
		this.nextLabVal=val1;
		this.nextLabVal2=val2;
		this.nextLabBeg=next;
	}
	
	/**
	 * link the vertices of the critical path 2 by 2
	 */
	public void addLinkVertices(){
		liaison= new int[tab.length];
			for(int i=0;i<tab.length;i++){
				if(!isLinked(i)){
					if(linkTwo(i,i+1)){
					addLink(i,i+1);
					}else{
						addLink(i+1,i);
					}
					liaison[i]+=1;
					liaison[i+1]+=1;
				}
				if(!isLinked(i)){
					i--;
				}
			}
	}
	
	

	/**
	 * lie les 2 vertices
	 * @param pos1
	 * @param pos2
	 * @return
	 */
	public boolean linkTwo(int pos1, int pos2){
		int minX, maxX, minY,maxY;
		int posX=tab[pos1].getPosX();
		int posY=tab[pos2].getPosY();
		if(grid[posX][posY]!='0'){
			return false;
		}

		if(posX<tab[pos2].getPosX()){
			minX=posX;
			maxX=tab[pos2].getPosX();
		}else{
			maxX=posX;
			minX=tab[pos2].getPosX();
		}
		if(posY<tab[pos1].getPosY()){
			minY=posY;
			maxY=tab[pos1].getPosY();
		}else{
			maxY=posY;
			minY=tab[pos1].getPosY();
		}
		if(oneOnPath(minX,maxX,posY)||oneOnPath2(minY,maxY,posX)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * s'assure qu'il n'y a pas de 1 entre la vertice intermédiaire et la vertice
	 * à lier de la meme ligne. Vérifie qu'il n'y a pas plus de deux 2.
	 * @param min
	 * @param max
	 * @param ligne
	 * @return
	 */
	public boolean oneOnPath(int min, int max, int ligne){
		for(int i=min+1;i<max;i++){
			if(grid[i][ligne]=='1'||grid[i][ligne]=='3'){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * s'assure qu'il n'y a pas de 1 entre la vertice intermédiaire et la vertice
	 * à lier de la meme colonne
	 * @param min
	 * @param max
	 * @param ligne
	 * @return
	 */
	public boolean oneOnPath2(int min, int max, int colonne){
		for(int i=min+1;i<max;i++){
			if(grid[colonne][i]=='1'||grid[colonne][i]=='3'){
				return true;
			}

		}
		return false;
	}
	public void addLink(int pos1, int pos2){
		int minX, maxX, minY,maxY;
		int posX=tab[pos1].getPosX();
		int posY=tab[pos2].getPosY();
		if(pos1==0){
		VerticesList.add(tab[pos1]);
		}
		VerticesList.add(new Vertice(posX,posY));
		VerticesList.add(tab[pos2]);
		
		if(posX<tab[pos2].getPosX()){
			VerticesList.elementAt(2*pos2).setDirections(2, 3, VerticesList.elementAt(2*pos1+1));
			VerticesList.elementAt(2*pos1+1).setDirections(3, 2, VerticesList.elementAt(2*pos2));
			minX=posX;
			maxX=tab[pos2].getPosX();
			checkValidityList("addLinkd");
		}else{
			VerticesList.elementAt(2*pos2).setDirections(3, 3, VerticesList.elementAt(2*pos1+1));
			VerticesList.elementAt(2*pos1+1).setDirections(2, 2, VerticesList.elementAt(2*pos2));
			maxX=posX;
			minX=tab[pos2].getPosX();
			checkValidityList("addLinkc");
		}
		if(posY<tab[pos1].getPosY()){
			VerticesList.elementAt(2*pos1).setDirections(0, 2, VerticesList.elementAt(2*pos1+1));
			VerticesList.elementAt(2*pos1+1).setDirections(1, 3, VerticesList.elementAt(2*pos1));
			minY=posY;
			maxY=tab[pos1].getPosY();
			checkValidityList("addLinkb");
		}else{
			VerticesList.elementAt(2*pos1).setDirections(1, 2, VerticesList.elementAt(2*pos1+1));
			VerticesList.elementAt(2*pos1+1).setDirections(0, 3, VerticesList.elementAt(2*pos1));
			maxY=posY;
			minY=tab[pos1].getPosY();
			checkValidityList("addLinka");
		}

		fillGridTwo(minX,maxX,minY,maxY,posX,posY);
		//correction("fonction addLink");
	}
	
	
	public void fillGridTwo(int minX, int maxX, int minY,int maxY, int colonne, int ligne){
		grid[colonne][ligne]='3';
		for(int i=minX+1;i<maxX;i++){
			grid[i][ligne]=fillLetter;
		}
		for(int i=minY+1;i<maxY;i++){
			grid[colonne][i]=fillLetter;
		}
		fillLetter++;
	}
	/**
	 * check if the vertice is linked
	 */
	public boolean isLinked(int pos){
		if(pos==0||pos==tab.length-1){
			if(liaison[pos]==1){
				return true;
			}
		}else{
			if(liaison[pos]==2){
				return true;
			}
		}
		return false;
	}

	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//add other false path to vertices 1-5
	public void addFalsePath(){
	/*	for(int i=1;i<5;i++){
			if(threeAvailable(i)){
				System.out.println("3 espaces disponibles au dessus du sommet "+i);
			}
		}*/
		for(int i=1;i<16;i++){
			if(!VerticesList.elementAt(i).is3Links()){
				addWayBeginning(i);
			}
		
		}
	}
	
	
	//check if we can go back to one of the first part of the CP
	public boolean goBackBeginning(int posX,int posY,int position){
		
		for(int i=0;i<height;i++){
			if(grid[posX][i]>'a'+position&&grid[posX][i]<='h'){
				if(posY>i){
					if(oThreeOnPath2(i,posY,posX,i,posY,grid[posX][i])){
						return true;
					}
				}else{
						if(oThreeOnPath2(posY,i,posX,i,posY,grid[posX][i])){
							return true;
					}
				}
			}
		}
		return false;
	}
	

	//check that there are no more than one 1 or three on the column between two points
	public boolean oThreeOnPath2(int min, int max, int colonne, int posYi, int posYY, char link){
		int compte=0;
		for(int i=min+1;i<max;i++){
			if(grid[colonne][i]=='1'||grid[colonne][i]=='3'){
				compte++;
			}
		}
		if(compte>0){
			return false;
		}
		for(int i=min+1;i<max;i++){
				grid[colonne][i]='4';
			}
		Vertice newElement= new Vertice(colonne, posYi);
		VerticesList.add(newElement);
		grid[colonne][posYi]='5';
		Vertice temp= atList(colonne,posYY);
		if(posYi>posYY){
			temp.setDirections(1, 1, newElement);
			newElement.setDirections(0, 1, temp);
			checkValidityList("oThreeOnPath2d");
		}else{
			temp.setDirections(0, 1, newElement);
			newElement.setDirections(1, 1, temp);
			checkValidityList("oThreeOnPath2c");
		}
		
		if(colonne>VerticesList.elementAt(2*(link-'a'+1)).getPosX()){
			newElement.setDirections(2, 1, VerticesList.elementAt(2*(link-'a'+1)));
			VerticesList.elementAt(2*(link-'a'+1)).setDirections(3, 1, newElement);
			newElement.setDirections(3, 1, VerticesList.elementAt(2*(link-'a'+1)-1));
			VerticesList.elementAt(2*(link-'a'+1)-1).setDirections(2, 1, newElement);
			checkValidityList("oThreeOnPath2b");
		}else{
			newElement.setDirections(3, 1, VerticesList.elementAt(2*(link-'a'+1)));
			VerticesList.elementAt(2*(link-'a'+1)).setDirections(2, 1, newElement);
			newElement.setDirections(2, 1, VerticesList.elementAt(2*(link-'a'+1)-1));
			VerticesList.elementAt(2*(link-'a'+1)-1).setDirections(3, 1, newElement);
			checkValidityList("oThreeOnPath2a");
		}
		//correction("fonction oThreeOnPath2");
		return true;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void addWayBeginning(int position){
		if(position%2==0){
			if(goBackBeginning2(tab[position/2],position/2)){
				System.out.println("\n\nRETOUR AU DEBUT SUR LA VERTICE DE POSX:"+tab[position/2].getPosX()+" ET DE POSY :"+tab[position/2].getPosY()+"\n\n");
			}else{
				newTryBeg(tab[position/2],position/2);
			}}
			else{
				//if(goBackBeginning(VerticesList.elementAt(position).getPosX(),VerticesList.elementAt(position).getPosY(),position/2));
	}
	}
	
	//check if we can go back to one of the first part of the CP
	public boolean goBackBeginning2(Vertice v, int position){
		int min=0, max=0;
		if(oppositeP(v.dirES())==2){
			min=0;
			max=v.getPosX();
		}else{
			min=v.getPosX()+1;
			max=width;
		}
		for(int i=min;i<max;i++){
			if(grid[i][v.getPosY()]>'a'+position&&grid[i][v.getPosY()]<='h'){
				if(v.getPosX()>i){
					if(oThreeOnPath(i,v.getPosX(),v.getPosY(),i,v.getPosX(),grid[i][v.getPosY()])){
						return true;
					}
				}else{
						if(oThreeOnPath(v.getPosX(),i,v.getPosY(),i,v.getPosX(),grid[i][v.getPosY()])){
							return true;
					}
				}
				
			}
		}
		return false;
	}
	
	
	
	
	public boolean oThreeOnPath(int min, int max, int line, int posXi, int posXX, char link){
		int compte=0;
		for(int i=min+1;i<max;i++){
			if(grid[i][line]=='1'||grid[i][line]=='3'||grid[i][line]=='5'){
				compte++;
			}
		}
		if(compte>0){
			return false;
		}
		for(int i=min+1;i<max;i++){
				grid[i][line]='4';
			}
		Vertice newElement= new Vertice(posXi, line);
		VerticesList.add(newElement);
		grid[posXi][line]='5';
		Vertice temp= atList(posXX,line);
		if(posXi>posXX){

			temp.setDirections(3, 1, newElement);
			newElement.setDirections(2, 1, temp);
			checkValidityList("oThreeOnPathd");
		}else{

			temp.setDirections(2, 1, newElement);
			newElement.setDirections(3, 1, temp);
			checkValidityList("oThreeOnPathc");
		}
		
		if(line>VerticesList.elementAt(2*(link-'a')).getPosY()){
			newElement.setDirections(0, 1, VerticesList.elementAt(2*(link-'a')));
			VerticesList.elementAt(2*(link-'a')).setDirections(1, 1, newElement);
			newElement.setDirections(1, 1, VerticesList.elementAt(2*(link-'a')+1));
			VerticesList.elementAt(2*(link-'a')+1).setDirections(0, 1, newElement);
			checkValidityList("oThreeOnPathb");
		}else{
			newElement.setDirections(1, 1, VerticesList.elementAt(2*(link-'a')));
			VerticesList.elementAt(2*(link-'a')).setDirections(0, 1, newElement);
			newElement.setDirections(0, 1, VerticesList.elementAt(2*(link-'a')+1));
			VerticesList.elementAt(2*(link-'a')+1).setDirections(1, 1, newElement);
			checkValidityList("oThreeOnPatha");
		}
		//correction("fonction oThreeOnPath");
		return true;
	}
	
	
	public void  newTryBeg(Vertice v, int position){
		int dir=oppositeP(v.dirES());
		int posX=isNewPossible(v,dir), posY;
		int link=0,enter=1;
		Vertice newElement2;
		if(posX!=-1){
			grid[posX][v.getPosY()]='5';
			fill4(posX,v.getPosX(),v.getPosY());
			Vertice newElement= new Vertice(posX,v.getPosY());
			VerticesList.add(newElement);
			v.setDirections(dir,1,newElement);
			newElement.setDirections(oppositeP(dir), 1, v);
			checkValidityList("fonction newTryBeg");
			nom_a_trouver(newElement,position);

			
		}
		//correction("fonction newTryBeg");
	}
	
	void nom_a_trouver(Vertice newElement, int position){
		int dir,posX,posY;
		//int posX=isNewPossible(v,dir), posY;
		int link=0,enter=1;
		Vertice newElement2;
		while(link==0&&enter!=0){
			enter=0;
			if(newElement.dirNotUsed()==0){
				if( isNewPossible2(newElement,findDirectionToGo(0,newElement))!=-1 ){
					if(goBackBeginning(newElement.getPosX(),newElement.getPosY(),position)){
						link=1;
					}else{
						dir=findDirectionToGo(0,newElement);
						posY=isNewPossible2(newElement,dir);
						grid[newElement.getPosX()][posY]='5';
						fill42(posY,newElement.getPosY(),newElement.getPosX());
						newElement2= new Vertice(newElement.getPosX(),posY);
						VerticesList.add(newElement2);
						newElement.setDirections(dir,1,newElement2);
						newElement2.setDirections(oppositeP(dir), 1, newElement);
						checkValidityList("nom_a_trouver2");
						newElement=newElement2;
						enter++;
					}
				}

			}
					
		if(newElement.dirNotUsed()==2){
			if( isNewPossible(newElement,findDirectionToGo(2,newElement))!=-1 ){
			if(goBackBeginning2(newElement,position)){
				link=1;
			}else{
				dir=findDirectionToGo(2,newElement);
				posX=isNewPossible(newElement,dir);
				grid[posX][newElement.getPosY()]='5';
				fill4(posX,newElement.getPosX(),newElement.getPosY());
				newElement2= new Vertice(posX,newElement.getPosY());
				VerticesList.add(newElement2);
				newElement.setDirections(dir,1,newElement2);
				newElement2.setDirections(oppositeP(dir), 1, newElement);
				newElement=newElement2;
				checkValidityList("nom_a_trouver");
				enter++;
			}
		}}
		}
	}
	/************************************************************************/
	
	//check if we can add a vertice horizontally, return -1 if we can't
	public int isNewPossible(Vertice v, int dir){
		if(dir==3){
			for(int i=width-1;i>v.getPosX();i--){
				if((grid[i][v.getPosY()]=='0') && !(otFOnPath(v.getPosX()+1,i,v.getPosY()))){
					return i;
				}
			}
		}
		
		if(dir==2){
			for(int i=0;i<v.getPosX();i++){
				if((grid[i][v.getPosY()]=='0') && !(otFOnPath(i-1,v.getPosX(),v.getPosY()))){
					return i;
				}
			}
		}
		return -1;
	}
	
	
	//check if we can add a vertice vertically, return -1 if we can't
	public int isNewPossible2(Vertice v, int dir){
		if(dir==1){
			for(int i=height-1;i>v.getPosY();i--){
				if((grid[v.getPosX()][i]=='0') && !(otFOnPath2(v.getPosY()+1,i,v.getPosX()))){
					return i;
				}
			}
		}
		
		if(dir==0){
			for(int i=0;i<v.getPosY();i++){
				if((grid[v.getPosX()][i]=='0') && !(otFOnPath2(i-1,v.getPosY(),v.getPosX()))){
					return i;
				}
			}
		}
		return -1;
	}
	public boolean otFOnPath(int min, int max, int line){
		for(int i=min+1;i<max;i++){
			if(grid[i][line]=='1'||grid[i][line]=='3'||grid[i][line]=='5'){
				return true;
			}
		}
		return false;
	}
	
	public boolean otFOnPath2(int min, int max, int column){
		for(int i=min+1;i<max;i++){
			if(grid[column][i]=='1'||grid[column][i]=='3'||grid[column][i]=='5'){
				return true;
			}
		}
		return false;
	}
	
	public int findDirectionToGo(int dir, Vertice v){
		int compte1=0, compte2=0;
		if(dir==0||dir==1){
			for(int i=v.getPosY()-1;i>=0;i--){
				if(grid[v.getPosX()][i]!='0'){
					compte1--;
					i=0;
				}
				compte1++;
			}
			for(int i=v.getPosY()+1;i<height;i++){
				if(grid[v.getPosX()][i]!='0'){
					compte2--;
					i=height;
				}
				compte2++;
			}
			if(compte1==compte2&&compte1==1){
				return -1;
			}else if(compte1>=compte2){
				return 0;
			}else{
				return 1;
			}
		}
		
		if(dir==2||dir==3){
			for(int i=v.getPosX()-1;i>=0;i--){
				if(grid[i][v.getPosY()]!='0'){
					compte1--;
					i=0;
				}
				compte1++;
			}
			for(int i=v.getPosX()+1;i<width;i++){
				if(grid[i][v.getPosY()]!='0'){
					compte2--;
					i=width;
				}
				compte2++;
			}
			if(compte1==compte2&&compte1==0){
				return -1;
			}else if(compte1>=compte2){
				return 2;
			}else{
				return 3;
			}
		}
		return -1;
	}

	
	public void fill4(int posX1,int posX2,int posY){
		if(posX1<posX2){
			for(int i=posX1+1;i<posX2;i++){
				grid[i][posY]='4';
			}
		}else{
			for(int i=posX2+1;i<posX1;i++){
				grid[i][posY]='4';
			}
		}
	}
	
	public void fill42(int posY1,int posY2,int posX){
		if(posY1<posY2){
			for(int i=posY1+1;i<posY2;i++){
				grid[posX][i]='4';
			}
		}else{
			for(int i=posY2+1;i<posY1;i++){
				grid[posX][i]='4';
			}
		}
	}
	
	public Vertice atList(int posX,int posY){
		for(int i=0;i<VerticesList.size();i++){
			if(VerticesList.elementAt(i).isEqual(posX,posY)){
				return VerticesList.elementAt(i);
			}
		}
		return null;
	}
	
	public int oppositeP(int dir){
		if(dir==0){
			return 1;
		}else if(dir==1){
			return 0;
		}else if(dir==2){
			return 3;
		}else if (dir==3){
			return 2;		
		}
		return -1;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * affiche la grille
	 */
	public void displayGrid(){
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				System.out.print(grid[j][i]);
			}
			System.out.print("\n");
		}
	}
	
	public void displayVector(){
		for(int i=0;i<tab.length;i++){
			System.out.println(tab[i].getPosX()+" "+tab[i].getPosY());
		}
	}
	
	public void displayLiaison(){
		System.out.println("on affiche les liaisons");
		for(int i=0;i<liaison.length;i++){
			System.out.println(liaison[i]);
		}
	}
	
	public void displayList(){
		System.out.println("on affiche la liste");
		for(int i=0;i<VerticesList.size();i++){
			VerticesList.elementAt(i).displayVertice();
		}
	}
	

	public void correction(String s){
		System.out.println("\n\nOn se trouve a l'interieur de la fonction : " +s+"\n");
		//displayList();
			
	}
	
	public void checkValidityList(String functionN){
		for(int i=0;i<VerticesList.size();i++){
			if(!VerticesList.elementAt(i).checkValidity()){
				System.out.println("\nerreur dans la fonction "+functionN+"\n");
				displayList();
				keyboard= new Scanner(System.in);
				keyboard.next();
			}
		}
	}
	
	
	public void createGrid(){
		fillGrid();
		addLinkVertices();
		addFalsePath();
	}
	
	
	public Vector<Vertice> getVerticesList() {
		return VerticesList;
	}

	public int getNextLabVal() {
		return nextLabVal;
	}

	public int getNextLabVal2() {
		return nextLabVal2;
	}

	public int getNextLabBeg() {
		return nextLabBeg;
	}
	
	
	public Vertice[] getTab() {
		return tab;
	}
	
	public void setNextLabVal(int nextLabVal) {
		this.nextLabVal = nextLabVal;
	}

	public void setNextLabVal2(int nextLabVal2) {
		this.nextLabVal2 = nextLabVal2;
	}
}
