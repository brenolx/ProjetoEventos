package entities;

public class Mensagem {

    private String operacao;          // Tipo: String - A operação a ser realizada (ex: "cadastrarParticipante")
    private String nome;              // Tipo: String - Nome do participante
    private String senha;             // Tipo: String - Senha do participante
    private String email;             // Tipo: String - Email do participante
    private String dataNascimento;    // Tipo: String - Data de nascimento do participante (formato: "dd/MM/yyyy")
    private String cpf;               // Tipo: String - CPF do participante

    // Getters e Setters
    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Mensagem [operacao=" + operacao + ", nome=" + nome + ", senha=" + senha + ", email=" + email + 
               ", dataNascimento=" + dataNascimento + ", cpf=" + cpf + "]";
    }
}