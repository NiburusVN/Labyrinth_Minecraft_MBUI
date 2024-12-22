
# **Projet A31 `Je suis perdu (Labyrinth - le jeu de société)`**

Ce Projet se base sur le jeu de société **Labyrinth**.
Pour plus d'information, regardez: [https://www.regledujeu.fr/labyrinthe/](https://www.regledujeu.fr/labyrinthe/).

***CECI EST LE RAPPORT FINALE DU PROJET***
Pour ce rapport de fin de projet, on a modifié **beaucoup** d'attributs, de classe et même de base.
On passera en revue toutes les classes présentes dans le projet.

Par rapport au premier rendu, nous n'avons pas de nouveau patron de conception.

Le design visuel du projet se base sur **Minecraft**. Le décor est un assemblage fait par **Michel BUI**

> `Par Michel BUI et Daniel YANG`

## **Composition du projet**

Notre projet suit une architecture **MVC** et comporte donc:
+ Des *models*:
    - une interface (**GameObserver**) 
    - une classe abstraite (**TileTemplate**)
    - 2 enum (**Direction** et **Entity** ; **Entity** ce sont les objectifs à atteindre) 
    - 8 classes (**Corner**, **StartingTile**, **Hallway**, **Intersection**, **TileFactory**, **GameBoard**, **Player** et **Game**).
+ Des *controllers*: (il n'y en a qu'un controlleur)
    - classe **GameController**
+ Des *views*: (aussi qu'une seule vue)
    - classe **GameWindow** ; elle hérite de l'interface **GameObserver**

Il y a aussi un `Main.java` qui permet compiler le projet dans son intégralité.

Le projet utilise au total **3 patrons** dont 2 de *comportement* et 1 de *création*
Plus de détail sur les classes et leurs implémentations sont disponible dans `uml/Rendu2.puml`.

## **Les modèles**

#### Changements générales

- Les positions étaient auparavant deux Integers `posX` et `posY`
    + Elles sont maintenant un tableau d'Integer de taille 2
    + Cela facilite le passage d'une position en paramètre à une fonction, on sera aussi certain de la correspondance (Y, X) des valeurs.
- **GameBoard** avait un attribut `_gameBoard` qui était une liste de liste de TileTemplate (`List<List<TileTemplate>>`) de taille **7x7**
    + Cet attribut a maintenant une taille **9x9**
    + Cela facilite la gestion de l'éjection des joueurs et de l'insertion de la nouvelle tuile.
    + Cependant, puisque notre nouvelle tuile se déplacera sur le bord, il faudra connaitre sa position à tout moment. On a ainsi ajouté un attribut `_extraTilePosition` dans **GameBoard**


#### Les enums **Direction** et **Entity**

**Direction** est l'énumération qui contient les orientations des tuiles.
Elle possède 4 valeurs qui sont les 4 directions principales (**Nord**/**Sud**/**Est**/**Ouest**).

**Entity** est l'énumération qui s'occupe des objectifs présent dans le jeu.
Les objectifs étaient nommés `"OBJECTIF1", "OBJECTIF2",...` et ce jusqu'à 24.
A présent, ces objectifs représente des objets de **Minecraft**.
Elle contient aussi 4 éléments supplémentaires qui correspondent à la case de départ de chaque joueur (condition de retour à la case de départ une fois tous les objectifs atteints).

#### Interface **GameObserver**

**GameObserver** correspond à notre premier *patron de comportement*.
Puisqu'on est dans une architecture MVC, l'utilisation d'observer est une formalité.

Ce que **GameObserver** observe est la classe **Game**.
Elle possède plusieurs fonction `update` afin de mettre à jour la vue.

#### Classe abstraite **TileTemplate** et Classes **Hallway**, **Corner**, **Intersection** et **StartingTile**

**TileTemplate** correspond aussi à un *patron de comportement*
Le patron `Template` semble le plus adapté à notre situation puisque les **Tuiles** sont très similaires mais juste différente dans la forme.

Cette classe possède donc toutes les fonctions utiles pour une tuile, elle sera ensuite *héritée* par les types de tuiles.
Elle possède deux constructeurs afin de pouvoir choisir une orientation si nécessaire (cases de départs ou débogage)
Par rapport au dernier rendu, cette classe a une nouvelle fonction `String getType();` afin de connaître le polymorphisme utilisé.

Les trois classes **Hallway**, **Corner** et **Intersection** héritent de la classe abstraite **TileTemplate**.
Leurs noms sont explicite et sont renvoyés par la fonction `getType()`.

Afin de déplacer un joueur, il faut connaître si le chemin est existant (pour ne pas foncer dans un mur):
On a une fonction `getEntries()` qui renvoi un tableau de `Boolean`: ce sont les chemins valides en fonction de l'orientation de la tuile.

Pour des raisons d'optimisation, une fois qu'un objectif est atteint, on retire l'*entity* présente sur la tuile afin de ne plus la charger pour les futurs mise à jour de l'image de la tuile. (ne rentre plus dans un `if`)

La classe **StartingTile** hérite de **Corner** et possède un autre constructeur avec une *orientation* et une *entity* (Les 4 supplémentaires).

#### Classe **TileFactory**

**TileFactory** est notre *patron de création*.
*Factory* est le patron le plus adapté à notre cas puisqu'on a un nombre limité de types de tuiles, et qu'on doit en créer beaucoup.
Comme dit dans le premier rapport, on aurait pu potentiellement utiliser un *Builder*, mais on ne l'a pas vu en cours (on ne l'utilisera pas).

#### Classe **Player**

La classe **Player** possède comme attribut:
- Une position (maintenant un tableau d'Integer)
- Une liste d'objectifs (`entity`) à atteindre 
- Une liste d'objectifs (`entity`) atteints

Elle possède une fonction `moveTo(position)` qui modifie l'attribut position du joueur.

#### Classe **GameBoard**

**GameBoard** est la classe responsable du terrain de jeu et de la tuile supplémentaire.
Par rapport au premier rendu, **GameBoard** fait maintenant une taille *9x9* au lieu de *7x7*
Cela facilite la gestion de l'éjection des joueurs et de l'insertion de la nouvelle tuile.

La tuile supplémentaire se déplace maintenant autour du terrain.
Il faudra donc connaitre sa position à tout moment.
On a ainsi ajouté un attribut `_extraTilePosition` pour palier à ce problème.

Il y a deux nouvelles fonctions qui gèrent la tuile supplémentaire:
- `rotateExtraTile` qui change l'orientation de la tuile supplémentaire (avant de l'insérer dans le terrain)
- `moveExtraTile` qui déplace la tuile supplémentaire sur les douze flèches normalement visibles sur le jeu de société de base.

#### Classe **Game**

La classe **Game** est la classe responsable de la coordination du code et c'est le maître du jeu.
Elle possède en attributs un **GameBoard**, une **TileFactory**, les ***Enums***, une liste de **Player**, un integer permet de savoir à qui le tour est et une liste de **GameObserver** (on aura qu'un seul *Observer*).
Par rapport au premier rendu, on a ajouté l'attribut `_blockedInsert` qui retient l'insertion dans le sens contraire du dernier déplacement de terrain.

**Game** a reçu de nouvelles fonctions:
- vérifications:
    + `checkGoals` qui vérifie si un objectif est atteint
    + `checkWinner` qui vérifie s'il y a déjà un gagnant
- sur la tuile supplémentaire
    + rotation
    + déplacement
- plus de `notify`


## Edit de dernière minute:

Lors de l'implémentation swing, on a rencontré un souci de duplication:
Le joueur se déplace, cependant il est dupliqué sur toutes les cases où il passe.

Et ainsi, la détection des objectifs du joueur ne marche plus.

Du coup: tout marche sauf le déplacement des joueurs.