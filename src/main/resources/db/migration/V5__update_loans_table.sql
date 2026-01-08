ALTER TABLE loans DROP COLUMN book_id;

ALTER TABLE loans ADD COLUMN book_copy_id BIGINT NOT NULL;

ALTER TABLE loans
    ADD CONSTRAINT fk_loans_book_copy
        FOREIGN KEY (book_copy_id) REFERENCES book_copies(id);