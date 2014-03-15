var serveur = require("./server");
var router = require("./router");
var cryptage = require("./cryptage");
var requestHandler = require("./requestHandler");
var handle = {}

handle["/"] = requestHandler.std;
handle["/inscription"] = requestHandler.newLog;
handle["/connexion"] = 	requestHandler.logIn;
handle["/deconnexion"] = requestHandler.logOut;
handle["/enigme"] =	requestHandler.getEnigme;
handle["/answer"] =	requestHandler.getAnswer;
handle["/updategame"] = requestHandler.updateGame;
handle["/reinitialisation"] = requestHandler.reinitialisation;

serveur.start(router.route, handle);