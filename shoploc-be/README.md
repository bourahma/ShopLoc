# ShopLoc-BE

Ce répertoire contient le code source pour l'implémentation de la partie back-end du système information de ShopLoc.

## Outils Utilisés

- **Spring Framework** (Version 3.1.4)
- **PostgreSQL** 
    - Comme base de données de production.

- **H2 Database** 
    - Une base de données SQL légère pour les tests.
  
- **Docker** 
  - Pour la conteneurisation de notre application.
  
- **Maven** (Version 4.0.0)
  - Comme outil de gestion de projets Java pour faciliter la construction, le test et la gestion des dépendances.
  - 
- **DBeaver** (Version X.X)
  - Comme outil de gestion de base de données, notamment pour l'exploration de la base de données.

## Workflows & Automation

1. Le fichier **ci.yml** dans le répertoire **.github/workflows** définit le flux de travail d'intégration continue (CI) pour ce projet sur GitHub Actions. A Chaque commit, la CI sera déclenché pour effectuer les actions suivantes :

  - **Build** : Le code source sera compilé pour s'assurer qu'il peut être construit sans erreurs.
  - **Tests** : Les tests unitaires / d'intégration seront exécutés pour s'assurer que le code fonctionne comme prévu.


## Commandes

Pour exécuter ce l'application localement, vous devez configurer la bases de données PostgreSQL dans un premier temps, utiliser les commandes suivantes : 

- `make runDB` : Crée l'image docker d'une base de données PostgreSQL puis lance cette base dans un conteneur Docker. PS : A exécuter une seule fois, pour la suite il faudra juste relancer la base avec `make restartDB`.
- `make stopDB` : Arrête la base de données, autrement le conteneur Docker.
- `make restartDB` : Relance la base de données, autrement le conteneur Docker.

Ce projet utilise un fichier Makefile pour simplifier l'exécution des commandes courantes. Vous pouvez utiliser `make` pour exécuter les tâches suivantes :

- `make test` : Exécute les tests de l'application.
- `make runApp` : Démarre l'application.
- `make stopApp` : Arrête l'application.

Ces commandes permettent de tout lancer simultanément :

- `make build` : Fais un `make runDB` puis `make runApp`.
- `make stop` : Fais un `make stopDB` puis `make stopApp`.
- `make clean` : Fais un clean du projet.
