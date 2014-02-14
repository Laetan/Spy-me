var http = require("http");
var util = require("util");
var url = require("url");
var fs=require('fs');


var prog = {};

prog.facto = function (a){
	return (a<2) ? 1 : a*prog.facto(a-1);
};

prog.answer = function (b, obj){
var answerE="";
answerE.setEncoding("utf8");
	if(b != obj.answer)
		answerE="Perdu"
	else
		answerE="Gagne";
	return answerE;
};

http.createServer(function(request, response) {

request.setEncoding('utf-8');
var u = url.parse(request.url, true);

var obj=fs.readFileSync('./enigme1.json');
obj=JSON.parse(obj);

util.log(util.inspect(obj));

util.log(util.inspect(u));
response.writeHead(200, {"Content-Type": "text/plain"});

if (u.pathname == '/facto') {
	
	response.write(prog.facto(+u.query.number)+"");
}

if (u.pathname == '/enigme') {
	var b="";

	b=obj.description;


	
	response.write(b,'utf-8');
}

if (u.pathname == '/answer') {
	var a="";
	a=u.query.text;
	response.write(prog.answer(a,obj)+"");
}

response.end();
}).listen(8888);