File Ninja v0.1 - 17/03/2014 � 23h30

1) File Ninja parce que bas� sur Fruit Ninja et qu'on joue ici avec des fichiers, et que
j'ai une imagination d�bordante.

2) Contient actuellement le noyeau du jeu : des items qui tombent � des vitesses et 
sur des trajectoires diff�rentes, qu'on peut r�cuperer en cliquant/tapant dessus,
et son 'stock�s' dans le bas de l'�cran

3) Instalation : Sous Eclipse avec l'ADT install�, on importe le projet : 
(File > Import > Existing projects into Workspace)
Prenez bien tout les projets (core + Desktop + Android + appcompat) sinon vous aurez des surprises.
J'enverrai les images par mails puisqu'il vaut mieux pas les mettre sur le git.
Ca devrai normalement suffire. En cas de probl�mes, on avisera.

4) Lancement : Peut se lancer sur android ou sur windows.
windows -> click droit sur le projet "SpyMe-FileNinja-Desktop" > Run As > Java application
>Selectionnez "DesktopGame" puis "Ok" > Enjoy

Android -> -> click droit sur le projet "SpyMe-FileNinja-android" > Run As > Android application
>Selectionnez l'appareil de votre choix puis "Ok" > Enjoy

Sur android, prenez si possible un vrai t�l�phone. L'�mulateur est efficace mais mets
des plomb � demarrer et lancer les jeux.

5) Commentaires : Je vais commenter un minimum le code, mais si vous avez pas encore
vu au moins jusqu'a la cr�ation du jeu Drop dans le wiki de libGDX, vous attendez pas
� comprendre.

6) Impl�mentations future :
	- Correction des divers bugs d'affichage ;
	- D�gotage de vrai sprites. Parce que des boules c'est bien pour les test, 
mais pas plus ;
	- Plus de trajectoires et vitesses possibles ;
	- Les fichiers r�cup�res sortiront de l'�cran une fois "compl�t�s" ;
	- Musique? ;
	- Diff�rents "types" de fichiers � r�cuperer par le joueur ;
	- Faire un vrai fond d'�cran. Le noir, c'est moche ;
	- Et plus si j'en trouve.
	