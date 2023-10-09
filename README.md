# Contexte 
Dans le cadre de notre **Master 2 Informatique** parcours **MIAGE** (**M**éthodes **I**nformatiques **A**ppliquées à la **G**estion des **E**ntreprises) et de l'UE **GLOP** (**G**énie **LO**giciel par la **P**ratique), nous sommes amenés à réaliser un projet de développement logiciel en équipe, L'objectif du projet étant de : 

- Comprendre la problématique et les enjeux de la conception de logiciels complexes.
- Mettre en place un processus de développement adapté au contexte et aux contraintes.
- Mettre en oeuvre un ensemble de méthodes et d'outils pour mener à bien un projet, et d'être agile dans leur application.
- Organiser un travail en équipe : planification, répartition des tâches, gestion du temps, négociation, résolution de conflits, amélioration continue ...
- Interagir avec un client pour expliciter les besoins, communiquer les solutions et les avancées.
- Formaliser des besoins, passer de la spécification à la conception puis à la réalisation en utilisant les méthodes et les bonnes pratiques du génie logiciel.

Pour plus d'informations [Ici](https://www.fil.univ-lille.fr/portail/index.php?dipl=MMiage&sem=M2MIAGE&ue=GLOP&label=Programme).

# Le projet

Dans le cadre de l'appel d'offres pour le projet **Shopping Local**, notre entreprise, **MIMKA**, a été sélectionnée pour développer une application novatrice en partenariat avec les associations de commerçants et la municipalité. Notre mission principale est de revitaliser un centre-ville en déclin en mettant en place une solution basée sur le concept du **Click & Collect**.

Dans les grandes lignes, l'application inclura une carte de fidélité et de paiement, offrant ainsi des avantages exclusifs aux utilisateurs du programme, stimulant ainsi leur engagement envers les commerces locaux. Une caractéristique clé de notre réalisation est la facilité de réutilisation de l'architecture logicielle dans d'autres contextes similaires, garantissant ainsi une adaptabilité future.

L'enjeux est de devenir le leader incontesté de ce marché en seulement 18 mois, grâce à une innovation constante et à notre engagement à redynamiser le tissu économique du centre-ville tout en offrant une expérience exceptionnelle aux consommateurs et aux commerçants locaux.

Le projet se présente sous le nom : **ShopLoc**

# Equipe du projet

Ce travail est réalisé en équipe dont les membres sont :

- Aziz **BOURAHMA**
- Mouctar **FOFANA**
- Mehdi **HALOUANE**
- Islam **BOUTERBIAT**
- Arnaud **KADERI**

# Organisation du projet

Le projet se répartit de la façon suivante :
- Un dossier **shoploc-be** pour les fichiers sources couvrant le developpement de la partie back-end de l'application.
- Un dossier **shoploc-fe** pour les fichiers sources couvrant le developpement de la partie front-end de l'application.
- La partie **back-end** ainsi que la partie *<strong>front-end</strong>* de l'application disposent de leur propre fichier **README** fournissant des explications détaillées.

# Environnement de Travail

Cet environnement de travail suit une approche de gestion de version basée sur Git avec trois branches principales pour gérer le développement, les tests et la production.

## Branches Principales

### Production

- **Nom de la branche**: `main`
- **Description de la branche**: La branche `main` est la branche de production de ce projet. Elle contient le code stable et approuvé qui est prêt à être déployé en production. Les déploiements en production sont effectués à partir de cette branche.

### Test

- **Nom de la branche**: `preprod`
- **Description de la branche**: La branche `develop` est utilisée pour les tests et l'intégration continue. C'est là où les fonctionnalités développées sont combinées pour former une version de test de l'application. Les tests et les validations sont effectués sur cette branche avant de fusionner dans `main`.

### Développement

- **Nom de la branche**: `develop`
- **Description de la branche**: La branche `features-develop` est destinée au développement actif des fonctionnalités. Les développeurs travaillent sur des fonctionnalités ou des correctifs de bogues dans des branches de fonctionnalités distinctes basées sur cette branche. Une fois que les fonctionnalités sont complètes et testées, elles sont fusionnées dans `develop` pour l'intégration continue.

## Workflow

Le workflow typique dans cet environnement de travail suit les étapes suivantes :

- Les développeurs créent des branches de fonctionnalités à partir de `develop` pour travailler sur des fonctionnalités spécifiques.

- Les fonctionnalités en cours de développement sont testées et validées dans les branches de fonctionnalités.

- Une fois qu'une fonctionnalité est prête, elle est fusionnée dans `preprod` pour être testée en tant qu'ensemble.

- La branche `preprod` est continuellement testée pour s'assurer que le code fonctionne correctement et qu'il peut être fusionné en toute sécurité dans `main`.

- Lorsque la branche `preprod` est stable, elle est fusionnée dans `main` pour être déployée en production.

# Architecture

- **Maven**
   - **Objectif**: Gérer efficacement les dépendances du projet et faciliter sa construction.
   - **Utilisation**: Le fichier `pom.xml` est au cœur de la configuration de Maven, définissant toutes les bibliothèques nécessaires. Il facilite la compilation, les tests et la construction du projet.

- **Docker & Docker Compose**
   - **Objectif**: Assurer un déploiement cohérent, isolé et portable des applications et de leurs services associés.
   - **Description**: 
     - **Docker** Pour l'encapsulation de l'application et ses dépendances dans des conteneurs.
     - **Docker Compose** Pour la gestion des applications multi-conteneurs.
   - **Utilisation**: Des fichiers Dockerfile définissent les images Docker. Un fichier `docker-compose.yml` orchestre les services, incluant l'application principale et les instances PostgreSQL.

- **PostgreSQL**
   - **Objectif**: Pour la persistance des données.
   - **Description**: Un SGBD relationnel.
   - **Utilisation**: Avec Docker Compose, deux instances PostgreSQL distinctes vont être déployées, une pour les données transactionnelles et une autre pour les données analytiques.

- **GitHub Actions**
   - **Objectif**: Pour l'automatisation des pipelines CI/CD.
   - **Description**: GitHub Actions facilite l'automatisation des workflows.
   - **Utilisation**: Les workflows sont déclenchés par des événements tels que des commits, ils compilent et testent le projet, construisent les images Docker et déploient les conteneurs via Docker Compose.

- **SonarCloud**
   - **Objectif**: Offrir une garantie de la qualité du code.
   - **Utilisation**: Intégré à GitHub Actions pour analyser le code.


# Avantages de l'Architecture

- **Gestion efficace du projet**: Maven facilite la gestion des dépendances, la compilation et la construction du projet.
- **Flexibilité et Portabilité**: Docker et Docker Compose garantissent un déploiement uniforme.
- **Persistance des données robuste**: L'utilisation de PostgreSQL assure une gestion solide des données.
- **Automatisation complète**: GitHub Actions élimine la nécessité d'interventions manuelles dans le pipeline CI/CD.
- **Qualité du code**: SonarCloud évalue la qualité du code, aidant à maintenir de hauts standards.


# Livrables

### Livrable I : Réponse à l'appel d'offre

**Date** : 13 / 09 / 2023

Ce Livrable 1 du projet, présente notre réponse à l'appel d'offre au projet.

#### Présentation du Livrable :

Pour cette première version de l'application, il y a deux objectifs principaux :
- [ ] Estimation des coûts du projet.
- [ ] Présentation des premiers éléments de l'architecture logicielle.

# Journal de bord

#### Semaine du 25 Septembre 2023
- Configuration des outils et de l'environnement de travail.

#### Semaine du 09 Octobre 2023
- Livraison d'une coquille logicielle fonctionnelle.



