package servico;

import java.util.List;

import dao.ArtistaDao;
import dao.DaoFactory;
import dao.Transaction;
import dominio.Artista;

public class ArtistaServico {

	private ArtistaDao dao;

	public ArtistaServico() {
		dao = DaoFactory.criarArtistaDao();
	}

	public void inserirAtualizar(Artista x) throws ServicoException {
		// M�TODO ESPECIFICO DO JPA - EM.getLocalEm().getTransaction().begin();
		try {
			Artista aux = dao.buscaNomeExato(x.getNome());
			if(aux != null){
				throw new ServicoException("J� existe um artista com este nome!", 1);
			}
			
			Transaction.begin();
			dao.inserirAtualizar(x);
			Transaction.commit();
			// M�TODO ESPECIFICO DO JPA -
			// EM.getLocalEm().getTransaction().commit();
		} catch (RuntimeException e) {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
			System.out.println("Erro: " + e.getMessage());
		}

	}

	public void excluir(Artista x) {
		try {
			Transaction.begin();
			dao.excluir(x);
			Transaction.commit();

		} catch (RuntimeException e) {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
			System.out.println("Erro: " + e.getMessage());
		}

	}

	public Artista buscar(int cod) {
		return dao.buscar(cod);
	}

	public List<Artista> buscarTodos() {
		return dao.buscarTodos();
	}
	
	public List<Artista> buscarTodosOrdenadosPorNome(){
		return dao.buscarTodosOrdenadosPorNome();
	}

}
