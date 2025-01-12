package entities;

import java.time.LocalDateTime;

import enuns.StatusInscricao;

public class Inscricao {
	private int id;
	private Evento evento;
	private Participante participante;
	private LocalDateTime dataInscricao;
	private StatusInscricao statusInscricao;
	private boolean presencaConfirmada;

	// Getters e Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public LocalDateTime getDataInscricao() {
		return dataInscricao;
	}

	public void setDataInscricao(LocalDateTime dataInscricao) {
		this.dataInscricao = dataInscricao;
	}

	public StatusInscricao getStatusInscricao() {
		return statusInscricao;
	}

	public void setStatusInscricao(StatusInscricao statusInscricao) {
		this.statusInscricao = statusInscricao;
	}

	public boolean isPresencaConfirmada() {
		return presencaConfirmada;
	}

	public void setPresencaConfirmada(boolean presencaConfirmada) {
		this.presencaConfirmada = presencaConfirmada;
	}

	@Override
	public String toString() {
		return "Inscricao [id=" + id + ", evento=" + evento + ", participante=" + participante + ", dataInscricao="
				+ dataInscricao + ", statusInscricao=" + statusInscricao + ", presencaConfirmada=" + presencaConfirmada
				+ "]";
	}
}
