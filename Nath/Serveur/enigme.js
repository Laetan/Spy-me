//Fonctions de choix et d'envoie d'énigme et de contrôle de réponses.

/*TO DO***************
sur le client: si le joueur se deco, il n'aura plus la meme enigme affichée lors de sa reconnection
				mettre un logo
				adapter les layout aux differentes tailles d'écrans
				recevoir une image
sur le serveur: implémenter la notion de points 
				pour la réponse checker que le mot clé apparait à +- 1 lettre près
				adapté au niveau du joueur dans la fonction getEnigme
				envoyer une image
		
*/
var updater = require("./updater");
var querystring = require("querystring");
var util = require("util");
var fs = require("fs");
var file = "spyme.db";
var exists=fs.existsSync(file);
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(file);


//----------------------------------------------------------------------------------------------------------------------------------------
//************************************fonctions relatives au traitement de l'envoi de l'énoncé****************************************************
//----------------------------------------------------------------------------------------------------------------------------------------

/**
*fonction relative à l'envoi de la description de l'enigme. Récupère la description de l'énigme dans son fichier JSON 
*et l'envoie au client.
*param id: id de l'énigme
*param reponse: objet pour emettre une réponse au client
*param path: chemin d'accès du fichier JSON contenant l'enigme
*ne retourne rien
*/
function sendEnigme(id,reponse,path)
{
		var enigme=fs.readFileSync(path);
		enigme=JSON.parse(enigme);
		enigme=enigme.description;
		reponse.writeHead(200,{"Content-type":"text/plain; charset=utf-8"});
		reponse.write("100/"+id+"/"+enigme);
		reponse.end();
}

/**
*fonction relative à l'envoi de la description de l'enigme. Cas où le joueur lit une énigme pour la première fois.
*enregistre dans la table JOUEUR_ENIGMES l'id de l'enigme ainsi que le pseudo du joueur
*selectionne le chemin du fichier JSON relatif à l'énigme et appelle la fonction sendEnigme
*param pseudo: pseudo du joueur
*param id: id de l'énigme
*reponse: objet permettant d'émettre une réponse au client
*ne retourne rien
*/
function readEnigme(idTemp,id,reponse)
{
	console.log("Read enigma "+id);
	var path;
	var stmt="SELECT pseudo FROM JOUEUR WHERE id_temp='"+idTemp+"'";
	db.all(stmt,function(err,row){
	db.run("INSERT INTO JOUEUR_ENIGMES VALUES('"+row[0].pseudo+"','"+id+"',0)");
	});
	var stmt2="SELECT path FROM ENIGMES WHERE id='"+id+"'ORDER BY id ASC";
	db.all(stmt2,function(err,row2){
		path = row2[0].path+"/enigme.json";
		sendEnigme(id,reponse,path);
	});
}

/**
*fonction relative à l'envoi de la description de l'enigme.
*cas où le joueur a déjà lu l'énigme mais ne l'a pas encore résolue
*renvoie au client un code d'erreur s'il a déjà fait toutes les énigmes auxquelles il a accès
*selectionne le chemin du fichier JSON relatif à l'énigme et appelle la fonction sendEnigme
*param pseudo: pseudo du joueur
*param id: id de l'énigme
*reponse: objet permettant d'émettre une réponse au client
*ne retourne rien
*/
function readEnigme2(id,reponse)
{
	console.log("Read enigma "+id);
	var path;
	var stmt2="SELECT ENIGMES.id, ENIGMES.path FROM ENIGMES, JOUEUR_ENIGMES WHERE ENIGMES.id=JOUEUR_ENIGMES.enigme AND JOUEUR_ENIGMES.est_resolue=0 ORDER BY RANDOM()";
	db.all(stmt2, function(err, row2){
		if(row2.length==0){
			reponse.writeHead(200,{"Content-type":"text/plain; charset=utf-8"});
			reponse.write("106");
			reponse.end();
		}
		else{
			id=row2[0].id;
			path = row2[0].path+"/enigme.json";
			sendEnigme(id,reponse,path);
		}
	});
}

/**
*fonction relative à l'envoi de la description de l'enigme. Appelée à chaque nouvelle demande d'énigme
*Affiche d'abord les énigmes non lues dans l'ordre des id_enigme (appel de readEnigme)
*sinon affiche les énigmes non résolues dans un ordre aléatoire (appel de readEnigme2) 
*selectionne le chemin du fichier JSON relatif à l'énigme et appelle la fonction sendEnigme
*param query: objet contenant la requête du client
*param reponse: objet permettant d'émettre une réponse au client
*ne retourne rien
*/
function getEnigme(query, reponse)
{
	console.log("Get Enigma");
	var idTemp = querystring.parse(query).idTemp;
	var id;
	var stmt="SELECT JOUEUR_ENIGMES.enigme,JOUEUR.pseudo, ENIGMES.niveau_enigme FROM JOUEUR_ENIGMES, JOUEUR, ENIGMES WHERE JOUEUR.id_temp='"+idTemp+"' AND JOUEUR_ENIGMES.pseudo=JOUEUR.pseudo AND JOUEUR_ENIGMES.enigme=ENIGMES.id AND ENIGMES.niveau_enigme<=JOUEUR.niveau_joueur ORDER BY JOUEUR_ENIGMES.enigme DESC";
	db.all(stmt, function(err, row){
		if(row.length==0){
			id=(0*100)+1; //a changer pour chaque niveau
			readEnigme(idTemp,id,reponse);
		}
		else if(row[0].enigme<((1+row[0].niveau_enigme)*100)){
		util.log(util.inspect(row));
			id=row[0].enigme+1;
			readEnigme(idTemp,id,reponse);
		}
		else{
			readEnigme2(id,reponse);
		}
	  });
}
//----------------------------------------------------------------------------------------------------------------------------------------
//************************************fonctions relatives au traitement de la réponse****************************************************
//----------------------------------------------------------------------------------------------------------------------------------------
/**fonciton relative à l'analyse de la réponse. Repère la présence de & dans une chaine de caractères
* les différents mots clés qui doivent apparaitre dans la réponse donnée sont séparés par un &
*param a: la cdc à analyser
*retourne un entier (1 si la chaine contient un "&" 0 sinon)
*/
function isThereEt(a){
	if(a.indexOf('&')!=-1){
		return 1;
	}
	return 0;
}

/**
*fonction relative à l'analyse de la réponse. Check si la cdc entrée par l'utilisateur
*est égale +- à un caractère près a une des réponses possibles (ensemble de mots clés)
*param b: Text : réponse entrée par l'utilisateur
*param d: Tableau de cdc :ensemble des réponses possibles constituées de plusieurs mots clés
*retourne un entier (0 si la réponse ne correspond pas, 1 sinon)
*/
function correctAnswer3(b, d){
	for(var i=0;i<d.length;i++){
		if(b.search(d[i])==-1){
			return 0;
		}
	}
return 1;
}

/**
*fonction relative à l'analyse de la réponse. Check si la cdc entrée par l'utilisateur
*est égale +- à un caractère près à une des réponses possibles (un seul mot clé)
*param b: Text : réponse entrée par l'utilisateur
*param d: Tableau de cdc :ensemble des réponses possibles constituée d'un seul mot clé
*retourne un entier (0 si la réponse ne correspond pas, 1 sinon)
*/
function correctAnswer2(b, c){
	if(b.search(c)!=-1){
		return 1;
	}
	return 0;
}

/**
*fonction getNiveau_enigme
*/

function getNiveauEnigme(idEnigme){
	var stmt="SELECT niveau_enigme FROM ENIGMES WHERE id='"+idEnigme+"'";
	var niveau=0;
	db.all(stmt,function(err,row3){
		niveau=row3[0].niveau_enigme;
	});
	return niveau;
}

/**
*fonction relative à la réponse, appelle les fonctions isThereEt, correctAnswer3,correctAnswer2
*update la table JOUEUR_ENIGMES si la résolution de l'énigme est exacte
*param enigme:ensemble des réponses correctes
*param pAnswer: réponse donnée par l'utilisateur
*param id: id de l'énigme
*retourne un code (104 pour gagné, 105 pour perdu)
*/
function correctAnswer(enigme,pAnswer,id,idTemp){
	var choicesTab=enigme.split(',');
	for(var i=0;i<choicesTab.length;i++)
	{
		if (isThereEt(choicesTab[i])==1){
		var choices2=choicesTab[i].split('&');
		//util.log(util.inspect(choices2));
			if(correctAnswer3(pAnswer,choices2)==1){
				db.run("UPDATE JOUEUR_ENIGMES SET est_resolue=1 WHERE enigme='"+id+"'AND pseudo=(SELECT pseudo FROM JOUEUR WHERE id_temp='"+idTemp+"')");
				return 104;
			}
		}
		else
			if(correctAnswer2(pAnswer,choicesTab[i])==1){
				db.run("UPDATE JOUEUR_ENIGMES SET est_resolue=1 WHERE enigme='"+id+"'AND pseudo=(SELECT pseudo FROM JOUEUR WHERE id_temp='"+idTemp+"')");
				return 104;
			}
	}
	return 105;
};

/**
*fonction relative à la réponse, appelle la fonction correctAnswer
*renvoie une réponse au client pour lui dire si la réponse est bonne ou non
*param path: chemin du fichier JSON contenant l'énigme
*param reponse: objet reponse permettant d'émettre une réponse au client
*param pAnswer: réponse donnée par l'utilisateur
*param id: id de l'énigme
*/
function checkAnswer(path,reponse,pAnswer,id,idTemp){
	var enigme=fs.readFileSync(path);
	enigme=JSON.parse(enigme);
	enigme=enigme.answer;
	reponse.writeHead(200,{"Content-type":"text/plain; charset=utf-8"});
	reponse.write(correctAnswer(enigme,pAnswer,id,idTemp)+"");
	reponse.end();

}

/**
*fonction relative à la réponse, appelle la fonction checkAnswer
*fait une requete à la db pour obtenir le path relatif à l'énigme en cours de traitement
*param query: requête du client
*param reponse: objet reponse permettant d'émettre une réponse au client
ne renvoie rien
*/
function getAnswer(query,reponse)
{
	var idTemp = querystring.parse(query).idTemp;
	var id = querystring.parse(query).id_enigme;
	var pAnswer = querystring.parse(query).answer;
	var stmt3="SELECT path FROM ENIGMES WHERE id='"+id+"'";
	db.all(stmt3,function(err,row3){
		path = row3[0].path+"/enigme.json";
		console.log(path);
		checkAnswer(path,reponse,pAnswer,id,idTemp);
	});
}
	


exports.getAnswer=getAnswer;
exports.getEnigme=getEnigme;