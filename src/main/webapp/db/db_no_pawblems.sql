-- CREAZIONE DATABASE
DROP DATABASE IF EXISTS db_no_pawblems;
CREATE DATABASE db_no_pawblems;
USE db_no_pawblems;

-- CREAZIONE TABELLE
CREATE TABLE UTENTE(
	ID_UTENTE INTEGER AUTO_INCREMENT PRIMARY KEY,
    NOME VARCHAR(50) NOT NULL,
    COGNOME VARCHAR(50) NOT NULL,
    EMAIL VARCHAR(100) NOT NULL UNIQUE,
    TELEFONO VARCHAR(20) NOT NULL,
    PASSWORD VARCHAR(64) NOT NULL,
	ADMIN BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE INDIRIZZO(
	ID_INDIRIZZO INTEGER AUTO_INCREMENT PRIMARY KEY,
    UTENTE INTEGER,
    VIA VARCHAR(100) NOT NULL,
    CITTA VARCHAR(50) NOT NULL,
    CAP VARCHAR(5) NOT NULL,
    PROVINCIA VARCHAR(5) NOT NULL,                  
    NAZIONE VARCHAR(50) NOT NULL, 

    CONSTRAINT FK_UTENTE_INDIRIZZO FOREIGN KEY (UTENTE) REFERENCES UTENTE(ID_UTENTE) ON DELETE SET NULL
);

CREATE TABLE CATEGORIA(
	ID_CATEGORIA INTEGER AUTO_INCREMENT PRIMARY KEY,
    ID_PADRE INTEGER NULL,
    NOME VARCHAR(50) NOT NULL UNIQUE,
    DESCRIZIONE TEXT NULL,
    
    CONSTRAINT FK_CATEGORIA_PADRE FOREIGN KEY (ID_PADRE) REFERENCES CATEGORIA(ID_CATEGORIA) ON DELETE CASCADE
);

CREATE TABLE PRODOTTO(
    ID_PRODOTTO INTEGER AUTO_INCREMENT PRIMARY KEY,
    CATEGORIA INTEGER NOT NULL,
    NOME VARCHAR(150) NOT NULL,
    DESCRIZIONE TEXT NOT NULL,
    ATTIVO BOOLEAN NOT NULL DEFAULT TRUE, 
    
    CONSTRAINT FK_CATEGORIA_PRODOTTO FOREIGN KEY (CATEGORIA) REFERENCES CATEGORIA(ID_CATEGORIA) ON UPDATE CASCADE
);

CREATE TABLE VARIANTE_PRODOTTO(
    ID_VARIANTE INTEGER AUTO_INCREMENT PRIMARY KEY,
    PRODOTTO_PADRE INTEGER NOT NULL, 
    TAGLIA VARCHAR(10) NOT NULL,    
    COLORE VARCHAR(30) NOT NULL,
    COLORE_HEX VARCHAR(7) NULL,
    PREZZO DECIMAL(10,2) NOT NULL,       
    IVA INTEGER NOT NULL DEFAULT 22,                 
    DISPONIBILITA INTEGER NOT NULL DEFAULT 0,
    
    CONSTRAINT FK_PRODOTTO_VARIANTE FOREIGN KEY (PRODOTTO_PADRE) REFERENCES PRODOTTO(ID_PRODOTTO) ON DELETE CASCADE
);

CREATE TABLE IMMAGINE(
	ID_IMMAGINE INTEGER AUTO_INCREMENT PRIMARY KEY,
    PRODOTTO INTEGER NOT NULL, 
    URL VARCHAR(255) NOT NULL, 
    ALT VARCHAR(50) NOT NULL,
    
    CONSTRAINT FK_PRODOTTO_IMMAGINE FOREIGN KEY (PRODOTTO) REFERENCES PRODOTTO(ID_PRODOTTO) ON DELETE CASCADE
);

CREATE TABLE ORDINE(
	ID_ORDINE INTEGER AUTO_INCREMENT PRIMARY KEY,
    UTENTE INTEGER,
    VIA_SPEDIZIONE VARCHAR(100) NOT NULL,
    CITTA_SPEDIZIONE VARCHAR(50) NOT NULL,
    CAP_SPEDIZIONE VARCHAR(5) NOT NULL,
    PROVINCIA_SPEDIZIONE VARCHAR(5) NOT NULL,                  
    NAZIONE_SPEDIZIONE VARCHAR(50) NOT NULL, 
    DATA_ORDINE DATETIME NOT NULL,
    STATO VARCHAR(20) NOT NULL DEFAULT "In Elaborazione",
    TOTALE DECIMAL(10, 2) NOT NULL,
    NUMERO_FATTURA VARCHAR(20) NULL,
	
    CONSTRAINT FK_UTENTE_ORDINE FOREIGN KEY (UTENTE) REFERENCES UTENTE(ID_UTENTE) ON DELETE SET NULL
);

CREATE TABLE DETTAGLIO_ORDINE(
    ID_DETTAGLIO_ORDINE INTEGER AUTO_INCREMENT PRIMARY KEY,
    ORDINE INTEGER NOT NULL,
    VARIANTE INTEGER NOT NULL,
    QUANTITA INTEGER NOT NULL,
    PREZZO_ACQUISTO DECIMAL(10,2) NOT NULL,    
    IVA_ACQUISTO INTEGER NOT NULL,                 

    CONSTRAINT UQ_ORDINE_VARIANTE UNIQUE (ORDINE, VARIANTE),
    CONSTRAINT FK_ORDINE_DETTAGLIO_ORDINE FOREIGN KEY (ORDINE) REFERENCES ORDINE(ID_ORDINE) ON DELETE CASCADE,
    CONSTRAINT FK_VARIANTE_DETTAGLIO_ORDINE FOREIGN KEY (VARIANTE) REFERENCES VARIANTE_PRODOTTO(ID_VARIANTE) ON DELETE RESTRICT 
);

CREATE TABLE PRODOTTO_CARRELLO(
    UTENTE INTEGER NOT NULL,
    VARIANTE INTEGER NOT NULL, 
    QUANTITA INTEGER NOT NULL DEFAULT 1,
    
    CONSTRAINT PK_CARRELLO PRIMARY KEY (UTENTE, VARIANTE),
    CONSTRAINT FK_UTENTE_CARRELLO FOREIGN KEY (UTENTE) REFERENCES UTENTE(ID_UTENTE) ON DELETE CASCADE,
    CONSTRAINT FK_VARIANTE_CARRELLO FOREIGN KEY (VARIANTE) REFERENCES VARIANTE_PRODOTTO(ID_VARIANTE) ON DELETE CASCADE
);

-- POPOLAMENTO DATABASE
INSERT INTO CATEGORIA (ID_PADRE, NOME, DESCRIZIONE) VALUES (NULL, 'Cani', 'Tutto per il tuo cane');
INSERT INTO CATEGORIA (ID_PADRE, NOME, DESCRIZIONE) VALUES (NULL, 'Gatti', 'Tutto per il tuo gatto');

-- Prodotti per Cani (CATEGORIA = 1)
INSERT INTO PRODOTTO (CATEGORIA, NOME, DESCRIZIONE, ATTIVO) VALUES 
(1, 'Cappello di Lana', 'Cappello in soffice lana, adatto ai climi più freddi.', TRUE),  -- ID Prodotto: 1
(1, 'Giacca di Jeans', 'Il materiale fresco e resistente permette una bellezza che dura tutto l\'anno.', TRUE),         -- ID Prodotto: 2
(1, 'Occhiali da Sole Oversize', 'Per un\'estate BIG.', TRUE),     -- ID Prodotto: 3
(1, 'Scarpe Convert', 'Scarpe dal design semplice ma accattivante, comode per qualsiasi tipo di zampa.', TRUE),    -- ID Prodotto: 4
(1, 'Scarpe Adeedas', 'Scarpe da tennis comodissime per attività ricreativa in esterna.', TRUE),   -- ID Prodotto: 5
(1, 'Tiara Diamantata', 'Con questa il tuo cagnolone diventerà una bellissima principessina (LGBTQ+ friendly).', TRUE);    -- ID Prodotto: 6

-- Prodotti per Gatti (CATEGORIA = 2)
INSERT INTO PRODOTTO (CATEGORIA, NOME, DESCRIZIONE, ATTIVO) VALUES 
(2, 'Sciarpa di Velluto per Gatti', 'Sciarpa in morbido tessuto setoso, resistente e di classe.', TRUE),        -- ID Prodotto: 7
(2, 'Pettorina Catarifrangente', 'Possa il tuo gatto non essere più investito.', TRUE),       -- ID Prodotto: 8
(2, 'Gilet Elegante con motivo a quadri', 'Un gilet da vero gentlecat per il tuo amico peloso.', TRUE), -- ID Prodotto: 9
(2, 'Scarpe Convert', 'Scarpe dal design semplice ma accattivante, comode per qualsiasi tipo di zampa.', TRUE), -- ID Prodotto 10
(2, 'Cappello Viva L\'Italia', 'Nessuno sbaglierà più la nazionalità del tuo gatto.', TRUE),     -- ID Prodotto: 11
(2, 'Kit da Compleanno Siesta', 'Anima la festa con i fantastici accessori Siesta.', TRUE),    -- ID Prodotto: 12
(2, 'Cappuccio con Mantello', 'Scarpe da tennis comodissime per attività ricreativa in esterna.', TRUE);    -- ID Prodotto: 13

INSERT INTO VARIANTE_PRODOTTO (PRODOTTO_PADRE, TAGLIA, COLORE, COLORE_HEX, PREZZO, IVA, DISPONIBILITA) VALUES
-- Varianti prodotti Cane
(1, 'XS', 'Rosso', '#FF0000', 55.99, 22, 49),  -- FUORI OFFERTA (> 50)
(1, 'S', 'Rosso', '#FF0000', 55.99, 22, 20),  -- FUORI OFFERTA (> 50)
(1, 'M', 'Rosso', '#FF0000', 55.99, 22, 27),  -- FUORI OFFERTA (> 50)
(2, 'Unica', 'Blu', '#1560BD', 39.99, 22, 15),    -- In offerta (< 50)
(3, 'Unica', 'Nero', '#000000', 12.99, 22, 30),     -- In offerta (< 50)
(4, 'M', 'Nero', '#000000', 79.90, 22, 55),     -- FUORI OFFERTA (> 50)
(4, 'M', 'Rosso', '#FF0000', 79.90, 22, 10),     -- FUORI OFFERTA (> 50)
(4, 'L', 'Nero', '#000000', 79.90, 22, 11),     -- FUORI OFFERTA (> 50)
(4, 'L', 'Rosso', '#FF0000', 79.90, 22, 10),     -- FUORI OFFERTA (> 50)
(4, 'XL', 'Nero', '#000000', 79.90, 22, 12),     -- FUORI OFFERTA (> 50)
(4, 'XL', 'Rosso', '#FF0000', 79.90, 22, 22),     -- FUORI OFFERTA (> 50)
(5, 'M', 'Bianco', '#FFFFFF', 99.90, 22, 10),     -- FUORI OFFERTA (> 50)
(5, 'L', 'Bianco', '#FFFFFF', 99.90, 22, 10),     -- FUORI OFFERTA (> 50)
(5, 'XL', 'Bianco', '#FFFFFF', 99.90, 22, 10),     -- FUORI OFFERTA (> 50)
(6, 'Unica', 'Argento', '#C0C0C0', 399.99, 22, 17),     -- FUORI OFFERTA (> 50)
(6, 'Unica', 'Oro', '#EFBF04', 399.99, 22, 12),     -- FUORI OFFERTA (> 50)

-- Varianti prodotti Gatto
(7, 'Unica', 'Verde', '#287B6F', 49.90, 22, 8),  -- In offerta (< 50)
(8, 'M', 'Rosa', '#FFC0CB', 28.90, 22, 100),  -- In offerta (< 50)
(8, 'L', 'Rosa', '#FFC0CB', 28.90, 22, 80),  -- In offerta (< 50)
(8, 'XL', 'Rosa', '#FFC0CB', 28.90, 22, 22),  -- In offerta (< 50)
(9, 'S', 'Unico', '#DED1B6', 192.99, 22, 40),  -- FUORI OFFERTA (> 50)
(9, 'M', 'Unico', '#DED1B6', 192.99, 22, 40),  -- FUORI OFFERTA (> 50)
(9, 'L', 'Unico', '#DED1B6', 192.99, 22, 40),  -- FUORI OFFERTA (> 50)
(9, 'XL', 'Unico', '#DED1B6', 192.99, 22, 40),  -- FUORI OFFERTA (> 50)
(10, 'M', 'Nero', '#000000', 49.90, 22, 40),     -- In offerta (< 50)
(10, 'M', 'Rosso', '#FF0000', 49.90, 22, 10),     -- In offerta (< 50)
(10, 'L', 'Nero', '#000000', 49.90, 22, 13),     -- In offerta (< 50)
(10, 'L', 'Rosso', '#FF0000', 69.90, 22, 20),     -- FUORI OFFERTA (> 50)
(10, 'XL', 'Nero', '#000000', 69.90, 22, 1),     -- FUORI OFFERTA (> 50)
(10, 'XL', 'Rosso', '#FF0000', 69.90, 22, 32),     -- FUORI OFFERTA (> 50)
(11, 'Unica', 'Unico', '#000000', 29.90, 22, 2),    -- In offerta (< 50)
(12, 'Unica', 'Rosso', '#FF0000', 12.90, 22, 22),    -- In offerta (< 50)
(12, 'Unica', 'Blu', '#305CDE', 12.90, 22, 22),    -- In offerta (< 50)
(12, 'Unica', 'Giallo', '#FFE817', 12.90, 22, 22),    -- In offerta (< 50)
(13, 'Unica', 'Unico', '#FF0000', 42.90, 22, 22);    -- In offerta (< 50)

INSERT INTO IMMAGINE (PRODOTTO, URL, ALT) VALUES
-- immagini slider header
(2, 'img/Header_img/Header_img_1.png', 'Giacca di Jeans per Cani'),
(13, 'img/Header_img/Header_img_2.png', 'Cappuccio con mantello per Gatti'),
(4, 'img/Header_img/Header_img_3.png', 'Convert per Cani'),
(11, 'img/Header_img/Header_img_4.png', 'Cappello Italia viva per Gatti'),
(12, 'img/Header_img/Header_img_5.png', 'Kit da compleanno per Gatti'),
(6, 'img/Header_img/Header_img_6.png', 'Tiara diamantata per Cani'),    

-- Immagini di test per i prodotti (mostrati nelle sezioni in basso)
(1, 'img/prodotti/bulldog-cappello-rosso-generale.png', 'Cappello di Lana per Cani'),
(1, 'img/prodotti/bulldog-cappello-rosso-dietro.png', 'Cappello di Lana per Cani'),
(1, 'img/prodotti/bulldog-cappello-rosso-frontale.png', 'Cappello di Lana per Cani'),
(1, 'img/prodotti/bulldog-cappello-rosso-laterale.png', 'Cappello di Lana per Cani'),
(8, 'img/prodotti/Pettorina-gatto.png', 'Pettorina Catarifrangente'),
(3, 'img/prodotti/Occhiali.png', 'Occhiali da Sole Oversize'),
(7, 'img/prodotti/sciarpa-gatto-generale.png', 'Sciarpa di Velluto per Gatti'),
(9, 'img/prodotti/Gilet-gatto.png', 'Sciarpa di Velluto per Gatti'),
(10, 'img/prodotti/Convert.png', 'Scarpe Convert'),
(4, 'img/prodotti/Convert.png', 'Scarpe Convert'),
(5, 'img/prodotti/Adeedas.png', 'Scarpe Adeedas');