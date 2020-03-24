create table if not exists ledger.roles
(
	role_id text not null
		constraint roles__pk
			primary key,
	description text not null,
	added_on timestamp default now() not null,
	added_by text not null
		constraint roles_added_by__fk
			references ledger.users
);

create table if not exists ledger.permissions
(
    permission_id text not null
		constraint permissions__pk
			primary key,
	description text not null,
	added_on timestamp default now() not null,
	added_by text not null
		constraint roles_added_by__fk
			references ledger.users
);

create table if not exists ledger.role_permissions
(
	role_id text not null
		constraint role_permissions_role__fk
			references ledger.roles,
	permission_id text not null
		constraint role_permissions_permissions__fk
			references ledger.permissions,
	enabled boolean default true not null,
	constraint role_permissions__pk
		primary key (role_id, permission_id)
);

create table ledger.user_roles
(
	user_id text not null
		constraint user_roles_user__fk
			references ledger.users,
	role_id text not null
		constraint user_roles_role__fk
			references ledger.roles,
	enabled boolean default true not null,
	constraint user_roles__pk
		primary key (user_id, role_id)
);

create table if not exists ledger.role_permissions_log
(
	id serial
		constraint role_permissions_log_pk
			primary key,
	role_id text not null,
	permission_id text not null,
	granted boolean not null,
	added_on timestamp default now() not null,
	added_by text not null
		constraint role_permissions_log_user__fk
			references ledger.users,
	constraint role_permissions_log_role_permissions__fk
		foreign key (role_id, permission_id) references ledger.role_permissions
);

create table if not exists ledger.user_roles_log
(
	id serial
		constraint user_role_log_pk
			primary key,
	role_id text not null,
	user_id text not null,
	granted boolean not null,
	added_on timestamp default now() not null,
	added_by text not null
		constraint user_role_log_user__fk
			references ledger.users,
	constraint user_role_log_user_roles__fk
		foreign key (role_id, user_id) references ledger.role_permissions
);
