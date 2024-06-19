INSERT INTO Blog (title, content, createdAt, updatedAt, user_id)
VALUES
    ('First Blog Title', 'Content of the first blog...', NOW(), NULL, 1),
    ('Second Blog Title', 'Content of the second blog...', NOW(), NULL, 1),
    ('Third Blog Title', 'Content of the third blog...', NOW(), NULL, 1),
    ('Fourth Blog Title', 'Content of the fourth blog...', NOW(), NULL, 2),
    ('Fifth Blog Title', 'Content of the fifth blog...', NOW(), NULL, 2),
    ('Sixth Blog Title', 'Content of the sixth blog...', NOW(), NULL, 2),
    ('Seventh Blog Title', 'Content of the seventh blog...', NOW(), NULL, 1),
    ('Eighth Blog Title', 'Content of the eighth blog...', NOW(), NULL, 2),
    ('Ninth Blog Title', 'Content of the ninth blog...', NOW(), NULL, 1),
    ('Tenth Blog Title', 'Content of the tenth blog...', NOW(), NULL, 2);


SELECT * FROM Blog;