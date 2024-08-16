use shopyy;

insert into role (name) values
('ROLE_ADMIN'),('ROLE_USER');

select * from role;

insert into user (full_name, username, password, email, phone, gender, dob, image) values
('Nguyen Ba Minh Dao', 'daonguyenadmin', '$2a$10$m2OGQ.J050IpVbQrzHb5hOnFJTX7ZHHgPZCjgN1436WnspX1cu6Wq', 'daonguyen@gmail.com',
'0908983906', 'Nam', '1995-01-01', 'avatar1.png'), -- password: daonguyenadmin
('Pham Hoang An', 'hoangan1109', '$2a$10$JDTZeIebeVa8KyslIOnJLOBFWsytGcEFF8veDGGen9MugPJN9OMiu', 'hoangan1109@gmail.com',
'0785768416', 'Nam', '2001-09-11', 'avatar1.png'), -- password: 123456
('Nguyen Huu Tri', 'huutri0311','$2a$10$JDTZeIebeVa8KyslIOnJLOBFWsytGcEFF8veDGGen9MugPJN9OMiu', 'huutri0311@gmail.com',
'0123456789', 'Nam', '2003-11-03', 'avatar1.png'); -- password: 123456

select * from user;

insert into users_roles (user_id, role_id) values
(1, 1)
,(1, 2)
,(2, 2)
,(3, 2);

INSERT INTO category (name) VALUES
("áo "),
("quần");

INSERT INTO sub_category (name, category_id) VALUES
("áo phông", 1),
("áo sơ mi", 1),
("áo khoác", 1),
("quần dùi", 2),
("quần tây", 2),
("quần Jeans", 2);

INSERT INTO size (size_name) VALUES
("XS"),
("S"),
("M"),
("L"),
("XL"),
("XXL");

INSERT INTO color (color_name) VALUES
("xanh"),
("đỏ"),
("trắng"),
("đen");

INSERT INTO product (name, price, description, quantity, avatar, sub_category_id) VALUES
("Áo phông nam", 150000, "Áo phông nam thời trang", 100, "avatar1.jpg", 1),
("Áo sơ mi nữ", 250000, "Áo sơ mi nữ công sở", 50, "avatar2.jpg", 2),
("Áo khoác gió", 350000, "Áo khoác gió chống nước", 75, "avatar3.jpg", 3),
("Quần dùi nam", 200000, "Quần dùi nam thể thao", 80, "avatar4.jpg", 4),
("Quần tây nữ", 300000, "Quần tây nữ công sở", 60, "avatar5.jpg", 5),
("Quần Jeans nam", 400000, "Quần Jeans nam trẻ trung", 90, "avatar6.jpg", 6),
("Áo hoodie nam", 180000, "Áo hoodie nam ấm áp", 120, "avatar7.jpg", 1),
("Áo thun nữ", 220000, "Áo thun nữ thoáng mát", 70, "avatar8.jpg", 2),
("Áo vest nam", 450000, "Áo vest nam lịch lãm", 40, "avatar9.jpg", 3),
("Quần shorts nam", 170000, "Quần shorts nam tiện dụng", 110, "avatar10.jpg", 4),
("Quần tây nam", 320000, "Quần tây nam lịch lãm", 65, "avatar11.jpg", 5),
("Quần Jeans nữ", 420000, "Quần Jeans nữ phong cách", 85, "avatar12.jpg", 6);

INSERT INTO product_color (product_id, color_id) VALUES
(1, 1),
(1, 3), 
(2, 2),
(2, 3), 
(3, 1), 
(3, 4),
(4, 1), 
(4, 4), 
(5, 3),
(5, 4),
(6, 1),
(6, 4);

INSERT INTO product_size (product_id, size_id) VALUES
(1, 2), 
(1, 3), 
(2, 3), 
(2, 4),
(3, 3), 
(4, 3), 
(4, 4), 
(5, 4), 
(5, 5), 
(6, 4), 
(6, 5);