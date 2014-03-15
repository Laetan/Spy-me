//Fonction d'inscription. OK

var querystring = require("querystring");
var util = require("util");
var fs = require("fs");
var file = "spyme.db";
var exists=fs.existsSync(file);
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(file);
var cryptage = require("./cryptage");

function newLog(query, reponse)
{
	var pseudo = querystring.parse(query)["pseudo"];
	var password = querystring.parse(query)["password"];
	var stmt = "SELECT * FROM JOUEUR WHERE pseudo ='"+ pseudo+"'";
	
	console.log("pseudo : "+pseudo+" -- password : "+password);
	console.log(stmt);
	db.all(stmt, function(err,row)
	{
		if(pseudo == "" || password == "")
		{
			reponse.writeHead(200, {"Content-type" : "text/plain"});
			reponse.write("102");
			reponse.end();
		}
		else if(row.length != 0 && pseudo != "" && password != "")
		{
			reponse.writeHead(200, {"Content-type" : "text/plain"});
			reponse.write("101");
			reponse.end();
		}
		else
		{
			var idTemp = cryptage.newIdTemp(pseudo);
			stmt = "INSERT INTO JOUEUR VALUES('"+pseudo+"','"+password+"','"+idTemp+"',0,0)"
			db.run(stmt);
			reponse.writeHead(200, {"Content-type" : "text/plain"});
			reponse.write("100/"+idTemp);
			reponse.end();

		}
	});
}

exports.newLog=newLog;