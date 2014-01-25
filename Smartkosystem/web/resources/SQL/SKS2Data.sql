INSERT INTO emne VALUES('TDAT3003', '3D-programmering', 'Alt er i 3D!');
INSERT INTO emne VALUES('TDAT3008', 'Java EE og distribuerte systemer', 'Dette er et spennende fag');
INSERT INTO emne VALUES('IFUD1043', 'Applikasjonsutvikling på .NET-plattformen', '.NET folkens');
INSERT INTO emne VALUES('IFUD1020', 'Opensource-utvikling', 'Opensource or bust!');
INSERT INTO emne VALUES('IFUD1042', 'Applikasjonsutvikling for Android', 'Istanbul next liksom.');
INSERT INTO emne VALUES('IFUD1048', 'C++ for programmerere', 'C++, the real language, the best language');

INSERT INTO bruker VALUES('sksmailsender@gmail.com', 'Test', 'Admin', '21232f297a57a5a743894a0e4a801fc3', 4, '453a88bfb7559aed089e4be36b63c44e');
INSERT INTO bruker VALUES('student@stud.hist.no', 'Test', 'Student', '21232f297a57a5a743894a0e4a801fc3', 1, '89e4be36b63c44e');
INSERT INTO bruker VALUES('assistent@stud.hist.no', 'Test', 'Studentassistent', '21232f297a57a5a743894a0e4a801fc3', 2, '453a88bfb36b63c44e');
INSERT INTO bruker VALUES('laerer@hist.no', 'Test', 'Laerer', '21232f297a57a5a743894a0e4a801fc3', 3, '453a88bfb7559aed0');
INSERT INTO bruker VALUES('haakon.jarle.hassel@gmail.com', 'Håkon Jarle', 'Hassel', '46251479872872459f5e4ce64a7d883d', 1, 'e4ce64a7d883d');
INSERT INTO bruker VALUES('petterlu@stud.hist.no', 'Petter', 'Lundemo', '7c3daa31f887c333291d5cf04e541db5', 1, '87c333291d5cf04');
INSERT INTO bruker VALUES('dummy1@hist.no', 'Dummy', 'Bruker', '275876e34cf609db118f3d84b799a790', 1, '609db118f3d84b799');
INSERT INTO bruker VALUES('dummy2@hist.no', 'Dummy', 'Bruker', '275876e34cf609db118f3d84b799a790', 1, '609db118f3d84b799');
INSERT INTO bruker VALUES('dummy3@hist.no', 'Dummy', 'Bruker', '275876e34cf609db118f3d84b799a790', 1, '609db118f3d84b799');
INSERT INTO bruker VALUES('dummy4@hist.no', 'Dummy', 'Bruker', '275876e34cf609db118f3d84b799a790', 1, '609db118f3d84b799');
INSERT INTO bruker VALUES('dummy5@hist.no', 'Dummy', 'Bruker', '275876e34cf609db118f3d84b799a790', 1, '609db118f3d84b799');


INSERT INTO emne_bruker VALUES('IFUD1043', 'sksmailsender@gmail.com', 3);
INSERT INTO emne_bruker VALUES('IFUD1042', 'sksmailsender@gmail.com', 3);
INSERT INTO emne_bruker VALUES('IFUD1020', 'sksmailsender@gmail.com', 3);
INSERT INTO emne_bruker VALUES('TDAT3003', 'sksmailsender@gmail.com', 3);
INSERT INTO emne_bruker VALUES('IFUD1043', 'haakon.jarle.hassel@gmail.com', 1);
INSERT INTO emne_bruker VALUES('IFUD1043', 'petterlu@stud.hist.no', 2);
INSERT INTO emne_bruker VALUES('TDAT3008', 'haakon.jarle.hassel@gmail.com', 1);
INSERT INTO emne_bruker VALUES('TDAT3008', 'petterlu@stud.hist.no', 1);

INSERT INTO kravgruppe VALUES(1, 'IFUD1043', 2, 'Må ha øving 4 godkjent, og to øvrige');
INSERT INTO kravgruppe VALUES(2, 'IFUD1042', 3, 'Må ha 3 av disse godkjent, samt øving 3');
INSERT INTO kravgruppe VALUES(3, 'IFUD1020', 2, 'Må ha minst 2 av disse godkjent');
INSERT INTO kravgruppe VALUES(4, 'TDAT3003', 5, 'Må ha 5 av disse godkjent');
INSERT INTO kravgruppe VALUES(5, 'TDAT3008', 3, 'Må ha 3 av disse godkjent');

INSERT INTO øving VALUES(1, 'TDAT3003', 4, DEFAULT), (2, 'TDAT3003', 4, DEFAULT), (3, 'TDAT3003', 4, DEFAULT),
(4, 'TDAT3003', 4, DEFAULT), (5, 'TDAT3003', 4, DEFAULT), (6, 'TDAT3003', 4, DEFAULT), (7, 'TDAT3003', 4, DEFAULT),
(8, 'TDAT3003', 4, DEFAULT), (9, 'TDAT3003', 4, DEFAULT), (10, 'TDAT3003', 4, DEFAULT);

INSERT INTO øving VALUES(1, 'TDAT3008', 5, DEFAULT), (2, 'TDAT3008', 5, DEFAULT), (3, 'TDAT3008', 5, DEFAULT),
(4, 'TDAT3008', 5, DEFAULT), (5, 'TDAT3008', 5, DEFAULT);

INSERT INTO øving VALUES(1, 'IFUD1043', 1, DEFAULT), (2, 'IFUD1043', 1, DEFAULT), (3, 'IFUD1043', 1, DEFAULT),
(4, 'IFUD1043', 1, TRUE), (5, 'IFUD1043', 1, DEFAULT);

INSERT INTO øving VALUES(1, 'IFUD1020', 3, DEFAULT), (2, 'IFUD1020', 3, DEFAULT), (3, 'IFUD1020', 3, DEFAULT),
(4, 'IFUD1020', 3, DEFAULT), (5, 'IFUD1020', 3, DEFAULT);

INSERT INTO øving VALUES(1, 'IFUD1042', 2, FALSE), (2, 'IFUD1042', 2, FALSE), (3, 'IFUD1042', 2, TRUE),
(4, 'IFUD1042', 2, FALSE), (5, 'IFUD1042', 2, FALSE);

INSERT INTO lokasjon VALUES('AITeL', 1, 'KA124', 1, 'IFUD1043');
INSERT INTO lokasjon VALUES('AITeL', 1, 'KA124', 2, 'IFUD1043');
INSERT INTO lokasjon VALUES('AITeL', 1, 'KA205', 1, 'IFUD1043');
INSERT INTO lokasjon VALUES('AITeL', 1, 'KA205', 2, 'IFUD1043');
INSERT INTO lokasjon VALUES('AITeL', 1, 'KA205', 3, 'IFUD1043');

INSERT INTO lokasjon VALUES('AITeL', 2, 'KA111', 10, 'IFUD1043');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA111', 11, 'IFUD1043');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA200', 10, 'IFUD1043');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA200', 11, 'IFUD1043');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA200', 12, 'IFUD1043');

INSERT INTO lokasjon VALUES('AITeL', 2, 'KA111', 10, 'TDAT3008');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA111', 11, 'TDAT3008');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA200', 10, 'TDAT3008');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA200', 11, 'TDAT3008');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA200', 12, 'TDAT3008');

INSERT INTO lokasjon VALUES('AITeL', 2, 'KA111', 10, 'IFUD1042');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA111', 11, 'IFUD1042');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA200', 10, 'IFUD1042');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA200', 11, 'IFUD1042');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA200', 12, 'IFUD1042');

INSERT INTO lokasjon VALUES('AITeL', 2, 'KA111', 10, 'TDAT3003');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA111', 11, 'TDAT3003');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA200', 10, 'TDAT3003');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA200', 11, 'TDAT3003');
INSERT INTO lokasjon VALUES('AITeL', 2, 'KA200', 12, 'TDAT3003');

INSERT INTO kø VALUES(1, 'IFUD1043', TRUE);
INSERT INTO kø VALUES(2, 'IFUD1042', DEFAULT);
INSERT INTO kø VALUES(3, 'TDAT3008', TRUE);
INSERT INTO kø VALUES(4, 'TDAT3003', TRUE);