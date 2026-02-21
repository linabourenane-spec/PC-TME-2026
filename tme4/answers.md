
# completer ce fichier avec vos réponses aux questions sans code

Q4 — Pourquoi ce code peut faire un deadlock ?
Un deadlock est possible si tous les philosophes exécutent la même stratégie : ils prennent d’abord leur première baguette (ex. la gauche), puis tentent de prendre la seconde.
Dans un scénario défavorable, chaque philosophe réussit à prendre une baguette, et la seconde baguette dont il a besoin est précisément celle déjà prise par son voisin. On obtient alors une attente circulaire :
P0 attend la baguette tenue par P1, P1 attend celle tenue par P2, …, P4 attend celle tenue par P0.
Comme personne ne peut obtenir sa deuxième baguette, personne ne libère la première : le programme se bloque définitivement (interblocage/deadlock).

voici la trace d'excution dans mon cas :
Thread-0 is thinking
Thread-4 is thinking
Thread-4 has one fork
Thread-2 is thinking
Thread-3 is thinking
Thread-1 is thinking
Thread-1 has one fork
Thread-0 has one fork
Thread-2 has one fork
Thread-3 has one fork  //on remarque que tous les fork de gauche on été pris ce qui a cosé le DeadLock


q5 _ le philosophe qui nous pose problème : 
 C'est évidemment le philosophe 0 (celui qui a les baguettes N−1 et 0).

puisque :

Son couple de baguettes est {N−1, 0}.

L’ordre naturel serait 0 puis N−1 , mais dans la configuration classique, il essaie souvent N−1 puis 0 (ordre inversé).

C’est lui qui casse l’ordre et permet le cycle complet qui mène au deadlock

On corrige en inversant ses baguettes à la construction (new Philosopher(fork0, forkN−1)), tandis que les autres philosophes gardent l’ordre (i−1 puis i). 
Ainsi toutes les acquisitions respectent l’ordre total, donc aucun deadlock n’est possible.



Question 7 :
Sur la version avec deadlock, l’arrêt par interrupt() ne fonctionne pas correctement car un philosophe peut être bloqué dans lock.lock() (acquisition non-interruptible) : lock() peut endormir le thread jusqu’à acquisition du verrou et il ne revient jamais tester isInterrupted().
Pour pouvoir terminer proprement, il faut acquérir une baguette avec une méthode interruptible, par exemple lockInterruptibly() (ou tryLock(timeout, unit)). Ainsi, si le thread est interrompu pendant l’attente d’une baguette, une InterruptedException est levée et le philosophe peut libérer les baguettes déjà prises et terminer