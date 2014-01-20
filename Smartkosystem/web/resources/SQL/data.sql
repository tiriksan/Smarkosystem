INSERT INTO emne VALUES('TDAT3003', '3D-programmering', 'Må ha godkjent 5 av 10 øvinger.');
INSERT INTO emne VALUES('TDAT3008', 'Java EE og distribuerte systemer', 'Må ha godtkjent 3 av de første fem øvingene, og minst 2 av de 4 siste.');
INSERT INTO emne VALUES('IFUD1043', 'Applikasjonsutvikling på .NET-plattformen', '');
INSERT INTO emne VALUES('IFUD1020', 'Opensource-utvikling', '');
INSERT INTO emne VALUES('IFUD1042', 'Applikasjonsutvikling for Android', '');
INSERT INTO emne VALUES('IFUD1048', 'C++ for programmerere', '');
INSERT INTO emne VALUES('IFUD1337', 'Navn kommer snart', '');

INSERT INTO bruker VALUES('anasky@hist.no', 'Anakin', 'Skywalker', '46251479872872459f5e4ce64a7d883d', 1);
INSERT INTO bruker VALUES('hansol@hist.no', 'Han', 'Solo', '46251479872872459f5e4ce64a7d883d', 1);
INSERT INTO bruker VALUES('laerer@hist.no', 'Kjell Toft', 'Hansen', '46251479872872459f5e4ce64a7d883d', 3);

INSERT INTO emne_bruker VALUES('TDAT3003', 'anasky@hist.no', 1);
INSERT INTO emne_bruker VALUES('TDAT3003', 'hansol@hist.no', 1);
INSERT INTO emne_bruker VALUES('TDAT3008', 'hansol@hist.no', 1);
INSERT INTO emne_bruker VALUES('IFUD1042', 'anasky@hist.no', 2);
INSERT INTO emne_bruker VALUES('TDAT3003', 'laerer@hist.no', 3);

INSERT INTO kravgruppe VALUES(DEFAULT, 'TDAT3003', 5);
INSERT INTO kravgruppe VALUES(DEFAULT, 'TDAT3008', 3);
INSERT INTO kravgruppe VALUES(DEFAULT, 'TDAT3008', 2);
INSERT INTO kravgruppe VALUES(DEFAULT, 'IFUD1020', 1);

INSERT INTO oving VALUES(1, 'TDAT3003', 1, DEFAULT), (2, 'TDAT3003', 1, DEFAULT), (3, 'TDAT3003', 1, DEFAULT),
(4, 'TDAT3003', 1, TRUE), (5, 'TDAT3003', 1, DEFAULT), (6, 'TDAT3003', 1, TRUE), (7, 'TDAT3003', 1, DEFAULT),
(8, 'TDAT3003', 1, TRUE), (9, 'TDAT3003', 1, TRUE), (10, 'TDAT3003', 1, TRUE);

INSERT INTO oving VALUES(1, 'TDAT3008', 2, TRUE), (2, 'TDAT3008', 2, TRUE), (3, 'TDAT3008', 2, TRUE),
(4, 'TDAT3008', 2, TRUE), (5, 'TDAT3008', 2, TRUE);

INSERT INTO oving VALUES(6, 'TDAT3008', 3, DEFAULT), (7, 'TDAT3008', 3, TRUE),
(8, 'TDAT3008', 3, TRUE), (9, 'TDAT3008', 3, TRUE);

INSERT INTO lokasjon VALUES('AITeL', 1, 'KA124',  8, 'TDAT3003');
INSERT INTO lokasjon VALUES('AITeL', 1, 'KA124',  9, 'TDAT3003');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA205', 11, 'TDAT3003');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA205', 12, 'TDAT3003');
INSERT INTO lokasjon VALUES('AITeL', 1, 'KA124',  8, 'TDAT3008');
INSERT INTO lokasjon VALUES('AITeL', 1, 'KA124',  9, 'TDAT3008');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA205', 11, 'TDAT3008');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA205', 12, 'TDAT3008');

INSERT INTO ko VALUES(1, 'TDAT3003', TRUE);
INSERT INTO ko VALUES(2, 'TDAT3008', DEFAULT);

INSERT INTO koinnlegg VALUES(1, DEFAULT, 1, 'anasky@hist.no', 'AITeL', 2, 'KA205', 11, 'TDAT3003', '', 'Godkjenning');

INSERT INTO brukere_i_innlegg VALUES(1, 'hansol@hist.no');
INSERT INTO brukere_i_innlegg VALUES(1, 'anasky@hist.no');

INSERT INTO ovinger_i_innlegg VALUES(1, 'anasky@hist.no', 2, 'TDAT3003');
INSERT INTO ovinger_i_innlegg VALUES(1, 'anasky@hist.no', 3, 'TDAT3003');
INSERT INTO ovinger_i_innlegg VALUES(1, 'hansol@hist.no', 3, 'TDAT3003');

