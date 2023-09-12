package br.com.saudeVital.salutem.model.repositorys;

import br.com.saudeVital.salutem.model.entities.Contato;
import br.com.saudeVital.salutem.model.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContatoRepository extends JpaRepository<Contato, Long>, JpaSpecificationExecutor<Contato>{

    @Query("SELECT c FROM Contato c WHERE id = :pId")
    public Contato searchById(@Param("pId") Long id);

    public Optional<Contato> findByCpf(String cpf);

    public Optional<Contato> findByEmail(String email);

    public Optional<Contato> findByTelefone(String telefone);
}
