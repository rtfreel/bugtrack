-- default users for testing purposes
INSERT INTO user (username, password, first_name, last_name) 
    VALUES ('o', '$2a$10$bZ.OgPdk0DqHE0ByGtqKCOovzahO9Tp9Fnwq/NJtiDSdgjD1oYS/q', 'Roman', 'Tkachenko'),
        ('a', '$2a$10$bZ.OgPdk0DqHE0ByGtqKCOovzahO9Tp9Fnwq/NJtiDSdgjD1oYS/q', 'Test', 'User');

-- default projects for testing purposes
INSERT INTO project (owner_id, title, description) 
    VALUES (1, 'Project 1', 'Project 1 description'),
        (2, 'Test proj 1', 'Project 5 description'),
        (1, 'Project 2', 'Project 2 description'),
        (2, 'Test proj 2', 'Project 5 description'),
        (1, 'Project 3', 'Project 3 description'),
        (1, 'Test proj 3', 'Project 5 description'),
        (2, 'Project 4', 'Project 4 description');


