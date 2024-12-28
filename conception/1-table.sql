CREATE TABLE unite(
   id VARCHAR(50) ,
   nom VARCHAR(50)  NOT NULL,
   description VARCHAR(255) ,
   PRIMARY KEY(id),
   UNIQUE(nom)
);

CREATE TABLE categorie(
   id VARCHAR(50) ,
   nom VARCHAR(255)  NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(nom)
);

CREATE TABLE fournisseur(
   id VARCHAR(50) ,
   contact VARCHAR(50) NOT NULL CHECK (contact ~ '^[0-9]+$'),
   email VARCHAR(255) NOT NULL CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),  -- Validation du format email
   nom VARCHAR(255) NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(contact),
   UNIQUE(nom),
   UNIQUE(email)  -- Assurer que l'email soit unique
);


CREATE TABLE ingredient(
   id VARCHAR(50) ,
   nom VARCHAR(255)  NOT NULL,
   seuilAlerte NUMERIC(15,2)   NOT NULL CHECK (seuilAlerte > 0),
   id_unite VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(nom),
   FOREIGN KEY(id_unite) REFERENCES unite(id)
);

CREATE TABLE produit(
   id VARCHAR(50) ,
   nom VARCHAR(255)  NOT NULL,
   description VARCHAR(255)  NOT NULL,
   dureeConservation TIME NOT NULL,
   id_categorie VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(nom),
   FOREIGN KEY(id_categorie) REFERENCES categorie(id)
);

CREATE TABLE recette(
   id VARCHAR(50) ,
   nom VARCHAR(255)  NOT NULL,
   tempsPreparation TIME NOT NULL,
   id_produit VARCHAR(50)  NOT NULL,
   id_ingredient VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(nom),
   FOREIGN KEY(id_produit) REFERENCES produit(id),
   FOREIGN KEY(id_ingredient) REFERENCES ingredient(id)
);

CREATE TABLE production(
   id VARCHAR(50) ,
   dateProduction DATE NOT NULL,
   quantite INTEGER NOT NULL >0,
   cout NUMERIC(15,2)   NOT NULL,
   datePeremption TIMESTAMP NOT NULL,
   id_produit VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_produit) REFERENCES produit(id)
);

CREATE TABLE vente(
   id VARCHAR(50) ,
   dateVente DATE NOT NULL,
   prixVente NUMERIC(15,2)   NOT NULL >0,
   quantite INTEGER NOT NULL >0,
   id_produit VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_produit) REFERENCES produit(id)
);

CREATE TABLE prix_produit(
   id_prixProduit VARCHAR(50) ,
   prixRevient NUMERIC(15,2)   NOT NULL,
   dateProduction DATE NOT NULL,
   id_produit VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_prixProduit),
   FOREIGN KEY(id_produit) REFERENCES produit(id)
);

CREATE TABLE detailRecette(
   id VARCHAR(50),
   qte NUMERIC(15,2) NOT NULL  CHECK (qte > 0),
   id_ingredient VARCHAR(50)  NOT NULL,
   id_recette VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_ingredient) REFERENCES ingredient(id),
   FOREIGN KEY(id_recette) REFERENCES recette(id)
);

CREATE TABLE fournisseur_ingredient(
   id_fournisseur VARCHAR(50) NOT NULL,
   id_ingredient VARCHAR(50) NOT NULL,
   adresse VARCHAR(255)  NOT NULL,
   PRIMARY KEY(id_fournisseur, id_ingredient),
   UNIQUE(adresse),
   FOREIGN KEY(id_fournisseur) REFERENCES fournisseur(id),
   FOREIGN KEY(id_ingredient) REFERENCES ingredient(id)
);

CREATE TABLE stock_indgredient(
   id_fournisseur VARCHAR(50) ,
   id_ingredient VARCHAR(50) ,
   qte INTEGER NOT NULL CHECK (qte > 0),
   dateStock DATE NOT NULL,
   prixAchat NUMERIC(15,2)   NOT NULL  CHECK (prixAchat > 0),
   prixAchatUnitaire NUMERIC(15,2)   NOT NULL  CHECK (prixAchatUnitaire > 0),
   PRIMARY KEY(id_fournisseur, id_ingredient),
   FOREIGN KEY(id_fournisseur) REFERENCES fournisseur(id),
   FOREIGN KEY(id_ingredient) REFERENCES ingredient(id)
);
