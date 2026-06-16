USE configdb;

CREATE TABLE PROPERTIES (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    APPLICATION VARCHAR(200),
    PROFILE VARCHAR(200),
    LABEL VARCHAR(200),
    PROP_KEY VARCHAR(200),
    PROP_VALUE VARCHAR(1000),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO properties (application, profile, label, prop_key, prop_value) VALUES
('configdemo', 'prod', 'main', 'build.id', '101'),
('configdemo', 'prod', 'main', 'build.version', '1.2.3'),
('configdemo', 'prod', 'main', 'build.name', 'Database-Production-Build SERVER APPLICATION'),
('configdemo', 'prod', 'main', 'build.type', 'Database Production Build SERVER APPLICATION');


INSERT INTO properties (application, profile, label, prop_key, prop_value) VALUES
('configdemo', 'dev', 'main', 'build.id', '101'),
('configdemo', 'dev', 'main', 'build.version', '1.2.3'),
('configdemo', 'dev', 'main', 'build.name', 'Database-Development-Build SERVER DB'),
('configdemo', 'dev', 'main', 'build.type', 'Database Development Build SERVER DB');

SELECT PROP_KEY, PROP_VALUE FROM PROPERTIES WHERE APPLICATION = 'configdemo' AND PROFILE = 'dev' AND LABEL = 'main';