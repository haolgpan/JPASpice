CREATE TABLE country
(
  id_country integer NOT NULL,
  code character varying(2),
  latitude float,
  longitud float,
  name character varying(100) NOT NULL,
  CONSTRAINT pk_country PRIMARY KEY (id_country)
);
CREATE TABLE product
(
  id_product integer NOT NULL,
  format character varying(30) NOT NULL,
  CONSTRAINT pk_product PRIMARY KEY (id_product),
  CONSTRAINT uk_format UNIQUE (format)
);
CREATE TABLE spices (
    id_spice int,
    id_country integer NOT NULL,
    id_product integer NOT NULL,
    name character varying(100) NOT NULL,
      CONSTRAINT fk_art_pais FOREIGN KEY (id_country)
          REFERENCES country (id_country) MATCH SIMPLE
          ON UPDATE NO ACTION ON DELETE NO ACTION,
      CONSTRAINT fk_articles_prodcut FOREIGN KEY (id_product)
          REFERENCES product (id_product) MATCH SIMPLE
          ON UPDATE NO ACTION ON DELETE NO ACTION,
      CONSTRAINT uk_spices UNIQUE (name)
);
