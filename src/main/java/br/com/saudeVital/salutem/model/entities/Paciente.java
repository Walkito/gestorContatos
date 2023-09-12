package br.com.saudeVital.salutem.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 11, unique = true, nullable = false)
    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;

    @Column(length = 20, nullable = false)
    @NotBlank
    @Size(max = 20)
    private String nome;

    @Column(length = 35)
    @Size(max = 35)
    private String nomeMeio;

    @Column(length = 35, nullable = false)
    @NotBlank
    @Size(max = 35)
    private String sobrenome;

    @Column(columnDefinition = "DATE")
    @NotNull
    private LocalDate dataNascimento;

    public Paciente(){

    }

    public Paciente(String nome, String nomeMeio, String sobrenome, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.nomeMeio = nomeMeio;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public String getNomeMeio() {
        return nomeMeio;
    }

    public void setNomeMeio(String nomeMeio) {
        this.nomeMeio = nomeMeio;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
