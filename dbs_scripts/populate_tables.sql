delete from rating;
insert into rating (player, game, rating, rated_on, ident) values
('user1', 'Gridhunt', 2, '1999-05-04', 1),
('user2', 'Gridhunt', 3, '1999-05-04', 2),
('user3', 'Gridhunt', 1, '1999-05-04', 3),
('user4', 'Gridhunt', 1, '1999-05-04', 4),
('user5', 'Gridhunt', 5, '1999-05-04', 5);

delete from score;
insert into score (player, game, points, played_on, ident) values
('user1', 'Gridhunt', 20, '1999-05-05', 1),
('user2', 'Gridhunt', 1, '1999-05-05', 2),
('user3', 'Gridhunt', 21, '1999-05-05', 3),
('user4', 'Gridhunt', 38, '1999-05-05', 4),
('user5', 'Gridhunt', 999, '1999-05-05', 5);

delete from comment;
insert into comment (player, game, comment, commented_on, ident) values
('user1', 'Gridhunt', 'first comment', '1999-05-03', 1),
('user2', 'Gridhunt', 'second comment', '1999-05-03', 2),
('user3', 'Gridhunt', 'third comment', '1999-05-03', 3),
('user4', 'Gridhunt', 'fourth comment', '1999-05-03', 4),
('user5', 'Gridhunt', 'fifth comment', '1999-05-03', 5);

