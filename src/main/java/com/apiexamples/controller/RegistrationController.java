package com.apiexamples.controller;

import com.apiexamples.entity.Registration;
import com.apiexamples.payload.RegistrationDto;
import com.apiexamples.service.RegistrationService;
import jakarta.validation.Valid;
import jdk.jfr.Frequency;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    private RegistrationService registrationService;
// dependency injection through constructor
private RegistrationController(RegistrationService registrationService) {
    this.registrationService = registrationService;
    }


    //http://localhost:8080/api/v1/registration

    @PostMapping
    public ResponseEntity<?> addRegistration(@Valid @RequestBody RegistrationDto registrationDto,
                                                            BindingResult result
     ){
        if(result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.OK);
        }
     RegistrationDto regDto = registrationService.createRegistration(registrationDto);
     return new ResponseEntity< >(regDto, HttpStatus.CREATED);
    }


    //http://localhost:8080/api/v1/registration?id=
    @DeleteMapping
    public ResponseEntity<String> deleteRegistrationById(@RequestParam long id){
     registrationService.deleteRegistrationById(id);
     return new ResponseEntity<>("Registration deleted successfully", HttpStatus.OK);
    }

    //http://localhost:8080/api/v1/registration
    @PutMapping // for updating specific registration by id.
    public ResponseEntity<RegistrationDto> updateRegistration
    (@RequestParam long id,
    @RequestBody RegistrationDto registrationDto
    ){


    RegistrationDto updatedRegDto = registrationService.updateRegistration(id, registrationDto);
    return  new ResponseEntity<>(updatedRegDto,HttpStatus.OK);

    }


    //http://localhost:8080/api/v1/registration?pageNo=0&pageSize=3&sortBy=email&sortDir=asc  

   @GetMapping
    public ResponseEntity<List<RegistrationDto>> getAllRegistrations(
    @RequestParam (name="pageNo", defaultValue="0", required= false) int pageNo,
    @RequestParam (name="pageSize", defaultValue="0", required= false) int pageSize,
    @RequestParam (name="sortBy",defaultValue="name", required= false) String sortBy,
    @RequestParam (name="sortDir",defaultValue="name", required= false) String sortDir
   ){
     List<RegistrationDto> dtos = registrationService.getAllRegistrations(pageNo, pageSize,sortBy,sortDir);
     return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

   @GetMapping("/byid/{id}")
    public ResponseEntity<RegistrationDto> getRegistrationById(
       @PathVariable long id
    ){
   RegistrationDto dto = registrationService.getRegistrationById(id);
   return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
