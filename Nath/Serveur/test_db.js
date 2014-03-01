var fs=require('fs');
var file="spyme.db";
var exists=fs.existsSync(file);
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(file);

// db.serialize(function(){
	// console.log("JOUEUR");
	// db.each("SELECT * FROM JOUEUR", function(err, row) {
		  // console.log("Pseudo : " + row.pseudo + " -- password : "+row.password+" -- level : "+row.niveau_joueur+" -- score : "+row.point);
	  // });
	  
	// console.log("ENIGMES"); 
	// db.each("SELECT * FROM ENIGMES", function(err, row) {
		  // console.log(row.id + " : " + row.path + " -- level : "+row.niveau_enigme);
	  // });
	// console.log("JOUEUR_ENIGMES");
	// db.each("SELECT * FROM JOUEUR_ENIGMES", function(err, row) {
		  // console.log("Pseudo : " + row.pseudo + " -- enigme : "+row.enigme);
	  // });
 // });
 
 db.all("SELECT * FROM JOUEUR", function(err, row) {
      console.log(row);
  });