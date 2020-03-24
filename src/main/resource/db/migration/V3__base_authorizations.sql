INSERT INTO ledger.users(id, name, added_by, removed_on, removed_by)
    VALUES ('0000000-0000-0000-0000-000000000000', 'System user', '0000000-0000-0000-0000-000000000000', now(), '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;

INSERT INTO ledger.roles(role_id, description, added_by)
    VALUES ('DEITY', 'Uberuser', '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;

-- user.get_all
INSERT INTO ledger.permissions(permission_id, description, added_by)
    VALUES ('user.get_all', 'Get all users', '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions(role_id, permission_id)
    VALUES ('DEITY', 'user.get_all')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions_log(role_id, permission_id, granted, added_by)
     VALUES ('DEITY', 'user.get_all', true, '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;

-- user.add
INSERT INTO ledger.permissions(permission_id, description, added_by)
    VALUES ('user.add', 'Add users', '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions(role_id, permission_id)
    VALUES ('DEITY', 'user.add')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions_log(role_id, permission_id, granted, added_by)
     VALUES ('DEITY', 'user.add', true, '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;

-- user.remove
INSERT INTO ledger.permissions(permission_id, description, added_by)
    VALUES ('user.remove', 'Remove user', '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions(role_id, permission_id)
    VALUES ('DEITY', 'user.remove')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions_log(role_id, permission_id, granted, added_by)
     VALUES ('DEITY', 'user.remove', true, '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;

-- user.update
INSERT INTO ledger.permissions(permission_id, description, added_by)
    VALUES ('user.update', 'Update user', '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions(role_id, permission_id)
    VALUES ('DEITY', 'user.update')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions_log(role_id, permission_id, granted, added_by)
     VALUES ('DEITY', 'user.update', true, '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;

-- user.grant_roles
INSERT INTO ledger.permissions(permission_id, description, added_by)
    VALUES ('user.grant_roles', 'Grant and revoke roles to user', '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions(role_id, permission_id)
    VALUES ('DEITY', 'user.grant_roles')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions_log(role_id, permission_id, granted, added_by)
     VALUES ('DEITY', 'user.grant_roles', true, '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;

-- user.masquerade
INSERT INTO ledger.permissions(permission_id, description, added_by)
    VALUES ('user.masquerade', 'Masquerade as user', '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions(role_id, permission_id)
    VALUES ('DEITY', 'user.masquerade')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions_log(role_id, permission_id, granted, added_by)
     VALUES ('DEITY', 'user.masquerade', true, '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;

-- role.get_all
INSERT INTO ledger.permissions(permission_id, description, added_by)
    VALUES ('role.get_all', 'Get all roles', '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions(role_id, permission_id)
    VALUES ('DEITY', 'role.get_all')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions_log(role_id, permission_id, granted, added_by)
     VALUES ('DEITY', 'role.get_all', true, '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;

-- role.add
INSERT INTO ledger.permissions(permission_id, description, added_by)
    VALUES ('role.add', 'Add roles', '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions(role_id, permission_id)
    VALUES ('DEITY', 'role.add')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions_log(role_id, permission_id, granted, added_by)
     VALUES ('DEITY', 'role.add', true, '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;

-- role.update
INSERT INTO ledger.permissions(permission_id, description, added_by)
    VALUES ('role.update', 'Update roles', '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions(role_id, permission_id)
    VALUES ('DEITY', 'role.update')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions_log(role_id, permission_id, granted, added_by)
     VALUES ('DEITY', 'role.update', true, '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;

-- role.grant_permission
INSERT INTO ledger.permissions(permission_id, description, added_by)
    VALUES ('role.grant_permission', 'Grant and revoke permissions to roles', '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions(role_id, permission_id)
    VALUES ('DEITY', 'role.grant_permission')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions_log(role_id, permission_id, granted, added_by)
     VALUES ('DEITY', 'role.grant_permission', true, '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;

-- permission.get_all
INSERT INTO ledger.permissions(permission_id, description, added_by)
    VALUES ('permission.get_all', 'Get all permissions', '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions(role_id, permission_id)
    VALUES ('DEITY', 'permission.get_all')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions_log(role_id, permission_id, granted, added_by)
     VALUES ('DEITY', 'permission.get_all', true, '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;

-- permission.add
INSERT INTO ledger.permissions(permission_id, description, added_by)
    VALUES ('permission.add', 'Add permissions', '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions(role_id, permission_id)
    VALUES ('DEITY', 'permission.add')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions_log(role_id, permission_id, granted, added_by)
     VALUES ('DEITY', 'permission.add', true, '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;

-- permission.update
INSERT INTO ledger.permissions(permission_id, description, added_by)
    VALUES ('permission.update', 'Update permissions', '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions(role_id, permission_id)
    VALUES ('DEITY', 'permission.update')
    ON CONFLICT DO NOTHING;
INSERT INTO ledger.role_permissions_log(role_id, permission_id, granted, added_by)
     VALUES ('DEITY', 'permission.update', true, '0000000-0000-0000-0000-000000000000')
    ON CONFLICT DO NOTHING;