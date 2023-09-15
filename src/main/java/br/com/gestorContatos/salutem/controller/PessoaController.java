package br.com.gestorContatos.salutem.controller;

import br.com.gestorContatos.salutem.model.entities.Contato;
import br.com.gestorContatos.salutem.model.entities.Pessoa;
import br.com.gestorContatos.salutem.model.entities.PessoaWrapper;
import br.com.gestorContatos.salutem.model.repositorys.PessoaRepository;
import br.com.gestorContatos.salutem.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/pessoas")

public class PessoaController {

    @Autowired
    PessoaService service;

    @Autowired
    PessoaRepository repository;

    @GetMapping
    public Iterable<Pessoa> getPessoaFiltrada(@RequestParam(name = "id", defaultValue = "0") Long id,
                                              @RequestParam(name = "cpf", required = false) String cpf,
                                              @RequestParam(name = "nome", required = false) String nome,
                                              @RequestParam(name = "nomeMeio", required = false) String nomeMeio,
                                              @RequestParam(name = "sobrenome", required = false) String sobrenome,
                                              @RequestParam(name = "dataNascimento", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataNascimento,
                                              @RequestParam(name = "numPagina", defaultValue = "0") int numPagina,
                                              @RequestParam(name = "tamanhoPagina", defaultValue = "0") int tamanhoPagina){
        return service.getPessoaFiltrada(id,cpf, nome, nomeMeio, sobrenome, dataNascimento, numPagina, tamanhoPagina);
    }

    @PostMapping("/cadastrar")
    public Pessoa insertPessoa(@RequestBody PessoaWrapper pessoaWrapper){
        return service.insertPessoa(pessoaWrapper);
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
