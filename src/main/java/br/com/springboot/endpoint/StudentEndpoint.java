package br.com.springboot.endpoint;

import br.com.springboot.error.CustomErrorType;
import br.com.springboot.model.Student;
import br.com.springboot.util.DateUtil;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("students")
public class StudentEndpoint {
    private final DateUtil dateUtil;

    @Autowired
    public StudentEndpoint(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    @GetMapping
    public ResponseEntity<?> listAll() {
        //   System.out.println("-----------"+dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(Student.studentList, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") int id) {
        Student student = new Student();
        student.setId(id);
        int index = Student.studentList.indexOf(student);
        if(index == -1) {
            return new ResponseEntity<>(new CustomErrorType("Student not found"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(Student.studentList.get(index), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Student student) {
        Student.studentList.add(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Student student) {
        Student.studentList.remove(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student) {
        Student.studentList.remove(student);
        Student.studentList.add(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
