create table if not exists ledger.users
(
	id text not null
		constraint users_pkey
			primary key,
	name text not null,
	added_on timestamp default now() not null,
	added_by text not null,
	removed_on timestamp,
	removed_by text,
    constraint users_added_by_fk
		foreign key (added_by) references ledger.users,
	constraint users_removed_by_fk
		foreign key (removed_by) references ledger.users
);

create table if not exists ledger.user_emails
(
	user_id text not null
		constraint user_emails_user__fk
			references ledger.users
				deferrable initially deferred,
	email text not null,
	added_on timestamp default now() not null,
	principal boolean default false not null,
	constraint user_emails_pk
		primary key (user_id, email)
);