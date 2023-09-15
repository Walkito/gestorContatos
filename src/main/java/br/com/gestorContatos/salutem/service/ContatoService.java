package br.com.gestorContatos.salutem.service;

import br.com.gestorContatos.salutem.Utils;
import br.com.gestorContatos.salutem.model.entities.Contato;
import br.com.gestorContatos.salutem.model.entities.Pessoa;
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

    public String updateContato(Long id, Contato contato){
        try {
            if(!Utils.verificarCPF(contato.getCpf())) {
                return "CPF Inválido";
            }
            if(!Utils.validarEmail(contato.getEmail())){
                return "E-mail Inválido";
            }

            Contato contatoAtual = repository.searchById(id);
            BeanUtils.copyProperties(contato, contatoAtual, Utils.getPropriedadesNulas(contato));
            repository.save(contatoAtual);
            return "Contato Alterado com Sucesso";
        } catch (Exception e){
            throw e;
        }
    }

    public String insertContato(Contato contato){
        try {
            if(Utils.verificarCPF(contato.getCpf())){
                if(!Utils.verificarCPF(contato.getCpf())) {
                    return "CPF Inválido";
                }
                if(!Utils.validarEmail(contato.getEmail())){
                    return "E-mail Inválido";
                }

                if(contato.getPessoa().getId() !=0){
                    Pessoa pessoa = pessoaRepository.searchById(contato.getPessoa().getId());
                    pessoa.setContatos(contato);
                }
                repository.save(contato);
                return "Contato Cadastrado com Sucesso";
            } else {
                return "CPF Inválido";
            }
        } catch (Exception e){
            throw e;
        }
    }

    public Iterable<Contato> getContatos(int numPagina, int tamanhoPagina){
        try {
            Pageable pagina = Utils.montarPagina(numPagina, tamanhoPagina);
            return repository.findAll(pagina);
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
                if(!estaVazio){
                    Pageable pagina = Utils.montarPagina(numPagina, tamanhoPagina);
                    Specification<Contato> specs = ContatoSpecifications.comNome(nome)
                                                .and(ContatoSpecifications.comNomeMeio(nomeMeio))
                                                .and(ContatoSpecifications.comSobrenome(sobrenome))
                                                .and(ContatoSpecifications.comPessoa(idPessoa));
                        return repository.findAll(specs, pagina);
                } else {
                    return getContatos(numPagina,tamanhoPagina);
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
