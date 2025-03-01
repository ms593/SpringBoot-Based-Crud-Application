package com.apiexamples.service;


import com.apiexamples.entity.Registration;
import com.apiexamples.exception.ResourceNotFound;
import com.apiexamples.payload.RegistrationDto;
import com.apiexamples.repository.RegistrationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegistrationServiceImpl implements RegistrationService{

    private RegistrationRepository registrationRepository;

 /*
    public  RegistrationServiceImpl(){

    }
*/

    //contructor based dependency injection
    public RegistrationServiceImpl(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public RegistrationDto createRegistration(RegistrationDto registrationDto) {
       Registration registration = mapToEntity(registrationDto);
       Registration savedEntity = registrationRepository.save(registration);
       RegistrationDto dto = maptoDto(savedEntity);
       dto.setMessage("Registration saved successfully!");
       return dto;
    }

    @Override
    public void deleteRegistrationById(long id) {
        registrationRepository.deleteById(id);
    }

    @Override
    public RegistrationDto updateRegistration(long id, RegistrationDto registrationDto) {
      Optional<Registration> opReg = registrationRepository.findById(id);
      Registration registration = opReg.get();

      registration.setName(registrationDto.getName());
      registration.setEmail(registrationDto.getEmail());
      registration.setMobile(registrationDto.getMobile());

      Registration savedEntity = registrationRepository.save(registration);
      RegistrationDto dto = maptoDto(savedEntity);
      dto.setMessage("updated Successfully!");
      return dto;
    }

    @Override
    public List<RegistrationDto> getAllRegistrations(int pageNo, int pageSize, String sortBy, String sortDir) {

   //  List<Registration> registrations  = registrationRepository.findAll()
   // Ternary operator

   Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(Sort.Direction.ASC, sortBy): Sort.by(Sort.Direction.DESC, sortBy);
      Pageable pageable= PageRequest.of(pageNo, pageSize, sort);
      Page<Registration> all =  registrationRepository.findAll(pageable);
      List<Registration> registrations = all.getContent();  // get the current page of elements

      List<RegistrationDto> registrationDtos = registrations.stream().map(r -> maptoDto(r)).collect(Collectors.toList());
        System.out.println(all.getTotalPages()); // it gives us the total number of pages of registration
        System.out.println(all.isLast()); // it gives us the last page of registration
        System.out.println(all.isFirst()); // it gives us the first page of registration
       // System.out.println(all.getTotalElements()); // it gives us the total number of elements in the database
        System.out.println(pageable.getPageSize()); // it gives us the page size of the database
        System.out.println(pageable.getPageNumber()); // it gives us the current page number of the database
      return registrationDtos;
        /*

        RegistrationServiceImpl r = new RegistrationServiceImpl();
        List<Registration> registrations  = registrationRepository.findAll();
        List<RegistrationDto> registrationDtos = registrations.stream().map(r::maptoDto).collect(Collectors.toList());
        return registrationDtos;
        */
    }

    @Override
    public RegistrationDto getRegistrationById(long id) {
       Registration registration = registrationRepository.findById(id)
               .orElseThrow(
                () -> new ResourceNotFound("Registration not found with id: " + id)
                 );
        RegistrationDto registrationDto = maptoDto(registration);
        return registrationDto;
    }

    Registration mapToEntity(RegistrationDto Dto) {
        Registration entity = new Registration();
        // map Dto to Entity
        entity.setName(Dto.getName());
        entity.setEmail(Dto.getEmail());
        entity.setMobile(Dto.getMobile());
        return entity;
    }

    RegistrationDto maptoDto(Registration registration){
        RegistrationDto dto = new RegistrationDto();
        // map Entity to Dto
        dto.setId(registration.getId());
        dto.setName(registration.getName());
        dto.setEmail(registration.getEmail());
        dto.setMobile(registration.getMobile());
        return dto;
    }




}
