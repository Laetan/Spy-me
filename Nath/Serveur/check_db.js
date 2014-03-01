//Fonction de lecture de la base de donnée. Appelée par pathname "/"

var fs = require("fs");
var file = "spyme.db";
var exists=fs.existsSync(file);
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(file);
var done=0;
var tab1="";
var tab2="";
var tab3="";

function readDB(query, reponse)
{

	db.all("SELECT * FROM JOUEUR", function(err, row) {
		var nb_line = row.length
		for(var i=0; i < nb_line; i++)
		{
			tab1 =tab1 +row[i].id + ":" + row[i].pseudo + " -- password : "+row[i].password;
			tab1 = tab1 + " -- level : "+row[i].niveau_joueur;
			tab1 = tab1 + " -- score : "+row[i].point;
			tab1 = tab1 + "\n";
		}
		done = done +1;
		close(reponse);
	});
	
	
	db.all("SELECT * FROM ENIGMES", function(err, row) {
		var nb_line = row.length
		for(var i=0; i < nb_line; i++)
		{
			tab2 = tab2 + row[i].id + ":" + row[i].path + " / level : "+row[i].niveau_enigme;
			tab2 = tab2 +"\n";
		}
		done = done +1;
		close(reponse);
	});
	

	db.all("SELECT * FROM JOUEUR_ENIGMES", function(err, row) {
		var nb_line = row.length
		for(var i=0; i < nb_line; i++)
		{
			tab3 = tab3 + row[i].id + ":";
			tab3 = tab3 + " pseudo : "+row[i].pseudo;
			tab3 = tab3 + " -- enigme : "+row[i].enigme;
			tab3 = tab3 + " -- résolue ? : "+row[i].est_resolue;
			tab3 = tab3 + "\n";
		}
		done = done +1;
		close(reponse)
	});
}


function close(reponse)
{ //write the response when all table have been read
	
	if(done==3)
	{
		reponse.writeHead(200, {"Content-type" : "text/plain"});
		reponse.write("Going through all DataBase :\n\n");
		reponse.write("Table : JOUEUR\n");
		reponse.write(tab1);
		reponse.write("\n");
		reponse.write("Table : ENIGMES\n");
		reponse.write(tab2);
		reponse.write("\n");
		reponse.write("Table : JOUEUR_ENIGMES\n");
		reponse.write(tab3);
		reponse.end();
	}
}

exports.readDB=readDB;