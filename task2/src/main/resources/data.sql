INSERT INTO users (uuid, first_name, last_name, email, version)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'John', 'Doe', 'john.doe@example.com', 0),
    ('0f9b7540-581b-4f28-86f1-5310a5b2aa41', 'Jane', 'Smith', 'jane.smith@example.com', 0),
    ('6c84fb90-12c4-11e1-840d-7b25c5ee775a', 'Bob', 'Johnson', 'bob.johnson@example.com', 0),
    ('e4eaaaf2-d142-11e1-b3e4-080027620cdd', 'Alice', 'Brown', 'alice.brown@example.com', 0),
    ('6c84fb90-12c4-11e1-840d-7b25c5ee775b', 'Eve', 'Williams', 'eve.williams@example.com', 0),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d47a', 'Charlie', 'Davis', 'charlie.davis@example.com', 0),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d47b', 'Grace', 'Harris', 'grace.harris@example.com', 0),
    ('0f9b7540-581b-4f28-86f1-5310a5b2aa42', 'Oliver', 'Lee', 'oliver.lee@example.com', 0),
    ('6c84fb90-12c4-11e1-840d-7b25c5ee775c', 'Mia', 'Martin', 'mia.martin@example.com', 0),
    ('e4eaaaf2-d142-11e1-b3e4-080027620cde', 'Liam', 'Taylor', 'liam.taylor@example.com', 0);

INSERT INTO tasks (uuid, title, description, status, due_date, version)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'Task 1', 'Description for Task 1', 'TODO', '2023-12-01 12:00:00', 0),
    ('0f9b7540-581b-4f28-86f1-5310a5b2aa41', 'Task 2', 'Description for Task 2', 'IN_PROGRESS', '2023-12-05 15:30:00', 0),
    ('6c84fb90-12c4-11e1-840d-7b25c5ee775a', 'Task 3', 'Description for Task 3', 'TODO', '2023-11-25 10:00:00', 0),
    ('e4eaaaf2-d142-11e1-b3e4-080027620cdd', 'Task 4', 'Description for Task 4', 'IN_PROGRESS', '2023-11-15 14:45:00', 0),
    ('6c84fb90-12c4-11e1-840d-7b25c5ee775b', 'Task 5', 'Description for Task 5', 'TODO', '2023-11-20 16:20:00', 0),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d47a', 'Task 6', 'Description for Task 6', 'DONE', '2023-11-30 09:30:00', 0),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d47b', 'Task 7', 'Description for Task 7', 'IN_PROGRESS', '2023-12-10 11:15:00', 0),
    ('0f9b7540-581b-4f28-86f1-5310a5b2aa42', 'Task 8', 'Description for Task 8', 'TODO', '2023-12-25 13:00:00', 0),
    ('6c84fb90-12c4-11e1-840d-7b25c5ee775c', 'Task 9', 'Description for Task 9', 'DONE', '2023-12-05 17:45:00', 0),
    ('e4eaaaf2-d142-11e1-b3e4-080027620cde', 'Task 10', 'Description for Task 10', 'IN_PROGRESS', '2023-12-20 10:30:00', 0);

INSERT INTO task_assigned_users (task_id, user_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 1),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 6),
    (7, 7),
    (8, 8),
    (9, 9),
    (10, 10);
