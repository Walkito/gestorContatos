package br.com.gestorContatos.salutem.service;

import br.com.gestorContatos.salutem.Utils;
import br.com.gestorContatos.salutem.model.entities.Contato;
import br.com.gestorContatos.salutem.model.exception.ExceptionsCustomizadas;
import br.com.gestorContatos.salutem.model.repositorys.ContatoRepository;
import br.com.gestorContatos.salutem.model.repositorys.PessoaRepository;
import br.com.gestorContatos.salutem.model.specifications.ContatoSpecifications;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContatoService {

    @Autowired
    ContatoRepository repository;

    @Autowired
    PessoaRepository pessoaRepository;

    public boolean deleteContato(Long id){
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e){
            throw e;
        }
    }

    public Contato updateContato(Contato contato) throws ExceptionsCustomizadas {
        try {
            if(!Utils.verificarCPF(contato.getCpf())) {
                throw new ExceptionsCustomizadas("CPF Inválido");
            }
            if(!Utils.validarEmail(contato.getEmail())){
                throw new ExceptionsCustomizadas("E-Mail Inválido");
            }

            Contato contatoAtual = repository.searchById(contato.getId());
            BeanUtils.copyProperties(contato, contatoAtual, "pessoa");
            return repository.save(contatoAtual);
        } catch (ExceptionsCustomizadas e){
            throw e;
        }
    }

    public Contato insertContato(Contato contato) throws ExceptionsCustomizadas {
        try {
            if(!Utils.verificarCPF(contato.getCpf())) {
                throw new ExceptionsCustomizadas("CPF Inválido");
            }
            if(!Utils.validarEmail(contato.getEmail())){
                throw new ExceptionsCustomizadas("E-Mail Inválido");
            }
            return repository.save(contato);
        } catch (ExceptionsCustomizadas e){
            throw e;
        }
    }

    public Iterable<Contato> getContatos(Pageable pagina){
        try {
            return repository.findAll(pagina);
        } catch (Exception e){
            throw e;
        }
    }

    public Iterable<Contato> getContatosSemPessoa(){
        try {
            return repository.searchContatosSemPessoa();
        } catch (Exception e){
            throw e;
        }
    }

    public Optional<Contato> getContatoById(Long id){
        try {
            return repository.findById(id);
        } catch (Exception e){
            throw e;
        }
    }

    public Contato getUltimoIndice(){
        try {
            return repository.findTopByOrderByIdDesc();
        } catch (Exception e){
            throw e;
        }
    }

    public Iterable<Contato> getContatoFiltrado(Long id, String cpf, String nome, String nomeMeio,
                                                String sobrenome, String telefone, String email, Long idPessoa,
                                                int numPagina, int tamanhoPagina){
        try{
            if (id != 0){
                return getContatoUnico("id", id);
            } else if (!cpf.isEmpty()){
                return getContatoUnico("cpf", cpf);
            } else if (!email.isEmpty()){
                return getContatoUnico("email", email);
            } else if (!telefone.isEmpty()){
                return getContatoUnico("telefone", telefone);
            } else {
                boolean estaVazio = Utils.verificaParametros(nome, nomeMeio, sobrenome, telefone, email, idPessoa);
                Pageable pagina = Utils.montarPagina(numPagina, tamanhoPagina);
                if(!estaVazio){
                    Specification<Contato> specs = ContatoSpecifications.comNome(nome)
                                                .and(ContatoSpecifications.comNomeMeio(nomeMeio))
                                                .and(ContatoSpecifications.comSobrenome(sobrenome))
                                                .and(ContatoSpecifications.comPessoa(idPessoa));
                        return repository.findAll(specs, pagina);
                } else {
                    return getContatos(pagina);
                }
            }
        } catch (Exception e){
            throw e;
        }
    }

    private <T> Iterable<Contato> getContatoUnico(String parametro, T valor){
        try{
            Optional<Contato> contato;
            switch (parametro){
                case "id":
                    contato = repository.findById((Long) valor);
                    break;
                case "cpf":
                    contato = repository.findByCpf((String) valor);
                    break;
                case "telefone":
                    contato = repository.findByTelefone((String) valor);
                    break;
                case "email":
                    contato = repository.findByEmail((String) valor);
                    break;
                default:
                    contato = null;
            }
            return Utils.transformaOptional(contato);
        } catch (Exception e){
            throw e;
        }
    }
}
