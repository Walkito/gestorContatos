package br.com.gestorContatos.salutem.service;

import br.com.gestorContatos.salutem.Utils;
import br.com.gestorContatos.salutem.model.entities.Contato;
import br.com.gestorContatos.salutem.model.entities.Pessoa;
import br.com.gestorContatos.salutem.model.repositorys.PessoaRepository;
import br.com.gestorContatos.salutem.model.specifications.PessoaSpecifications;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Pessoa insertPessoa(Pessoa pessoa){
        try {
            if(!verificarData(pessoa.getDataNascimento())){
                return new Pessoa();
            }
            if(!Utils.verificarCPF(pessoa.getCpf())) {
                return new Pessoa();
            }
            return repository.save(pessoa);
        } catch (Exception e){
            throw e;
        }
    }

    public Pessoa updatePessoa(Pessoa pessoa){
        try {
            if(!verificarData(pessoa.getDataNascimento())){
                return null;
            }
            if(!Utils.verificarCPF(pessoa.getCpf())) {
                return null;
            }
            Pessoa pessoaAtual = repository.searchById(pessoa.getId());

            if (pessoaAtual != null) {
                BeanUtils.copyProperties(pessoa, pessoaAtual, "contatos");
                if (pessoa.getContatos() != null) {
                    pessoaAtual.getContatos().removeIf(contato -> !pessoa.getContatos().contains(contato));
                    for (Contato novoContato : pessoa.getContatos()) {
                        if (!pessoaAtual.getContatos().contains(novoContato)) {
                            pessoaAtual.getContatos().add(novoContato);
                        }
                    }
                } else {
                    pessoaAtual.getContatos().clear();
                }
            }
            return repository.save(pessoaAtual);
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

    public Optional<Pessoa> getPessoaById(Long id){
        try {
            return repository.findById(id);
        }catch (Exception e){
            throw e;
        }
    }

    public Pessoa getUltimoIndice(){
        try{
            return repository.findTopByOrderByIdDesc();
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

    private boolean verificarData(LocalDate date){
        if(date.isAfter(LocalDate.now())){
            return false;
        } else {
            return true;
        }
    }
}
