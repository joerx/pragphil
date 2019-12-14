INSERT INTO "roles" ("id", "name") VALUES
(1,	'ROLE_MEMBER'),
(2,	'ROLE_STUDENT'),
(3,	'ROLE_LECTURER'),
(4,	'ROLE_ADMIN');

INSERT INTO "users" ("id", "username", "password", "enabled", "api_token") VALUES
(1,	'socrates',	    '{bcrypt}$2a$10$nOPSsmCL2pXA6kCSKXRFmel9QPxDvYmcxchDIqXZLoXjmTdU/WndC',	'1',	'socratoken'),
(2,	'plato',	    '{bcrypt}$2a$10$nOPSsmCL2pXA6kCSKXRFmel9QPxDvYmcxchDIqXZLoXjmTdU/WndC',	'1',	'platotoken'),
(3,	'zeno',	        '{bcrypt}$2a$10$nOPSsmCL2pXA6kCSKXRFmel9QPxDvYmcxchDIqXZLoXjmTdU/WndC',	'1',	'zenotoken'),
(4,	'aristotle',	'{bcrypt}$2a$10$nOPSsmCL2pXA6kCSKXRFmel9QPxDvYmcxchDIqXZLoXjmTdU/WndC',	'1',	'aristokle');

INSERT INTO "roles_users" ("user_id", "role_id") VALUES
(1,	1),
(1,	2),
(1,	3),
(1,	4),
(2,	1),
(2,	2),
(2,	3),
(2,	4),
(3,	1),
(3,	2),
(3,	3),
(4,	1),
(4,	2);

INSERT INTO "lectures" ("id", "name", "lecturer_id") VALUES
(1,	    'Socratic Method',	            1),
(2,	    'Classical Greek Philosophy',	1),
(3,	    'The Republic',	                2),
(4,	    'Symposium',	                2),
(5,	    'Stoicism',	                    3);

INSERT INTO "lectures_students" ("lecture_id", "student_id") VALUES
(1,	2),
(1,	3),
(1,	4),
(2,	2),
(2,	3),
(2,	4),
(3,	4);
