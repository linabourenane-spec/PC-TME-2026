# Question 1

Complexité de "repeat" la version fournie qui utilise String ?

# Complexité globale : O(n×d) si d est proche de n alors O(n2) 

#Complexité du mode "listfreq" :
 O(n×d) 

#Trace d'exécution :
Preparing to parse data/WarAndPeace.txt (mode=listfreq), containing 3300667 bytes
Total words: 565527
Unique words: 20332
WordCount [mot=the, cpt=34562]

WordCount [mot=and, cpt=22148]

WordCount [mot=to, cpt=16709]

WordCount [mot=of, cpt=14990]

WordCount [mot=a, cpt=10513]

Total runtime (wall clock) : 8920 ms for mode listfreq
# Complexité du mode "tree" :
 O(nlogd)
  Pour chaque mot (n), on effectue une insertion ou une mise à jour dans le TreeMap. Chaque opération dans un arbre binaire de recherche équilibré coûte O(logd). Total : O(nlogd). 2. Phase d'extraction : Copier les d éléments dans une liste coûte O(d). 3. Phase de tri : Trier la liste de d éléments coûte O(dlogd). 4. Total : Le terme dominant est O(nlogd) puisque n≥d
 
 
#Trace d'exécution :
Preparing to parse data/WarAndPeace.txt (mode=tree), containing 3300667 bytes
Total words: 565527
Unique words: 20332
the : 34562
and : 22148
to : 16709
of : 14990
a : 10513
Total runtime (wall clock) : 895 ms for mode tree
 
#Complexité du mode "hash"
Complexité globale : O(n+dlogd)

#Trace d'exécution :
Preparing to parse data/WarAndPeace.txt (mode=hash), containing 3300667 bytes
Total words: 565527
Unique words: 20332
--- Top 5 ---
the : 34562
and : 22148
to : 16709
of : 14990
a : 10513
Total runtime (wall clock) : 745 ms for mode hash

#Question 5 : Complexité de repeat (version Naive)

  Complexité en temps : O(n2).

 Justification : La classe String étant immuable, l'opération s += c crée une nouvelle chaîne à chaque étape. À l'itération i, on copie i caractères. Sur n itérations, le nombre total de copies suit une progression arithmétique, ce qui donne une complexité quadratique en O(n2)

#Question 6 : Analyse des performances de construction de chaînes :
Naive Elapsed time: 734ms
Default Elapsed time: 4ms
Capacity Elapsed time: 3ms

Version Naive (O(n²)) : C'est la version la plus lente (environ 1 seconde). À chaque ajout de caractère avec s += c, Java crée une nouvelle String en mémoire et doit copier l'intégralité de la chaîne précédente. Pour N=100000, cela représente des milliards de copies de caractères inutiles. La courbe de temps est quadratique.

Version StringBuilder Default (O(n)) : Le temps chute de manière spectaculaire (6ms). Contrairement à String, StringBuilder travaille sur un tableau de caractères mutable (on modifie le contenu sans recréer d'objet). Le gain est massif car on ne fait plus qu'une seule copie finale lors du toString(). Les quelques millisecondes supplémentaires par rapport à la version Capacity correspondent aux moments où le StringBuilder doit agrandir son tableau interne (réallocation).

Version StringBuilder Capacity (O(n)) : C'est la version la plus rapide (3ms). En passant n au constructeur new StringBuilder(n), on alloue immédiatement la mémoire nécessaire. Il n'y a donc aucune réallocation ni aucune copie intermédiaire durant la boucle. C'est l'optimisation maximal
# Question 2

Temps version initiale (String) :

Temps version StringBuilder() :

Temps version StringBuilder(n) :


# Question 3

Nombre de réallocations en fonction de N ?


Complexité de "repeat" avec StringBuilder() ?


Complexité de "repeat" avec StringBuilder(n) ?


# Question 4

Copiez la trace du script python.

Commentez vos résultats du benchmark


# Exercice II : fréquence des mots


