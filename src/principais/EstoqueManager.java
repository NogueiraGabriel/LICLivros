package principais;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bd.JavaConnection;
import principais.Livro;
/**
 * Então essa class aqui deve pegar todos os livros na Database assim que iniciar
 * Deve ter um método salvar que vai jogar todo o array de livro na dataBase de novo --> apaga toda a database e subir de novo!
 * (Isso por que vão ter livros novos e editados, daí pra não conferir apenas joga tudo de novo)
 * 
 * @author felcks
 *
 */
public class EstoqueManager {
	private static EstoqueManager estoqueManager;
	private List<Livro> livros;
	private Statement stmt;
	private Connection conn = null;
	
	private EstoqueManager(){
		this.livros = new ArrayList<Livro>();
	}
	
	public static EstoqueManager getInstance(){
		if(estoqueManager == null)
			estoqueManager = new EstoqueManager();
		
		return estoqueManager;
	}
	
	public List<Livro> getLivros(){
		return this.livros;
	}
	
	public Livro getLivroPeloId(int id){
		return this.livros.get(id);
	}
	
	public void removerLivro(int id){
		this.livros.remove(id);
	}
	
	public void atualizarLivro(int index, Livro livro){
		this.livros.set(index, livro);
	}
	
	public void reorganizarLista(){
		for(int i = 0; i < this.livros.size(); i++){
			this.livros.get(i).setId(i);
		}
	}
	
	public void getLivrosDoBancoDeDados(){
		try{
			conn = JavaConnection.getInstance().connection;
			stmt = conn.createStatement();
			
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM LIVROS");
			livros.clear();
			while (resultSet.next()){
				Livro livro = new Livro(resultSet);
				this.livros.add(livro);
			}
			resultSet.close();
			stmt.close();
		}catch(Exception e){}
	}
	
	public List<Livro> getLivrosDeUmaEditora(String editora){
		List<Livro> livrosDeEditora = new ArrayList<Livro>();
		for(int i = 0; i < this.getLivros().size(); i++){
			if(this.getLivros().get(i).getEditora().equals(editora)){
				livrosDeEditora.add(this.getLivros().get(i));
			}
		}

		return livrosDeEditora;
	}
	
	public String[] getTodosOsNomesDosLivros(){
		String[] todosOsNomesDosLivros = new String[this.livros.size()];
		for(int i = 0; i < todosOsNomesDosLivros.length; i++){
			todosOsNomesDosLivros[i] = this.livros.get(i).getNome();
		}
		
		return todosOsNomesDosLivros;
	}
	
	public void adicionarNovoLivro(Livro livro){
		this.livros.add(livro);
	}
	
	public int gerarId(){
		/*int id = 0;
		if(getLivrosDeUmaEditora(editora).size() != 0){
			for(int i = 0; i < getLivrosDeUmaEditora(editora).size(); i++){
				if(getLivrosDeUmaEditora(editora).get(i).getId() + 1 != getLivrosDeUmaEditora(editora).get(i+1).getId()){
					id = getLivrosDeUmaEditora(editora).get(i).getId() + 1;
					break;
				}else{
					//id = getLivrosDeUmaEditora(editora).size() + editora.getIdInicial();
				}
			}
		}else
			//id = getLivrosDeUmaEditora(editora).size() + editora.getIdInicial();
		
		System.out.println(id);*/
		
		return 0;
	}
	
	public Livro getLivroPeloNome(String nome){
		nome = nome.toUpperCase();
		String nomeSemUmEspaco = nome.substring(0, nome.length() - 1);
		
		Livro livro = new Livro("LivroInexistente");
		for(int i = 0; i < this.livros.size() ; i++){
			if(nome.equals(this.livros.get(i).getNome().toUpperCase()) || nomeSemUmEspaco.equals(this.livros.get(i).getNome().toUpperCase())){
				livro = this.livros.get(i);
				break;
			}
		}
		
		return livro;
	}
	

}
