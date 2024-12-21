
# On est PERDU! (Labyrinth) 

Ce Projet se base sur le jeu de société **Labyrinth**.

Pour ce rapport de fin de projet, on a bien évidemment fini tout le développement.
Par Michel BUI et Daniel YANG
-------
Notre projet comporte donc:
+ Une classe pour les *controlleurs* (**GameController**)
+ Une classe pour les *views* (**GameWindow** qui hérite d'une interface dans les *models* **GameObserver**)
+ et enfin, une interface (**GameObserver**), une classe abstraite (**TileTemplate**), 2 enum (**Direction** et **Entity** ; **Entity** ce sont les objectifs à atteindre) et 8 classes (**Corner**, **StartingTile**, **Hallway**, **Intersection**, **TileFactory**, **GameBoard**, **Player** et **Game**).

Pour plus de détail sur les classes et leurs implémentations, il y a un dossier ***uml*** contenant l'uml à la fin du projet.
-------
Pour le design visuel, on décidé de se baser sur Minecraft où quatre entités entrent dans une forteresse afin de chercher 6 trésors différents (les objectifs, qui sont eux aussi basé sur Minecraft).
-------
Par rapport au fonctionnement du projet, on aura un Main qui aura pour responsabilité:
- créer les éléments nécessaires
- lier les éléments pour faire MVC
Ensuite il faut juste initialiser le swing, et de là, tout sera automatique!