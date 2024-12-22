
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
- **Gameboard** avait un attribut `_gameBoard` qui était une liste de liste de TileTemplate (`List<List<TileTemplate>>`) de taille **7x7**
    + Cet attribut a maintenant une taille **9x9**
    + Cela facilite la gestion


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

#### Classe abstraite **TileTemplate**

**TileTemplate** correspond aussi à un *patron de comportement*
Le patron `Template` semble le plus adapté à notre situation puisque les **Tuiles** sont très similaires mais juste différente dans la forme.

Cette classe possède donc toutes les fonctions utiles pour une tuile, elle sera ensuite *héritée* par les types de tuiles.

####



- un **rapport** pour présenter vos nouveaux choix de conception depuis le 1er rendu et expliquer les raisons des évolutions des choix de conception qui avaient été annoncés au 1er rendu.