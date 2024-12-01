Pour ce premier rendu bi-hebdomadaire, on devait faire la conception principalement et un bout de développement.
On a décidé dans notre groupe de nommer toutes nos classes en anglais.
Comme on doit faire la conception en format MVC, on a 3 étapes:

Tout d'abord, les Modèles...

TUILES (patron Template):
Pour nos tuiles, nous avons décidé d'en faire un Template TileTemplate qui aura 3 filles pour chaque type de tuiles.
Le template est nécessaire afin que chaque type retourne le chemin valide correcte pour son type (Hallway laisse passer en haut et en bas OU à droite et à gauche)
La tuile aura un attribut _orientation qui vient d'une enum contenant les 4 directions afin d'avoir le sens d'affichage et sera utilisé pour connaitre les chemins valides, plus précisément pour définir les issues de l'attribit _entries.

TERRAIN (+ patron Factory):
On a une classe GameBoard, qui gère des tuiles pour créer le terrain (il pourra donc créer et gérer des tuiles).
Elle aura un attribut _extraTile pour la pièce qui déplace le labyrinth (la 50e pièce) et un tableau de tableau de TileTemplate afin de représenter le terrain.
Afin de créer les 50 tuiles nécessaires, on a décidé de faire une TileFactory qui sera utilisé par le GameBoard;
La factory est le patron adapté ici puisqu'on a plusieurs classes qui héritent d'une classe abstraite et elle est faite pour pouvoir créer plusieurs instances différentes. ~~On notera que c'est le seul patron de création qu'on a vu aussi et il n'y a pas assez d'attribut pour en faire un patron Builder (recherche personnel)~~.

JOUEURS:
Par rapport aux Joueurs, il n'y a pas grand chose de spécifique, c'est une classe avec plusieurs attributs dont _goalsDeck, un tableau de 6 objectifs, une position principalement définie par les attributs _posX et _posY. (on peut rajouter des noms pour différencier les 4 joueurs, mais on n'en est pas encore là, YAGNI!)
Leurs déplacements seront gérés par un controlleur.

OBJECTIFS:
Les objectifs à atteindre sont aussi dans une enum nommé Entity (pas monstre, pas trésor, les deux à la fois!);
Pour le moment, les noms des objectifs ne sont pas très pertinents, on les modifiera pour qu'ils correspondent à une image visuelle qui représentera l'objectif.

OBSERVER:
L'observeur ici va permettre de notifier les vues à chaque changement apporté aux classes Joueurs et Gameboard. En effet, les notifications sont utiles pour actualiser la fenêtre du jeu afin qu'elle affiche les bonnes informations et au bon moment.


En ce qui concerne les modèles, on aura besoin d'un controller afin de gérer le jeu lui-même...

Controller:
On a créé une classe GameController qui va gérer l'avancé du jeu.
Elle aura un terrain, 4 joueurs sous le format d'une liste ~~et devrait être un Singleton~~.
Elle s'occupera du déplacement des joueurs, le déplacement du labyrinth avec la 50e tuile et s'occupe grosso modo des règles du jeu (mettre le pion à l'autre bout s'il est expulsé du terrain, vérification d'un objectif atteint, etc).

Et enfin, les vues!

View:
On aura une classe GameWindow qui s'occupera de l'interface graphique, mais aussi de EndingWindow qui s'occupe de l'affichage graphique de la fin du jeu.
Elles ne sont cependant pas encore codées, mais on suppose l'utilisation du patron observer : GameObserver afin de mettre à jour l'interface graphique lors du jeu
On devra potentiellement faire une façade si on veut pouvoir jouer plusieurs parties à la suite ou tout simplement recommencer depuis le début (en Bonus :D).

Notre conception du jeu Labyrinth a donc 3 patrons dont 1 de création et 2 de comportement.
à noter aussi, tout ce qui est aléatoire sera utilisé via une instance de classe random.