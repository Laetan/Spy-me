var http = require("http");
var url = require("url");
var querystring = require("querystring");
var util = require("util");
function start(routeur, handle)
{
	http.createServer(function(request, reponse)
	{
		request.setEncoding("utf-8");
		
		var pathname = url.parse(request.url).pathname;
		var query = url.parse(request.url).query;
		console.log("Request/ pathname : "+pathname);
		routeur(handle, pathname, query, reponse);
		
		
		
	}).listen(8888);
	console.log("Server running : localhost:8888");
}

exports.start = start;