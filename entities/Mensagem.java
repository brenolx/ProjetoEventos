package entities;

public class Mensagem {

	private String operacao;
	private String nome;
	private String senha;
	private String email;
	
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
	@Override
	public String toString() {
		return "Mensagem [operacao=" + operacao + ", nome=" + nome + ", senha=" + senha + ", email=" + email + "]";
	}
	
	
}
