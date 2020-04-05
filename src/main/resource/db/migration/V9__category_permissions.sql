INSERT INTO ledger.permissions(permission_id, description, added_by)
VALUES ('categories.list', 'Get categories', '0000000-0000-0000-0000-000000000000'),
       ('categories.add', 'Add categories', '0000000-0000-0000-0000-000000000000'),
       ('categories.remove', 'Remove categories', '0000000-0000-0000-0000-000000000000'),
       ('categories.update', 'Update categories', '0000000-0000-0000-0000-000000000000')
ON CONFLICT DO NOTHING;

INSERT INTO ledger.role_permissions(role_id, permission_id)
SELECT 'DEITY', permission_id
FROM ledger.permissions
WHERE permission_id ilike 'categories.%'
ON CONFLICT DO NOTHING;

INSERT INTO ledger.role_permissions_log(role_id, permission_id, granted, added_by)
SELECT 'DEITY', permission_id, true, added_by
FROM ledger.permissions
WHERE permission_id ilike 'categories.%'
ON CONFLICT DO NOTHING;
