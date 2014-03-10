var url = require("url");
var cryptage = require("./cryptage");
var util = require("util");
var querystring = require("querystring");
var EventEmitter = require("events").EventEmitter;

function route(handle,request, reponse)
{
	var r=new router(handle,request, reponse);
	r.run();
	r=null;
}



router = function(handle,request,reponse)
{
	this.request=request;
	this.handle=handle;
	this.reponse=reponse;
};

router.prototype.run = function(){
	var pathname = url.parse(this.request.url).pathname;
	var query = url.parse(this.request.url).query;
	if(query)
		var id = querystring.parse(query).id;
	console.log("");
	console.log("--------------------------------");
	console.log("");
	console.log("Routing :: pathname : "+pathname);
	if(typeof this.handle[pathname]==='function')
	{
		console.log(id);
		if(id)
		{
			console.log("Pseudo needed :: id ="+id);
			cryptage.getPseudo(pathname, query, this.handle, this.reponse);
		}
		else
		{
			this.handle[pathname](query, this.reponse);
		}
	}
	else
	{
		this.reponse.writeHead(404, {"Content-type" : "text/plain"});
		this.reponse.write("Error 404\n")
		this.reponse.write("No response available for the pathname : "+pathname);
		this.reponse.end();
	}
}

exports.route=route;