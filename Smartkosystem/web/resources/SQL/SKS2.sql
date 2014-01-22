DROP TABLE øvinger_i_innlegg;
DROP TABLE brukere_i_innlegg;
DROP TABLE lokasjon;
DROP TABLE køinnlegg;
DROP TABLE kø;
DROP TABLE emne_bruker;
DROP TABLE bruker;
DROP TABLE øving;
DROP TABLE kravgruppe;
DROP TABLE emne;


CREATE TABLE emne(
emnekode VARCHAR(8) PRIMARY KEY NOT NULL,
emnenavn VARCHAR(64) NOT NULL,
beskrivelse VARCHAR(256) NOT NULL,
INDEX(emnekode)
) ENGINE=innoDB;

CREATE TABLE lokasjon(
bygg VARCHAR(64) NOT NULL,
etasje INT NOT NULL,
rom VARCHAR(64) NOT NULL,
bord INT NOT NULL,
emnekode VARCHAR(8),
CONSTRAINT lokasjon_fk1 FOREIGN KEY(emnekode) REFERENCES emne(emnekode) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT lokasjon_pk1 PRIMARY KEY(bygg, etasje, rom, bord, emnekode),
INDEX(bygg, etasje, rom, bord, emnekode)
) ENGINE=innoDB;

CREATE TABLE bruker(
brukernavn VARCHAR(64) PRIMARY KEY NOT NULL,
fornavn VARCHAR(64) NOT NULL,
etternavn VARCHAR(64) NOT NULL,
passord VARCHAR(64) NOT NULL,
hovedbrukertype INT DEFAULT 1 NOT NULL,
INDEX(brukernavn)
) ENGINE=innoDB;

CREATE TABLE emne_bruker(
emnekode VARCHAR(8) NOT NULL,
brukernavn VARCHAR(64) NOT NULL,
brukertype INT DEFAULT 1 NOT NULL,
CONSTRAINT emne_bruker_fk1 FOREIGN KEY(emnekode) REFERENCES emne(emnekode) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT emne_bruker_fk2 FOREIGN KEY(brukernavn) REFERENCES bruker(brukernavn) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT emne_bruker_pk1 PRIMARY KEY(emnekode, brukernavn),
INDEX(emnekode, brukernavn)
) ENGINE=innoDB;

CREATE TABLE kø(
kønummer INT PRIMARY KEY NOT NULL,
emnekode VARCHAR(8) NOT NULL,
aktiv BOOLEAN DEFAULT FALSE,
CONSTRAINT kø_fk1 FOREIGN KEY(emnekode) REFERENCES emne(emnekode) ON DELETE CASCADE ON UPDATE CASCADE,
INDEX(kønummer)
) ENGINE=innoDB;

CREATE TABLE køinnlegg(
innleggsid INT PRIMARY KEY NOT NULL,
tid TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
kønummer INT NOT NULL,
eier VARCHAR(64) NOT NULL,
bygg VARCHAR(64) NOT NULL,
etasje INT NOT NULL,
rom VARCHAR(64) NOT NULL,
bord INT NOT NULL,
emnekode VARCHAR(8),
hjelp VARCHAR(64),
kommentar VARCHAR(64) DEFAULT '&nbsp',
CONSTRAINT køinnlegg_fk1 FOREIGN KEY(kønummer) REFERENCES kø(kønummer) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT køinnlegg_fk2 FOREIGN KEY(eier) REFERENCES bruker(brukernavn) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT køinnlegg_fk3 FOREIGN KEY(bygg, etasje, rom, bord, emnekode) REFERENCES lokasjon(bygg, etasje, rom, bord, emnekode) ON DELETE CASCADE ON UPDATE CASCADE,
INDEX(innleggsid)
) ENGINE=innoDB;

CREATE TABLE brukere_i_innlegg(
innleggsid INT NOT NULL,
brukernavn VARCHAR(64) NOT NULL,
CONSTRAINT brukere_i_innlegg_fk1 FOREIGN KEY(innleggsid) REFERENCES køinnlegg(innleggsid) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT brukere_i_innlegg_fk2 FOREIGN KEY(brukernavn) REFERENCES bruker(brukernavn) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT brukere_i_innlegg_pk1 PRIMARY KEY(innleggsid, brukernavn),
INDEX(innleggsid, brukernavn)
) ENGINE=innoDB;

CREATE TABLE kravgruppe(
gruppeid INT PRIMARY KEY NOT NULL,
emnekode VARCHAR(8) NOT NULL,
antall INT NOT NULL,
beskrivelse VARCHAR(256),
CONSTRAINT kravgruppe_fk1 FOREIGN KEY(emnekode) REFERENCES emne(emnekode) ON DELETE CASCADE ON UPDATE CASCADE,
INDEX(gruppeid)
) ENGINE=innoDB;

CREATE TABLE øving(
øvingsnummer INT NOT NULL,
emnekode VARCHAR(8) NOT NULL,
gruppeid INT,
obligatorisk BOOLEAN DEFAULT FALSE,
CONSTRAINT øving_fk1 FOREIGN KEY(emnekode) REFERENCES emne(emnekode) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT øving_fk2 FOREIGN KEY(gruppeid) REFERENCES kravgruppe(gruppeid) ON DELETE CASCADE,
CONSTRAINT øving_pk1 PRIMARY KEY(øvingsnummer, emnekode),
INDEX(øvingsnummer, emnekode)
) ENGINE=innoDB;

CREATE TABLE øvinger_i_innlegg(
innleggsid INT NOT NULL,
brukernavn VARCHAR(64) NOT NULL,
øvingsnummer INT NOT NULL,
emnekode VARCHAR(8) NOT NULL,
CONSTRAINT øvinger_i_innlegg_fk1 FOREIGN KEY(innleggsid, brukernavn) REFERENCES brukere_i_innlegg(innleggsid, brukernavn) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT øvinger_i_innlegg_fk2 FOREIGN KEY(øvingsnummer, emnekode) REFERENCES øving(øvingsnummer, emnekode) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT øvinger_i_innlegg_pk1 PRIMARY KEY(brukernavn, øvingsnummer, emnekode),
INDEX(brukernavn, øvingsnummer, emnekode)
) ENGINE=innoDB;

CREATE TABLE godkjente_øvinger(
godkjent_av VARCHAR(50) NOT NULL,
emnekode VARCHAR(8) NOT NULL,
brukernavn VARCHAR(64) NOT NULL,
øvingsnummer INT NOT NULL,
CONSTRAINT godkjente_øvinger_fk1 FOREIGN KEY(emnekode) REFERENCES emne(emnekode) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT godkjente_øvinger_fk2 FOREIGN KEY(brukernavn) REFERENCES bruker(brukernavn) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT godkjente_øvinger_fk3 FOREIGN KEY(øvingsnummer) REFERENCES øving(øvingsnummer) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT godkjente_øvinger_pk1 PRIMARY KEY(emnekode, brukernavn, øvingsnummer),
INDEX(emnekode, brukernavn, øvingsnummer)
) ENGINE=innoDB;

ALTER TABLE 'øvinger_i_innlegg' ENGINE = InnoDB;
ALTER TABLE 'brukere_i_innlegg' ENGINE = InnoDB;
ALTER TABLE 'lokasjon' ENGINE = InnoDB;
ALTER TABLE 'køinnlegg' ENGINE = InnoDB;
ALTER TABLE 'kø' ENGINE = InnoDB;
ALTER TABLE 'emne_bruker' ENGINE = InnoDB;
ALTER TABLE 'bruker' ENGINE = InnoDB;
ALTER TABLE 'kravgruppe' ENGINE = InnoDB;
ALTER TABLE 'øving' ENGINE = InnoDB;
ALTER TABLE 'emne' ENGINE = InnoDB;
ALTER TABLE bruker ALTER COLUMN hovedbrukertype SET DEFAULT 1;
ALTER TABLE emne_bruker ALTER COLUMN brukertype SET DEFAULT 1;
ALTER TABLE emne ADD COLUMN beskrivelse VARCHAR(256) NOT NULL;
ALTER TABLE øving MODIFY COLUMN gruppeid INT;
ALTER TABLE kravgruppe ADD PRIMARY KEY(gruppeid);
ALTER TABLE øving ADD CONSTRAINT øving_fk2 FOREIGN KEY(gruppeid) REFERENCES kravgruppe(gruppeid);