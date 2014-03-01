var fs=require('fs');
var file="spyme.db";
var exists=fs.existsSync(file);

var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(file);

 db.serialize(function(){
	 db.run("CREATE TABLE JOUEUR (pseudo TEXT PRIMARY KEY,password TEXT, niveau_joueur INTEGER, points INTEGER)");
	 db.run("CREATE TABLE ENIGMES (id INTEGER PRIMARY KEY, path TEXT, niveau_enigme INTEGER)");
	 db.run("CREATE TABLE JOUEUR_ENIGMES (pseudo TEXT REFERENCES JOUEUR(pseudo), enigme INTEGER, est_resolue INTEGER, FOREIGN KEY(pseudo) REFERENCES JOUEUR(pseudo),FOREIGN KEY(enigme) REFERENCES ENIGMES(id))");//

	 db.run("INSERT INTO ENIGMES VALUES (1,'Enigmes/enigme01', 0)");
 });
db.close();