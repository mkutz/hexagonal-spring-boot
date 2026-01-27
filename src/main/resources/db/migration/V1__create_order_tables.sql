CREATE TABLE orders (
    id UUID PRIMARY KEY,
    status VARCHAR(50) NOT NULL
);

CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    article_id UUID NOT NULL,
    article_name VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    unit_price_amount DECIMAL(19, 4) NOT NULL CHECK (unit_price_amount >= 0),
    unit_price_currency VARCHAR(3) NOT NULL
);

CREATE INDEX idx_order_items_order_id ON order_items(order_id);
