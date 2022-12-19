INSERT INTO `roles` (`id_role`, `role_name`) VALUES
    (2, 'ADMIN');


INSERT INTO `users` (`id_user`, `email`, `is_active`, `is_email_confirmed`, `login`, `password`)
VALUES
(2, 'pregosik322@gmail.com', b'1', b'1', 'admin', '$2a$10$rbqb1J3Zfv3Ad25kMhtfDe0eD5uvokEjC3HTgbRW.oVnWR9gKDuM2');

INSERT INTO `user_roles` (`user_id_user`, `role_id_role`) VALUES
    (2, 1);

INSERT INTO `user_roles` (`user_id_user`, `role_id_role`) VALUES
    (2, 2);

INSERT INTO `settings` (`id_setting`, `font_size`, `theme`, `user_id_user`) VALUES
    (2, 'MEDIUM', 'DAY', 2);
