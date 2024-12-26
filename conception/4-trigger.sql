-- Séquences pour chaque table
CREATE SEQUENCE seq_unite_id START 1;
CREATE SEQUENCE seq_categorie_id START 1;
CREATE SEQUENCE seq_fournisseur_id START 1;
CREATE SEQUENCE seq_ingredient_id START 1;
CREATE SEQUENCE seq_produit_id START 1;
CREATE SEQUENCE seq_recette_id START 1;
CREATE SEQUENCE seq_production_id START 1;
CREATE SEQUENCE seq_vente_id START 1;
CREATE SEQUENCE seq_prix_produit_id START 1;
CREATE SEQUENCE seq_detail_recette_id START 1;

-- Ajout des triggers pour générer les IDs automatiquement
-- Table: unite
CREATE OR REPLACE FUNCTION trigger_unite_id()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id := 'U' || NEXTVAL('seq_unite_id');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_unite_id BEFORE INSERT ON unite
FOR EACH ROW EXECUTE FUNCTION trigger_unite_id();

-- Table: categorie
CREATE OR REPLACE FUNCTION trigger_categorie_id()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id := 'C' || NEXTVAL('seq_categorie_id');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_categorie_id BEFORE INSERT ON categorie
FOR EACH ROW EXECUTE FUNCTION trigger_categorie_id();

-- Table: fournisseur
CREATE OR REPLACE FUNCTION trigger_fournisseur_id()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id := 'F' || NEXTVAL('seq_fournisseur_id');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_fournisseur_id BEFORE INSERT ON fournisseur
FOR EACH ROW EXECUTE FUNCTION trigger_fournisseur_id();

-- Table: ingredient
CREATE OR REPLACE FUNCTION trigger_ingredient_id()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id := 'I' || NEXTVAL('seq_ingredient_id');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_ingredient_id BEFORE INSERT ON ingredient
FOR EACH ROW EXECUTE FUNCTION trigger_ingredient_id();

-- Table: produit
CREATE OR REPLACE FUNCTION trigger_produit_id()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id := 'P' || NEXTVAL('seq_produit_id');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_produit_id BEFORE INSERT ON produit
FOR EACH ROW EXECUTE FUNCTION trigger_produit_id();

-- Table: recette
CREATE OR REPLACE FUNCTION trigger_recette_id()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id := 'R' || NEXTVAL('seq_recette_id');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_recette_id BEFORE INSERT ON recette
FOR EACH ROW EXECUTE FUNCTION trigger_recette_id();

-- Table: production
CREATE OR REPLACE FUNCTION trigger_production_id()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id := 'PROD' || NEXTVAL('seq_production_id');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_production_id BEFORE INSERT ON production
FOR EACH ROW EXECUTE FUNCTION trigger_production_id();

-- Table: vente
CREATE OR REPLACE FUNCTION trigger_vente_id()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id := 'V' || NEXTVAL('seq_vente_id');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_vente_id BEFORE INSERT ON vente
FOR EACH ROW EXECUTE FUNCTION trigger_vente_id();

-- Table: prix_produit
CREATE OR REPLACE FUNCTION trigger_prix_produit_id()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id_prixProduit := 'PP' || NEXTVAL('seq_prix_produit_id');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_prix_produit_id BEFORE INSERT ON prix_produit
FOR EACH ROW EXECUTE FUNCTION trigger_prix_produit_id();

-- Table: detailRecette
CREATE OR REPLACE FUNCTION trigger_detail_recette_id()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id := 'DR' || NEXTVAL('seq_detail_recette_id');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_detail_recette_id BEFORE INSERT ON detailRecette
FOR EACH ROW EXECUTE FUNCTION trigger_detail_recette_id();


CREATE OR REPLACE FUNCTION update_stock_after_production()
RETURNS TRIGGER AS $$
BEGIN
    -- Met à jour le stock des produits après une production
    UPDATE produit
    SET stock_actuel = stock_actuel + NEW.quantite
    WHERE id = NEW.id_produit;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_stock_after_production
AFTER INSERT ON production
FOR EACH ROW
EXECUTE FUNCTION update_stock_after_production();

CREATE OR REPLACE FUNCTION update_stock_after_sale()
RETURNS TRIGGER AS $$
BEGIN
    -- Met à jour le stock des produits après une vente
    UPDATE produit
    SET stock_actuel = stock_actuel - NEW.quantite
    WHERE id = NEW.id_produit;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_stock_after_sale
AFTER INSERT ON vente
FOR EACH ROW
EXECUTE FUNCTION update_stock_after_sale();

