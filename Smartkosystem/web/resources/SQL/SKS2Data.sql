INSERT INTO emne VALUES('TDAT3003', '3D-programmering', 'Må ha godkjent 5 av 10 øvinger.');
INSERT INTO emne VALUES('TDAT3008', 'Java EE og distribuerte systemer', 'Må ha godtkjent 3 av de første fem øvingene, og minst 2 av de 4 siste.');
INSERT INTO emne VALUES('IFUD1043', 'Applikasjonsutvikling på .NET-plattformen', '');
INSERT INTO emne VALUES('IFUD1020', 'Opensource-utvikling', '');
INSERT INTO emne VALUES('IFUD1042', 'Applikasjonsutvikling for Android', '');
INSERT INTO emne VALUES('IFUD1048', 'C++ for programmerere', '');
INSERT INTO emne VALUES('IFUD1337', 'Navn kommer snart', '');

INSERT INTO bruker VALUES('anasky@hist.no', 'Anakin', 'Skywalker', 3, '46251479872872459f5e4ce64a7d883d');
INSERT INTO bruker VALUES('hansol@hist.no', 'Han', 'Solo', 1, '46251479872872459f5e4ce64a7d883d');

INSERT INTO emne_bruker VALUES('TDAT3003', 'anasky@hist.no', 1);
INSERT INTO emne_bruker VALUES('TDAT3003', 'hansol@hist.no', 1);
INSERT INTO emne_bruker VALUES('TDAT3008', 'hansol@hist.no', 1);
INSERT INTO emne_bruker VALUES('IFUD1042', 'anasky@hist.no', 2);
INSERT INTO emne_bruker VALUES('IFUD1043', 'sksmailsender@gmail.com', 3);
INSERT INTO emne_bruker VALUES('IFUD1043', 'haakon.jarle.hassel@gmail.com', 1);
INSERT INTO emne_bruker VALUES('IFUD1043', 'petterlu@stud.hist.no', 3);
INSERT INTO emne_bruker VALUES('TDAT1001', 'sksmailsender@gmail.com', 3);
INSERT INTO emne_bruker VALUES('TDAT1001', 'haakon.jarle.hassel@gmail.com', 2);
INSERT INTO emne_bruker VALUES('TDAT1001', 'petterlu@stud.hist.no', 1);

INSERT INTO kravgruppe VALUES(DEFAULT, 'TDAT3003', 5);
INSERT INTO kravgruppe VALUES(DEFAULT, 'TDAT3008', 3);
INSERT INTO kravgruppe VALUES(DEFAULT, 'TDAT3008', 2);

INSERT INTO øving VALUES(1, 'TDAT3003', 1, DEFAULT), (2, 'TDAT3003', 1, DEFAULT), (3, 'TDAT3003', 1, DEFAULT),
(4, 'TDAT3003', 1, TRUE), (5, 'TDAT3003', 1, DEFAULT), (6, 'TDAT3003', 1, TRUE), (7, 'TDAT3003', 1, DEFAULT),
(8, 'TDAT3003', 1, TRUE), (9, 'TDAT3003', 1, TRUE), (10, 'TDAT3003', 1, TRUE);

INSERT INTO øving VALUES(1, 'TDAT3008', 2, TRUE), (2, 'TDAT3008', 2, TRUE), (3, 'TDAT3008', 2, TRUE),
(4, 'TDAT3008', 2, TRUE), (5, 'TDAT3008', 2, TRUE);

INSERT INTO øving VALUES(6, 'TDAT3008', 3, DEFAULT), (7, 'TDAT3008', 3, TRUE),
(8, 'TDAT3008', 3, TRUE), (9, 'TDAT3008', 3, TRUE);

INSERT INTO lokasjon VALUES('AITeL', 1, 'KA124', 8, 'IFUD1043');
INSERT INTO lokasjon VALUES('AITeL', 1, 'KA124', 9, 'IFUD1043');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA205', 11, 'IFUD1043');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA205', 12, 'IFUD1043');
INSERT INTO lokasjon VALUES('AITeL', 1, 'KA124', 8, 'TDAT1001');
INSERT INTO lokasjon VALUES('AITeL', 1, 'KA124', 9, 'TDAT2002');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA205', 11, 'TDAT1001');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA205', 12, 'TDAT2002');

INSERT INTO kø VALUES(1, 'IFUD1043', TRUE);
INSERT INTO kø VALUES(2, 'TDAT1001', DEFAULT);
INSERT INTO kø VALUES(3, 'TDAT2002', DEFAULT);

INSERT INTO køinnlegg VALUES(1, DEFAULT, 1, 'haakon.jarle.hassel@gmail.com', 'AITeL', 1, 'KA124', 8, 'IFUD1043', '', 'Trenger HJELP huehuehue...');
INSERT INTO køinnlegg VALUES(2, DEFAULT, 1, 'sksmailsender@gmail.com', 'AITeL', 1, 'KA124', 9, 'IFUD1043', '', 'Godkjenning på Mail pl0x..');
INSERT INTO køinnlegg VALUES(3, DEFAULT, 2, 'petterlu@stud.hist.no', 'AITeL', 2, 'KA205', 11, 'TDAT1001', '', 'Godkjenning');