package it.epicode.w6d4.service;

import it.epicode.w6d4.exception.NotFoundException;
import it.epicode.w6d4.model.Autore;
import it.epicode.w6d4.repository.AutoreRepository;
import it.epicode.w6d4.request.AutoreRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AutoreService {

    @Autowired
    private AutoreRepository autoreRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public Page<Autore> cercaTuttiAutori(Pageable pageable){

        return autoreRepository.findAll(pageable);
    }

    public Autore cercaAutorePerId(int id) throws NotFoundException {
        return autoreRepository.findById(id).orElseThrow(()->new NotFoundException("Autore con id= " + id + " non trovato"));
    }

    public Autore salvaAutore(AutoreRequest autoreRequest){
        Autore a = new Autore();
        a.setNome(autoreRequest.getNome());
        a.setCognome(autoreRequest.getCognome());
        a.setEmail(autoreRequest.getEmail());
        a.setDataNascita(autoreRequest.getDataNascita());
        a.setAvatar(autoreRequest.getAvatar());
        sendMail(autoreRequest.getEmail());


        return autoreRepository.save(a);
    }

    public Autore aggiornaAutore(int id, AutoreRequest autoreRequest) throws NotFoundException{
        Autore a = cercaAutorePerId(id);

        a.setNome(autoreRequest.getNome());
        a.setCognome(autoreRequest.getCognome());
        a.setEmail(autoreRequest.getEmail());
        a.setDataNascita(autoreRequest.getDataNascita());
        a.setAvatar(autoreRequest.getAvatar());

        return autoreRepository.save(a);
    }

    public void eliminaAutore(int id) throws NotFoundException{
        Autore a = cercaAutorePerId(id);
        autoreRepository.delete(a);
    }

    public Autore uploadAvatar(int id, String url) throws NotFoundException{
        Autore a = cercaAutorePerId(id);
        a.setAvatar(url);
        return autoreRepository.save(a);
    }

    private void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione Servizio rest");
        message.setText("Registrazione al servizio rest avvenuta con successo");

        javaMailSender.send(message);
    }



}
