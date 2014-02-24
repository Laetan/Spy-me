var exec = require("child_process").exec;
var sql = require("sqlite3").verbose();
var db = new sql.Database("./enigme_dtbs.db");
var prog = {};

//compare la réponse de l'utilisateur à la bonne réponse
prog.answer = function (b, obj){
var answerE="";

	if(b != obj.answer)
		answerE="Perdu"
	else
		answerE="Gagne";
	return answerE;
};

//envoie l'enigme au client
function enigme(response,obj,u){
	var b="";
	b=obj.description;	
	response.writeHead(200, {"Content-Type": "text/plain"});
	response.write(b,'utf-8');
	response.end();
}

//envoie gagné ou perdu en fonction de la réponse entrée par l'utilisateur
function answer(response,obj,u){
	var a="";
	a=u.query.text;
	response.writeHead(200, {"Content-Type": "text/plain"});
	response.write(prog.answer(a,obj)+"");
	response.end();
}


exports.enigme=enigme;
exports.answer=answer;

