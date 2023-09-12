package br.com.saudeVital.salutem.controller;

import br.com.saudeVital.salutem.model.entities.Contato;
import br.com.saudeVital.salutem.model.entities.Pessoa;
import br.com.saudeVital.salutem.service.ContatoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/contatos")
public class ContatoController {

    @Autowired
    ContatoService service;

    @PostMapping(path = "/cadastrar")
    public String insertContato(@Valid Contato contato){
        return service.insertContato(contato);
    }

    @PutMapping(path = "/contato/editar")
    public String updateContato(@RequestParam(name = "id") Long id, Contato contato){
        return service.updateContato(id,contato);
    }

    @DeleteMapping(path = "/contato/editar")
    public boolean deleteContato(@RequestParam(name = "id") Long id){
        return service.deleteContato(id);
    }

    @GetMapping(path = "/contato")
    public Iterable<Contato> getContatoFiltrado(@RequestParam(name = "id", defaultValue = "0") Long id,
                                                @RequestParam(name = "cpf", required = false) String cpf,
                                                @RequestParam(name = "nome", required = false) String nome,
                                                @RequestParam(name = "nomeMeio", required = false) String nomeMeio,
                                                @RequestParam(name = "sobrenome", required = false) String sobrenome,
                                                @RequestParam(name = "telefone", required = false) String telefone,
                                                @RequestParam(name = "email", required = false) String email,
                                                @RequestParam(name = "idPessoa", defaultValue = "0")Long idPessoa,
                                                @RequestParam(name = "numPagina", defaultValue = "0") int numPagina,
                                                @RequestParam(name = "tamanhoPagina", defaultValue = "0") int tamanhoPagina)
    {
        return service.getContatoFiltrado(id,cpf,nome,nomeMeio,sobrenome,telefone,email,idPessoa, numPagina, tamanhoPagina);
    }


    @GetMapping
    public Iterable<Contato> getContatos(@RequestParam(name = "numPagina", defaultValue = "0") int numPagina,
                                         @RequestParam(name = "tamanhoPagina", defaultValue = "0") int tamanhoPagina){
        return service.getContatos(numPagina, tamanhoPagina);
    }

}
