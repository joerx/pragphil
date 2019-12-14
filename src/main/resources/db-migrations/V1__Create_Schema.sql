-- Users --

DROP TABLE IF EXISTS "users";
DROP SEQUENCE IF EXISTS user_id_seq;
CREATE SEQUENCE user_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."users" (
    "id" integer DEFAULT nextval('user_id_seq') NOT NULL,
    "username" character varying(50) NOT NULL,
    "password" character varying(70) NOT NULL,
    "enabled" boolean DEFAULT true NOT NULL,
    "api_token" character varying(32),
    CONSTRAINT "user_id" PRIMARY KEY ("id"),
    CONSTRAINT "user_username" UNIQUE ("username"),
    CONSTRAINT "users_api_token" UNIQUE ("api_token")
) WITH (oids = false);

-- Roles --

DROP TABLE IF EXISTS "roles";
DROP SEQUENCE IF EXISTS roles_id_seq;
CREATE SEQUENCE roles_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."roles" (
    "id" integer DEFAULT nextval('roles_id_seq') NOT NULL,
    "name" character varying(50) NOT NULL,
    CONSTRAINT "roles_id" PRIMARY KEY ("id"),
    CONSTRAINT "roles_name" UNIQUE ("name")
) WITH (oids = false);

-- User Roles --

DROP TABLE IF EXISTS "roles_users";
CREATE TABLE "public"."roles_users" (
    "user_id" integer NOT NULL,
    "role_id" integer NOT NULL,
    CONSTRAINT "roles_users_user_id_role_id" PRIMARY KEY ("user_id", "role_id"),
    CONSTRAINT "roles_users_role_id_fkey" FOREIGN KEY (role_id) REFERENCES roles(id) NOT DEFERRABLE,
    CONSTRAINT "roles_users_user_id_fkey" FOREIGN KEY (user_id) REFERENCES users(id) NOT DEFERRABLE
) WITH (oids = false);

-- Lectures --

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

-- Lectures / Students --

DROP TABLE IF EXISTS "lectures_students";
CREATE TABLE "public"."lectures_students" (
    "lecture_id" integer NOT NULL,
    "student_id" integer NOT NULL,
    CONSTRAINT "lectures_students_lecture_id_student_id" UNIQUE ("lecture_id", "student_id"),
    CONSTRAINT "lectures_students_lecture_id_fkey" FOREIGN KEY (lecture_id) REFERENCES lectures(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "lectures_students_student_id_fkey" FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);
