INSERT INTO Room (code, description) VALUES ('D105', 'room1');
INSERT INTO Room (code, description) VALUES ('D0206', 'room2');

INSERT INTO Person (name, surname, username) VALUES ('Ivan', 'Mrkva', 'xmrkva00');
INSERT INTO Person (name, surname, username) VALUES ('Daniel', 'Drevo', 'xdrevo00');
INSERT INTO Person (name, surname, username) VALUES ('Jozef', 'Buchta', 'xbucht00');
INSERT INTO Person (name, surname, username) VALUES ('Monika', 'Rybarova', 'xrybar00');

INSERT INTO Lector (id) VALUES (1);
INSERT INTO Lector (id) VALUES (2);

INSERT INTO Student (id) VALUES (3);
INSERT INTO Student (id) VALUES (4);

INSERT INTO StudyCourse (code, name, description, guarant_id) VALUES ('IAL', 'Algoritmy', 'course1', 1);
INSERT INTO StudyCourse (code, name, description, guarant_id) VALUES ('IMP', 'Mikroprocesory', 'course2', 2);

INSERT INTO Teaches (course_id, lector_id) VALUES ('1', '2');

INSERT INTO CourseDate (description, course_id, room_id) VALUES ('Cviko 1', '1', '1');
INSERT INTO CourseDate (description, course_id, room_id) VALUES ('Cviko 1', '2', '2');
INSERT INTO CourseDate (description, course_id, room_id) VALUES ('Cviko 2', '2', '2');