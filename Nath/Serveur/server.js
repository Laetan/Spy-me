var http = require("http");
var url = require("url");
var util = require("util");

function start(routeur, handle)
{
	http.createServer(function(request, reponse)
	{
		request.setEncoding("utf-8");
		//reponse.setEncoding("utf-8");
		routeur(handle, request, reponse);

	}).listen(8888);
	console.log("Server running : localhost:8888");
}

exports.start = start;