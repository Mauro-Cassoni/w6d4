package it.epicode.w6d4.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.w6d4.model.Categoria;
import it.epicode.w6d4.model.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.List;

@Data
public class AutoreRequest {


    @NotNull(message = "nome obbligatorio")
    @NotEmpty(message = "nome vuoto")
    private String nome;
    @NotNull(message = "cognome obbligatorio")
    @NotEmpty(message = "cognome vuoto")
    private String cognome;
    @Email
    @NotNull(message = "email obbligatoria")
    @NotEmpty(message = "email vuota")
    private String email;
    @NotNull(message = "data obbligatoria")
    private LocalDate dataNascita;
    @URL
    private String avatar;

}
