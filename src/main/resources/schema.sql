CREATE TABLE user_profile (
    id INT PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR(60) NOT NULL UNIQUE,
    email VARCHAR(60) NOT NULL UNIQUE
);

CREATE TABLE lecture(
    id INT PRIMARY KEY AUTO_INCREMENT,
    main_subject VARCHAR(40) NOT NULL,
    start_time TIMESTAMP WITH TIME ZONE NOT NULL,
    path_num INT NOT NULL,
    max_listeners INT NOT NULL
);

CREATE TABLE user_profile_lecture_junction(
    user_profile_id INT,
    lecture_id INT,
    CONSTRAINT user_lecture_pk PRIMARY KEY (user_profile_id, lecture_id),
    CONSTRAINT FK_user_profile FOREIGN KEY (user_profile_id)
                                          REFERENCES user_profile (id),
    CONSTRAINT FK_lecture FOREIGN KEY (lecture_id)
                                          REFERENCES lecture (id)
);