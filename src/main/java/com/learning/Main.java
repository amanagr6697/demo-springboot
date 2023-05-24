package com.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/student")
public class Main {

    private final StudentInterface studentInterface;

    public Main(StudentInterface studentInterface) {
        this.studentInterface = studentInterface;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }

    @GetMapping
    public List<Student> getStudent(){
        return studentInterface.findAll();
    }

    record StudentAdd(
            String rollNo,
            String name,
            String email,
            String dateOfBirth
    ){}

    @PostMapping
    public void addStudent(@RequestBody StudentAdd request)
    {
        Student student=new Student();
        student.setName(request.name());
        student.setEmail(request.email());
        student.setDateOfBirth(request.dateOfBirth());
        student.setRollNo(request.rollNo());
        studentInterface.save(student);
    }

    @DeleteMapping("{stud_rollNo}")
    public void deleteStud(@PathVariable("stud_rollNo") String rollNo)
    {
        studentInterface.deleteById(rollNo);
    }

    @PutMapping("{stud_rollNo}")
    public void updateStud(@RequestBody Student request, @PathVariable("stud_rollNo") String rollNo) {
        Optional<Student> stud = studentInterface.findById(rollNo);
        if (stud.isPresent()) {
            Student student = stud.get();
            if (student.getName() != null) {
                student.setName(request.getName());
            }
            if (student.getEmail() != null) {
                student.setEmail(request.getEmail());
            }
//            if(student.getRollNo()!=null)
//            {
//                throw new NoSuchElementException("User not found");
//            }
            studentInterface.save(student);
        }
        else
        {
            throw new NoSuchElementException("User not found");
        }
    }
}