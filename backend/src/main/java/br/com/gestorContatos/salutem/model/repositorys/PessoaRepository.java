package br.com.gestorContatos.salutem.model.repositorys;

import br.com.gestorContatos.salutem.model.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, JpaSpecificationExecutor<Pessoa> {
    @Query("SELECT p FROM Pessoa p WHERE p.id = :pId")
    public Pessoa searchById(@Param("pId") Long id);

    public Optional<Pessoa> findByCpf(String cpf);

    public Pessoa findTopByOrderByIdDesc();
}
