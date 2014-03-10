
var querystring = require("querystring");
var util = require("util");
var Chance = require("chance")
var fs = require("fs");
var file = "spyme.db";
var exists = fs.existsSync(file);
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(file);
var chance = new Chance();


//Fonction de création d'id temporaire
function newIdTemp(pseudo)
{
	var idTemp = 0;
	while(idTemp==0){idTemp = chance.integer();}
	
	var stmt = "UPDATE JOUEUR SET id_temp="+idTemp+" WHERE pseudo='"+pseudo+"'";
	
	db.run(stmt);
	console.log("new id : "+idTemp);
	return idTemp;	
}

// Fonction de récupération du pseudo à partir de l'id
// Intègre le pseudo dans le query

function getPseudo(pathname, query, handle, reponse)
{
	
	var stmt = "SELECT pseudo FROM JOUEUR WHERE id_temp="+querystring.parse(query).id;
	console.log(stmt);
	db.all(stmt, function(err,row)
	{
		console.log(row);
		if(row.length!=0)
		{
			query+="&pseudo="+row[0].pseudo;
			console.log("Convert id to pseudo : "+querystring.parse(query).pseudo);
			handle[pathname](query, reponse);
		}
		else	
		{	
			reponse.writeHead(200, {"Content-type":"text/plain"});
			reponse.write("107");
			reponse.end();
		}
	});
}

exports.getPseudo = getPseudo;
exports.newIdTemp = newIdTemp;