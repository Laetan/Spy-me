//Fonctions de choix et d'envoie d'énigme et de contrôle de réponses.

var updater = require("./updater");
var querystring = require("querystring");
var util = require("util");
var fs = require("fs");
var file = "spyme.db";
var exists=fs.existsSync(file);
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(file);


function getNewEnigme(row1, pseudo,reponse)
{	// Find a new enigma (lowest level unresolved and unread yet)
	console.log("Search new enigma");
	var le = row1.length;
	var stmt = "SELECT * FROM ENIGMES ORDER BY niveau_enigme ASC";
	var ok = 0;
	var id = -1;
	var level;
	console.log(le);
	db.all(stmt, function(err,row2)
	{
		console.log(row2);
		var la = row2.length;
		for(var j =0; j<la; j++)
		{
			console.log("row2[j] = "+row2[j]);
			if(id==-1)
			{
				ok=1;
				for(var i =0; i< le; i++)
				{
					if(row2[j].id == row1[i].enigme){ok=0;break;}
				}
				
				if(ok==1){id=row2[j].id; level=row2[j].niveau_enigme;break;}
			}
		
		}
		
		if(id==-1)
		{
			console.log("Cannot find new enigma");
			reponse.writeHead(200, {"Content-type":"text/plain"});
			reponse.write("106"); // Cannot find new enigma
			reponse.end();
		}
		else
		{
			//updater.updateEnigme(pseudo, id, level, 0);
			readEnigme(id,reponse);
		}
		
	});
}

function readEnigme(id, reponse)
{	//Lit le contenu de l'enigme
	console.log("Read enigma "+id);
	var stmt = "SELECT path, niveau_enigme FROM ENIGMES WHERE id = "+id;
	db.each(stmt, function(err,row)
	{
		var path = row.path+"/enigme.json";
		var level = row.niveau_enigme;
		var enigme=fs.readFileSync(path);
		enigme=JSON.parse(enigme);
		enigme=enigme.description;
		
		reponse.writeHead(200,{"Content-type":"text/plain; charset=utf-8"});
		reponse.write("000/"+level+"/"+enigme);
		reponse.end();
	});
}

function getEnigme(query, reponse)
{
	console.log("Get Enigma");
	var pseudo = querystring.parse(query).pseudo;
	var id_enigme = querystring.parse(query).id;
	var enigma_found=0;
	if(id_enigme)
	{
	//A completer : enigme attaché aux fichiers débloqués.
	}
	else
	{
		var stmt = "SELECT enigme, est_resolue FROM JOUEUR_ENIGMES WHERE pseudo='"+pseudo+"'";
		db.all(stmt, function(err,row)
		{
			var nb_enigme = row.length;
			for(var i=0; i<nb_enigme;i++)
			{
				if(row[i].est_resolue==0)
				{
					enigma_found=1;
					readEnigme(row[i].enigme, reponse);
					break;
				}
			}
			if(enigma_found==0){getNewEnigme(row,pseudo, reponse);}
			
		});
	}
}





function getAnswer(query,reponse)
{
	var pseudo = querystring.parse(query).pseudo;
	var id = querystring.parse(query).id;
	var pAnswer = querystring.parse(query).answer;
	
	var stmt = "SELECT path, niveau_enigme FROM ENIGMES WHERE id = "+id;
	db.each(stmt, function(err,row)
	{
		var path=row.path+"/enigme.json";
		var answers=JSON.parse(fs.readFileSynch(path)).answer;
		answers=answers.split(',');
		var nb_answers= answers.length;
		
		for(var i = 0; i<nb_answers; i++)
		{
			anwers[i]=answers[i].swplit('&');
			if(pAnswer.search(answers[i]) != -1)
			{
				updateEnigme(pseudo, id, row.niveau_enigme, 1);
				reponse.writeHead(200, {"Content-type":"text/plain"});
				reponse.write("101");
				reponse.end();
			}
			else
			{
				reponse.writeHead(200, {"Content-type":"text/plain"});
				reponse.write("102");
				reponse.end();
			}
		}
	});
}



exports.getEnigme=getEnigme;