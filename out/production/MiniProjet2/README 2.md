Un solveur pour éviter la surchauffe cérébrale du miniprojet précédent à réaliser dans le cadre du cours de Java

# Présentation

Dans ce TP, on veut construire un programme qui, étant donné une configuration initiale d'un niveau de Mr Matt, recherche s'il est possible de collecter toutes les pommes à partir de cette configuration initiale et qui dans ce cas calcule une solution (c'est-à-dire une liste de coups permettant d'atteindre une configuration gagnante) 

Ce programme va utiliser le paquetage réalisé dans le miniprojet précédent.

## Présentation et analyse du problème

La recherche d'une solution optimale du jeu est un cas particulier de l'algorithme de Dijkstra : on recherche un plus court chemin dans un graphe orienté à partir d'une origine unique. Cette section présente le principe de cet algorithme dans le cas particulier qui nous intéresse, sans faire référence aux graphes orientés.

Pour ce faire, on va s’intéresser à l’ensemble des configurations atteignables du niveau de jeu. On définit "configuration atteignable" comme étant une configuration du niveau dans laquelle on peut arriver en jouant un nombre fini de coups depuis la configuration initiale.

L’algorithme procède en deux étapes :

1. Dans un premier temps, on liste les configurations atteignables du niveau. On s'arrête soit quand on ne trouve plus de nouvelles configurations atteignables, soit lorsqu'on vient de trouver un état où nous avons gagné.
2. Dans ce dernier cas, on peut alors passer à la deuxième étape : on affiche la liste de coups permettant d’arriver à la configuration gagnante.

Le gros du travail est réalisé dans la méthode `calculerFils` de la classe `Noeud` : cette méthode utilise une structure de données particulière, appelée un dictionnaire. Ce dictionnaire permet de stocker l’ensemble des configurations rencontrées au cours de l’exploration. Ces configurations sont par la suite dites connues. En fait, les méthodes du dictionnaire permettent :

- de savoir si un état du plateau correspond à une configuration connue, et si oui, laquelle ;
- d’ajouter une nouvelle configuration connue dans le dictionnaire, lorsque celle-ci n’est pas
déjà présente dans le dictionnaire.

L'algorithme utilise aussi une autre structure de données, appelée liste. Essentiellement, cette liste sert à stocker les configurations connues, et celles qu'il nous reste à traiter.

## Role du dictionnaire

En général, le rôle d’un dictionnaire est d’enregistrer un ensemble de données (ici les configurations connues) de manière à pouvoir retrouver la donnée associée à une clé (ici un état du plateau). On va réaliser l'implémentation du dictionnaire à l'aide d'une liste chaînée. En effet, on a besoin d’une structure dont la taille peut être étendue dynamiquement, car on n’a aucune idée à priori du nombre de configurations que l’on doit stocker. Mais la fonction de recherche dans une telle liste est coûteuse, car dans le pire cas, on doit parcourir toute la liste. En pratique, on utilise une table de hachage (qui s'appuie sur la fonction `hashCode`) pour être plus rapide.

## Travail demandé

Il vous est demandé, dans ce mini-projet, de faire un solveur (presque trivial) d'un niveau du jeu Mr. Matt, jouable en console. Votre programme devra pouvoir charger un niveau depuis un fichier, et afficher un message s'il existe une solution ou non (ainsi que de la donner le cas échéant).

### Classes à réaliser

Il y a plusieurs classes à réaliser, dans des paquetages différents.

Parmi ce qui vous est donné :

- Ma solution de MrMatt (dans le paquetage `sources`) vous est fournie. Elle contient, entre autres, l'implémentation de référence de `Niveau`, qui sera utilisée de façon intensive dans ce projet. À la fin du fichier, plusieurs fonctions utiles pour notre résolution ont été ajoutées : `deplacementPossible`, qui teste si un mouvement est possible ; `deplacer`, qui joue un déplacement ; `calcule()`, qui égrène les états jusqu'à trouver un état fixe; `estGagnant` qui nous indique si le jeu est une configuration gagnante ; et `valeurChaine` qui transforme la configuration du niveau en chaîne de caractère.
- Les interfaces `Liste` et `Dictionnaire` du paquetage `structures` ;
- Le squelette de la classe `Solveur` de `Noeud`.

Vous devrez alors réaliser :

- Deux implémentations de `Liste` et `Dictionnaire` (avec des tableaux).
- La classe `Noeud` pour l'algorithme.
- Enfin, terminer la classe `Solveur` en entier.

### Tests unitaires, `git` et Javadoc

- Il vous est demandé de bien *commenter* et de bien réaliser la documentation (au format Javadoc) de vos fichiers.
- Pensez à commiter & pusher votre travail ***de façon régulière***.
- Les messages de vos commits sont essentiels (pas de "lol ça marche", "ENFIN!!!!", "toto", ou autres). Soyez clairs (vous pouvez vous inspirer de [cette norme](https://buzut.net/cours/versioning-avec-git/bien-nommer-ses-commits).
- N'oubliez pas de réaliser des tests unitaires afin de valider votre code au fur et à mesure de votre production. Réalisez au moins un test pour chaque méthode des classes `ListeTableau` et `DictionnaireChaine`.

### Structure du rendu

1. Il vous est demandé un rendu *propre*, c'est à dire qui **compile** à minima.
2. Si votre projet **ne compile pas**, la note est automatiquement plafonnée à 10.
3. Votre projet comportera à la racine un fichier `AUTHORS` qui comporte votre adresse 3il (du genre `philr@3il.fr`).
4. Ce projet est à faire ***individuellement***. Toutefois, je n'ai rien contre l'entraide entre vous (tant que cela ne se résume pas à "pomper le code du voisin"). Si vous avez été aidé par quelqu'un, réalisez un fichier `HELPERS` à la racine de votre projet où vous indiquez celui qui vous a aidé, et de quelle façon (cela constituera un bonus pour cette personne lors de la notation).
5. Enfin, vous réaliserez un fichier `README.md` à la racine du projet dans lequel vous indiquerez, sous réserve de pertinence :
    - ce que vous avez réalisé du projet ;
    - les réponses aux diverses questions du sujet s'il y en a ;
    - un retour personnel sur les points qui vous ont paru difficiles ;
    - tout autre commentaire que vous jugerez utile...

# Plan d'attaque pas à pas

## Définition des structures de données

**Attention**, les structures de données vues ici ne sont pas **optimisées**. Les recoder n'a qu'un but pédagogique. Dans les "vrais projets", en pratique, on utilisera toujours celles de la bibliothèque standard, fournie dans le paquetage `Collection` (que l'on verra dans le chapitre à la rentrée).

### Classe `ListeTableau<T>`

La première classe conseillée à réaliser est la classe de gestion des listes sous forme de tableau. Cette classe implémente l'interface `Liste<T>` présente dans le code fourni, et utilise la généricité pour permettre la réutilisation de code.

On utilisera en interne un tableau pour stocker les éléments.

*Question : quel est le type du tableau à utiliser ?*

Pour réaliser cette classe, vous devrez coder les attributs et méthodes suivantes :

~~~~~{.java}
void ajouter(T element);
boolean estVide();
int taille();
T enlever(int i);
T element(int i);
boolean contient(T e);
~~~~~

Quelques indices pour réaliser cette partie :

1. Pensez à initialiser le tableau à un certain nombre d'éléments dans le constructeur.
2. Pour l'ajout d'éléments, attention lorsque vous arrivez au "bout" du tableau !
3. Si vous devez ajouter des éléments et que le tableau est rempli, pensez à le redimensionner. Généralement, doubler sa taille est une bonne idée.
4. Une liste vide est une liste de taille nulle.
5. Pensez à "déplacer" les éléments situés après lorsque vous en supprimez un.
6. Attention à bien faire les tests nécessaires avant d'appeler `equals`.

### Classe `ListeChainee<E>`

La deuxième classe conseillée à réaliser est la classe de gestion des listes. Cette classe implémente l'interface `Liste<E>` présente dans le code fourni, et utilise la généricité pour permettre la réutilisation de code.

On utilisera en interne une classe `Maillon`, déclarée de la façon suivante :

~~~~~~{.java}
class Maillon {
    private T donnee;
    private Maillon suivant;
}
~~~~~~

Pour réaliser les listes chaînées, vous devrez coder les attributs et méthodes suivantes :

~~~~~{.java}
void ajouter(T element);
boolean estVide();
int taille();
T enlever(int i);
T element(int i);
boolean contient(T e);
~~~~~

Quelques indices pour réaliser cette partie :

1. À l'initialisation, la tête de la liste pointe vers `null`, et sa taille est nulle.
2. L'ajout se fait toujours à la fin (pour simplifier) de la liste.
3. Pensez à "raccrocher" les maillons situés après lorsque vous en supprimez un.
4. Attention à bien faire les tests nécessaires avant d'appeler `equals`.
5. Les remarques de la classe précédente sont applicables ici...


*Question : Selon vous, quelles sont les différences majeures entre `ListeTableau` et `ListeChainee` ?*

### Classe `DictionnaireChaine<C, V>`

La classe `DictionnaireChaine<C,V>` est celle qui va nous permettre de gérer le dictionnaire. Elle implémente l'interface `Dictionnaire<C, V>` présente dans le code fourni, et utilise la généricité pour permettre la réutilisation de code.

On utilisera en interne une liste chaînée pour stocker les éléments.

Pour réaliser cette classe, vous devrez coder les attributs et méthodes suivantes :

~~~~~{.java}
void inserer(C cle, V valeur);
boolean contient(C cle);
V valeur(C cle);
~~~~~

Remarquez qu'il n'y a **pas** de méthode permettant de supprimer une clé du dictionnaire, ni de calcul de la taille. Libre à vous de les réaliser si vous souhaitez, mais vous *ne devez pas* toucher à la définition de l'interface `Dictionnaire`.

Quelques indices pour réaliser cette partie :

1. Pensez à réaliser une classe *interne*, par exemple, `Entree<C, V>`, qui représente un couple (clé, valeur) du dictionnaire.
2. L'implémentation des méthodes nécessite plusieurs appels à `equals`. Pensez à faire les tests nécessaires !
3. Pour l'insertion ou la recherche d'une valeur, il se peut que vous ayiez à faire (au moins) une boucle utilisant `equals`. Ne cherchez pas inutilement à "optimiser" ces boucles, mais faites des fonctions simples (20 lignes maximum).
4. L'insertion dans un dictionnaire est particulière : si la clé existe déjà dans le dictionnaire, vous n'ajoutez pas, vous remplacez. N'oubliez pas de redimensionner le tableau interne si besoin.
5. Les remarques des classes précédentes sont applicables ici...

## Algorithme de recherche

### Représentation des nœuds

Chaque nœud possède (au moins) quatre attributs :

1. une référence vers le dictionnaire des configurations connues ;
2. une référence vers l'état du niveau actuel (de type `Niveau`) ;
3. un moyen de représenter ses quatre fils ;
4. un moyen de représenter la succession de commandes nécessaires pour atteindre cette configuration.

Pour réaliser la classe `Noeud`, vous aurez besoin de réaliser les méthodes suivantes :

- (au moins) un constructeur public ;
- un accesseur sur `visite` ;
- la méthode `equals` ;
- un accesseur sur les fils;
- une méthode `calculerFils`.

La seule méthode un peu "difficile" est la méthode `calculerFils`. Voici ce qu'elle fait, avec des mots, si vous avez besoin d'aide :

- on considère que le nœud est visité ;
- pour chaque direction (haut, bas, gauche, droite) :
  - on considère le "fils" du noeud actuel, obtenu en jouant la direction considérée : si on ne peut pas bouger dans cette direction, ou si la partie est terminée, il n'y a pas de fils pour cette direction.
  - on s'intéresse à la chaîne de commande depuis le nœud actuel vers le fils ;
  - on copie l'état du niveau et on se déplace ;
  - si le niveau est gagnant, on a terminé : on renvoie alors la chaîne de direction gagnante.
  - sinon, on calcule sa `valeurChaine`, et on regarde si elle est dans le dictionnaire des configurations :
    - si elle y est, cela veut dire que le nœud fils existe déjà : il faut récupérer sa valeur depuis le dictionnaire.
    - sinon, il faut l'y ajouter.
  - dans tous les cas, on ajoute ce nœud fils aux fils du nœud ;
- on renvoie `null` si on n'a pas trouvé de solution.

### La classe `Solveur`

La classe `Solveur` sert à réaliser le cœur de l'algorithme. La seule méthode dont l'implémentation est demandée est la méthode `trouverSolution`, qui renvoie, étant une configuration de niveau initiale, soit la solution trouvée selon la méthode présentée dans la première partie, soit `null`, indiquant qu'il n'y a pas de solution.

Pour implémenter cette méthode, voici la marche à suivre :

- On propose deux listes, `traités` et `àTraiter`, qui vont servir à itérer les nœuds de la résolution ;
- on considère un `Noeud` initial qui contient la valeur du niveau sans déplacement (c'est le premier nœud à traiter) ;
- l'algorithme s'arrête lorsque la liste `àTraiter` est vide, ou que la solution a été trouvée.

À chaque itération de l'algorithme :

- On récupère le premier nœud à traiter de la liste ;
- on le déplace vers la liste des nœuds traités ;
- on regarde si la solution n'est pas directement donnée par `calculerFils` :
  - si oui, on s'arrête, on a une solution ;
  - sinon, on va itérer les nœuds fils du nœud actuel. On regarde si ces nœuds n'ont pas été déjà traités, et on les ajoute à la liste à traiter.

*Question : dans votre code, le type statique de vos références de liste et dictionnaires est-il celui de vos implémentations concrètes ? Pourquoi ?*

PDF : [Une version PDF du sujet est disponible ici](https://philippe.roussille.io/documents/java/)
