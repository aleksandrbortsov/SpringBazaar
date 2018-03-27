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

  CREATE TABLE public.sb_login_attempts
  (
      id serial NOT NULL,
      username text NOT NULL,
      attempts text NOT NULL,
      last_modified datetime NOT NULL,
      PRIMARY KEY (id)
  );

  CREATE TABLE public.sb_roles
  (
      id serial NOT NULL,
      name text NOT NULL,
      is_default_role boolean NULL,
      PRIMARY KEY (id)
  );

  INSERT INTO public.sb_roles (id, name, is_default_role) VALUES ('1', 'ROLE_ADMIN', '0');
  INSERT INTO public.sb_roles (id, name, is_default_role) VALUES ('2', 'ROLE_USER', '1');
  INSERT INTO public.sb_roles (id, name, is_default_role) VALUES ('3', 'ROLE_SELLER', '0');
  INSERT INTO public.sb_roles (id, name, is_default_role) VALUES ('4', 'ROLE_BUYER', '0');

  CREATE TABLE public.sb_permissions
  (
    id serial NOT NULL,
    name text NOT NULL,
    description text NULL,
    value boolean NULL,
    PRIMARY KEY (id)
  );

  INSERT INTO public.sb_permissions (id, name, description) VALUES ('1', 'PERM_PRODUCT_CREATE', 'Create products');
  INSERT INTO public.sb_permissions (id, name, description) VALUES ('2', 'PERM_PRODUCT_VIEW', 'View products');
  INSERT INTO public.sb_permissions (id, name, description) VALUES ('3', 'PERM_PRODUCT_EDIT', 'Edit product');
  INSERT INTO public.sb_permissions (id, name, description) VALUES ('4', 'PERM_PRODUCT_STATUS_CLOSE', 'Close products');
  INSERT INTO public.sb_permissions (id, name, description) VALUES ('5', 'PERM_PRODUCT_DELETE', 'Delete products');

  CREATE TABLE public.sb_roles_permissions
  (
    permission_id bigint NOT NULL,
    role_id bigint NOT NULL,
    PRIMARY KEY (role_id, permission_id)
  );

  INSERT INTO public.sb_roles_permissions (permission_id, role_id) VALUES ('1', '3');
  INSERT INTO public.sb_roles_permissions (permission_id, role_id) VALUES ('2', '3');
  INSERT INTO public.sb_roles_permissions (permission_id, role_id) VALUES ('3', '3');
  INSERT INTO public.sb_roles_permissions (permission_id, role_id) VALUES ('4', '3');
  INSERT INTO public.sb_roles_permissions (permission_id, role_id) VALUES ('5', '3');

  CREATE TABLE public.sb_persons
  (
      id serial NOT NULL,
      user_id bigint NOT NULL,
      email text NOT NULL,
      first_name text NOT NULL,
      middle_name text NULL,
      last_name text NOT NULL,
      PRIMARY KEY (id)
  );

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
  );

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
  );

  create table public.persistent_logins (
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp not null,
    primary key(series)
  );
