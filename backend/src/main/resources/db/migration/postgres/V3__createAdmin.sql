INSERT INTO roles (id_role, role_name) VALUES
    (2, 'ADMIN');

INSERT INTO users (id_user, email, is_active, is_email_confirmed, login, password)
VALUES
    (2, 'pregosik322@gmail.com', TRUE, TRUE, 'admin', '$2a$10$uHSXYNPqejYiME2slci.heu0Jn.FGScfl.IpHyJJ.9y9445JUp8Dq');

INSERT INTO user_roles (user_id_user, role_id_role) VALUES
    (2, 1);

INSERT INTO user_roles (user_id_user, role_id_role) VALUES
    (2, 2);

INSERT INTO settings (id_setting, font_size, theme, user_id_user) VALUES
    (2, 'MEDIUM', 'DAY', 2);

-- Reset sequences to continue after seeded data
SELECT setval('roles_id_role_seq', (SELECT MAX(id_role) FROM roles));
SELECT setval('users_id_user_seq', (SELECT MAX(id_user) FROM users));
SELECT setval('settings_id_setting_seq', (SELECT MAX(id_setting) FROM settings));
