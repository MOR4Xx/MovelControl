ALTER TABLE nota_fiscal DROP COLUMN codigo;
ALTER TABLE nota_fiscal ADD COLUMN codigo_de_barras VARCHAR(50);