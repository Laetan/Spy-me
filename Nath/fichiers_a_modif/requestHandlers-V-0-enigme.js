var exec = require("child_process").exec;
var util = require("util");
var sql = require("sqlite3").verbose();
var db = new sql.Database("./enigme_dtbs.db");
var prog = {};


prog.isThereEt =function(a){
if(a.indexOf('&')!=-1){
	return 1;
}
return 0;
}
prog.correctAnswer=function(b, d){
for(var i=0;i<d.length;i++){
//console.log(d[i]);
if(b.search(d[i])==-1){
	return 0;
}}
return 1;
}
prog.correctAnswer2=function(b, c){
if(b.search(c)!=-1){
	return 1;
}
return 0;
}

prog.answer2 = function (b, obj){
var answerE="";
var choicesTab=obj.answer.split(',');
for(var i=0;i<choicesTab.length;i++)
{
	console.log(choicesTab[i]);
	if (prog.isThereEt(choicesTab[i])==1){
	var choices2=choicesTab[i].split('&');
	util.log(util.inspect(choices2));
	if(prog.correctAnswer(b,choices2)==1){
	return "Gagne";
	}}
	else
	if(prog.correctAnswer2(b,choicesTab[i])==1){
	return "Gagne";
	}
}
return "Perdu";
};



//compare la réponse de l'utilisateur à la bonne réponse
/*prog.answer = function (b, obj){
var answerE="";

	if(b != obj.answer)
		answerE="Perdu"
	else
		answerE="Gagne";
	return answerE;
};*/

/****************************************
fonction associée au pathname /enigme
*****************************************/
//envoie l'enigme au client
function enigme(response,obj,u){
	var b="";
	b=obj.description;	
	response.writeHead(200, {"Content-Type": "text/plain"});
	response.write(b,'utf-8');
	response.end();
}

/******************************************
fonction associée au pathname /answer
*******************************************/
//envoie gagné ou perdu en fonction de la réponse entrée par l'utilisateur
function answer(response,obj,u){
	var a="";
	a=u.query.text;
	response.writeHead(200, {"Content-Type": "text/plain"});
	response.write(prog.answer2(a,obj)+"");
	response.end();
}

/******************************************
fonction associée au pathname /connexion
*******************************************/
//envoie gagné ou perdu en fonction de la réponse entrée par l'utilisateur
function connexion(response,obj,u){
	var a="";
	a=u.query.id;
	b=u.query.password;
	util.log(util.inspect(b));
	

	a=2;
	response.writeHead(200, {"Content-Type": "text/plain"});
	response.write(a+"");
	response.end();
}

/******************************************
fonction associée au pathname /inscription
*******************************************/
//envoie gagné ou perdu en fonction de la réponse entrée par l'utilisateur
function inscription(response,obj,u){
	var a=1;
	response.writeHead(200, {"Content-Type": "text/plain"});
	response.write(a+"");
	response.end();
}

/******************************************
fonction associée au pathname /deconnexion
*******************************************/
//envoie gagné ou perdu en fonction de la réponse entrée par l'utilisateur
function deconnexion(response,obj,u){
	var a=1;
	response.writeHead(200, {"Content-Type": "text/plain"});
	response.write(a+"");
	response.end();	
}

exports.enigme=enigme;
exports.answer=answer;
exports.connexion=connexion;
exports.inscription=inscription;
exports.deconnexion=deconnexion;
