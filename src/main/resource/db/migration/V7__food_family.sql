alter table ledger.clients
	add family int;

create unique index clients_name__uindex
	on ledger.clients (name);

UPDATE ledger.clients SET family = 1 WHERE family is null;

alter table ledger.clients alter column family set not null;

alter table ledger.entries
	add food boolean;

UPDATE ledger.entries SET food = false WHERE food is null;

alter table ledger.entries alter column food set not null;
