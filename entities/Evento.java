package entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import enuns.CategoriaEvento;
import enuns.StatusEvento;

public class Evento {
    private int id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataHora;
    private int duracaoHoras;
    private String local;
    private int capacidadeMaxima;
    private StatusEvento status;
    private CategoriaEvento categoria;
    private BigDecimal preco;
    private Administrador organizador;
    private List<Participante> participantes;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public int getDuracaoHoras() {
        return duracaoHoras;
    }

    public void setDuracaoHoras(int duracaoHoras) {
        this.duracaoHoras = duracaoHoras;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public StatusEvento getStatus() {
        return status;
    }

    public void setStatus(StatusEvento status) {
        this.status = status;
    }

    public CategoriaEvento getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEvento categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Administrador getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Administrador organizador) {
        this.organizador = organizador;
    }

	public List<Participante> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Participante> participantes) {
		this.participantes = participantes;
	}

	@Override
	public String toString() {
		return "Evento [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", dataHora=" + dataHora
				+ ", duracaoHoras=" + duracaoHoras + ", local=" + local + ", capacidadeMaxima=" + capacidadeMaxima
				+ ", status=" + status + ", categoria=" + categoria + ", preco=" + preco + ", organizador="
				+ organizador + ", participantes=" + participantes + "]";
	}
	
	
}