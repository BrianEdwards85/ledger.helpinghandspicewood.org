INSERT INTO ledger.permissions(permission_id, description, added_by)
VALUES ('entries.list', 'Get client entries', '0000000-0000-0000-0000-000000000000'),
       ('entries.add', 'Add client entry', '0000000-0000-0000-0000-000000000000'),
       ('entries.remove', 'Remove client entry from same user', '0000000-0000-0000-0000-000000000000'),
       ('entries.remove.all', 'Remove client entry from all users', '0000000-0000-0000-0000-000000000000'),
       ('entries.update', 'Update client entries from same user', '0000000-0000-0000-0000-000000000000'),
       ('entries.update.all', 'Update client entries from all user', '0000000-0000-0000-0000-000000000000')
ON CONFLICT DO NOTHING;

INSERT INTO ledger.role_permissions(role_id, permission_id)
SELECT 'DEITY', permission_id
FROM ledger.permissions
WHERE permission_id ilike 'entries.%'
ON CONFLICT DO NOTHING;

INSERT INTO ledger.role_permissions_log(role_id, permission_id, granted, added_by)
SELECT 'DEITY', permission_id, true, added_by
FROM ledger.permissions
WHERE permission_id ilike 'entries.%'
ON CONFLICT DO NOTHING;
