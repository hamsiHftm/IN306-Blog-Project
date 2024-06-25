-- Comments for Blog 1
INSERT INTO Comment (content, createdAt, blog_id, user_id) VALUES
                                                               ('Great article!', NOW(), 1, 1),
                                                               ('Interesting points.', NOW(), 1, 2),
                                                               ('I disagree with this.', NOW(), 1, 3),
                                                               ('Looking forward to more.', NOW(), 1, 4),
                                                               ('Well written.', NOW(), 1, 5),
                                                               ('This helped me a lot.', NOW(), 1, 6);

-- Comments for Blog 2
INSERT INTO Comment (content, createdAt, blog_id, user_id) VALUES
                                                               ('Nice read.', NOW(), 2, 7),
                                                               ('Thanks for sharing.', NOW(), 2, 8),
                                                               ('Could you elaborate on this?', NOW(), 2, 9),
                                                               ('I have a question.', NOW(), 2, 10),
                                                               ('Can''t wait for the next post.', NOW(), 2, 11);

-- Comments for Blog 3
INSERT INTO Comment (content, createdAt, blog_id, user_id) VALUES
                                                               ('Well explained.', NOW(), 3, 12),
                                                               ('I learned something new.', NOW(), 3, 13),
                                                               ('This is relevant to my work.', NOW(), 3, 14);

-- Comments for Blog 4
INSERT INTO Comment (content, createdAt, blog_id, user_id) VALUES
                                                               ('Keep up the good work!', NOW(), 4, 15),
                                                               ('I''m sharing this with my friends.', NOW(), 4, 16),
                                                               ('Would love to see more examples.', NOW(), 4, 17),
                                                               ('This changed my perspective.', NOW(), 4, 18);

-- Comments for Blog 5
INSERT INTO Comment (content, createdAt, blog_id, user_id) VALUES
                                                               ('Informative.', NOW(), 5, 19),
                                                               ('I''m bookmarking this.', NOW(), 5, 20),
                                                               ('Very insightful.', NOW(), 5, 21);

-- Comments for Blog 6
INSERT INTO Comment (content, createdAt, blog_id, user_id) VALUES
                                                               ('Thanks for the detailed explanation.', NOW(), 6, 22),
                                                               ('I appreciate the effort.', NOW(), 6, 23);

-- Comments for Blog 7 (No comments)

-- Comments for Blog 8
INSERT INTO Comment (content, createdAt, blog_id, user_id) VALUES
                                                               ('Interesting perspective.', NOW(), 8, 24),
                                                               ('I''m sharing this with my colleagues.', NOW(), 8, 25);

-- Comments for Blog 9
INSERT INTO Comment (content, createdAt, blog_id, user_id) VALUES
    ('Looking forward to more from you.', NOW(), 9, 26);

-- Comments for Blog 10
INSERT INTO Comment (content, createdAt, blog_id, user_id) VALUES
                                                               ('This is exactly what I needed.', NOW(), 10, 27),
                                                               ('I have a suggestion.', NOW(), 10, 28),
                                                               ('Can you explain this in more detail?', NOW(), 10, 29);

-- Comments for Blog 11 (No comments)

-- Comments for Blog 12
INSERT INTO Comment (content, createdAt, blog_id, user_id) VALUES
                                                               ('I''m glad I found this blog.', NOW(), 12, 30),
                                                               ('This resonates with me.', NOW(), 12, 31),
                                                               ('Could you write about topic X next?', NOW(), 12, 32);

-- Comments for Blog 13
INSERT INTO Comment (content, createdAt, blog_id, user_id) VALUES
                                                               ('I have a question about point Y.', NOW(), 13, 33),
                                                               ('I''ve shared this on social media.', NOW(), 13, 34);

-- Comments for Blog 14 (No comments)

-- Comments for Blog 15
INSERT INTO Comment (content, createdAt, blog_id, user_id) VALUES
                                                               ('Very helpful.', NOW(), 15, 35),
                                                               ('I''ve subscribed to your newsletter.', NOW(), 15, 36),
                                                               ('Can you recommend further reading?', NOW(), 15, 37),
                                                               ('Please write more about topic Z.', NOW(), 15, 38);


SELECT * FROM Comment;
