var serveur = require("./server");
var router = require("./router");
var requestHandler = require("./requestHandler");
var handle = {}

handle["/"] = 			requestHandler.std;
handle["/newlog"] = 	requestHandler.newLog;
handle["/login"] = 		requestHandler.logIn;
handle["/logout"] = 	requestHandler.logOut;
handle["/getenigme"] =	requestHandler.getEnigme;
handle["getanswer"] =	requestHandler.getAnswer;

serveur.start(router.route, handle);