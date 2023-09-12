package br.com.saudeVital.salutem.model.specifications;

import br.com.saudeVital.salutem.model.entities.Pessoa;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PessoaSpecifications {

    public static Specification<Pessoa> comNome(String nome){
        return ((root, query, criteriaBuilder) -> {
            if (nome != null && !nome.isEmpty()){
                return criteriaBuilder.equal(root.get("nome"), nome);
            } else {
                return criteriaBuilder.conjunction();
            }
        });
    }

    public static Specification<Pessoa> comNomeMeio(String nomeMeio){
        return (root, query, criteriaBuilder) -> {
            if (nomeMeio != null && !nomeMeio.isEmpty()) {
                return criteriaBuilder.equal(root.get("nomeMeio"), nomeMeio);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Pessoa> comSobrenome(String sobrenome){
        return (root, query, criteriaBuilder) -> {
            if (sobrenome != null && !sobrenome.isEmpty()) {
                return criteriaBuilder.equal(root.get("sobrenome"), sobrenome);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Pessoa> comDataNascimento(LocalDate dataNascimento){
        return (root, query, criteriaBuilder) -> {
            if (dataNascimento != null) {
                return criteriaBuilder.equal(root.get("dataNascimento"), dataNascimento);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }
}
