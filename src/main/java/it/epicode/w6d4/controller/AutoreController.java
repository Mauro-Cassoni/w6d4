package it.epicode.w6d4.controller;

import it.epicode.w6d4.exception.NotFoundException;
import it.epicode.w6d4.model.Autore;
import it.epicode.w6d4.model.CustomResponse;
import it.epicode.w6d4.service.AutoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autore")
public class AutoreController {

    @Autowired
    private AutoreService autoreService;

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
    public ResponseEntity<CustomResponse> saveAutore(@RequestBody @Validated Autore autore){
        try{
            return CustomResponse.success(HttpStatus.OK.toString(), autoreService.salvaAutore(autore), HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateAutore(@PathVariable int id, @RequestBody @Validated Autore autore){
        try {
            return CustomResponse.success(HttpStatus.OK.toString(), autoreService.aggiornaAutore(id, autore), HttpStatus.OK);
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
}
