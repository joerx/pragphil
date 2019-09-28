DROP TABLE IF EXISTS "authorities";
CREATE TABLE "public"."authorities" (
    "username" character varying(50) NOT NULL,
    "authority" character varying(50) NOT NULL,
    CONSTRAINT "authorities_username_authority" UNIQUE ("username", "authority"),
    CONSTRAINT "authorities_username_fkey" FOREIGN KEY (username) REFERENCES users(username) ON UPDATE CASCADE ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);

CREATE INDEX "authorities_username" ON "public"."authorities" USING btree ("username");

INSERT INTO "authorities" ("username", "authority") VALUES
('socrates',	'ROLE_LECTURER'),
('socrates',	'ROLE_MEMBER'),
('plato',	'ROLE_MEMBER'),
('plato',	'ROLE_LECTURER'),
('plato',	'ROLE_STUDENT'),
('aristotle',	'ROLE_MEMBER'),
('aristotle',	'ROLE_STUDENT'),
('xenophon',	'ROLE_MEMBER'),
('xenophon',	'ROLE_STUDENT'),
('zeno',	'ROLE_MEMBER'),
('zeno',	'ROLE_STUDENT'),
('zeno',	'ROLE_LECTURER'),
('epictetus',	'ROLE_MEMBER'),
('epictetus',	'ROLE_LECTURER'),
('epictetus',	'ROLE_STUDENT'),
('marcus',	'ROLE_MEMBER'),
('marcus',	'ROLE_STUDENT');

DROP TABLE IF EXISTS "lectures";
DROP SEQUENCE IF EXISTS lecture_id_seq;
CREATE SEQUENCE lecture_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."lectures" (
    "id" integer DEFAULT nextval('lecture_id_seq') NOT NULL,
    "name" character varying(50) NOT NULL,
    "lecturer_id" integer,
    CONSTRAINT "lecture_id" PRIMARY KEY ("id"),
    CONSTRAINT "lecture_lecturer_id_fkey" FOREIGN KEY (lecturer_id) REFERENCES users(id) ON DELETE SET NULL NOT DEFERRABLE
) WITH (oids = false);

INSERT INTO "lectures" ("id", "name", "lecturer_id") VALUES
(1,	'Socratic Method',	1),
(2,	'Classical Greek Philosophy',	1),
(3,	'The Republic',	2),
(4,	'Symposium',	2),
(5,	'Stoicism',	5);

DROP TABLE IF EXISTS "lectures_students";
CREATE TABLE "public"."lectures_students" (
    "lecture_id" integer NOT NULL,
    "student_id" integer NOT NULL,
    CONSTRAINT "lectures_students_lecture_id_student_id" UNIQUE ("lecture_id", "student_id"),
    CONSTRAINT "lectures_students_lecture_id_fkey" FOREIGN KEY (lecture_id) REFERENCES lectures(id) NOT DEFERRABLE,
    CONSTRAINT "lectures_students_student_id_fkey" FOREIGN KEY (student_id) REFERENCES users(id) NOT DEFERRABLE
) WITH (oids = false);


DROP TABLE IF EXISTS "users";
DROP SEQUENCE IF EXISTS user_id_seq;
CREATE SEQUENCE user_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."users" (
    "id" integer DEFAULT nextval('user_id_seq') NOT NULL,
    "username" character varying(50) NOT NULL,
    "password" character varying(68) NOT NULL,
    "enabled" smallint NOT NULL,
    CONSTRAINT "user_id" PRIMARY KEY ("id"),
    CONSTRAINT "user_username" UNIQUE ("username")
) WITH (oids = false);

INSERT INTO "users" ("id", "username", "password", "enabled") VALUES
(1,	'socrates',	'{bcrypt}$2a$10$hROTNW13dKUA5fKIN40fNu96crbNUjD2cRwI9J/oLH3iHbRUsD9nK',	1),
(2,	'plato',	'{bcrypt}$2a$10$hROTNW13dKUA5fKIN40fNu96crbNUjD2cRwI9J/oLH3iHbRUsD9nK',	1),
(3,	'aristotle',	'{bcrypt}$2a$10$hROTNW13dKUA5fKIN40fNu96crbNUjD2cRwI9J/oLH3iHbRUsD9nK',	1),
(4,	'xenophon',	'{bcrypt}$2a$10$hROTNW13dKUA5fKIN40fNu96crbNUjD2cRwI9J/oLH3iHbRUsD9nK',	1),
(5,	'zeno',	'{bcrypt}$2a$10$hROTNW13dKUA5fKIN40fNu96crbNUjD2cRwI9J/oLH3iHbRUsD9nK',	1),
(6,	'epictetus',	'{bcrypt}$2a$10$hROTNW13dKUA5fKIN40fNu96crbNUjD2cRwI9J/oLH3iHbRUsD9nK',	1),
(7,	'marcus',	'{bcrypt}$2a$10$hROTNW13dKUA5fKIN40fNu96crbNUjD2cRwI9J/oLH3iHbRUsD9nK',	1);
