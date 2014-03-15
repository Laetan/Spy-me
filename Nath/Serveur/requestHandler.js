var check_db=require("./check_db");
var signIn = require("./newLog");
var logInOut = require("./loginout");
var updater=require("./updater");
var enigme = require("./enigme");
var reinit=require("./reinit");



function std(query, reponse)
{	//Standard request answer

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
	logInOut.logIn(query, reponse);
}

function logOut(query, reponse)
{	// Log Out function
	console.log("Handled by logOut()");
	logInOut.logOut(query, reponse);
}

function getEnigme(query, reponse)
{	// Send an enigma to the player
	console.log("Handled by getEnigme()");
	enigme.getEnigme(query,reponse);
}

function getAnswer(query, reponse)
{	// Check the player's answer
	console.log("Handled by getAnswer()");
	enigme.getAnswer(query,reponse);
}

function reinitialisation(query, reponse)
{	// Update the db when winning a game

	reinit.reinitialisation(query, reponse);
}


function updateGame(query, reponse)
{	// Update the db when winning a game

	updater.updateGame(query, reponse);
}



exports.std = std;
exports.newLog = newLog;
exports.logIn = logIn;
exports.logOut = logOut;
exports.getEnigme = getEnigme;
exports.getAnswer = getAnswer;
exports.updateGame = updateGame;
exports.reinitialisation=reinitialisation;