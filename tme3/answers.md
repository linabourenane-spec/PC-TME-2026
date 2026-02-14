Complétez avec vos réponses.

# Q1


   Mode hash (Séquentiel complet) :
   Temps : 728 ms
   Total words : 565 527
    Unique words : 20 332

  Mode partition (Séquentiel par morceaux) :
Temps : 736 ms
Total words : 565 527 (Doit être identique au mode hash)

#Q2
Trace d'exécution :
Exception in thread "Thread-0" Exception in thread "Thread-3" java.util.ConcurrentModificationException
	at java.base/java.util.HashMap.compute(HashMap.java:1326)
	at pc.NaiveWorker.run(NaiveWorker.java:31)
	at java.base/java.lang.Thread.run(Thread.java:1447)
java.util.ConcurrentModificationException
	at java.base/java.util.HashMap.compute(HashMap.java:1326)
	at pc.NaiveWorker.run(NaiveWorker.java:31)
	at java.base/java.lang.Thread.run(Thread.java:1447)
Exception in thread "Thread-1" java.util.ConcurrentModificationException
	at java.base/java.util.HashMap.compute(HashMap.java:1326)
	at pc.NaiveWorker.run(NaiveWorker.java:31)
	at java.base/java.lang.Thread.run(Thread.java:1447)
Exception in thread "Thread-2" java.lang.ClassCastException: class java.util.HashMap$Node cannot be cast to class java.util.HashMap$TreeNode (java.util.HashMap$Node and java.util.HashMap$TreeNode are in module java.base of loader 'bootstrap')
	at java.base/java.util.HashMap$TreeNode.moveRootToFront(HashMap.java:1995)
	at java.base/java.util.HashMap$TreeNode.treeify(HashMap.java:2111)
	at java.base/java.util.HashMap.treeifyBin(HashMap.java:779)
	at java.base/java.util.HashMap.compute(HashMap.java:1341)
	at pc.NaiveWorker.run(NaiveWorker.java:31)
	at java.base/java.lang.Thread.run(Thread.java:1447)

...
Total runtime: 448 ms for mode naive

Analyse de l'échec :

   Erreur rencontrée : ConcurrentModificationException.

  Cause technique : La classe HashMap n'est pas "Thread-Safe". Plusieurs threads ont tenté de modifier la structure interne de la Map (via compute) en même temps. Java a détecté cette corruption potentielle et a arrêté les threads immédiatement.
  
  synchronized(map) { // On prend le verrou au début
    Integer count = map.get(word);
    if (count == null) {
        map.put(word, 1);
    } else {
        map.put(word, count + 1);
    }
} // On ne le relâche qu'à la toute fin

   Data Race : Même si le programme n'avait pas planté, le résultat du compteur totalWords[0]++ aurait été faux car les threads se marchent dessus sans protection pour mettre à jour la valeur
   
#Q4
Preparing to parse data/WarAndPeace.txt (mode=atomic, N=4), containing 3235342 bytes
Computed partition of 3235342 B  into 4 in 2 ms
Exception in thread "Thread-3" Exception in thread "Thread-0" java.util.ConcurrentModificationException
	at java.base/java.util.HashMap.compute(HashMap.java:1326)
	at pc.WordFrequency.lambda$4(WordFrequency.java:143)
	at java.base/java.lang.Thread.run(Thread.java:1447)
java.util.ConcurrentModificationException
	at java.base/java.util.HashMap.compute(HashMap.java:1326)
	at pc.WordFrequency.lambda$4(WordFrequency.java:143)
	at java.base/java.lang.Thread.run(Thread.java:1447)
Exception in thread "Thread-2" java.util.ConcurrentModificationException
	at java.base/java.util.HashMap.compute(HashMap.java:1326)
	at pc.WordFrequency.lambda$4(WordFrequency.java:143)
	at java.base/java.lang.Thread.run(Thread.java:1447)
Total words: 142483
Unique words: 10207
7548 the
5825 and
4323 to
3275 of
2688 a
Total runtime: 438 ms for mode atomic

Comportement : Le programme plante toujours avec ConcurrentModificationException.

Performance : Le temps est court (438 ms) mais trompeur, car le travail est incomplet.

Explication : L'utilisation d'un type atomique sécurise l'incrémentation du compteur global, mais la HashMap n'étant pas synchronisée, les accès concurrents provoquent une corruption de sa structure interne. L'atomicité d'une variable ne se propage pas aux autres objets partagés.

#Q6

Preparing to parse data/WarAndPeace.txt (mode=synchronized, N=4), containing 3235342 bytes
Computed partition of 3235342 B  into 4 in 2 ms
Total words: 565527
Unique words: 20332
34562 the
22148 and
16709 to
14990 of
10513 a
Total runtime: 547 ms for mode synchronized

Protection des données : Le bloc synchronized(sharedMap) garantit l'exclusion mutuelle. Un seul thread peut modifier la structure de la HashMap à un instant T, ce qui empêche la corruption de la mémoire.

La Contention : Le ralentissement s'explique par la contention sur le verrou. Comme chaque mot nécessite d'entrer dans le bloc synchronisé, les threads passent la majorité de leur temps à attendre que le verrou se libère au lieu de travailler en parallèle.

Surcoût (Overhead) : La gestion des mises en attente et des réveils des threads par la JVM (context switching) consomme plus de ressources que le travail de calcul lui-même

#Q7
Est-ce que cette version est correcte ?
Non, cette version est incorrecte. Même si chaque ligne est individuellement protégée par synchronized, l'enchaînement des opérations ne l'est pas
#Q9
Preparing to parse data/WarAndPeace.txt (mode=decorated, N=4), containing 3235342 bytes
Computed partition of 3235342 B  into 4 in 2 ms
Total words: 565527
Unique words: 20332
34562 the
22148 and
16709 to
14990 of
10513 a
Total runtime: 531 ms for mode decorated
Le problème reste le même que pour le mode synchronized : la contention.Même si tu n'as pas écrit le mot synchronized dans ton code, il est présent à l'intérieur du décorateur.Un seul thread à la fois peut modifier la Map. Les autres attendent.Le verrou est dit "grossier" (coarse-grained lock) : il bloque la Map entière pour n'importe quelle petite opération.

#Q10
Bien que Collections.synchronizedMap rende chaque méthode (get et put) atomique individuellement, elle ne garantit pas l'atomicité d'une séquence d'opérations. Le problème : Entre l'exécution de count = map.get(word) et map.put(word, ...), le verrou interne de la Map est relâché.Scénario d'erreur : Si deux threads lisent le même mot en même temps, ils recevront tous les deux la même valeur (par exemple null). Ils essaieront ensuite tous les deux d'écrire 1. L'un écrasera l'autre, et un mot sera "perdu" dans le décompte final.
