Complétez avec vos réponses.

# Q1
En mode naïf, les deux threads sont désynchronisés. Le ratio u/r n'est pas stable à 1. Si on réduit les délais, le thread d'Update calcule plusieurs générations entre deux affichages (u/r > 1), ce qui rend l'évolution saccadée visuellement car on rate des étapes intermédiaires.

#Q2 
Source de la data race : Accès concurrent non protégé à la matrice next entre model.updateNext() (écriture) et model.refreshCurrent() (lecture/copie).
Conséquence (Tearing) : Le Refresher copie une matrice next partiellement mise à jour. L'affichage montre un mélange de deux générations différentes.
Effet du slider à zéro : Cela augmente drastiquement la fréquence des accès du Refresher, rendant les collisions avec l'Updater quasi systématiques et les corruptions visuelles permanentes

#Q5 
En mode mtsafe, tes compteurs sont beaucoup plus bas !

 Pourquoi ? Parce que chaque thread doit attendre que l'autre lâche le verrou (synchronized). Ils se ralentissent mutuellement pour garantir la sécurité.

 En mode naive, ils courent chacun de leur côté sans s'arrêter, ce qui fait grimper les chiffres mais cause des erreurs de dessin

#Q8 
À ce stade, avoir deux sleeps est redondant car l'alternance stricte lie les deux threads. Le thread de rafraîchissement (Refresher) dictant déjà la cadence visuelle, l'Updater est naturellement bridé par les appels à wait(). Supprimer le sleep de l'Updater permet d'optimiser la réactivité : la génération suivante est calculée immédiatement après l'affichage, sans ajouter de latence supplémentaire entre les cycles.
 
#Q12


Les approches par alternance binaire (LifeModelBlock ou Turn) ne sont pas adaptées au cas multi-updaters pour deux raisons majeures :

Perte du parallélisme : Le verrouillage synchronized utilisé dans les étapes précédentes impose que chaque méthode s'exécute l'une après l'autre. Si 4 updaters appellent une méthode synchronisée, ils s'exécuteront en série. On perd tout l'intérêt d'avoir plusieurs cœurs CPU.
Absence de barrière de groupe : L'alternance simple (Joueur 1 puis Joueur 2) ne sait pas gérer un groupe de threads. Pour que la simulation soit correcte, le thread Refresher ne doit démarrer qu'une fois que la totalité des N threads Updater ont terminé leur portion de la grille. Les outils précédents ne permettent pas de compter et d'attendre la fin d'un groupe de threads
#Q15
Un  problème majeur de l'approche twosem est l'absence d'assignation stricte des permis. Les sémaphores distribuent des jetons anonymes. Un thread privilégié par le scheduler pourrait consommer plusieurs permis ready à la suite, effectuant ainsi plusieurs cycles de calcul sur sa propre zone, tandis qu'un thread plus lent se verrait 'voler' son tour de calcul. Cela brise la cohérence globale de la génération.
 