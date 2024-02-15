package it.epicode.w6d4.request;

import it.epicode.w6d4.model.Categoria;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class PostRequest {


    @NotNull(message = "categoria obbligatoria")
    private Categoria categoria;
    @NotNull(message = "titolo obbligatorio")
    @NotEmpty(message = "titolo vuoto")
    private String titolo;
    @URL
    private String cover;
    @NotNull(message = "contenuto obbligatorio")
    @NotEmpty(message = "contenuto vuoto")
    private String contenuto;
    @Min(1)
    private int tempoDiLettura;
    private int autoreId;

}
