SELECT * FROM Commentlike;

-- Example insert queries for CommentLike with varying likes per comment

-- Comment 1: Randomly choose 5 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 1, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 5) AS subquery1
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 1);

-- Comment 2: Randomly choose 12 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 2, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 12) AS subquery2
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 2);

-- Comment 3: Randomly choose 8 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 3, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 8) AS subquery3
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 3);

-- Comment 4: Randomly choose 15 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 4, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 15) AS subquery4
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 4);

-- Comment 5: Randomly choose 6 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 5, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 6) AS subquery5
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 5);

-- Comment 6: Randomly choose 10 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 6, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 10) AS subquery6
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 6);

-- Comment 7: Randomly choose 20 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 7, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 20) AS subquery7
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 7);

-- Comment 8: Randomly choose 4 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 8, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 4) AS subquery8
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 8);

-- Comment 9: Randomly choose 18 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 9, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 18) AS subquery9
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 9);

-- Comment 10: Randomly choose 9 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 10, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 9) AS subquery10
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 10);

-- Comment 11: Randomly choose 13 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 11, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 13) AS subquery11
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 11);

-- Comment 12: Randomly choose 7 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 12, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 7) AS subquery12
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 12);

-- Comment 13: Randomly choose 11 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 13, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 11) AS subquery13
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 13);

-- Comment 14: Randomly choose 16 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 14, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 16) AS subquery14
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 14);

-- Comment 15: Randomly choose 8 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 15, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 8) AS subquery15
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 15);

-- Comment 16: Randomly choose 12 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 16, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 12) AS subquery16
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 16);

-- Comment 17: Randomly choose 5 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 17, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 5) AS subquery17
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 17);

-- Comment 18: Randomly choose 9 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 18, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 9) AS subquery18
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 18);

-- Comment 19: Randomly choose 14 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 19, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 14) AS subquery19
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 19);

-- Comment 20: Randomly choose 10 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 20, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 10) AS subquery20
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 20);

-- Comment 21: Randomly choose 6 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 21, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 6) AS subquery21
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 21);

-- Comment 22: Randomly choose 15 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 22, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 15) AS subquery22
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 22);

-- Comment 23: Randomly choose 17 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 23, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 17) AS subquery23
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 23);

-- Comment 24: Randomly choose 20 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 24, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 20) AS subquery24
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 24);

-- Comment 25: Randomly choose 25 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 25, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 25) AS subquery25
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 25);

-- Comment 26: Randomly choose 8 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 26, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 8) AS subquery26
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 26);

-- Comment 27: Randomly choose 12 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 27, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 12) AS subquery27
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 27);

-- Comment 28: Randomly choose 19 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 28, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 19) AS subquery28
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 28);

-- Comment 29: Randomly choose 14 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 29, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 14) AS subquery29
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 29);

-- Comment 30: Randomly choose 11 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 30, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 11) AS subquery30
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 30);

-- Comment 31: Randomly choose 9 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 31, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 9) AS subquery31
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 31);

-- Comment 32: Randomly choose 16 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 32, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 16) AS subquery32
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 32);

-- Comment 33: Randomly choose 7 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 33, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 7) AS subquery33
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 33);

-- Comment 34: Randomly choose 13 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 34, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 13) AS subquery34
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 34);

-- Comment 35: Randomly choose 18 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 35, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 18) AS subquery35
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 35);

-- Comment 36: Randomly choose 10 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 36, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 10) AS subquery36
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 36);

-- Comment 37: Randomly choose 15 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 37, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 15) AS subquery37
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 37);

-- Comment 38: Randomly choose 6 users to like
INSERT INTO CommentLike (comment_id, user_id)
SELECT 38, id
FROM (SELECT id FROM User ORDER BY RAND() LIMIT 6) AS subquery38
WHERE id NOT IN (SELECT user_id FROM CommentLike WHERE comment_id = 38);



