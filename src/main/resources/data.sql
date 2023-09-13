INSERT INTO user_profile (login, email) VALUES ('mieszekleszek', 'mieszekleszek@wp.pl');
INSERT INTO user_profile (login, email) VALUES ('zdzisiek', 'zdzisiek@wp.pl');
INSERT INTO user_profile (login, email) VALUES ('misiek', 'misiek@wp.pl');
INSERT INTO user_profile (login, email) VALUES ('wisniek', 'wisniek@wp.pl');
INSERT INTO user_profile (login, email) VALUES ('lisciek', 'lisciek@wp.pl');
INSERT INTO user_profile (login, email) VALUES ('adassmieszek', 'adassmieszek@onet.pl');

INSERT INTO user_profile (login, email, password, is_admin) VALUES ('server_admin', 'serveradmin@wp.pl', 'qwerty', true);

INSERT INTO lecture (main_subject, start_time, path_number, max_listeners)
VALUES ('Analiza matematyczna', '2023-06-01 10:00:00', 1, 5);
INSERT INTO lecture (main_subject, start_time, path_number, max_listeners)
VALUES ('Programowanie niskopoziomowe', '2023-06-01 10:00:00', 1, 5);
INSERT INTO lecture (main_subject, start_time, path_number, max_listeners)
VALUES ('Filologia angielska', '2023-06-01 10:00:00', 1, 5);

INSERT INTO lecture (main_subject, start_time, path_number, max_listeners)
VALUES ('Matematyka dyskretna', '2023-06-01 12:00:00', 2, 5);
INSERT INTO lecture (main_subject, start_time, path_number, max_listeners)
VALUES ('Aplikacje webowe', '2023-06-01 12:00:00', 2, 5);
INSERT INTO lecture (main_subject, start_time, path_number, max_listeners)
VALUES ('Filologia hiszpanska', '2023-06-01 12:00:00', 2, 5);

INSERT INTO lecture (main_subject, start_time, path_number, max_listeners)
VALUES ('Rachunek prawdopodobienstwa', '2023-06-01 14:00:00', 3, 5);
INSERT INTO lecture (main_subject, start_time, path_number, max_listeners)
VALUES ('Bazy danych SQL', '2023-06-01 14:00:00', 3, 5);
INSERT INTO lecture (main_subject, start_time, path_number, max_listeners)
VALUES ('Filologia polska', '2023-06-01 14:00:00', 3, 5);

INSERT INTO user_profile_lecture_junction (user_profile_id, lecture_id)
VALUES (1, 2);
INSERT INTO user_profile_lecture_junction (user_profile_id, lecture_id)
VALUES (1, 5);
INSERT INTO user_profile_lecture_junction (user_profile_id, lecture_id)
VALUES (2, 9);
INSERT INTO user_profile_lecture_junction (user_profile_id, lecture_id)
VALUES (3, 9);
INSERT INTO user_profile_lecture_junction (user_profile_id, lecture_id)
VALUES (4, 9);
INSERT INTO user_profile_lecture_junction (user_profile_id, lecture_id)
VALUES (5, 9);
INSERT INTO user_profile_lecture_junction (user_profile_id, lecture_id)
VALUES (6, 9);






