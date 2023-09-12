package br.com.saudeVital.salutem.model.repositorys;

import br.com.saudeVital.salutem.model.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PacienteRepository extends JpaRepository<Paciente, Long>, JpaSpecificationExecutor<Paciente> {
    @Query("SELECT p FROM Paciente p WHERE p.id = :pId")
    public Paciente searchById(@Param("pId") Long id);
}
