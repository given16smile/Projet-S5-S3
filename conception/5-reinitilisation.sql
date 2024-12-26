drop database boulangerie;

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

-- Instructions :
-- 1. TRUNCATE TABLE <table_name> :
--    Supprime toutes les lignes de la table spécifiée sans toucher à sa structure.
-- 2. RESTART IDENTITY :
--    Réinitialise toutes les séquences associées aux colonnes `SERIAL` ou utilisant des séquences spécifiques.
-- 3. CASCADE :
--    S'assure que toutes les tables dépendantes (via des clés étrangères) sont également vidées pour respecter les relations.
