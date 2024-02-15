package it.epicode.w6d4.controller;

import com.cloudinary.Cloudinary;
import it.epicode.w6d4.exception.NotFoundException;
import it.epicode.w6d4.model.Autore;
import it.epicode.w6d4.model.CustomResponse;
import it.epicode.w6d4.request.AutoreRequest;
import it.epicode.w6d4.service.AutoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/autore")
public class AutoreController {

    @Autowired
    private AutoreService autoreService;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("")
    public ResponseEntity<CustomResponse> getAll(Pageable pageable){
        try {
            return CustomResponse.success(HttpStatus.OK.toString(), autoreService.cercaTuttiAutori(pageable),HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getAutoreById(@PathVariable int id, @RequestBody Autore autore){
        try {
            return CustomResponse.success(HttpStatus.OK.toString(), autoreService.cercaAutorePerId(id), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<CustomResponse> saveAutore(@RequestBody @Validated AutoreRequest autoreRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return CustomResponse.error(bindingResult.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }
        try{
            return CustomResponse.success(HttpStatus.OK.toString(), autoreService.salvaAutore(autoreRequest), HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateAutore(@PathVariable int id, @RequestBody @Validated AutoreRequest autoreRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return CustomResponse.error(bindingResult.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }

        try {
            return CustomResponse.success(HttpStatus.OK.toString(), autoreService.aggiornaAutore(id, autoreRequest), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteAutore(@PathVariable int id) {
        try{
            autoreService.eliminaAutore(id);
            return CustomResponse.emptyResponse("Autore con id=" + id + " cancellata", HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/upload")
    public ResponseEntity<CustomResponse> uploadLogo(@PathVariable int id,@RequestParam("upload") MultipartFile file){
        try {
            Autore autore = autoreService.uploadAvatar(id, (String)cloudinary.uploader().upload(file.getBytes(), new HashMap()).get("url"));
            return CustomResponse.success(HttpStatus.OK.toString(), autore, HttpStatus.OK);
        }
        catch (IOException | NotFoundException e){
            return CustomResponse.error(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
