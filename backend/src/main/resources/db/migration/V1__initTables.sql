CREATE TABLE `fast_reading_questions` (
                                          `id_fast_reading_question` bigint(20) NOT NULL,
                                          `answer` bit(1) NOT NULL,
                                          `question` longtext NOT NULL,
                                          `fast_reading_text_id_fast_reading_text` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `fast_reading_results`
--

CREATE TABLE `fast_reading_results` (
                                        `id_fast_reading_result` bigint(20) NOT NULL,
                                        `level` varchar(255) NOT NULL,
                                        `score` double NOT NULL,
                                        `start_time` datetime NOT NULL,
                                        `time` float NOT NULL,
                                        `type` varchar(255) NOT NULL,
                                        `text_id_fast_reading_text` bigint(20) DEFAULT NULL,
                                        `user_id_user` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `fast_reading_texts`
--

CREATE TABLE `fast_reading_texts` (
                                      `id_fast_reading_text` bigint(20) NOT NULL,
                                      `level` varchar(255) NOT NULL,
                                      `text` longtext NOT NULL,
                                      `title` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `fast_writing_courses`
--

CREATE TABLE `fast_writing_courses` (
                                        `id_fast_writing_course` bigint(20) NOT NULL,
                                        `number_of_attempts` int(11) NOT NULL,
                                        `score` double NOT NULL,
                                        `start_time` datetime NOT NULL,
                                        `time` float NOT NULL,
                                        `fast_writing_lesson_id_fast_writing_lesson` bigint(20) DEFAULT NULL,
                                        `user_id_user` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `fast_writing_lessons`
--

CREATE TABLE `fast_writing_lessons` (
                                        `id_fast_writing_lesson` bigint(20) NOT NULL,
                                        `generated_characters` varchar(10) NOT NULL,
                                        `name` varchar(255) NOT NULL,
                                        `number` int(11) NOT NULL,
                                        `module_id_fast_writing_module` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `fast_writing_modules`
--

CREATE TABLE `fast_writing_modules` (
                                        `id_fast_writing_module` bigint(20) NOT NULL,
                                        `name` varchar(255) NOT NULL,
                                        `number` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `fast_writing_tests`
--

CREATE TABLE `fast_writing_tests` (
                                      `id_fast_writing_test` bigint(20) NOT NULL,
                                      `score` double NOT NULL,
                                      `start_time` datetime NOT NULL,
                                      `time` float NOT NULL,
                                      `typed_text` longtext NOT NULL,
                                      `text_id_fast_writing_text` bigint(20) DEFAULT NULL,
                                      `user_id_user` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `fast_writing_texts`
--

CREATE TABLE `fast_writing_texts` (
                                      `id_fast_writing_text` bigint(20) NOT NULL,
                                      `level` int(11) NOT NULL,
                                      `text` longtext NOT NULL,
                                      `title` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `memorizings`
--

CREATE TABLE `memorizings` (
                               `id_memorizing` bigint(20) NOT NULL,
                               `level` varchar(255) NOT NULL,
                               `score` bigint(20) NOT NULL,
                               `start_time` datetime NOT NULL,
                               `type` varchar(255) NOT NULL,
                               `user_id_user` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `memorizings`
--

INSERT INTO `memorizings` (`id_memorizing`, `level`, `score`, `start_time`, `type`, `user_id_user`) VALUES
    (1, 'ADVANCED', 0, '2022-12-10 15:42:13', 'MEMORY', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `reports`
--

CREATE TABLE `reports` (
                           `id_report` bigint(20) NOT NULL,
                           `active` bit(1) NOT NULL,
                           `date` datetime NOT NULL,
                           `email` varchar(45) DEFAULT NULL,
                           `text` longtext DEFAULT NULL,
                           `title` varchar(45) DEFAULT NULL,
                           `user_id_user` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `roles`
--

CREATE TABLE `roles` (
                         `id_role` bigint(20) NOT NULL,
                         `role_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `roles`
--

INSERT INTO `roles` (`id_role`, `role_name`) VALUES
    (1, 'USER');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `settings`
--

CREATE TABLE `settings` (
                            `id_setting` bigint(20) NOT NULL,
                            `font_size` varchar(255) NOT NULL,
                            `theme` varchar(255) NOT NULL,
                            `user_id_user` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `settings`
--

INSERT INTO `settings` (`id_setting`, `font_size`, `theme`, `user_id_user`) VALUES
    (1, 'MEDIUM', 'DAY', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
                         `id_user` bigint(20) NOT NULL,
                         `email` varchar(45) DEFAULT NULL,
                         `is_active` bit(1) DEFAULT NULL,
                         `is_email_confirmed` bit(1) DEFAULT NULL,
                         `login` varchar(45) DEFAULT NULL,
                         `password` varchar(120) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`id_user`, `email`, `is_active`, `is_email_confirmed`, `login`, `password`) VALUES
    (1, 'string@email.com', b'1', b'0', 'string', '$2a$10$rbqb1J3Zfv3Ad25kMhtfDe0eD5uvokEjC3HTgbRW.oVnWR9gKDuM2');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_roles`
--

CREATE TABLE `user_roles` (
                              `user_id_user` bigint(20) NOT NULL,
                              `role_id_role` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `user_roles`
--

INSERT INTO `user_roles` (`user_id_user`, `role_id_role`) VALUES
    (1, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `validation_code`
--

CREATE TABLE `validation_code` (
                                   `id_validation_code` bigint(20) NOT NULL,
                                   `code` varchar(255) DEFAULT NULL,
                                   `purpose` varchar(255) NOT NULL,
                                   `was_used` bit(1) NOT NULL,
                                   `user_id_user` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `validation_code`
--

INSERT INTO `validation_code` (`id_validation_code`, `code`, `purpose`, `was_used`, `user_id_user`) VALUES
    (1, '79818', 'EMAIL_VERIFICATION', b'0', 1);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `fast_reading_questions`
--
ALTER TABLE `fast_reading_questions`
    ADD PRIMARY KEY (`id_fast_reading_question`),
  ADD KEY `FK56y7qrbtbvcyxsnyejxrvms53` (`fast_reading_text_id_fast_reading_text`);

--
-- Indeksy dla tabeli `fast_reading_results`
--
ALTER TABLE `fast_reading_results`
    ADD PRIMARY KEY (`id_fast_reading_result`),
  ADD KEY `FKlsdqhp6qx1u8ok8s4pn8xrraf` (`text_id_fast_reading_text`),
  ADD KEY `FKatfbtbp9qrytpy74f8disqnsm` (`user_id_user`);

--
-- Indeksy dla tabeli `fast_reading_texts`
--
ALTER TABLE `fast_reading_texts`
    ADD PRIMARY KEY (`id_fast_reading_text`);

--
-- Indeksy dla tabeli `fast_writing_courses`
--
ALTER TABLE `fast_writing_courses`
    ADD PRIMARY KEY (`id_fast_writing_course`),
  ADD KEY `FKarg8j9ud4rua7ogxk48rg5sr3` (`fast_writing_lesson_id_fast_writing_lesson`),
  ADD KEY `FKj60xosqif7cxxo5w3c3wrlpxy` (`user_id_user`);

--
-- Indeksy dla tabeli `fast_writing_lessons`
--
ALTER TABLE `fast_writing_lessons`
    ADD PRIMARY KEY (`id_fast_writing_lesson`),
  ADD UNIQUE KEY `UK_e9i1sukjxbilyod8qcnqnnfas` (`generated_characters`),
  ADD UNIQUE KEY `UK_jvsknkl0pv38gqb8asxw7a1td` (`name`),
  ADD UNIQUE KEY `UK_spvhw1oovsv2mjws77yuuwtrr` (`number`),
  ADD KEY `FKbhupboe6wnwadm77ii8d8ywv1` (`module_id_fast_writing_module`);

--
-- Indeksy dla tabeli `fast_writing_modules`
--
ALTER TABLE `fast_writing_modules`
    ADD PRIMARY KEY (`id_fast_writing_module`);

--
-- Indeksy dla tabeli `fast_writing_tests`
--
ALTER TABLE `fast_writing_tests`
    ADD PRIMARY KEY (`id_fast_writing_test`),
  ADD KEY `FKdlfm2ug5h08o20q6barhro130` (`text_id_fast_writing_text`),
  ADD KEY `FKm89jf0pj29uuxx9fabxso24mu` (`user_id_user`);

--
-- Indeksy dla tabeli `fast_writing_texts`
--
ALTER TABLE `fast_writing_texts`
    ADD PRIMARY KEY (`id_fast_writing_text`);

--
-- Indeksy dla tabeli `memorizings`
--
ALTER TABLE `memorizings`
    ADD PRIMARY KEY (`id_memorizing`),
  ADD KEY `FKja9h6aeruo9cuiv91p65txkvn` (`user_id_user`);

--
-- Indeksy dla tabeli `reports`
--
ALTER TABLE `reports`
    ADD PRIMARY KEY (`id_report`),
  ADD KEY `FK8m4ajtxo0y9bmeee0iai2krb1` (`user_id_user`);

--
-- Indeksy dla tabeli `roles`
--
ALTER TABLE `roles`
    ADD PRIMARY KEY (`id_role`);

--
-- Indeksy dla tabeli `settings`
--
ALTER TABLE `settings`
    ADD PRIMARY KEY (`id_setting`),
  ADD KEY `FKd2apmg99sqi9qgus2a468ts4` (`user_id_user`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
    ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD UNIQUE KEY `UK_ow0gan20590jrb00upg3va2fn` (`login`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD UNIQUE KEY `UKow0gan20590jrb00upg3va2fn` (`login`);

--
-- Indeksy dla tabeli `user_roles`
--
ALTER TABLE `user_roles`
    ADD PRIMARY KEY (`user_id_user`,`role_id_role`),
  ADD KEY `FK44ncdoscjlvedib8598o0qx8u` (`role_id_role`);

--
-- Indeksy dla tabeli `validation_code`
--
ALTER TABLE `validation_code`
    ADD PRIMARY KEY (`id_validation_code`),
  ADD KEY `FK1jqygbhrtisf174wb1flriepj` (`user_id_user`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `fast_reading_questions`
--
ALTER TABLE `fast_reading_questions`
    MODIFY `id_fast_reading_question` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `fast_reading_results`
--
ALTER TABLE `fast_reading_results`
    MODIFY `id_fast_reading_result` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `fast_reading_texts`
--
ALTER TABLE `fast_reading_texts`
    MODIFY `id_fast_reading_text` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `fast_writing_courses`
--
ALTER TABLE `fast_writing_courses`
    MODIFY `id_fast_writing_course` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `fast_writing_lessons`
--
ALTER TABLE `fast_writing_lessons`
    MODIFY `id_fast_writing_lesson` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `fast_writing_modules`
--
ALTER TABLE `fast_writing_modules`
    MODIFY `id_fast_writing_module` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `fast_writing_tests`
--
ALTER TABLE `fast_writing_tests`
    MODIFY `id_fast_writing_test` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `fast_writing_texts`
--
ALTER TABLE `fast_writing_texts`
    MODIFY `id_fast_writing_text` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `memorizings`
--
ALTER TABLE `memorizings`
    MODIFY `id_memorizing` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT dla tabeli `reports`
--
ALTER TABLE `reports`
    MODIFY `id_report` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `roles`
--
ALTER TABLE `roles`
    MODIFY `id_role` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT dla tabeli `settings`
--
ALTER TABLE `settings`
    MODIFY `id_setting` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
    MODIFY `id_user` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT dla tabeli `validation_code`
--
ALTER TABLE `validation_code`
    MODIFY `id_validation_code` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `fast_reading_questions`
--
ALTER TABLE `fast_reading_questions`
    ADD CONSTRAINT `FK56y7qrbtbvcyxsnyejxrvms53` FOREIGN KEY (`fast_reading_text_id_fast_reading_text`) REFERENCES `fast_reading_texts` (`id_fast_reading_text`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `fast_reading_results`
--
ALTER TABLE `fast_reading_results`
    ADD CONSTRAINT `FKatfbtbp9qrytpy74f8disqnsm` FOREIGN KEY (`user_id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKlsdqhp6qx1u8ok8s4pn8xrraf` FOREIGN KEY (`text_id_fast_reading_text`) REFERENCES `fast_reading_texts` (`id_fast_reading_text`);

--
-- Ograniczenia dla tabeli `fast_writing_courses`
--
ALTER TABLE `fast_writing_courses`
    ADD CONSTRAINT `FKarg8j9ud4rua7ogxk48rg5sr3` FOREIGN KEY (`fast_writing_lesson_id_fast_writing_lesson`) REFERENCES `fast_writing_lessons` (`id_fast_writing_lesson`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKj60xosqif7cxxo5w3c3wrlpxy` FOREIGN KEY (`user_id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `fast_writing_lessons`
--
ALTER TABLE `fast_writing_lessons`
    ADD CONSTRAINT `FKbhupboe6wnwadm77ii8d8ywv1` FOREIGN KEY (`module_id_fast_writing_module`) REFERENCES `fast_writing_modules` (`id_fast_writing_module`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `fast_writing_tests`
--
ALTER TABLE `fast_writing_tests`
    ADD CONSTRAINT `FKdlfm2ug5h08o20q6barhro130` FOREIGN KEY (`text_id_fast_writing_text`) REFERENCES `fast_writing_texts` (`id_fast_writing_text`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKm89jf0pj29uuxx9fabxso24mu` FOREIGN KEY (`user_id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `memorizings`
--
ALTER TABLE `memorizings`
    ADD CONSTRAINT `FKja9h6aeruo9cuiv91p65txkvn` FOREIGN KEY (`user_id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `reports`
--
ALTER TABLE `reports`
    ADD CONSTRAINT `FK8m4ajtxo0y9bmeee0iai2krb1` FOREIGN KEY (`user_id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `settings`
--
ALTER TABLE `settings`
    ADD CONSTRAINT `FKd2apmg99sqi9qgus2a468ts4` FOREIGN KEY (`user_id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `user_roles`
--
ALTER TABLE `user_roles`
    ADD CONSTRAINT `FK44ncdoscjlvedib8598o0qx8u` FOREIGN KEY (`role_id_role`) REFERENCES `roles` (`id_role`),
  ADD CONSTRAINT `FKmyufnukpwujoa3s35tvisg94q` FOREIGN KEY (`user_id_user`) REFERENCES `users` (`id_user`);

--
-- Ograniczenia dla tabeli `validation_code`
--
ALTER TABLE `validation_code`
    ADD CONSTRAINT `FK1jqygbhrtisf174wb1flriepj` FOREIGN KEY (`user_id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE;