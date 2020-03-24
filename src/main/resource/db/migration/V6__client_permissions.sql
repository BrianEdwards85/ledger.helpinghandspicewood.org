INSERT INTO ledger.permissions(permission_id, description, added_by)
VALUES ('client.list', 'Get all clients', '0000000-0000-0000-0000-000000000000'),
       ('client.add', 'Add Client', '0000000-0000-0000-0000-000000000000'),
       ('client.remove', 'Remove Client', '0000000-0000-0000-0000-000000000000'),
       ('client.update', 'Update Client', '0000000-0000-0000-0000-000000000000')
ON CONFLICT DO NOTHING;

INSERT INTO ledger.role_permissions(role_id, permission_id)
SELECT 'DEITY', permission_id
FROM ledger.permissions
WHERE permission_id ilike 'client.%'
ON CONFLICT DO NOTHING;

INSERT INTO ledger.role_permissions_log(role_id, permission_id, granted, added_by)
SELECT 'DEITY', permission_id, true, added_by
FROM ledger.permissions
WHERE permission_id ilike 'client.%'
ON CONFLICT DO NOTHING;
