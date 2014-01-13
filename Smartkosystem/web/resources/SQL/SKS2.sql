DROP TABLE øvinger_i_innlegg;
DROP TABLE brukere_i_innlegg;
DROP TABLE køinnlegg;
DROP TABLE kø;
DROP TABLE emne_bruker;
DROP TABLE bruker;
DROP TABLE øving;
DROP TABLE kravgruppe;
DROP TABLE arbeidskrav;
DROP TABLE emne;


CREATE TABLE emne(
emnekode VARCHAR(8) PRIMARY KEY NOT NULL,
emnenavn VARCHAR(64) NOT NULL
);

CREATE TABLE bruker(
brukernavn VARCHAR(64) PRIMARY KEY NOT NULL,
fornavn VARCHAR(64) NOT NULL,
etternavn VARCHAR(64) NOT NULL,
passord VARCHAR(64) NOT NULL,
hovedbrukertype INT DEFAULT 0 NOT NULL
);

CREATE TABLE emne_bruker(
emnekode VARCHAR(8) NOT NULL,
brukernavn VARCHAR(64) NOT NULL,
brukertype INT DEFAULT 0 NOT NULL,
CONSTRAINT emne_bruker_fk1 FOREIGN KEY(emnekode) REFERENCES emne(emnekode),
CONSTRAINT emne_bruker_fk2 FOREIGN KEY(brukernavn) REFERENCES bruker(brukernavn),
CONSTRAINT emne_bruker_pk1 PRIMARY KEY(emnekode, brukernavn)
);

CREATE TABLE kø(
kønummer INT PRIMARY KEY NOT NULL,
emnekode VARCHAR(8) NOT NULL,
aktiv BOOLEAN DEFAULT FALSE,
CONSTRAINT kø_fk1 FOREIGN KEY(emnekode) REFERENCES emne(emnekode)
);

CREATE TABLE køinnlegg(
innleggsid INT PRIMARY KEY NOT NULL,
tid TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
kønummer INT NOT NULL,
eier VARCHAR(64) NOT NULL,
aktiv BOOLEAN DEFAULT TRUE,
CONSTRAINT køinnlegg_fk1 FOREIGN KEY(kønummer) REFERENCES kø(kønummer),
CONSTRAINT køinnlegg_fk2 FOREIGN KEY(eier) REFERENCES bruker(brukernavn)
);

CREATE TABLE brukere_i_innlegg(
innleggsid INT NOT NULL,
brukernavn VARCHAR(64) NOT NULL,
CONSTRAINT brukere_i_innlegg_fk1 FOREIGN KEY(innleggsid) REFERENCES køinnlegg(innleggsid),
CONSTRAINT brukere_i_innlegg_fk2 FOREIGN KEY(brukernavn) REFERENCES bruker(brukernavn),
CONSTRAINT brukere_i_innlegg_pk1 PRIMARY KEY(innleggsid, brukernavn)
);

CREATE TABLE øvinger_i_innlegg(
innleggsid INT NOT NULL,
brukernavn VARCHAR(64) NOT NULL,
øvingsnummer INT NOT NULL,
emnekode VARCHAR(8) NOT NULL,
CONSTRAINT øvinger_i_innlegg_fk1 FOREIGN KEY(innleggsid, brukernavn) REFERENCES brukere_i_innlegg(innleggsid, brukernavn),
CONSTRAINT øvinger_i_innlegg_fk2 FOREIGN KEY(øvingsnummer, emnekode) REFERENCES øving(øvingsnummer, emnekode),
CONSTRAINT øvinger_i_innlegg_pk1 PRIMARY KEY(brukernavn, øvingsnummer, emnekode)
);

CREATE TABLE arbeidskrav(
kravid INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
emnekode VARCHAR(8) NOT NULL,
beskrivelse VARCHAR(256) NOT NULL,
CONSTRAINT arbeidskrav_fk1 FOREIGN KEY(emnekode) REFERENCES emne(emnekode)
);

CREATE TABLE kravgruppe(
gruppeid INT UNIQUE AUTO_INCREMENT NOT NULL,
kravid INT NOT NULL,
CONSTRAINT kravgruppe_fk1 FOREIGN KEY(kravid) REFERENCES arbeidskrav(kravid),
CONSTRAINT kravgruppe_pk1 PRIMARY KEY(gruppeid, kravid)
);

CREATE TABLE øving(
øvingsnummer INT NOT NULL,
emnekode VARCHAR(8) NOT NULL,
gruppeid INT NOT NULL,
obligatorisk BOOLEAN DEFAULT FALSE,
CONSTRAINT øving_fk1 FOREIGN KEY(emnekode) REFERENCES emne(emnekode),
CONSTRAINT øving_fk2 FOREIGN KEY(gruppeid) REFERENCES kravgruppe(gruppeid),
CONSTRAINT øving_pk1 PRIMARY KEY(øvingsnummer, emnekode)
);