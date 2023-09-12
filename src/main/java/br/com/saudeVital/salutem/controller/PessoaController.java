package br.com.saudeVital.salutem.controller;

import br.com.saudeVital.salutem.model.entities.Contato;
import br.com.saudeVital.salutem.model.entities.Pessoa;
import br.com.saudeVital.salutem.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/api/pessoas")
public class PessoaController {

    @Autowired
    PessoaService service;

    @GetMapping("/pessoa")
    public Iterable<Pessoa> getPessoaFiltrada(@RequestParam(name = "id", defaultValue = "0") Long id,
                                              @RequestParam(name = "cpf", required = false) String cpf,
                                              @RequestParam(name = "nome", required = false) String nome,
                                              @RequestParam(name = "nomeMeio", required = false) String nomeMeio,
                                              @RequestParam(name = "sobrenome", required = false) String sobrenome,
                                              @RequestParam(name = "dataNascimento", required = false) LocalDate dataNascimento,
                                              @RequestParam(name = "numPagina", defaultValue = "0") int numPagina,
                                              @RequestParam(name = "tamanhoPagina", defaultValue = "0") int tamanhoPagina){
        return service.getPessoaFiltrada(id,cpf, nome, nomeMeio, sobrenome, dataNascimento, numPagina, tamanhoPagina);
    }

    @GetMapping
    public Iterable<Pessoa> getPessoas(@RequestParam(name = "numPagina", defaultValue = "0") int numPagina,
                                         @RequestParam(name = "tamanhoPagina", defaultValue = "0") int tamanhoPagina){
        return service.getPessoas(numPagina,tamanhoPagina);
    }

    @PostMapping("/cadastrar")
    public String insertPessoa(@Valid Pessoa pessoa, @Valid Contato contato){
        return service.insertPessoa(pessoa, contato);
    }

    @PutMapping("/pessoa/editar")
    public String updatePessoa(Long id, Pessoa pessoa){
        return service.updatePessoa(id, pessoa);
    }

    @DeleteMapping("/pessoa/editar")
    public boolean deletePessoa(Long id){
        return service.deletePessoa(id);
    }

}
