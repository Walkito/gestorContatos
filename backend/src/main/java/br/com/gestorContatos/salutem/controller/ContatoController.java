package br.com.gestorContatos.salutem.controller;

import br.com.gestorContatos.salutem.model.entities.Contato;
import br.com.gestorContatos.salutem.model.exception.ExceptionsCustomizadas;
import br.com.gestorContatos.salutem.service.ContatoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/contatos")
public class ContatoController {

    @Autowired
    ContatoService service;

    @PostMapping(path = "/contato/cadastrar")
    public Contato insertContato(@RequestBody @Valid Contato contato) throws ExceptionsCustomizadas {
        return service.insertContato(contato);
    }

    @PutMapping(path = "/contato/editar")
    public Contato updateContato(@RequestBody @Valid Contato contato) throws ExceptionsCustomizadas {
        return service.updateContato(contato);
    }

    @DeleteMapping(path = "/contato/excluir")
    public boolean deleteContato(@RequestParam(name = "id") Long id){
        return service.deleteContato(id);
    }

    @GetMapping(path = "/contato")
    public Optional<Contato> getContatoById(@RequestParam(name = "id") Long id){
        return service.getContatoById(id);
    }

    @GetMapping(path = "/contato/ultimoId")
    public Contato getUltimoIndice(){
        return service.getUltimoIndice();
    }

    @GetMapping(path = "/semPessoas")
    public Iterable<Contato> getContatosSemPessoas(){
        return service.getContatosSemPessoa();
    }

    @GetMapping
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
}
