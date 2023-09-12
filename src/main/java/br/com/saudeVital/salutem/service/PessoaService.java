package br.com.saudeVital.salutem.service;

import br.com.saudeVital.salutem.Utils;
import br.com.saudeVital.salutem.model.entities.Contato;
import br.com.saudeVital.salutem.model.entities.Pessoa;
import br.com.saudeVital.salutem.model.repositorys.PessoaRepository;
import br.com.saudeVital.salutem.model.specifications.PessoaSpecifications;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PessoaService {
    @Autowired
    PessoaRepository repository;

    public Iterable<Pessoa> getPessoaFiltrada(Long id, String cpf, String nome, String nomeMeio, String sobrenome, LocalDate dataNascimento,
                                                int numPagina, int tamanhoPagina){
        try{
            if(id != 0){
                return getPessoaUnico("id", id);
            } else if (!cpf.isEmpty()) {
                return getPessoaUnico("cpf", cpf);
            } else{
                boolean estaVazio = Utils.verificaParametros(cpf,nome,nomeMeio,sobrenome,dataNascimento);
                if(!estaVazio){
                    Pageable pagina = Utils.montarPagina(numPagina, tamanhoPagina);
                    Specification<Pessoa> specs = PessoaSpecifications.comNome(nome)
                            .and(PessoaSpecifications.comNomeMeio(nomeMeio))
                            .and(PessoaSpecifications.comSobrenome(sobrenome))
                            .and(PessoaSpecifications.comDataNascimento(dataNascimento));
                    return repository.findAll(specs, pagina);
                } else {
                    return getPessoas(numPagina, tamanhoPagina);
                }
            }
        } catch (Exception e){
            throw e;
        }
    }

    public Iterable<Pessoa> getPessoas(int numPagina, int tamanhoPagina){
        try {
            Pageable pagina = Utils.montarPagina(numPagina, tamanhoPagina);
            return repository.findAll(pagina);
        } catch (Exception e){
            throw e;
        }
    }

    public String insertPessoa(Pessoa pessoa, Contato contato){
        try {
            if(!verificarData(pessoa.getDataNascimento())){
                return "Data de Nascimento Inválida";
            }
            if(!Utils.verificarCPF(pessoa.getCpf())) {
                return "CPF Inválido";
            }
            repository.save(pessoa);
            pessoa.setContatos(contato);
            return "Pessoa inserido";

        } catch (Exception e){
            throw e;
        }
    }

    public String updatePessoa(Long id, Pessoa pessoa){
        try {
            if(!verificarData(pessoa.getDataNascimento())){
                return "Data de Nascimento Inválida";
            }
            if(!Utils.verificarCPF(pessoa.getCpf())) {
                return "CPF Inválido";
            }

            Pessoa pessoaAtual = repository.searchById(id);
            BeanUtils.copyProperties(pessoa, pessoaAtual, Utils.getPropriedadesNulas(pessoa));
            repository.save(pessoaAtual);
            return "Pessoa Alterado com Sucesso";
        } catch (Exception e){
            throw e;
        }
    }

    public boolean deletePessoa(Long id){
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e){
            throw e;
        }
    }

    private <T> Iterable<Pessoa> getPessoaUnico(String parametro, T valor){
        try{
            Optional<Pessoa> pessoa;
            switch (parametro){
                case "id":
                    pessoa = repository.findById((Long) valor);
                    break;
                case "cpf":
                    pessoa = repository.findByCpf((String) valor);
                    break;
                default:
                    pessoa = null;
            }
            return Utils.transformaOptional(pessoa);
        } catch (Exception e){
            throw e;
        }
    }

    private List<Contato> getContatosPessoa(Pessoa pessoa){
        return pessoa.getContatos();
    }

    private boolean verificarData(LocalDate date){
        if(date.isAfter(LocalDate.now())){
            return false;
        } else {
            return true;
        }
    }
}
