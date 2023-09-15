package br.com.gestorContatos.salutem.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;

public class PessoaWrapper {
    @Valid
    Pessoa pessoa;

    @Valid
    Contato contato;

    public PessoaWrapper(){

    }

    public PessoaWrapper(Pessoa pessoa, Contato contato) {
        this.pessoa = pessoa;
        this.contato = contato;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }
}
