function route(handle,pathname,response,obj,u) {
  console.log("Début du traitement de l'URL " + pathname + ".");
  if(typeof handle[pathname]==='function'){
	handle[pathname](response,obj,u);
  }
  else{
	console.log("Aucun gestionnaire associé à "+pathname);
  }
  }
  exports.route = route;