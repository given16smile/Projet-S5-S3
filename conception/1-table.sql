CREATE TABLE unite(
   id SERIAL,
   nom VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(nom)
);

CREATE TABLE categorie(
   id SERIAL,
   nom VARCHAR(255)  NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(nom)
);

CREATE TABLE fournisseur(
   id SERIAL,
   contact VARCHAR(50)  NOT NULL,
   nom VARCHAR(255)  NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(contact),
   UNIQUE(nom)
);

CREATE TABLE ingredient(
   id SERIAL,
   nom VARCHAR(255)  NOT NULL,
   stock_actuel NUMERIC(15,2)   NOT NULL,
   prix_achat NUMERIC(15,2)   NOT NULL,
   seuil_alerte NUMERIC(15,2)   NOT NULL,
   id_unite INTEGER NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(nom),
   FOREIGN KEY(id_unite) REFERENCES unite(id)
);

CREATE TABLE produit(
   id SERIAL,
   nom VARCHAR(255)  NOT NULL,
   description VARCHAR(255)  NOT NULL,
   stcok_actuel INTEGER NOT NULL,
   prix_vente NUMERIC(15,2)   NOT NULL,
   prix_revient NUMERIC(15,2)   NOT NULL,
   id_categorie INTEGER NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(nom),
   FOREIGN KEY(id_categorie) REFERENCES categorie(id)
);

CREATE TABLE recette(
   id SERIAL,
   nom VARCHAR(255)  NOT NULL,
   quantite VARCHAR(50) ,
   id_produit INTEGER NOT NULL,
   id_ingredient INTEGER NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(nom),
   FOREIGN KEY(id_produit) REFERENCES produit(id),
   FOREIGN KEY(id_ingredient) REFERENCES ingredient(id)
);

CREATE TABLE production(
   id SERIAL,
   date_production DATE NOT NULL,
   quantite INTEGER NOT NULL,
   cout NUMERIC(15,2)   NOT NULL,
   etat_stock INTEGER NOT NULL,
   id_produit INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_produit) REFERENCES produit(id)
);

CREATE TABLE vente(
   id SERIAL,
   date_vente DATE NOT NULL,
   quantite INTEGER NOT NULL,
   etat_stock INTEGER NOT NULL,
   id_produit INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_produit) REFERENCES produit(id)
);

CREATE TABLE fournisseur_ingredient(
   id_fournisseur INTEGER,
   id_ingredient INTEGER,
   adresse VARCHAR(255)  NOT NULL,
   PRIMARY KEY(id_fournisseur, id_ingredient),
   UNIQUE(adresse),
   FOREIGN KEY(id_fournisseur) REFERENCES fournisseur(id),
   FOREIGN KEY(id_ingredient) REFERENCES ingredient(id)
);
