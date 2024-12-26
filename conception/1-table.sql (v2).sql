-- Table unite
CREATE SEQUENCE unite_seq;
CREATE TABLE unite (
    id VARCHAR(7) PRIMARY KEY DEFAULT CONCAT('U', LPAD(nextval('unite_seq')::TEXT, 5, '0')),
    nom VARCHAR(255) NOT NULL UNIQUE,
    descri VARCHAR(255) NOT NULL
);

CREATE SEQUENCE ingredient_seq;
-- Table ingredient
CREATE TABLE ingredient (
    id VARCHAR(7) PRIMARY KEY DEFAULT CONCAT('I', LPAD(nextval('ingredient_seq')::TEXT, 5, '0')),
    nom VARCHAR(255) NOT NULL UNIQUE,
    seuil_alerte DOUBLE PRECISION NOT NULL CHECK (seuil_alerte > 0),
    id_unite VARCHAR(7) REFERENCES unite(id) ON DELETE SET NULL
);


-- Table fournisseur
CREATE SEQUENCE fournisseur_seq;
CREATE TABLE fournisseur (
    id VARCHAR(7) PRIMARY KEY DEFAULT CONCAT('F', LPAD(nextval('fournisseur_seq')::TEXT, 5, '0')),
    nom VARCHAR(255) NOT NULL UNIQUE,
    adresse VARCHAR(255) NOT NULL,
    telephone VARCHAR(10) NOT NULL CHECK (telephone ~ '^[0-9]+$')
);


-- Table stock_ingredient
CREATE SEQUENCE stock_ingredient_seq;
CREATE TABLE stock_ingredient (
    id VARCHAR(7) PRIMARY KEY DEFAULT CONCAT('S', LPAD(nextval('stock_ingredient_seq')::TEXT, 5, '0')),
    quantite DOUBLE PRECISION NOT NULL CHECK (quantite > 0),
    id_ingredient VARCHAR(7) REFERENCES ingredient(id) ON DELETE CASCADE,
    date_stock TIMESTAMP NOT NULL,
    prix_achat DOUBLE PRECISION NOT NULL CHECK (prix_achat >= 0),
    prix_achat_unitaire DOUBLE PRECISION NOT NULL CHECK (prix_achat_unitaire >= 0),
    id_fournisseur VARCHAR(7) REFERENCES fournisseur(id) ON DELETE SET NULL,
    idMere VARCHAR(7) DEFAULT '0'
);


-- Table categorie
CREATE SEQUENCE categorie_seq;
CREATE TABLE categorie (
    id VARCHAR(7) PRIMARY KEY DEFAULT CONCAT('C', LPAD(nextval('categorie_seq')::TEXT, 5, '0')),
    nom VARCHAR(255) NOT NULL UNIQUE
);


-- Table produit
CREATE SEQUENCE produit_seq;
CREATE TABLE produit (
    id VARCHAR(7) PRIMARY KEY DEFAULT CONCAT('P', LPAD(nextval('produit_seq')::TEXT, 5, '0')),
    nom VARCHAR(255) NOT NULL UNIQUE,
    duree_conservation TIME NOT NULL,
    id_categorie VARCHAR(7) REFERENCES categorie(id) ON DELETE SET NULL
);


-- Table prix_produit
CREATE SEQUENCE prix_produit_seq;
CREATE TABLE prix_produit (
    id VARCHAR(7) PRIMARY KEY DEFAULT CONCAT('PP', LPAD(nextval('prix_produit_seq')::TEXT, 5, '0')),
    prix DOUBLE PRECISION NOT NULL CHECK (prix > 0),
    id_produit VARCHAR(7) REFERENCES produit(id) ON DELETE CASCADE,
    date_debut DATE NOT NULL
);


-- Table recette
CREATE SEQUENCE recette_seq;
CREATE TABLE recette (
    id VARCHAR(7) PRIMARY KEY DEFAULT CONCAT('R', LPAD(nextval('recette_seq')::TEXT, 5, '0')),
    nom VARCHAR(255) NOT NULL UNIQUE,
    id_produit VARCHAR(7) REFERENCES produit(id) ON DELETE CASCADE,
    temp_preparation INTERVAL NOT NULL
);

-- Table recette detail
CREATE SEQUENCE recette_detail_seq;
CREATE TABLE recette_detail (
    id VARCHAR(7) PRIMARY KEY DEFAULT CONCAT('R', LPAD(nextval('recette_seq')::TEXT, 5, '0')),
    id_recette VARCHAR(7) REFERENCES recette(id) ON DELETE CASCADE,
    id_ingredient VARCHAR(7) REFERENCES ingredient(id) ON DELETE CASCADE,
    quantite DOUBLE PRECISION NOT NULL CHECK (quantite > 0),
);



-- Table production
CREATE SEQUENCE production_seq;
CREATE TABLE production (
    id VARCHAR(7) PRIMARY KEY DEFAULT CONCAT('PR', LPAD(nextval('production_seq')::TEXT, 5, '0')),
    date_production TIMESTAMP NOT NULL,
    id_produit VARCHAR(7) REFERENCES produit(id) ON DELETE CASCADE,
    quantite DOUBLE PRECISION NOT NULL CHECK (quantite > 0),
    date_peromption TIMESTAMP NOT NULL,
    cout_production DOUBLE PRECISION NOT NULL CHECK (cout_production > 0)
);


-- Table vente
CREATE SEQUENCE vente_seq;
CREATE TABLE vente (
    id VARCHAR(7) PRIMARY KEY DEFAULT CONCAT('V', LPAD(nextval('vente_seq')::TEXT, 5, '0')),
    date_vente TIMESTAMP NOT NULL,
    id_produit VARCHAR(7) REFERENCES produit(id) ON DELETE CASCADE,
    quantite DOUBLE PRECISION NOT NULL CHECK (quantite > 0),
    prix DOUBLE PRECISION NOT NULL CHECK (prix > 0)
);