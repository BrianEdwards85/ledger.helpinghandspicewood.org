-- :name get-users-permissions-sql :? :*
SELECT role_permissions.permission_id
FROM ledger.user_roles
JOIN ledger.role_permissions ON user_roles.role_id = role_permissions.role_id
WHERE user_roles.enabled
  AND role_permissions.enabled
  AND user_roles.user_id = :user_id