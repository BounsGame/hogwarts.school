SELECT student.age, student.name, student.faculty
FROM student

SELECT student
FROM student
WHERE avatar IS NOT NULL

//альтернатива через JOIN

SELECT student.age, student.name, faculty.name
FROM student
INNER JOIN faculty ON ANY(faculty.studentLust) = student.faculty

SELECT student
FROM student
INNER JOIN avatar ON avatar.student = student.avatar

