//Fonctions de log in et log out
var cryptage = require("./cryptage");
var querystring = require("querystring");
var util = require("util");
var fs = require("fs");
var file = "spyme.db";
var exists=fs.existsSync(file);
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(file);


function logIn(query,reponse)
{
	var pseudo = querystring.parse(query)["pseudo"];
	var password = querystring.parse(query)["password"];
	
	var stmt = "SELECT * FROM JOUEUR WHERE pseudo ='"+pseudo+"' AND password ='"+password+"'";
	db.all(stmt, function(err, row)
	{
		if(row.length == 0)
		{
			reponse.writeHead(200, {"Content-type":"text/plain"});
			reponse.write("103");
			reponse.end();
		}
		else
		{
			var idTemp = cryptage.newIdTemp(pseudo)
			reponse.writeHead(200, {"Content-type":"text/plain"});
			reponse.write("100/"+idTemp+"/"+row[0].points+"/"+row[0].niveau_joueur);
			/*Send additional info
			...
			*/
			reponse.end();
		}
	});
}


function logOut(query,reponse)
{
	console.log("Erasing id for "+querystring.parse(query).pseudo);
	var stmt = "UPDATE JOUEUR SET id_temp = -1 WHERE pseudo ='"+querystring.parse(query).pseudo+"'";
	db.run(stmt);
	reponse.writeHead(200, {"Content-type":"text/plain"});
	reponse.write("100");
	reponse.end();
}


exports.logIn = logIn;
exports.logOut = logOut;