DROP INDEX ledger.entries_group_current__uindex;

ALTER TABLE ledger.entries
    ADD CONSTRAINT current_group_entry_u EXCLUDE USING btree
    (group_id WITH =)
    WHERE (current) DEFERRABLE INITIALLY DEFERRED;