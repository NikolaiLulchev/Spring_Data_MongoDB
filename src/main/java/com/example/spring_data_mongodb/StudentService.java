package com.example.spring_data_mongodb;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(String studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException("student with Id" + studentId + " dose not exists");
        }
        studentRepository.deleteById(studentId);
    }

    public void updateStudent(String studentId, String firstName, String lastName, String email) {
        Student student = studentRepository.findById(studentId).
                orElseThrow(() -> new IllegalStateException("student with Id" + studentId + " dose not exists"));

        if (firstName != null &&
            firstName.length() > 0 &&
            !Objects.equals(student.getFirstName(), firstName)) {
            student.setFirstName(firstName);
        }

        if (lastName != null &&
            lastName.length() > 0 &&
            !Objects.equals(student.getLastName(), lastName)) {
            student.setLastName(lastName);
        }

        if (email != null &&
            email.length() > 0 &&
            !Objects.equals(student.getEmail(), email)) {

            if (studentRepository.findStudentByEmail(email).isPresent()) {
                throw new IllegalStateException("email taken");
            }
        }
    }
}
