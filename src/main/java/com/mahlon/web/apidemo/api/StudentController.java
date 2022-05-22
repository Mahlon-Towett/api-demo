/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mahlon.web.apidemo.api;

import com.mahlon.web.apidemo.model.Student;
import com.mahlon.web.apidemo.model.dto.EmailSearchRequestObject;
import com.mahlon.web.apidemo.model.dto.ResponseObject;
import com.mahlon.web.apidemo.model.dto.StudentDTO;
import com.mahlon.web.apidemo.model.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;









import java.util.logging.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author mahlo
 */
@RestController
@RequestMapping(value="students/v1/")
public class StudentController {
    
    @Autowired
    StudentRepository studentRepository;
    
    private static final Logger LOG = Logger.getLogger(StudentController.class.getName());
    
    @GetMapping("all")
    public ResponseEntity<?> getAllStudents() {
        LOG.info("Request received.........");
        try{
            List<Student> students = studentRepository.findAll();
            LOG.log(Level.INFO, "Student list fetched successfully; size: {0}", students.size());
            return new ResponseEntity(students, HttpStatus.OK);
        } catch(Exception e) {
            LOG.info("Error ocured!!!!......: ");
            e.printStackTrace(System.out);
            ResponseObject responseObj = new ResponseObject();
            responseObj.setStatusCode("-1");
            responseObj.setStatusMessage("Could not process request, please try again later......");
            return new ResponseEntity(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("save")
    public ResponseEntity<?> saveStudent(@RequestBody StudentDTO studentDto) {
        LOG.info("INCOMMING REQUEST......");
        ResponseObject responseObj = new ResponseObject();
        try{
            Optional<Student> existing = studentRepository.findByEmail(studentDto.getEmail());
            if(existing.isPresent()) {
                responseObj.setStatusCode("01");
                responseObj.setStatusMessage("Student with email: " + studentDto.getEmail() + " already exists");
                return new ResponseEntity(responseObj, HttpStatus.NOT_ACCEPTABLE);     
            }
            Student student = new Student();
            student.setEmail(studentDto.getEmail());
            student.setName(studentDto.getName());
            student.setPassword(studentDto.getPassword());
            studentRepository.save(student);

            responseObj.setStatusCode("00");
            responseObj.setStatusMessage("Student saved succesfully!!!");
            return new ResponseEntity(responseObj, HttpStatus.OK);
        } catch(Exception e) {
            LOG.info("Error ocured!!......: ");
            e.printStackTrace(System.out);
            responseObj.setStatusCode("-1");
            responseObj.setStatusMessage("Could not process request, please try again later......");
            return new ResponseEntity(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
        }
      
    }
    
    @PostMapping("getStudentByEmail")
    public ResponseEntity<?> getStudentByEmail(@RequestBody EmailSearchRequestObject emailSearchRequestObject) {
        ResponseObject responseObj = new ResponseObject();
        try{
            Optional<Student> studentOpt = studentRepository.findByEmail(emailSearchRequestObject.getEmail());
            if(studentOpt.isPresent()) {
                Student student = studentOpt.get();
                LOG.log(Level.INFO, "Student fetched successfully; size: " + student.toString());
                return new ResponseEntity(student, HttpStatus.OK);
            } else {
                responseObj.setStatusCode("02");
                responseObj.setStatusMessage("Student with email: " + emailSearchRequestObject.getEmail() + " not found");
                return new ResponseEntity(responseObj, HttpStatus.OK);
            }
        } catch(Exception e) {
            LOG.info("Error ocured!!!!......: ");
            e.printStackTrace(System.out);
            responseObj.setStatusCode("-1");
            responseObj.setStatusMessage("Could not process request, please try again later......");
            return new ResponseEntity(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
    
    }
    

    }
    }
