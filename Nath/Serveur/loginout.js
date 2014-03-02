//Fonctions de log in et log out

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
	console.log(stmt);
	db.all(stmt, function(err, row)
	{
		if(row.length == 0)
		{
			reponse.writeHead(200, {"Content-type":"text/plain"});
			reponse.write("010\nWrong pseudo or password");
			reponse.end();
		}
		else
		{
			reponse.writeHead(200, {"Content-type":"text/plain"});
			reponse.write("000\npoints="+row[0].points);
			/*Send additional info
			...
			*/
			reponse.end();
		}
	});
}


function logOut(query,reponse)
{
	var pseudo = querystring.parse(query)["pseudo"];
	
	/*Erase temp id?
	...
	*/
	
	reponse.writeHead(200, {"Content-type":"text/plain"});
	reponse.write("000\nLogged out");
	reponse.end();
}


exports.logIn = logIn;
exports.logOut = logOut;