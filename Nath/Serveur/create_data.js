
var fs=require('fs');

var file="enigme_dtbs.db";
var exists=fs.existsSync(file);

var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(file);

db.serialize(function() {
  db.run("CREATE TABLE Enigme02 (ident INTEGER,info TEXT)");

  var stmt = db.prepare("INSERT INTO Enigme02 VALUES (?,?)");
  for (var i = 0; i < 100; i++) {
      stmt.run(i,"./enigme/enigme" + i+".json");
  }
  stmt.finalize();

  db.each("SELECT rowid AS id, ident FROM Enigme02", function(err, row) {
      console.log(row.id + ": " +row.ident);
  });
});

db.close();


/*


var sql_req = "SELECT * FROM Enigme02 ORDER BY RANDOM() LIMIT 1";
util.log(util.inspect(sql_req));


  db.each(" SELECT * FROM Enigme02 ORDER BY RANDOM() LIMIT 1", function(err, row) {
      console.log(row.info);
  });




var sql_req = "SELECT * FROM Enigme02 ORDER BY RANDOM() LIMIT 1";

  db.each(sql_req, function(err, row) {
      console.log(row.info);
	  var pathname=url.parse(request.url).pathname;
	console.log("requete reçue pour le chemin "+ pathname+ ".");


var u = url.parse(request.url, true);
var obj=fs.readFileSync(row.info);

obj=JSON.parse(obj);
util.log(util.inspect(obj));
route(handle,pathname,response,obj,u);
*/