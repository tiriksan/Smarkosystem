CREATE TABLE emne(
emnekode VARCHAR(8) PRIMARY KEY NOT NULL,
emnenavn VARCHAR(64) NOT NULL,
ovingsbeskrivelse VARCHAR(256) NOT NULL
);

CREATE TABLE lokasjon(
bygg VARCHAR(64) NOT NULL,
etasje INT NOT NULL,
rom VARCHAR(64) NOT NULL,
bord INT NOT NULL,
emnekode VARCHAR(8),
CONSTRAINT lokasjon_fk1 FOREIGN KEY(emnekode) REFERENCES emne(emnekode),
CONSTRAINT lokasjon_pk1 PRIMARY KEY(bygg, etasje, rom, bord, emnekode)
);

CREATE TABLE bruker(
brukernavn VARCHAR(64) PRIMARY KEY NOT NULL,
fornavn VARCHAR(64) NOT NULL,
etternavn VARCHAR(64) NOT NULL,
passord VARCHAR(64) NOT NULL,
hovedbrukertype INT DEFAULT 1 NOT NULL
);

CREATE TABLE emne_bruker(
emnekode VARCHAR(8) NOT NULL,
brukernavn VARCHAR(64) NOT NULL,
brukertype INT DEFAULT 1 NOT NULL,
CONSTRAINT emne_bruker_fk1 FOREIGN KEY(emnekode) REFERENCES emne(emnekode),
CONSTRAINT emne_bruker_fk2 FOREIGN KEY(brukernavn) REFERENCES bruker(brukernavn),
CONSTRAINT emne_bruker_pk1 PRIMARY KEY(emnekode, brukernavn)
);

CREATE TABLE ko(
konummer INT PRIMARY KEY NOT NULL,
emnekode VARCHAR(8) NOT NULL,
aktiv BOOLEAN DEFAULT FALSE,
CONSTRAINT ko_fk1 FOREIGN KEY(emnekode) REFERENCES emne(emnekode)  
);

CREATE TABLE koinnlegg(
innleggsid INT PRIMARY KEY NOT NULL,
tid TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
konummer INT NOT NULL,
eier VARCHAR(64) NOT NULL,
bygg VARCHAR(64) NOT NULL,
etasje INT NOT NULL,
rom VARCHAR(64) NOT NULL,
bord INT NOT NULL,
emnekode VARCHAR(8),
hjelp VARCHAR(64),
kommentar VARCHAR(64) DEFAULT '&nbsp',
CONSTRAINT koinnlegg_fk1 FOREIGN KEY(konummer) REFERENCES ko(konummer),
CONSTRAINT koinnlegg_fk2 FOREIGN KEY(eier) REFERENCES bruker(brukernavn),
CONSTRAINT koinnlegg_fk3 FOREIGN KEY(bygg, etasje, rom, bord, emnekode) REFERENCES lokasjon(bygg, etasje, rom, bord, emnekode)  
);

CREATE TABLE brukere_i_innlegg(
innleggsid INT NOT NULL,
brukernavn VARCHAR(64) NOT NULL,
CONSTRAINT brukere_i_innlegg_fk1 FOREIGN KEY(innleggsid) REFERENCES koinnlegg(innleggsid),
CONSTRAINT brukere_i_innlegg_fk2 FOREIGN KEY(brukernavn) REFERENCES bruker(brukernavn),
CONSTRAINT brukere_i_innlegg_pk1 PRIMARY KEY(innleggsid, brukernavn)
);

CREATE TABLE kravgruppe(
gruppeid INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY NOT NULL,
emnekode VARCHAR(8) NOT NULL,
antall INT NOT NULL,
CONSTRAINT kravgruppe_fk1 FOREIGN KEY(emnekode) REFERENCES emne(emnekode)  
);

CREATE TABLE oving(
ovingsnummer INT NOT NULL,
emnekode VARCHAR(8) NOT NULL,
gruppeid INT NOT NULL,
obligatorisk BOOLEAN DEFAULT FALSE,
CONSTRAINT oving_fk1 FOREIGN KEY(emnekode) REFERENCES emne(emnekode),
CONSTRAINT oving_fk2 FOREIGN KEY(gruppeid) REFERENCES kravgruppe(gruppeid),
CONSTRAINT oving_pk1 PRIMARY KEY(ovingsnummer, emnekode)
);

CREATE TABLE ovinger_i_innlegg(
innleggsid INT NOT NULL,
brukernavn VARCHAR(64) NOT NULL,
ovingsnummer INT NOT NULL,
emnekode VARCHAR(8) NOT NULL,
CONSTRAINT ovinger_i_innlegg_fk1 FOREIGN KEY(innleggsid, brukernavn) REFERENCES brukere_i_innlegg(innleggsid, brukernavn),
CONSTRAINT ovinger_i_innlegg_fk2 FOREIGN KEY(ovingsnummer, emnekode) REFERENCES oving(ovingsnummer, emnekode),
CONSTRAINT ovinger_i_innlegg_pk1 PRIMARY KEY(brukernavn, ovingsnummer, emnekode)
);