var http = require("http");
var util = require("util");
var url = require("url");
var fs=require('fs');
var sql = require("sqlite3").verbose();
var db = new sql.Database("./enigme_dtbs.db");


function start(route,handle){
function onRequest(request, response) {

request.setEncoding('utf-8');

var pathname=url.parse(request.url).pathname;
console.log("requete reçue pour le chemin "+ pathname+ ".");

var u = url.parse(request.url, true);
var obj=fs.readFileSync('./enigme.json');
obj=JSON.parse(obj);
route(handle,pathname,response,obj,u);
/*util.log(util.inspect(obj));
util.log(util.inspect(u));

*/
}

http.createServer(onRequest).listen(8888);
console.log("Démarrage du serveur");
}

exports.start=start;