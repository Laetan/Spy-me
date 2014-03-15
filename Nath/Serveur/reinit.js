var querystring = require("querystring");
var util = require("util");
var fs = require("fs");
var file = "spyme.db";
var exists=fs.existsSync(file);
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(file);


/**
*fonction réinitialisation appelée dans le menu option - réinitialisation
* set le niveau du joueur à 0, ses points à 0, et efface toutes les données relative au joueur en BDD
*renvoie 127 au client
*/
function reinitialisation(query,reponse){

var pseudo = querystring.parse(query)["pseudo"];
db.run("UPDATE JOUEUR SET niveau_joueur=0 AND points=0 WHERE pseudo='"+pseudo+"'");
db.run("DELETE FROM JOUEUR_ENIGMES WHERE pseudo='"+pseudo+"'");
	reponse.writeHead(200, {"Content-type":"text/plain"});
	reponse.write("127");
	reponse.end();
}

exports.reinitialisation = reinitialisation;