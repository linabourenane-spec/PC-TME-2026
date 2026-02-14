Question 1 : Baseline (Mode Hash)

Exécution du mode hash sur le fichier WarAndPeace.txt :

    Fichier : data/WarAndPeace.txt (3.23 MB)
    Nombre total de mots : 565 527
    Nombre de mots uniques : 20 332
    Temps d'exécution : 786 ms

Trace complète : Preparing to parse data/WarAndPeace.txt (mode=hash, N=4), containing 3235342 bytes Total words: 565527 Unique words: 20332 34562 the 22148 and 16709 to 14990 of 10513 a Total runtime: 786 ms for mode hash

Trace avec le mode Hash2(get/put) Preparing to parse data/WarAndPeace.txt (mode=hash2, N=4), containing 3235342 bytes Total words: 565527 Unique words: 20332 34562 the 22148 and 16709 to 14990 of 10513 a Total runtime: 850 ms for mode hash2

Pourquoi est-il important que les partitions soient découpées sur des frontières de mots ? C’est indispensable pour ne pas casser les mots en deux. Si on coupe au pif, un mot comme "Bonjour" pourrait être séparé : la fin de la première partition lirait "Bon" et le début de la suivante "jour". Ça fausserait tes stats car tu aurais deux "faux" mots au lieu d'un seul vrai, et le compte total serait faux. En coupant sur les espaces (frontières), chaque Thread travaille proprement de son côté sur des mots complets.

Question 5 : Analyse du mode "range" Valeurs utilisées : L'invocation est FileUtils.getRange(file, 0, fileSize). On utilise donc 0 pour l'indice de début (start) et fileSize (la taille totale du fichier) pour l'indice de fin (end).

Le but est que ce mode traite l'intégralité du fichier. On veut imiter le comportement du mode hash tout en utilisant la nouvelle API getRange. Ça permet de vérifier que la lecture par "range" (avec le LimitedInputStream et le RandomAccessFile) fonctionne parfaitement sur tout le texte avant de commencer à le découper en petits morceaux pour les threads.

Trace avec le mode "range": Preparing to parse data/WarAndPeace.txt (mode=range, N=4), containing 3235342 bytes Total words: 565527 Unique words: 20332 34562 the 22148 and 16709 to 14990 of 10513 a Total runtime: 820 ms for mode rang (plus complexe techniquement vu de l'ajout de Decorator et RandomAcessFile ,...

Trace avec le mode "partition" (sans threads): Preparing to parse data/WarAndPeace.txt (mode=partition, N=4), containing 3235342 bytes Computed partition of 3235342 B into 4 in 2 ms Total words: 565528 Unique words: 20332 34562 the 22148 and 16709 to 14990 of 10513 a Total runtime: 877 ms for mode partition

C'est plus lent car on multiplie les appels à getRange et on attend la fin de chaque bloc séquentiellement au lieu d'utiliser des threads pour paralléliser le travail , et en pluus on fait plusieures ouvertures pour le même fichier.

Question 11 : Complexité et OptimisationComplexité : L'opération est en O(n), où n est la taille de la map source. On parcourt chaque entrée de la source une seule fois , et chaque mise à jour dans la destination prend un temps constant O(1) amorti. ptimisation : Pour réduire le temps de calcul, il faut parcourir la map qui contient le moins d'éléments.

LE MODE "THREADS":

Preparing to parse data/WarAndPeace.txt (mode=shard, N=4), containing 3235342 bytes Computed partition of 3235342 B into 4 in 1 ms Total words: 565528 Unique words: 0 Total runtime: 442 ms for mode shard

avec N = 8 : Preparing to parse data/WarAndPeace.txt (mode=shard, N=8), containing 3235342 bytes Computed partition of 3235342 B into 8 in 1 ms Total words: 565528 Unique words: 0 Total runtime: 372 ms for mode shard

avec N = 16 : Preparing to parse data/WarAndPeace.txt (mode=shard, N=16), containing 3235342 bytes Computed partition of 3235342 B into 16 in 1 ms Total words: 565528 Unique words: 0 Total runtime: 497 ms for mode shard

avec N = 10 : (qui est le nombre de mes coeurs physiques de ma machine perso :

Preparing to parse data/WarAndPeace.txt (mode=shard, N=10), containing 3235342 bytes Computed partition of 3235342 B into 10 in 1 ms Total words: 565527 Unique words: 0 Total runtime: 358 ms for mode shard

Question 14 : Efficacité et configuration matérielleValeur de N la plus efficace : N = 10 (358 ms).Nombre de cœurs physiques : 10.Configuration matérielle : Acer Nitro V15, processeur avec 10 cœurs physiques et 16 processeurs logiques (Hyper-threading).

Pourquoi s’intéresser à ce paramètre ? Le nombre de cœurs physiques représente la limite réelle de parallélisation matérielle. La performance est optimale à N = 10 car chaque thread dispose d'un cœur dédié. Au-delà de ce seuil (comme à N = 16 ), le processeur doit jongler entre les threads, ce qui crée un surcoût de gestion (context switching) et ralentit l'exécution globale