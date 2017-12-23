CREATE SCHEMA IF NOT EXISTS public AUTHORIZATION postgres;

CREATE TABLE public.sb_users
(
    id serial NOT NULL,
    username text NOT NULL,
    password text NOT NULL,
    state boolean,
    person_id bigint,
    account_non_expired boolean,
    account_non_locked boolean,
    credentials_non_expired boolean,
    PRIMARY KEY (id)
);
ALTER TABLE public.sb_users
    OWNER to postgres;

CREATE TABLE public.sb_roles
(
    id serial NOT NULL,
    name text NOT NULL,
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE public.sb_roles
    OWNER to postgres;

CREATE TABLE public.sb_groups
(
    id serial NOT NULL,
    name text NOT NULL,
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);
ALTER TABLE public.sb_groups
    OWNER to postgres;

CREATE TABLE public.sb_persons
(
    id serial NOT NULL,
    user_id bigint NOT NULL,
	  email text NOT NULL,
	  first_name text NOT NULL,
	  middle_name text NULL,
	  last_name text NOT NULL,
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);
ALTER TABLE public.sb_persons
    OWNER to postgres;

CREATE TABLE public.sb_products
(
    id serial NOT NULL,
	  caption text NOT NULL,
	  description text,
	  price money,
	  image_url text,
    person_id bigint NOT NULL,
	  created_when timestamp without time zone NOT NULL,
	  created_by bigint NOT NULL,
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);
ALTER TABLE public.sb_products
    OWNER to postgres;

CREATE TABLE public.sb_users_roles
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_usersroles_role FOREIGN KEY (role_id)
        REFERENCES public.sb_roles (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_usersroles_user FOREIGN KEY (user_id)
        REFERENCES public.sb_users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
);
ALTER TABLE public.sb_users_roles
    OWNER to postgres;