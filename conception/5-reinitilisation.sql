-- Réinitialiser toutes les tables et leurs séquences associées
TRUNCATE TABLE fournisseur_ingredient RESTART IDENTITY CASCADE;
TRUNCATE TABLE vente RESTART IDENTITY CASCADE;
TRUNCATE TABLE production RESTART IDENTITY CASCADE;
TRUNCATE TABLE recette RESTART IDENTITY CASCADE;
TRUNCATE TABLE produit RESTART IDENTITY CASCADE;
TRUNCATE TABLE ingredient RESTART IDENTITY CASCADE;
TRUNCATE TABLE fournisseur RESTART IDENTITY CASCADE;
TRUNCATE TABLE categorie RESTART IDENTITY CASCADE;
TRUNCATE TABLE unite RESTART IDENTITY CASCADE;

-- TRUNCATE TABLE <table_name> : Cette commande supprime toutes les lignes de la table spécifiée sans supprimer sa structure.
-- RESTART IDENTITY : Réinitialise les séquences des colonnes de type SERIAL (les identifiants auto-incrémentés) pour recommencer à 1.
-- CASCADE : Cette option garantit que les tables qui dépendent d'autres tables par des clés étrangères sont également vidées, afin de respecter les contraintes de relation entre les tables.