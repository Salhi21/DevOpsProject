# language: fr
Fonctionnalité: Gestion des utilisateurs
En tant qu'administrateur
Je veux gérer les utilisateurs
Afin de maintenir la base de données

Scénario: Créer un nouvel utilisateur
Etant donné une base de données vide
Quand je crée un utilisateur "Ahmed" avec le prénom "Ahmed" et le nom "Ali"
Alors l'utilisateur "Ahmed" existe dans la base

Scénario: Lister tous les utilisateurs
Etant donné les utilisateurs suivants existent:
| id | firstName | lastName |
| 1  | Ahmed     | Ali      |
| 2  | Fatma     | Khaled   |
Quand je demande la liste des utilisateurs
Alors je reçois 2 utilisateurs

Scénario: Supprimer un utilisateur
Etant donné un utilisateur "Ahmed" existe avec l'ID 1
Quand je supprime l'utilisateur avec l'ID 1
Alors l'utilisateur avec l'ID 1 n'existe plus
