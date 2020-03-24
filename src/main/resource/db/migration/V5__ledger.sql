create table ledger.clients
(
	client_id text not null
		constraint clients_pk
			primary key,
	name text not null,
	added_by text not null
		constraint clients_user__fk
			references ledger.users,
	added_on timestamp default now() not null
);

create table ledger.categories
(
	category_id text
		constraint categories_pk
			primary key,
	description text not null,
	added_by text not null
		constraint categories_added_user__fk
			references ledger.users,
	added_on timestamp default now() not null,
	removed_by text
		constraint categories_removed_user__fk
			references ledger.users,
	removed_on timestamp
);

create table ledger.entries
(
	entry_id text not null
		constraint entries_pk
			primary key,
	group_id text not null
		constraint entries_group__fk
			references ledger.entries,
	effective_date date not null,
	client_id text not null
		constraint entries_client__fk
			references ledger.clients,
	current boolean default true not null,
	added_by text not null
		constraint categories_added_user__fk
			references ledger.users,
	added_on timestamp default now() not null
);

create unique index entries_group_current__uindex
	on ledger.entries (group_id)
    WHERE (current);

create table ledger.entry_categories
(
	entry_id text not null
		constraint entry_categories_entry__fk
			references ledger.entries,
	category_id text not null
		constraint entry_categories_category_id_fk
			references ledger.categories (category_id),
	value bigint not null,
	constraint entry_categories_pk
		primary key (entry_id, category_id)
);
