--
-- Baza danych: `brainutrain`
--

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

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `reading_questions`
--

CREATE TABLE `reading_questions` (
                                     `id_reading_question` bigint(20) NOT NULL,
                                     `answer` bit(1) NOT NULL,
                                     `question` longtext NOT NULL,
                                     `reading_text_id_reading_text` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `reading_results`
--

CREATE TABLE `reading_results` (
                                   `id_reading_result` bigint(20) NOT NULL,
                                   `level` varchar(255) NOT NULL,
                                   `score` double NOT NULL,
                                   `start_time` datetime NOT NULL,
                                   `time` float NOT NULL,
                                   `type` varchar(255) NOT NULL,
                                   `text_id_reading_text` bigint(20) DEFAULT NULL,
                                   `user_id_user` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `reading_texts`
--

CREATE TABLE `reading_texts` (
                                 `id_reading_text` bigint(20) NOT NULL,
                                 `level` varchar(255) NOT NULL,
                                 `text` longtext NOT NULL,
                                 `title` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_roles`
--

CREATE TABLE `user_roles` (
                              `user_id_user` bigint(20) NOT NULL,
                              `role_id_role` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `validation_codes`
--

CREATE TABLE `validation_codes` (
                                    `id_validation_code` bigint(20) NOT NULL,
                                    `code` varchar(255) DEFAULT NULL,
                                    `purpose` varchar(255) NOT NULL,
                                    `was_used` bit(1) NOT NULL,
                                    `user_id_user` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `writing_lessons`
--

CREATE TABLE `writing_lessons` (
                                   `id_writing_lesson` bigint(20) NOT NULL,
                                   `generated_characters` varchar(10) NOT NULL,
                                   `name` varchar(255) NOT NULL,
                                   `number` int(11) NOT NULL,
                                   `module_id_writing_module` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `writing_lesson_results`
--

CREATE TABLE `writing_lesson_results` (
                                          `id_writing_lesson_result` bigint(20) NOT NULL,
                                          `number_of_attempts` int(11) NOT NULL,
                                          `number_of_typed_letters` int(11) NOT NULL,
                                          `score` double NOT NULL,
                                          `start_time` datetime NOT NULL,
                                          `time` float NOT NULL,
                                          `user_id_user` bigint(20) DEFAULT NULL,
                                          `writing_lesson_id_writing_lesson` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `writing_modules`
--

CREATE TABLE `writing_modules` (
                                   `id_writing_module` bigint(20) NOT NULL,
                                   `name` varchar(255) NOT NULL,
                                   `number` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `writing_texts`
--

CREATE TABLE `writing_texts` (
                                 `id_writing_text` bigint(20) NOT NULL,
                                 `level` varchar(255) NOT NULL,
                                 `text` longtext NOT NULL,
                                 `title` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `writing_text_results`
--

CREATE TABLE `writing_text_results` (
                                        `id_writing_text_result` bigint(20) NOT NULL,
                                        `score` double NOT NULL,
                                        `start_time` datetime NOT NULL,
                                        `time` float NOT NULL,
                                        `typed_text` longtext NOT NULL,
                                        `text_id_writing_text` bigint(20) DEFAULT NULL,
                                        `user_id_user` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `memorizings`
--
ALTER TABLE `memorizings`
    ADD PRIMARY KEY (`id_memorizing`),
  ADD KEY `FKja9h6aeruo9cuiv91p65txkvn` (`user_id_user`);

--
-- Indeksy dla tabeli `reading_questions`
--
ALTER TABLE `reading_questions`
    ADD PRIMARY KEY (`id_reading_question`),
  ADD KEY `FKcf0cfux397yj1gv7ow1bq8uxk` (`reading_text_id_reading_text`);

--
-- Indeksy dla tabeli `reading_results`
--
ALTER TABLE `reading_results`
    ADD PRIMARY KEY (`id_reading_result`),
  ADD KEY `FK4s3k4rew922c4ighkpybu61rw` (`text_id_reading_text`),
  ADD KEY `FK7obvde87o5roxisq5f4ftpbdi` (`user_id_user`);

--
-- Indeksy dla tabeli `reading_texts`
--
ALTER TABLE `reading_texts`
    ADD PRIMARY KEY (`id_reading_text`);

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
  ADD UNIQUE KEY `UK_ow0gan20590jrb00upg3va2fn` (`login`);

--
-- Indeksy dla tabeli `user_roles`
--
ALTER TABLE `user_roles`
    ADD PRIMARY KEY (`user_id_user`,`role_id_role`),
  ADD KEY `FK44ncdoscjlvedib8598o0qx8u` (`role_id_role`);

--
-- Indeksy dla tabeli `validation_codes`
--
ALTER TABLE `validation_codes`
    ADD PRIMARY KEY (`id_validation_code`),
  ADD KEY `FKn3qn2t9lce2mu3p3e6ifhxt6c` (`user_id_user`);

--
-- Indeksy dla tabeli `writing_lessons`
--
ALTER TABLE `writing_lessons`
    ADD PRIMARY KEY (`id_writing_lesson`),
  ADD UNIQUE KEY `UK_3rwgbgk0g1tnbglj8sf5hu3x` (`generated_characters`),
  ADD UNIQUE KEY `UK_nm7tghg0lmvmybc3b6e8wi8sb` (`name`),
  ADD UNIQUE KEY `UK_bmrtbhng3snhti91hx84potp1` (`number`),
  ADD KEY `FKah0bcubj0lo9314okq4hmexep` (`module_id_writing_module`);

--
-- Indeksy dla tabeli `writing_lesson_results`
--
ALTER TABLE `writing_lesson_results`
    ADD PRIMARY KEY (`id_writing_lesson_result`),
  ADD KEY `FKl5v7feoc8nae2oah27s2cs2st` (`user_id_user`),
  ADD KEY `FKe9pb7b8chw1ab1owuns9e1ypx` (`writing_lesson_id_writing_lesson`);

--
-- Indeksy dla tabeli `writing_modules`
--
ALTER TABLE `writing_modules`
    ADD PRIMARY KEY (`id_writing_module`);

--
-- Indeksy dla tabeli `writing_texts`
--
ALTER TABLE `writing_texts`
    ADD PRIMARY KEY (`id_writing_text`);

--
-- Indeksy dla tabeli `writing_text_results`
--
ALTER TABLE `writing_text_results`
    ADD PRIMARY KEY (`id_writing_text_result`),
  ADD KEY `FKa5qt2at6gqwx3v059qeqm2qja` (`text_id_writing_text`),
  ADD KEY `FKpi3yxf515me9yvlb6mohhcmq2` (`user_id_user`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `memorizings`
--
ALTER TABLE `memorizings`
    MODIFY `id_memorizing` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `reading_questions`
--
ALTER TABLE `reading_questions`
    MODIFY `id_reading_question` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `reading_results`
--
ALTER TABLE `reading_results`
    MODIFY `id_reading_result` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `reading_texts`
--
ALTER TABLE `reading_texts`
    MODIFY `id_reading_text` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `reports`
--
ALTER TABLE `reports`
    MODIFY `id_report` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `roles`
--
ALTER TABLE `roles`
    MODIFY `id_role` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `settings`
--
ALTER TABLE `settings`
    MODIFY `id_setting` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
    MODIFY `id_user` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `validation_codes`
--
ALTER TABLE `validation_codes`
    MODIFY `id_validation_code` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `writing_lessons`
--
ALTER TABLE `writing_lessons`
    MODIFY `id_writing_lesson` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `writing_lesson_results`
--
ALTER TABLE `writing_lesson_results`
    MODIFY `id_writing_lesson_result` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `writing_modules`
--
ALTER TABLE `writing_modules`
    MODIFY `id_writing_module` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `writing_texts`
--
ALTER TABLE `writing_texts`
    MODIFY `id_writing_text` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `writing_text_results`
--
ALTER TABLE `writing_text_results`
    MODIFY `id_writing_text_result` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `memorizings`
--
ALTER TABLE `memorizings`
    ADD CONSTRAINT `FKja9h6aeruo9cuiv91p65txkvn` FOREIGN KEY (`user_id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `reading_questions`
--
ALTER TABLE `reading_questions`
    ADD CONSTRAINT `FKcf0cfux397yj1gv7ow1bq8uxk` FOREIGN KEY (`reading_text_id_reading_text`) REFERENCES `reading_texts` (`id_reading_text`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `reading_results`
--
ALTER TABLE `reading_results`
    ADD CONSTRAINT `FK4s3k4rew922c4ighkpybu61rw` FOREIGN KEY (`text_id_reading_text`) REFERENCES `reading_texts` (`id_reading_text`),
  ADD CONSTRAINT `FK7obvde87o5roxisq5f4ftpbdi` FOREIGN KEY (`user_id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE;

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
-- Ograniczenia dla tabeli `validation_codes`
--
ALTER TABLE `validation_codes`
    ADD CONSTRAINT `FKn3qn2t9lce2mu3p3e6ifhxt6c` FOREIGN KEY (`user_id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `writing_lessons`
--
ALTER TABLE `writing_lessons`
    ADD CONSTRAINT `FKah0bcubj0lo9314okq4hmexep` FOREIGN KEY (`module_id_writing_module`) REFERENCES `writing_modules` (`id_writing_module`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `writing_lesson_results`
--
ALTER TABLE `writing_lesson_results`
    ADD CONSTRAINT `FKe9pb7b8chw1ab1owuns9e1ypx` FOREIGN KEY (`writing_lesson_id_writing_lesson`) REFERENCES `writing_lessons` (`id_writing_lesson`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKl5v7feoc8nae2oah27s2cs2st` FOREIGN KEY (`user_id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `writing_text_results`
--
ALTER TABLE `writing_text_results`
    ADD CONSTRAINT `FKa5qt2at6gqwx3v059qeqm2qja` FOREIGN KEY (`text_id_writing_text`) REFERENCES `writing_texts` (`id_writing_text`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKpi3yxf515me9yvlb6mohhcmq2` FOREIGN KEY (`user_id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE;