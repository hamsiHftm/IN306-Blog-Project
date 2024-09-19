-- Example insert queries for BlogLike with varying likes per blog

-- Blog 1: Randomly choose 4 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 1, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 4) AS subquery1
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 1);

-- Blog 2: Randomly choose 10 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 2, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 10) AS subquery2
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 2);

-- Blog 3: Randomly choose 20 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 3, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 20) AS subquery3
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 3);

-- Blog 4: Randomly choose 22 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 4, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 22) AS subquery4
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 4);

-- Blog 5: Randomly choose 15 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 5, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 15) AS subquery5
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 5);

-- Blog 6: Randomly choose 12 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 6, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 12) AS subquery6
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 6);

-- Blog 7: Randomly choose 18 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 7, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 18) AS subquery7
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 7);

-- Blog 8: Randomly choose 22 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 8, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 22) AS subquery8
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 8);

-- Blog 9: Randomly choose 20 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 9, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 20) AS subquery9
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 9);

-- Blog 10: Randomly choose 12 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 10, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 12) AS subquery10
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 10);

-- Blog 11: Randomly choose 13 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 11, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 13) AS subquery11
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 11);

-- Blog 12: Randomly choose 21 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 12, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 21) AS subquery12
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 12);

-- Blog 13: Randomly choose 17 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 13, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 17) AS subquery13
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 13);

-- Blog 14: Randomly choose 20 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 14, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 20) AS subquery14
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 14);

-- Blog 15: Randomly choose 22 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 15, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 22) AS subquery15
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 15);

-- Blog 16: Randomly choose 19 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 16, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 19) AS subquery16
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 16);

-- Blog 17: Randomly choose 14 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 17, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 14) AS subquery17
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 17);

-- Blog 18: Randomly choose 6 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 18, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 6) AS subquery18
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 18);

-- Blog 19: Randomly choose 16 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 19, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 16) AS subquery19
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 19);

-- Blog 20: Randomly choose 23 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 20, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 22) AS subquery20
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 20);

-- Blog 21: Randomly choose 11 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 21, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 11) AS subquery21
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 21);

-- Blog 22: Randomly choose 1 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 22, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 1) AS subquery22
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 22);

-- Blog 23: Randomly choose 13 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 23, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 13) AS subquery23
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 23);

-- Blog 24: Randomly choose 20 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 24, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 20) AS subquery24
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 24);

-- Blog 25: Randomly choose 18 unique users to like
INSERT INTO BlogLike (blog_id, user_id)
SELECT 25, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 18) AS subquery25
WHERE id NOT IN (SELECT user_id FROM BlogLike WHERE blog_id = 25);



