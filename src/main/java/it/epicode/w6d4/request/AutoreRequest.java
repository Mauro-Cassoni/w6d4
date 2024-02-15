package it.epicode.w6d4.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.w6d4.model.Categoria;
import it.epicode.w6d4.model.Post;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AutoreRequest {


    private String nome;
    private String cognome;
    private String email;
    private LocalDate dataNascita;
    private String avatar;

}
