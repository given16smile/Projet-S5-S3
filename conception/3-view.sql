CREATE VIEW view_stock_actuel AS
SELECT p.id,
       p.nom,
       p.stock_actuel,
       COALESCE(SUM(v.quantite), 0) AS total_ventes,
       COALESCE(SUM(pr.quantite), 0) AS total_productions,
       p.stock_actuel - COALESCE(SUM(v.quantite), 0) + COALESCE(SUM(pr.quantite), 0) AS stock_final
FROM produit p
LEFT JOIN vente v ON p.id = v.id_produit
LEFT JOIN production pr ON p.id = pr.id_produit
GROUP BY p.id;

CREATE VIEW vrapport_vente_et_production AS
SELECT p.id,
       p.nom,
       COALESCE(SUM(v.quantite), 0) AS total_ventes,
       COALESCE(SUM(pr.quantite), 0) AS total_productions
FROM produit p
LEFT JOIN vente v ON p.id = v.id_produit
LEFT JOIN production pr ON p.id = pr.id_produit
GROUP BY p.id;
