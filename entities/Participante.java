package entities;

import java.time.LocalDate;

public class Participante extends Usuario {
    private LocalDate dataNascimento;
    private String cpf;

    // Getters e Setters
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
