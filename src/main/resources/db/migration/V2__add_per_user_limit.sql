ALTER TABLE flash_sales
ADD COLUMN per_user_limit INT NOT NULL DEFAULT 1;

ALTER TABLE order_items
    ADD COLUMN flash_sale_id BIGINT NULL,
ADD FOREIGN KEY (flash_sale_id) REFERENCES flash_sales(id);