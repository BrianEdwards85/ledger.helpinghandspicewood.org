ALTER TABLE ledger.user_roles_log DROP CONSTRAINT user_role_log_user_roles__fk;

ALTER TABLE ledger.user_roles_log
    ADD CONSTRAINT user_role_log_user_roles__fk FOREIGN KEY (role_id, user_id)
    REFERENCES ledger.user_roles (role_id, user_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;