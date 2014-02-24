var server = require("./server-V-0-enigme");
var router = require("./router-V-0-enigme");
var requestHandlers = require("./requestHandlers-V-0-enigme");
var handle = {};
//gestionnaire de requête à appeler en fonction de l'url reçue
handle["/enigme"] = requestHandlers.enigme;
handle["/answer"] = requestHandlers.answer;


server.start(router.route,handle);