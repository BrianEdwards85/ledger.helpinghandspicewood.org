-- :name get-all-users-sql :? :*
SELECT users.id, users.name, users.added_on, users.added_by, users.removed_on, users.removed_by, user_emails.email
FROM ledger.users
JOIN ledger.user_emails ON users.id = user_emails.user_id AND user_emails.principal
WHERE :archived OR users.removed_by IS NULL

-- :name get-user-by-email-sql :? :1
SELECT users.id, users.name, users.added_on, users.added_by, user_emails.email
FROM ledger.users
JOIN ledger.user_emails ON users.id = user_emails.user_id
WHERE users.removed_by IS NULL
AND user_emails.email = :email

-- :name get-user-emails-sql :? :*
SELECT user_emails.email, user_emails.principal
FROM ledger.user_emails
WHERE user_emails.user_id = :user_id

-- :name get-user-permissions-by-email-sql :? :*
SELECT role_permissions.permission_id
FROM ledger.user_roles
JOIN ledger.role_permissions ON user_roles.role_id = role_permissions.role_id
JOIN ledger.user_emails on user_roles.user_id = user_emails.user_id
WHERE user_roles.enabled
  AND role_permissions.enabled
  AND user_emails.email = :email

-- :name get-user-by-id-sql :? :1
SELECT users.id, users.name, users.added_on, users.added_by, user_emails.email
FROM ledger.users
JOIN ledger.user_emails ON users.id = user_emails.user_id AND user_emails.principal
WHERE users.id = :id
