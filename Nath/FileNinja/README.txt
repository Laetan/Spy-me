File Ninja v0.1 - 17/03/2014 à 23h30

1) File Ninja parce que basé sur Fruit Ninja et qu'on joue ici avec des fichiers, et que
j'ai une imagination débordante.

2) Contient actuellement le noyeau du jeu : des items qui tombent à des vitesses et 
sur des trajectoires différentes, qu'on peut récuperer en cliquant/tapant dessus,
et son 'stockés' dans le bas de l'écran

3) Instalation : Sous Eclipse avec l'ADT installé, on importe le projet : 
(File > Import > Existing projects into Workspace)
Prenez bien tout les projets (core + Desktop + Android + appcompat) sinon vous aurez des surprises.
J'enverrai les images par mails puisqu'il vaut mieux pas les mettre sur le git.
Ca devrai normalement suffire. En cas de problèmes, on avisera.

4) Lancement : Peut se lancer sur android ou sur windows.
windows -> click droit sur le projet "SpyMe-FileNinja-Desktop" > Run As > Java application
>Selectionnez "DesktopGame" puis "Ok" > Enjoy

Android -> -> click droit sur le projet "SpyMe-FileNinja-android" > Run As > Android application
>Selectionnez l'appareil de votre choix puis "Ok" > Enjoy

Sur android, prenez si possible un vrai téléphone. L'émulateur est efficace mais mets
des plomb à demarrer et lancer les jeux.

5) Commentaires : Je vais commenter un minimum le code, mais si vous avez pas encore
vu au moins jusqu'a la création du jeu Drop dans le wiki de libGDX, vous attendez pas
à comprendre.

6) Implémentations future :
	- Correction des divers bugs d'affichage ;
	- Dégotage de vrai sprites. Parce que des boules c'est bien pour les test, 
mais pas plus ;
	- Plus de trajectoires et vitesses possibles ;
	- Les fichiers récupéres sortiront de l'écran une fois "complétés" ;
	- Musique? ;
	- Différents "types" de fichiers à récuperer par le joueur ;
	- Faire un vrai fond d'écran. Le noir, c'est moche ;
	- Et plus si j'en trouve.
	