package br.com.saudeVital.salutem.service;

import br.com.saudeVital.salutem.Utils;
import br.com.saudeVital.salutem.model.entities.Paciente;
import br.com.saudeVital.salutem.model.repositorys.PacienteRepository;
import br.com.saudeVital.salutem.model.specifications.PacienteSpecifications;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    PacienteRepository repository;

    public Iterable<Paciente> getPacienteFiltrado(Long id, String cpf, String nome, String nomeMeio, String sobrenome, LocalDate dataNascimento,
                               int numPagina, int tamanhoPagina){
        try{
            if(id != 0){
                return getPacienteById(id);
            } else{
                boolean estaVazio = Utils.verificaParametros(cpf,nome,nomeMeio,sobrenome,dataNascimento);
                System.out.println(estaVazio);
                if(!estaVazio){
                Specification<Paciente> specs = PacienteSpecifications.comCPF(cpf)
                        .and(PacienteSpecifications.comNome(nome))
                        .and(PacienteSpecifications.comNomeMeio(nomeMeio))
                        .and(PacienteSpecifications.comSobrenome(sobrenome))
                        .and(PacienteSpecifications.comDataNascimento(dataNascimento));
                    return repository.findAll(specs);
                } else {
                    return getPacientes(numPagina, tamanhoPagina);
                }
            }
        } catch (Exception e){
            throw e;
        }
    }

    public Iterable<Paciente> getPacienteById(Long id){
        try {
            Optional<Paciente> paciente = repository.findById(id);
            return paciente.map(Collections::singletonList).orElse(Collections.emptyList());
        } catch (Exception e){
            throw e;
        }
    }

    public Iterable<Paciente> getPacientes(int numPagina, int tamanhoPagina){
        try {
            numPagina = numPagina == 0 ? 0 : numPagina;
            tamanhoPagina = tamanhoPagina == 0 ? 10 : tamanhoPagina;
            Pageable pagina = PageRequest.of(numPagina, tamanhoPagina, Sort.by(Sort.Order.asc("id")));
            return repository.findAll(pagina);
        } catch (Exception e){
            throw e;
        }
    }

    public String insertPaciente(Paciente paciente){
        try {
            repository.save(paciente);
            return "Paciente inserido";
        } catch (Exception e){
            throw e;
        }
    }

    public String updatePaciente(Long id, Paciente paciente){
        try {
            Paciente pacienteAtual = repository.searchById(id);
            BeanUtils.copyProperties(paciente, pacienteAtual, Utils.getPropriedadesNulas(paciente));
            repository.save(pacienteAtual);
            return "Paciente Alterado com Sucesso";
        } catch (Exception e){
            throw e;
        }
    }

    public boolean deletePaciente(Long id){
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e){
            throw e;
        }
    }
}
