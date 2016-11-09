package principais;
import java.util.ArrayList;
import java.util.List;

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
	
	private EstoqueManager(){
		//Vou criar ums livros só pra né...
		this.livros = new ArrayList<Livro>();
		this.livros.add(new Livro("Ciencias", Editora.ED_ATICA, 0));
		this.livros.add(new Livro("Circuitos", Editora.EDITORA_CONSTRUIR, 100));
	}
	
	public static EstoqueManager getInstance(){
		if(estoqueManager == null)
			estoqueManager = new EstoqueManager();
		
		return estoqueManager;
	}
	
	public List<Livro> getLivros(){
		return this.livros;
	}
	
	public List<Livro> getLivrosDeUmaEditora(Editora editora){
		List<Livro> livrosDeEditora = new ArrayList<Livro>();
		for(int i = 0; i < this.getLivros().size(); i++){
			if(this.getLivros().get(i).getEditora().getNome() ==  editora.getNome()){
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
	
	public int gerarId(Editora editora){
		int id = getLivrosDeUmaEditora(editora).size() + editora.getIdInicial();
		return id;
	}
	
	public Livro getLivroPeloNome(String nome){
		nome = nome.toUpperCase();
		String nomeSemUmEspaco = nome.substring(0, nome.length() - 1);
		
		Livro livro = new Livro("LivroInexistente", Editora.ED_ATICA);
		for(int i = 0; i < this.livros.size() ; i++){
			if(nome.equals(this.livros.get(i).getNome().toUpperCase()) || nomeSemUmEspaco.equals(this.livros.get(i).getNome().toUpperCase())){
				livro = this.livros.get(i);
				break;
			}
		}
		
		return livro;
	}
	

}
