var fs = require("fs");
var file = "spyme.db";
var exists=fs.existsSync(file);
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(file);
function readDB(query, reponse)
{
	reponse.writeHead(200, {"Content-type" : "text/plain"});
	db.serialize(function(){
		reponse.write("Going through all DataBase :\n\n");
		reponse.write("Table : JOUEUR\n");
		db.each("SELECT * FROM JOUEUR", function(err, row) {
		  reponse.write(row.id + ":" + row.pseudo + " -- password : "+row.password);
		  reponse.write(" -- level : "+row.niveau_joueur);
		  reponse.write(" -- score : "+row.point);
		  reponse.write("\n");
		});
		
		reponse.write("\n");
		reponse.write("Table : ENIGMES\n");
		db.each("SELECT * FROM ENIGMES", function(err, row) {
		  reponse.write(row.id + ":" + row.path + " / level : "+row.niveau_enigme);
		  reponse.write("\n");
		});
		
		reponse.write("\n");
		reponse.write("Table : JOUEUR_ENIGMES\n");
		db.each("SELECT * FROM JOUEUR_ENIGMES", function(err, row) {
		  reponse.write(row.id + ":");
		  reponse.write(" pseudo : "+row.pseudo);
		  reponse.write(" -- enigme : "+row.enigme);
		  reponse.write(" -- résolue ? : "+row.est_resolue);
		});
		
		
		reponse.end();
	});
	
}


exports.readDB=readDB;