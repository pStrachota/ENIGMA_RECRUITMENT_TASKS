INSERT INTO users (id, uuid, first_name, last_name, email, version)
VALUES (1, 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'John', 'Doe', 'john.doe@example.com', 0),
       (2, '0f9b7540-581b-4f28-86f1-5310a5b2aa41', 'Jane', 'Smith', 'jane.smith@example.com', 0),
       (3, 'e4eaaaf2-d142-11e1-b3e4-080027620cde', 'Liam', 'Taylor', 'liam.taylor@example.com', 0);

INSERT INTO task (id, uuid, title, description, status, due_date, version)
VALUES (1, 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'Task 1', 'Description for Task 1', 'TODO', '2023-12-01 12:00:00',
        0),
       (2, '0f9b7540-581b-4f28-86f1-5310a5b2aa41', 'Task 2', 'Description for Task 2', 'DONE',
        '2023-12-05 15:30:00', 0),
       (3, '6c84fb90-12c4-11e1-840d-7b25c5ee775a', 'Task 3', 'Description for Task 3', 'IN_PROGRESS', '2023-11-25 10:00:00',
        0);

INSERT INTO task_assigned_users (task_id, user_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (3, 3);
