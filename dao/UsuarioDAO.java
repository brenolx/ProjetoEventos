package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Administrador;
import entities.Participante;
import entities.Usuario;
import enuns.TipoUsuario;

public class UsuarioDAO {

	private Connection conn;

	public UsuarioDAO(Connection conn) {
		this.conn = conn;
	}

	public boolean adicionarUsuario(Usuario usuario) throws SQLException {
		String query = "INSERT INTO usuarios (nome_completo, email, senha, tipo_usuario, cargo, data_contratacao, data_nascimento, cpf) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, usuario.getNomeCompleto());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getSenha());
			stmt.setString(4, usuario.getTipoUsuario().name());

			if (usuario instanceof Administrador) {
				Administrador admin = (Administrador) usuario;
				stmt.setString(5, admin.getCargo());
				stmt.setDate(6, java.sql.Date.valueOf(admin.getDataContratacao()));
				stmt.setDate(7, null); // Administrador não possui data de nascimento
				stmt.setString(8, null); // Administrador não possui CPF
			} else if (usuario instanceof Participante) {
				Participante participante = (Participante) usuario;
				stmt.setString(5, null); // Participante não possui cargo
				stmt.setDate(6, null); // Participante não possui data de contratação
				stmt.setDate(7, java.sql.Date.valueOf(participante.getDataNascimento()));
				stmt.setString(8, participante.getCpf());
			}

			stmt.executeUpdate();
			return true; // Cadastro bem-sucedido
		} catch (SQLException e) {
			throw new SQLException("Erro ao adicionar o usuário", e);
		}
	}

	public Usuario getUsuarioPorEmail(String email) throws SQLException {
		Usuario usuario = null;
		String query = "SELECT id, nome_completo, email, senha, tipo_usuario, cargo, data_contratacao, data_nascimento, cpf FROM usuarios WHERE email = ?";

		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				TipoUsuario tipoUsuario = TipoUsuario.valueOf(rs.getString("tipo_usuario"));
				if (tipoUsuario == TipoUsuario.ADMINISTRADOR) {
					usuario = new Administrador();
					((Administrador) usuario).setCargo(rs.getString("cargo"));
					if (rs.getDate("data_contratacao") != null) {
						((Administrador) usuario).setDataContratacao(rs.getDate("data_contratacao").toLocalDate());
					}
				} else if (tipoUsuario == TipoUsuario.PARTICIPANTE) {
					usuario = new Participante();
					if (rs.getDate("data_nascimento") != null) {
						((Participante) usuario).setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
					}
					((Participante) usuario).setCpf(rs.getString("cpf"));
				}
				usuario.setId(rs.getInt("id"));
				usuario.setNomeCompleto(rs.getString("nome_completo"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setTipoUsuario(tipoUsuario);
			}
		} catch (SQLException e) {
			throw new SQLException("Erro ao buscar usuário pelo email: " + e.getMessage());
		}

		return usuario;
	}

	public boolean validarEmail(String email) throws SQLException {
		String query = "SELECT * FROM usuarios WHERE email = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			return !rs.next(); // Retorna true se o email não existir
		} catch (SQLException e) {
			throw new SQLException("Erro ao validar email: " + e.getMessage());
		}
	}

	public List<Usuario> listarUsuarios() throws SQLException {
		List<Usuario> usuarios = new ArrayList<>();
		String query = "SELECT id, nome_completo, email, senha, tipo_usuario, cargo, data_contratacao, data_nascimento, cpf FROM usuarios";

		try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Usuario usuario;
				TipoUsuario tipoUsuario = TipoUsuario.valueOf(rs.getString("tipo_usuario"));
				if (tipoUsuario == TipoUsuario.ADMINISTRADOR) {
					usuario = new Administrador();
					((Administrador) usuario).setCargo(rs.getString("cargo"));
					if (rs.getDate("data_contratacao") != null) {
						((Administrador) usuario).setDataContratacao(rs.getDate("data_contratacao").toLocalDate());
					}
				} else {
					usuario = new Participante();
					if (rs.getDate("data_nascimento") != null) {
						((Participante) usuario).setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
					}
					((Participante) usuario).setCpf(rs.getString("cpf"));
				}
				usuario.setId(rs.getInt("id"));
				usuario.setNomeCompleto(rs.getString("nome_completo"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setTipoUsuario(tipoUsuario);
				usuarios.add(usuario);
			}

		} catch (SQLException e) {
			throw new SQLException("Erro ao listar usuários: " + e.getMessage());
		}

		return usuarios;
	}

	public Usuario getUsuarioPorId(int id) throws SQLException {
		Usuario usuario = null;
		String query = "SELECT id, nome_completo, email, senha, tipo_usuario, cargo, data_contratacao, data_nascimento, cpf FROM usuarios WHERE id = ?";

		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				TipoUsuario tipoUsuario = TipoUsuario.valueOf(rs.getString("tipo_usuario"));
				if (tipoUsuario == TipoUsuario.ADMINISTRADOR) {
					usuario = new Administrador();
					((Administrador) usuario).setCargo(rs.getString("cargo"));
					if (rs.getDate("data_contratacao") != null) {
						((Administrador) usuario).setDataContratacao(rs.getDate("data_contratacao").toLocalDate());
					}
				} else if (tipoUsuario == TipoUsuario.PARTICIPANTE) {
					usuario = new Participante();
					if (rs.getDate("data_nascimento") != null) {
						((Participante) usuario).setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
					}
					((Participante) usuario).setCpf(rs.getString("cpf"));
				}
				usuario.setId(rs.getInt("id"));
				usuario.setNomeCompleto(rs.getString("nome_completo"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setTipoUsuario(tipoUsuario);
			}
		} catch (SQLException e) {
			throw new SQLException("Erro ao buscar usuário pelo ID: " + e.getMessage());
		}

		return usuario;
	}

	public boolean removerUsuario(int usuarioId) {
		String query = "DELETE FROM usuarios WHERE id = ?";
		PreparedStatement stmt = null;

		try {

			// Preparar a instrução SQL
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, usuarioId); // Define o ID do usuário a ser removido

			// Executar a atualização
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0; // Retorna true se o usuário foi removido com sucesso
		} catch (SQLException e) {
			System.err.println("Erro ao remover usuário: " + e.getMessage());
			return false; // Retorna false em caso de erro
		} finally {
			// Fechar a conexão e o PreparedStatement
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					BancoDados.desconectar();
				}
			} catch (SQLException e) {
				System.err.println("Erro ao fechar recursos: " + e.getMessage());
			}
		}
	}

	// Método para atualizar um usuário
	public boolean atualizarUsuario(Usuario usuario) throws SQLException {
		String query = "UPDATE usuarios SET nome_completo = ?, email = ?, tipo_usuario = ?, cargo = ?, data_contratacao = ?, data_nascimento = ?, cpf = ? WHERE id = ?";

		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, usuario.getNomeCompleto());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getTipoUsuario().name()); // Define o tipo de usuário

			if (usuario instanceof Administrador) {
				Administrador admin = (Administrador) usuario;
				stmt.setString(4, admin.getCargo());
				stmt.setDate(5, java.sql.Date.valueOf(admin.getDataContratacao())); // Data de contratação
				stmt.setDate(6, null); // Administrador não possui data de nascimento
				stmt.setString(7, null); // Administrador não possui CPF
			} else if (usuario instanceof Participante) {
				Participante participante = (Participante) usuario;
				stmt.setString(4, null); // Participante não possui cargo
				stmt.setDate(5, null); // Participante não possui data de contratação
				stmt.setDate(6, java.sql.Date.valueOf(participante.getDataNascimento())); // Data de nascimento
				stmt.setString(7, participante.getCpf()); // CPF do participante
			}

			stmt.setInt(8, usuario.getId()); // ID do usuário a ser atualizado

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0; // Retorna true se a atualização foi bem-sucedida
		} catch (SQLException e) {
			throw new SQLException("Erro ao atualizar usuário: " + e.getMessage(), e);
		}
	}
}