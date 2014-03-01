var check_db=require("./check_db");
var signIn = require("./newLog");
var querystring = require("querystring");
var fs = require("fs");
var file = "spyme.db";
var exists=fs.existsSync(file);
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(file);

function std(query, reponse)
{	//Standard request answer
	// reponse.writeHead(200,{"Content-type" : "text/plain"});
	// reponse.write("Standard reponse\nQuery : "+query);
	// reponse.end();
	check_db.readDB(query,reponse);
	console.log("Handled by sdt()");	
}

function newLog(query, reponse)
{	//Create new player
	console.log("Handled by newLog()");
	
	signIn.newLog(query, reponse);
	
}

function logIn(query, reponse)
{	// Log In function
	console.log("Handled by logIn()");
}

function logOut(query, reponse)
{	// Log Out function
	console.log("Handled by logOut()");
}

function getEnigme(query, reponse)
{	// Send an enigma to the player
	console.log("Handled by getEnigme()");
}

function getAnswer(query, reponse)
{	// Check the player's answer
	console.log("Handled by getAnswer()");
}

exports.std = std;
exports.newLog = newLog;
exports.logIn = logIn;
exports.logOut = logOut;
exports.getEnigme = getEnigme;
exports.getAnswer = getAnswer;
