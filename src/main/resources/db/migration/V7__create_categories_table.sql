CREATE TABLE categories
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);


ALTER TABLE books
    ADD COLUMN category_id BIGINT;


ALTER TABLE books
    ADD CONSTRAINT fk_books_category
        FOREIGN KEY (category_id) REFERENCES categories (id);