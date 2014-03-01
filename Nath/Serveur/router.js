
function route(handle,pathname, query, reponse)
{
	console.log("Routing / pathname : "+pathname);
	console.log(typeof handle[pathname]);
	if(typeof handle[pathname]==='function')
	{
		handle[pathname](query, reponse);
	}
	else
	{
		reponse.writeHead(404, {"Content-type" : "text/plain"});
		reponse.write("Error 404\n")
		reponse.write("No response available for the pathname : "+pathname);
		reponse.end();
	}
}

exports.route=route;