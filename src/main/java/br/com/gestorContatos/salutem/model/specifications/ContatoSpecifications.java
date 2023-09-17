package br.com.gestorContatos.salutem.model.specifications;

import br.com.gestorContatos.salutem.model.entities.Contato;
import org.springframework.data.jpa.domain.Specification;

public class ContatoSpecifications {

    public static Specification<Contato> comNome(String nome){
        return (root, query, criteriaBuilder) -> {
            if(nome.isEmpty() || nome.equals(null)){
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("nome"),nome);
            }
        };
    }

    public static Specification<Contato> comNomeMeio(String nomeMeio){
        return (root, query, criteriaBuilder) -> {
            if(nomeMeio.isEmpty() || nomeMeio.equals(null)){
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("nomeMeio"), "%" + nomeMeio + "%");
            }
        };
    }

    public static Specification<Contato> comSobrenome(String sobrenome){
        return (root, query, criteriaBuilder) -> {
            if(sobrenome.isEmpty() || sobrenome.equals(null)){
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("sobrenome"),sobrenome);
            }
        };
    }

    public static Specification<Contato> comPessoa(Long idPessoa){
        return (root, query, criteriaBuilder) -> {
            if(idPessoa == 0L || idPessoa.equals(null)){
                return criteriaBuilder.conjunction();
            } else {
                root.fetch("pessoa");
                return criteriaBuilder.equal(root.get("pessoa").get("id"), idPessoa);
            }
        };
    }
}
