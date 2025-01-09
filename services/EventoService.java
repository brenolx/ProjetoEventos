package services;

import dao.BancoDados;
import dao.EventoDAO;
import entities.Evento;
import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EventoService {

    // Método para adicionar um evento
    public boolean adicionarEvento(Evento evento) throws IOException {
        Connection conn = null;
        try {
            // Conectar ao banco de dados
            conn = BancoDados.conectar();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.",
                        "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Criar uma instância do EventoDAO
            EventoDAO eventoDAO = new EventoDAO(conn);
            return eventoDAO.adicionarEvento(evento); // Adiciona o evento ao banco de dados
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar evento: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return false; // Retorna false em caso de erro
        } finally {
            // Desconectar do banco de dados
            try {
                if (conn != null) {
                    BancoDados.desconectar();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao desconectar do banco de dados: " + e.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para listar eventos por status
    public List<Evento> listarEventos(String status) throws IOException {
        Connection conn = null;
        try {
            // Conectar ao banco de dados
            conn = BancoDados.conectar();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.",
                        "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Criar uma instância do EventoDAO
            EventoDAO eventoDAO = new EventoDAO(conn);
            return eventoDAO.listarEventos(status); // Retorna a lista de eventos
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar eventos: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return null; // Retorna null em caso de erro
        } finally {
            // Desconectar do banco de dados
            try {
                if (conn != null) {
                    BancoDados.desconectar();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao desconectar do banco de dados: " + e.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para listar todos os eventos
    public List<Evento> listarEventos() throws IOException {
        Connection conn = null;
        try {
            // Conectar ao banco de dados
            conn = BancoDados.conectar();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.",
                        "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Criar uma instância do EventoDAO
            EventoDAO eventoDAO = new EventoDAO(conn);
            return eventoDAO.listarEventos(); // Retorna a lista de eventos
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar eventos: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return null; // Retorna null em caso de erro
        } finally {
            // Desconectar do banco de dados
            try {
                if (conn != null) {
                    BancoDados.desconectar();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao desconectar do banco de dados: " + e.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public Evento buscarEventoPorId(int id) throws IOException {
        Connection conn = null;
        try {
            conn = BancoDados.conectar();
            if (conn == null) {
                throw new SQLException("Conexão com o banco de dados não foi estabelecida.");
            }
            EventoDAO eventoDAO = new EventoDAO(conn);
            return eventoDAO.buscarEventoPorId(id); // Método que busca o evento pelo ID
        } catch (SQLException e) {
            throw new IOException("Erro ao buscar evento: " + e.getMessage(), e);
        } finally {
            try {
                if (conn != null) {
                    BancoDados.desconectar();
                }
            } catch (SQLException e) {
                throw new IOException("Erro ao desconectar do banco de dados: " + e.getMessage(), e);
            }
        }
    }

    // Método para atualizar um evento
    public boolean atualizarEvento(Evento evento) throws IOException {
        Connection conn = null;
        try {
            // Conectar ao banco de dados
            conn = BancoDados.conectar();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Erro : Conexão com o banco de dados não foi estabelecida.",
                        "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Criar uma instância do EventoDAO
            EventoDAO eventoDAO = new EventoDAO(conn);
            return eventoDAO.atualizarEvento(evento); // Atualiza o evento no banco de dados
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar evento: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return false; // Retorna false em caso de erro
        } finally {
            // Desconectar do banco de dados
            try {
                if (conn != null) {
                    BancoDados.desconectar();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao desconectar do banco de dados: " + e.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para remover um evento
    public boolean removerEvento(int id) throws IOException {
        Connection conn = null;
        try {
            // Conectar ao banco de dados
            conn = BancoDados.conectar();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.",
                        "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Criar uma instância do EventoDAO
            EventoDAO eventoDAO = new EventoDAO(conn);
            return eventoDAO.removerEvento(id); // Remove o evento do banco de dados
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover evento: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return false; // Retorna false em caso de erro
        } finally {
            // Desconectar do banco de dados
            try {
                if (conn != null) {
                    BancoDados.desconectar();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao desconectar do banco de dados: " + e.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para modificar o status de um evento
    public boolean modificarStatusEvento(int id, String novoStatus) throws IOException {
        Connection conn = null;
        try {
            // Conectar ao banco de dados
            conn = BancoDados.conectar();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.",
                        "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Criar uma instância do EventoDAO
            EventoDAO eventoDAO = new EventoDAO(conn);
            return eventoDAO.modificarStatusEvento(id, novoStatus); // Modifica o status do evento no banco de dados
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao modificar status do evento: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return false; // Retorna false em caso de erro
        } finally {
            // Desconectar do banco de dados
            try {
                if (conn != null) {
                    BancoDados.desconectar();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao desconectar do banco de dados: " + e.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}