package br.com.saudeVital.salutem.controller;

import br.com.saudeVital.salutem.model.entities.Paciente;
import br.com.saudeVital.salutem.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/pacientes")
public class PacienteController {

    @Autowired
    PacienteService service;

    @GetMapping("/paciente")
    public Iterable<Paciente> getPacienteFiltrado(@RequestParam(name = "id", defaultValue = "0") Long id,
                                                  @RequestParam(name = "cpf", required = false) String cpf,
                                                  @RequestParam(name = "nome", required = false) String nome,
                                                  @RequestParam(name = "nomeMeio", required = false) String nomeMeio,
                                                  @RequestParam(name = "sobrenome", required = false) String sobrenome,
                                                  @RequestParam(name = "dataNascimento", required = false) LocalDate dataNascimento,
                                                  @RequestParam(name = "numPagina", defaultValue = "0") int numPagina,
                                                  @RequestParam(name = "tamanhoPagina", defaultValue = "0") int tamanhoPagina){
        return service.getPacienteFiltrado(id,cpf, nome, nomeMeio, sobrenome, dataNascimento, numPagina, tamanhoPagina);
    }

    @GetMapping
    public Iterable<Paciente> getPacientes(@RequestParam(name = "numPagina", defaultValue = "0") int numPagina,
                                           @RequestParam(name = "tamanhoPagina", defaultValue = "0") int tamanhoPagina){
        return service.getPacientes(numPagina,tamanhoPagina);
    }

    @PostMapping("/cadastrar")
    public String insertPaciente(@Valid Paciente paciente){
        return service.insertPaciente(paciente);
    }

    @PutMapping("/paciente/editar")
    public String updatePaciente(Long id, Paciente paciente){
        return service.updatePaciente(id,paciente);
    }

    @DeleteMapping("/paciente/editar")
    public boolean deletePaciente(Long id){
        return service.deletePaciente(id);
    }

}
