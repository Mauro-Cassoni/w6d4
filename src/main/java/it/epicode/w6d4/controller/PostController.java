package it.epicode.w6d4.controller;

import it.epicode.w6d4.exception.NotFoundException;
import it.epicode.w6d4.model.CustomResponse;
import it.epicode.w6d4.request.PostRequest;
import it.epicode.w6d4.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("")
    public ResponseEntity<CustomResponse> getAll(Pageable pageable){
        try {
            return CustomResponse.success(HttpStatus.OK.toString(), postService.cercaTuttiPost(pageable), HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getAutoById(@PathVariable int id){
        try {
            return CustomResponse.success(HttpStatus.OK.toString(), postService.cercaPostPerId(id), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<CustomResponse> savePost(@RequestBody PostRequest postRequest){
        try{
            return CustomResponse.success(HttpStatus.OK.toString(), postService.salvaPost(postRequest), HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateAuto(@PathVariable int id, @RequestBody PostRequest postRequest){
        try {
            return CustomResponse.success(HttpStatus.OK.toString(), postService.aggiornaPost(id, postRequest), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteAuto(@PathVariable int id){
        try {
            postService.eliminaPost(id);
            return CustomResponse.emptyResponse("Auto con id=" + id + " cancellata", HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
