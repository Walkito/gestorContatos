package br.com.gestorContatos.salutem.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, nullable = false)
    @NotBlank
    @Size(max = 25)
    private String nome;

    @Column(length = 11, nullable = false, unique = true)
    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;

    @Column(length = 40)
    @Size(max = 40)
    private String nomeMeio;

    @Column(length = 25, nullable = false)
    @Size(max = 25)
    private String sobrenome;

    @Column(length = 11, nullable = false, unique = true)
    @Size(max = 11, min = 11)
    private String telefone;

    @Column(length = 80, nullable = false, unique = true)
    @Size(max = 80)
    private String email;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    @JsonBackReference
    private Pessoa pessoa;

    public Contato(){

    }

    public Contato(String nome, String cpf, String nomeMeio, String sobrenome, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.nomeMeio = nomeMeio;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.email = email;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
