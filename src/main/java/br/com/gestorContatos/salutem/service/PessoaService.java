package br.com.gestorContatos.salutem.service;

import br.com.gestorContatos.salutem.Utils;
import br.com.gestorContatos.salutem.model.entities.Contato;
import br.com.gestorContatos.salutem.model.entities.Pessoa;
import br.com.gestorContatos.salutem.model.entities.PessoaWrapper;
import br.com.gestorContatos.salutem.model.repositorys.PessoaRepository;
import br.com.gestorContatos.salutem.model.specifications.PessoaSpecifications;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
                Pageable pagina = Utils.montarPagina(numPagina, tamanhoPagina);
                if(!estaVazio){
                    Specification<Pessoa> specs = PessoaSpecifications.comNome(nome)
                            .and(PessoaSpecifications.comNomeMeio(nomeMeio))
                            .and(PessoaSpecifications.comSobrenome(sobrenome))
                            .and(PessoaSpecifications.comDataNascimento(dataNascimento));
                    return repository.findAll(specs, pagina);
                } else {
                    return getPessoas(pagina);
                }
            }
        } catch (Exception e){
            throw e;
        }
    }

    private Iterable<Pessoa> getPessoas(Pageable pagina){
        try {
            return repository.findAll(pagina);
        } catch (Exception e){
            throw e;
        }
    }

    public Pessoa insertPessoa(PessoaWrapper pessoaWrapper){
        try {
            Pessoa pessoa = pessoaWrapper.getPessoa();
            Contato contato = pessoaWrapper.getContato();
            if(!verificarData(pessoa.getDataNascimento())){
                return new Pessoa();
            }
            if(!Utils.verificarCPF(pessoa.getCpf())) {
                return new Pessoa();
            }
            pessoa.setContatos(contato);
            return repository.save(pessoa);

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
