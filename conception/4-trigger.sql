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

