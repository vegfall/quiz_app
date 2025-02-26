CREATE TABLE IF NOT EXISTS sessions (
    session_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_key VARCHAR(255) NOT NULL UNIQUE,
    theme VARCHAR(255) NOT NULL,
    number_of_alternatives INT NOT NULL,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS questions (
     question_id BIGINT AUTO_INCREMENT PRIMARY KEY,
     question_key INT NOT NULL,
     question_text VARCHAR(255) NOT NULL,
     session_id BIGINT NOT NULL,
     FOREIGN KEY (session_id) REFERENCES sessions(session_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS alternatives (
    alternative_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    alternative_key INT NOT NULL,
    alternative_text VARCHAR(255) NOT NULL,
    correct BOOLEAN NOT NULL,
    alternative_explanation VARCHAR(255),
    question_id BIGINT NOT NULL,
    FOREIGN KEY (question_id) REFERENCES questions(question_id) ON DELETE CASCADE
);

-- Mock question were provided by OpenAI
-- Can be used if OpenAI doesn't respond
INSERT IGNORE INTO sessions (session_id, session_key, theme, number_of_alternatives, version)
VALUES (1, 'MOCKQUIZ', 'History', 4, 1);

INSERT IGNORE INTO questions (question_key, question_text, session_id) VALUES
    (1, 'In the context of the late Roman Empire, which fiscal reform is most emblematic of Emperor Diocletians efforts to stabilize the economy?', 1);
INSERT IGNORE INTO alternatives (alternative_key, alternative_text, correct, alternative_explanation, question_id)
VALUES (1, 'The Constitutio Antoniniana', FALSE,
        'The Constitutio Antoniniana, issued by Emperor Caracalla, granted citizenship rather than addressing fiscal stabilization.',
        LAST_INSERT_ID()),
       (2, 'The Edict on Maximum Prices', TRUE,
        'This edict was implemented to curb rampant inflation by setting fixed maximum prices for goods and services across the empire.',
        LAST_INSERT_ID()),
       (3, 'The Tetrarchy Implementation', FALSE,
        'The Tetrarchy reorganized imperial rule into a four-man system and was aimed at administrative efficiency, not fiscal reform.',
        LAST_INSERT_ID()),
       (4, 'The Lex Julia', FALSE,
        'The Lex Julia pertains to legal reforms related to citizenship and morality, not to the economic stabilization of the empire.',
        LAST_INSERT_ID());

INSERT INTO questions (question_key, question_text, session_id) VALUES
    (2, 'Which treaty, concluded in 1108, is noted for its attempt to formalize Norman-Byzantine relations, despite ultimately being unfulfilled?', 1);
INSERT INTO alternatives (alternative_key, alternative_text, correct, alternative_explanation, question_id)
VALUES (1,'Treaty of Tordesillas', FALSE,
        'The Treaty of Tordesillas dealt with colonial claims between Spain and Portugal and is unrelated to Norman-Byzantine affairs.',
        LAST_INSERT_ID()),
       (2,'Treaty of Verdun', FALSE,
        'The Treaty of Verdun partitioned the Carolingian Empire in 843, having no connection to Norman-Byzantine relations.',
        LAST_INSERT_ID()),
       (3,'Treaty of Devol', TRUE,
        'Signed in 1108, the Treaty of Devol attempted to subordinate the Norman principalities to Byzantine authority, though it was never fully implemented.',
        LAST_INSERT_ID()),
       (4,'Treaty of Windsor', FALSE,
        'The Treaty of Windsor relates to Anglo-Portuguese relations and does not pertain to Norman-Byzantine history.',
        LAST_INSERT_ID());

INSERT INTO questions (question_key, question_text, session_id) VALUES
    (3,'Which diplomatic event is widely reinterpreted by scholars as marking the inception of the modern state system in Europe?', 1);
INSERT INTO alternatives (alternative_key, alternative_text, correct, alternative_explanation, question_id)
VALUES (1,'The Peace of Westphalia', TRUE,
        'The Peace of Westphalia (1648) is considered a seminal moment in establishing the framework of sovereign nation-states in Europe.',
        LAST_INSERT_ID()),
       (2,'The Treaty of Tordesillas', FALSE,
        'The Treaty of Tordesillas focused on dividing new territories between Spain and Portugal, not on shaping the European state system.',
        LAST_INSERT_ID()),
       (3,'The Congress of Vienna', FALSE,
        'While influential in post-Napoleonic Europe, the Congress of Vienna refined existing structures rather than originating the modern state system.',
        LAST_INSERT_ID()),
       (4,'The Treaty of Utrecht', FALSE,
        'The Treaty of Utrecht ended the War of Spanish Succession and did not lay the foundation for the modern system of sovereign states.',
        LAST_INSERT_ID());

INSERT INTO questions (question_key, question_text, session_id) VALUES
    (4,'Which conference, held in 1944, is considered by historians to have influenced the trajectory of African decolonization by signaling impending reforms in colonial policy?', 1);
INSERT INTO alternatives (alternative_key, alternative_text, correct, alternative_explanation, question_id)
VALUES (1,'Cairo Conference', FALSE,
        'The Cairo Conference primarily addressed Allied strategies in Asia and the Pacific during World War II.',
        LAST_INSERT_ID()),
       (2,'Yalta Conference', FALSE,
        'The Yalta Conference focused on post-war reorganization in Europe and did not directly influence African colonial policy.',
        LAST_INSERT_ID()),
       (3,'Potsdam Conference', FALSE,
        'The Potsdam Conference dealt with issues related to post-war Europe rather than initiating colonial reforms in Africa.',
        LAST_INSERT_ID()),
       (4,'Brazzaville Conference', TRUE,
        'The 1944 Brazzaville Conference signaled potential reforms in French colonial policy, impacting later decolonization movements in Africa.',
        LAST_INSERT_ID());

INSERT INTO questions (question_key, question_text, session_id) VALUES
    (5,'Which historian authored the influential work ''The Global Cold War,'' challenging traditional narratives of the Cold War?', 1);
INSERT INTO alternatives (alternative_key, alternative_text, correct, alternative_explanation, question_id)
VALUES (1,'John Lewis Gaddis', FALSE,
        'John Lewis Gaddis is renowned for his Cold War studies but is not the author of ''The Global Cold War.''',
        LAST_INSERT_ID()),
       (2,'Melvyn P. Leffler', FALSE,
        'Melvyn P. Leffler has contributed significantly to Cold War historiography, though he did not write ''The Global Cold War.''',
        LAST_INSERT_ID()),
       (3,'Odd Arne Westad', TRUE,
        'Odd Arne Westad is the author of ''The Global Cold War,'' which offers a comprehensive reexamination of Cold War dynamics.',
        LAST_INSERT_ID()),
       (4,'Fredrik Logevall', FALSE,
        'While an expert on Cold War history, Fredrik Logevall did not author ''The Global Cold War.''',
        LAST_INSERT_ID());
