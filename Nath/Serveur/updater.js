var querystring = require("querystring");
var util = require("util");
var fs = require("fs");
var file = "spyme.db";
var exists=fs.existsSync(file);
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(file);


function updateEnigme(query,reponse)
{
	var pseudo = querystring.parse(query)["pseudo"];
	var enigma = querystring.parse(query)["enigme"];
	var solve = querystring.parse(query)["resolue"];

	var stmt = "SELECT * FROM JOUEUR_ENIGMES WHERE pseudo ='"+pseudo+"' AND enigme='"+enigma+"'";
	db.all(stmt,function(err, row)
	{
		if(row.length==0)
		{
			stmt = "INSERT INTO JOUEUR_ENIGMES VALUES ('"+pseudo+"','"+enigma+"',"+solve+")";
			db.run(stmt);
			reponse.writeHead(200,{"Content-type":"text/plain"});
			reponse.write("New line added in database JOUEUR_ENIGMES :\n");
			reponse.write("pseudo : "+pseudo);
			reponse.write("enigma : "+enigma);
			reponse.write("state : "+solve);
			reponse.end();
		}
		else if(solve==1)
		{
			stmt = "UPDATE JOUEUR_ENIGMES SET est_resolue=1  WHERE pseudo ='"+pseudo+"' AND enigme='"+enigma+"'";
			db.run(stmt)
			reponse.writeHead(200,{"Content-type":"text/plain"});
			reponse.write("Line updated in JOUEUR_ENIGMES :\n");
			reponse.write("pseudo : "+pseudo);
			reponse.write("enigma : "+enigma);
			reponse.write("state : "+solve);
			reponse.end();
		}
		else
		{
			reponse.writeHead(200,{"Content-type":"text/plain"});
			reponse.write("No change in database");
			reponse.end();
		}
	});
}

function updateLevel(pseudo, rise)
{	// Rise the player's level
	
	var stmt = "SELECT niveau_joueur FROM JOUEUR WHERE pseudo='"+pseudo+"'";
	db.serialize(function()
	{
		db.each(stmt, function(err,row)
		{
			var level = row.niveau_joueur;
			level = level + rise;
		});
	
		stmt = "UPDATE JOUEUR SET niveau_joueur="+level+"  WHERE pseudo ='"+pseudo+"'";
		db.run(stmt);
	});
}


function updateGame(query,reponse)
{
	var pseudo = querystring.parse(query)["pseudo"];
	var game = querystring.parse(query)["jeu"];
	var score = querystring.parse(query)["points"];
	var level = querystring.parse(query)["niveau"];
	
	var stmt = "SELECT niveau,point FROM JOUEUR_JEUX WHERE pseudo ='"+pseudo+"' AND jeu='"+game+"'";
	db.all(stmt,function(err, row)
	{
		stmt="";
		if(row.length==0)
		{
			stmt = "INSERT INTO JOUEUR_JEUX VALUES ('"+pseudo+"','"+game+"',"+level+","+score+")";
			db.run(stmt);
			reponse.writeHead(200,{"Content-type":"text/plain"});
			reponse.write("New line added in database JOUEUR_JEUX :\n");
			reponse.write("pseudo : "+pseudo);
			reponse.write("jeu : "+game);
			reponse.write("niveau : "+level);
			reponse.write("score : "+score);
			reponse.end();
		}
		else
		{
			
			if(score > row[0].points)
			{
				stmt = "UPDATE JOUEUR_JEUX SET points="+score+"  WHERE pseudo ='"+pseudo+"' AND jeu='"+game+"'";
				db.run(stmt)
				reponse.writeHead(200,{"Content-type":"text/plain"});
				reponse.write("Line updated in JOUEUR_JEUX :\n");
				reponse.write("pseudo : "+pseudo);
				reponse.write("jeu : "+game);
				reponse.write("niveau : "+level);
				reponse.write("score : "+score);
				reponse.write("\n");
			}
			if(level > row[0].niveau)
			{
				if(stmt==""){reponse.writeHead(200,{"Content-type":"text/plain"});}
				stmt = "UPDATE JOUEUR_JEUX SET niveau="+level+"  WHERE pseudo ='"+pseudo+"' AND jeu='"+game+"'";
				db.run(stmt)
				reponse.writeHead(200,{"Content-type":"text/plain"});
				reponse.write("Line updated in JOUEUR_JEUX :\n");
				reponse.write("pseudo : "+pseudo);
				reponse.write("jeu : "+game);
				reponse.write("niveau : "+level);
				reponse.write("score : "+score);
			}
			if(stmt="")
			{
				reponse.writeHead(200,{"Content-type":"text/plain"});
				reponse.write("No change in database");
			}
			reponse.end();
		}
	});
}


exports.updateEnigme	=	updateEnigme;
exports.updateLevel		=	updateLevel;
exports.updateGame		=	updateGame;	