package services;

import userinterfaces.TelaGerenciamentoEventos;

public class LoginService {

	
	public void logarUsuario(String email, String senha) {

		if(email.equals("breno")) {
			
			TelaGerenciamentoEventos telaAdmin = new TelaGerenciamentoEventos();
			telaAdmin.setVisible(true);
			
		} else {
			System.out.println(email);
		}
	}
	
	
}
