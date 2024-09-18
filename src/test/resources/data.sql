--CREATE TABLE `cache_table` (
--  `cache_key` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
--  `cache_value` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
--  `expired` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
--) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE oauth2_authorized_client (
    client_registration_id VARCHAR(100) NOT NULL,
    principal_name VARCHAR(200) NOT NULL,
    access_token_type VARCHAR(100),
    access_token_value BLOB NOT NULL,
    access_token_issued_at TIMESTAMP NULL,
    access_token_expires_at TIMESTAMP NULL,
    access_token_scopes VARCHAR(1000),
    refresh_token_value BLOB,
    refresh_token_issued_at TIMESTAMP NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (client_registration_id, principal_name)
);

create table users(
	username varchar_ignorecase(50) not null primary key,
	password varchar_ignorecase(500) not null,
	enabled boolean not null
);

create table authorities (
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);

create table groups (
	id bigint generated by default as identity(start with 0) primary key,
	group_name varchar(50) not null
);

create table group_authorities (
	group_id bigint not null,
	authority varchar(50) not null,
	constraint fk_group_authorities_group foreign key(group_id) references groups(id)
);

create table group_members (
	id bigint generated by default as identity(start with 0) primary key,
	username varchar(50) not null,
	group_id bigint not null,
	constraint fk_group_members_group foreign key(group_id) references groups(id)
);

create table persistent_logins (
    username varchar(64) not null,
    series varchar(64) primary key,
    token varchar(64) not null,
    last_used timestamp not null
);




CREATE TABLE acl_sid (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    principal BOOLEAN NOT NULL,
    sid VARCHAR(100) NOT NULL,
    UNIQUE KEY unique_acl_sid (sid, principal)
) ENGINE=InnoDB;

CREATE TABLE acl_class (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    class VARCHAR(100) NOT NULL,
    UNIQUE KEY uk_acl_class (class)
) ENGINE=InnoDB;

CREATE TABLE acl_object_identity (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    object_id_class BIGINT UNSIGNED NOT NULL,
    object_id_identity VARCHAR(36) NOT NULL,
    parent_object BIGINT UNSIGNED,
    owner_sid BIGINT UNSIGNED,
    entries_inheriting BOOLEAN NOT NULL,
    UNIQUE KEY uk_acl_object_identity (object_id_class, object_id_identity),
    CONSTRAINT fk_acl_object_identity_parent FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id),
    CONSTRAINT fk_acl_object_identity_class FOREIGN KEY (object_id_class) REFERENCES acl_class (id),
    CONSTRAINT fk_acl_object_identity_owner FOREIGN KEY (owner_sid) REFERENCES acl_sid (id)
) ENGINE=InnoDB;

CREATE TABLE acl_entry (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    acl_object_identity BIGINT UNSIGNED NOT NULL,
    ace_order INTEGER NOT NULL,
    sid BIGINT UNSIGNED NOT NULL,
    mask INTEGER UNSIGNED NOT NULL,
    granting BOOLEAN NOT NULL,
    audit_success BOOLEAN NOT NULL,
    audit_failure BOOLEAN NOT NULL,
    UNIQUE KEY unique_acl_entry (acl_object_identity, ace_order),
    CONSTRAINT fk_acl_entry_object FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id),
    CONSTRAINT fk_acl_entry_acl FOREIGN KEY (sid) REFERENCES acl_sid (id)
) ENGINE=InnoDB;





INSERT INTO oauth2_client
(client_id, client_secret, client_name, registration_id, redirect_uri)
VALUES('1092064151256-3tivm1do87o4bhjgin48bpsq8mkr2tir.apps.googleusercontent.com', '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',  'google', 'google', '{baseUrl}/{action}/oauth2/code/{registrationId}');

INSERT INTO oauth2_client_scope
(client_id, `scope`)
VALUES(1, 'profile');

INSERT INTO oauth2_client_scope
(client_id, `scope`)
VALUES(1, 'email');

INSERT INTO oauth2_client
( authorization_grant_type, authorization_uri, client_id,
 client_name, client_secret,
 redirect_uri, registration_id, token_uri, user_info_uri, user_name_attribute_name)
VALUES
('authorization_code', 'https://kauth.kakao.com/oauth/authorize', '5fe862057d5ccb87d77b835241b62b9a',
'kakao', 'xJV6P0ZbNzPnbAYW2uffdGCH1ZoVJngu',
'{baseUrl}/{action}/oauth2/code/{registrationId}', 'kakao', 'https://kauth.kakao.com/oauth/token', 'https://kapi.kakao.com/v2/user/me', 'id')

INSERT INTO oauth2_client_scope
(client_id, `scope`)
VALUES(2, 'profile_nickname');

INSERT INTO oauth2_client_scope
(client_id, `scope`)
VALUES(2, 'profile_image');

INSERT INTO oauth2_client_scope
(client_id, `scope`)
VALUES(2, 'account_email');


INSERT INTO authorities (authority) VALUES('ROLE_ADMIN');
INSERT INTO authorities (authority) VALUES('ROLE_DEVELOPER');
INSERT INTO authorities (authority) VALUES('ROLE_MANAGER');
INSERT INTO authorities (authority) VALUES('ROLE_OPERATOR');
INSERT INTO authorities (authority) VALUES('ROLE_USER');
INSERT INTO authorities (authority) VALUES('ROLE_GUEST');

-- INSERT INTO user_detail (account_locked, admin_yn, email_verified, failed_login_attempts, image_url, modify_dtime, provider, provider_id, recommend_code, reg_dtime, user_id, user_name, user_pwd, user_status, my_recommend_code, create_at, expired_at, update_at, version) VALUES('N', 1, 0, NULL, '2024-08-19 06:23:20', 'local', NULL, 'N', '2024-08-17 02:28:57', 'admin', 'admin', '{noop}admin', 'NORMAL', NULL, '2024-09-05 14:24:13', NULL, '2024-09-05 14:24:13', 0);

INSERT INTO user_detail (user_id, user_name, user_pwd, account_locked, create_at, email_verified, expired_at, failed_login_attempts, image_url, my_recommend_code, recommend_code, update_at,  user_status, version) VALUES('admin', 'admin', '{noop}admin', 0, CURRENT_TIMESTAMP, 0, null,  0, '', '', '', CURRENT_TIMESTAMP, 'NORMAL', 0);
INSERT INTO user_detail (user_id, user_name, user_pwd, account_locked, create_at, email_verified, expired_at, failed_login_attempts, image_url, my_recommend_code, recommend_code, update_at,  user_status, version) VALUES('test', 'test', '{noop}test', 0, CURRENT_TIMESTAMP, 0, null,  0, '', '', '', CURRENT_TIMESTAMP, 'NORMAL', 0);

--INSERT INTO user_detail
--(user_seq, account_locked, create_at, email_verified, expired_at, failed_login_attempts, image_url, my_recommend_code, recommend_code, update_at, user_id, user_name, user_pwd, user_status, version)
--VALUES(0, 0, CURRENT_TIMESTAMP, 0, '', 0, '', '', '', CURRENT_TIMESTAMP, '', '', '', 'NORMAL', 0);
--

-- INSERT INTO user_personal_info (user_seq, birth_date, cell_phone, ci, di, gender, mobile_corp, mod_dtime, name, national_info, reg_dtime, address1, address2, alarm_agree_dtime, alarm_agree_yn, device_push_id, email, marketing_agree_dtime, marketing_agree_yn, zipcode, create_at, update_at) VALUES(0, 'GhRhtX1oB3Rh12MitJhfSw==', '2uNKy7m5DK9MF5jgMJnXIg==', '', '', NULL, NULL, NULL, '8m/dAo+JvUolW5lCjTBZKA==', NULL, '2024-08-17 02:28:57', '', '', NULL, 'N', '', 'rimars@naver.com', NULL, 'N', '', '2024-09-05 14:24:14', '2024-09-05 14:24:14');

INSERT INTO user_authorities (authority, user_seq) VALUES('ROLE_ADMIN', (select user_seq from user_detail where user_id = 'admin'));
INSERT INTO user_authorities (authority, user_seq) VALUES('ROLE_USER', (select user_seq from user_detail where user_id = 'test'));


INSERT INTO oauth2_client (client_id, client_secret, client_name, registration_id, redirect_uri) VALUES('1092064151256-3tivm1do87o4bhjgin48bpsq8mkr2tir.apps.googleusercontent.com', 'xxxxxxxxxxxxxxxxxxxxx',  'google', 'google', '{baseUrl}/{action}/oauth2/code/{registrationId}');

INSERT INTO oauth2_client_scope (client_id, `scope`) VALUES((select id from oauth2_client where registration_id = 'google'), 'profile');
INSERT INTO oauth2_client_scope (client_id, `scope`) VALUES((select id from oauth2_client where registration_id = 'google'), 'email');


INSERT INTO oauth2_client( authorization_grant_type, authorization_uri, client_id, client_name, client_secret, redirect_uri, registration_id, token_uri, user_info_uri, user_name_attribute_name) VALUES ('authorization_code', 'https://kauth.kakao.com/oauth/authorize', '5fe862057d5ccb87d77b835241b62b9a', 'kakao', 'xxxxxxxxxxxxxxxxxxxxx', '{baseUrl}/{action}/oauth2/code/{registrationId}', 'kakao', 'https://kauth.kakao.com/oauth/token', 'https://kapi.kakao.com/v2/user/me', 'id');

INSERT INTO oauth2_client_scope (client_id, `scope`) VALUES((select id from oauth2_client where registration_id = 'kakao'), 'profile_nickname');
INSERT INTO oauth2_client_scope (client_id, `scope`) VALUES((select id from oauth2_client where registration_id = 'kakao'), 'profile_image');
INSERT INTO oauth2_client_scope (client_id, `scope`) VALUES((select id from oauth2_client where registration_id = 'kakao'), 'account_email');