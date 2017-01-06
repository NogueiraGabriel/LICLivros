package principais;

import java.sql.*;
import java.text.NumberFormat;

public class Livro {
	
	private int id;
	private String nome;
	private String editora;
	private int quantidade;
	private int comprar;
	private int vendidos;
	private double preco;
	
	public Livro(ResultSet rs)
	{
		try{
			int id = rs.getInt("ID");
			this.setId(id);
			String nome = rs.getString("NOME");
			this.setNome(nome);
			String editora = rs.getString("EDITORA");
			this.setEditora(editora);
			int quantidade = rs.getInt("QUANTIDADE");
			this.setQuantidade(quantidade);
			int comprar = rs.getInt("COMPRAR");
			this.setComprar(comprar);
			int vendidos = rs.getInt("VENDIDOS");
			this.setVendidos(vendidos);
			double preco = rs.getDouble("PRECO");
			this.setPreco(preco);
		}catch(Exception e){}
	}
	
	public Livro(String[] campos){
		try{
			this.setNome(campos[1]);
			this.setEditora(campos[2]);
			
			this.setQuantidade(0);
			this.setQuantidade(Integer.parseInt(campos[3]));
		}
		catch (Exception e){}
		
		try{
			this.setComprar(0);
			this.setComprar(Integer.parseInt(campos[4]));
		}
		catch(Exception e){}
		
		try{
			this.setVendidos(0);
			this.setVendidos(Integer.parseInt(campos[5]));
		}
		catch(Exception e){}
		
		try{
			this.setPreco(0);
			this.setPreco(Double.parseDouble(campos[6]));
		}
		catch(Exception e){}
	}
	
	public Livro(String nome){
		this.setNome(nome);
	}
	
	public Livro(int id, String nome, String editora, int quantidade, int comprar, int vendidos, double preco){
		this.setId(id);
		this.setNome(nome);
		this.setEditora(editora);
		this.setQuantidade(quantidade);
		this.setComprar(comprar);
		this.setVendidos(vendidos);
		this.setPreco(preco);
	}
	
	public Boolean isValidLivro(){
		Object[] object = pegarTodosParametros();
		
		if(this.getId() < 0)
			return false;
		
		for(int i = 1; i < object.length - 1; i++){
			if(object[i].toString().length() == 0)
				return false;
		}
		
		return true;
	}
	
	public Object[] pegarTodosParametros(){
		Object[] todosParametros = new Object[7];
		todosParametros[0] = this.getId();
		todosParametros[1] = this.getNome();
		todosParametros[2] = this.getEditora();
		todosParametros[3] = this.getQuantidade();
		todosParametros[4] = this.getComprar();
		todosParametros[5] = this.getVendidos();
		NumberFormat nf = NumberFormat.getCurrencyInstance();  
		String formatado = nf.format (this.getPreco());
		todosParametros[6] = formatado;
		return todosParametros;
	}
	public Object[] pegarTodosParametrosParaEstoque(){
		Object[] todosParametros = new Object[7];
		todosParametros[0] = this.getId();
		todosParametros[1] = this.getNome();
		todosParametros[2] = this.getEditora();
		todosParametros[3] = this.getQuantidade();
		todosParametros[4] = this.getComprar();
		todosParametros[5] = this.getVendidos();
		todosParametros[6] = this.getPreco();
		return todosParametros;
	}
	public Object[] pegarTodosParametrosValidos(){
		Object[] todosParametros = new Object[7];
		todosParametros[0] = this.getId();
		todosParametros[1] = this.getNome();
		todosParametros[2] = this.getEditora();
		todosParametros[3] = (this.getQuantidade() == -999) ? "" : this.getQuantidade();
		todosParametros[4] = (this.getComprar() == -999) ? "" : this.getComprar();
		todosParametros[5] = this.getVendidos();
		todosParametros[6] = this.getPreco();
		return todosParametros;
	}
	public Object[] pegarParametrosParaPacote(){
		Object[] todosParametros = new Object[4];
		todosParametros[0] = this.getId();
		todosParametros[1] = this.getNome();
		todosParametros[2] = this.getEditora();
		NumberFormat nf = NumberFormat.getCurrencyInstance();  
		String formatado = nf.format (this.getPreco());
		todosParametros[3] = formatado;
		return todosParametros;
	}
	public Object[] pegarParametrosParaPedido(){
		Object[] todosParametros = new Object[4];
		todosParametros[0] = this.getNome();
		todosParametros[1] = this.getEditora();
		todosParametros[2] = 1;
		NumberFormat nf = NumberFormat.getCurrencyInstance();  
		String formatado = nf.format (this.getPreco());
		todosParametros[3] = formatado;
		return todosParametros;
	}
	public void setarTodosParametros(Object[] object){
		try{
			this.setNome(object[1].toString());
			this.setEditora(object[2].toString());
			this.setComprar(Integer.parseInt(object[3].toString()));
			this.setQuantidade(Integer.parseInt(object[4].toString()));
			this.setVendidos(Integer.parseInt(object[5].toString()));
			System.out.println(object[6].toString() + "zzzzzzz");
			this.setPreco(Double.parseDouble(object[6].toString()));
		}
		catch (Exception e){}
	}
	
	public Object[] pegarParametrosDePacote(){
		Object[] parametrosDePacote = new Object[4];
		parametrosDePacote[0] = this.getId();
		parametrosDePacote[1] = this.getNome();
		parametrosDePacote[2] = this.getEditora();
		NumberFormat nf = NumberFormat.getCurrencyInstance();  
		String formatado = nf.format (this.getPreco());
		parametrosDePacote[3] = formatado;
		return parametrosDePacote;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEditora() {
		return editora;
	}
	public void setEditora(String editora) {
		this.editora = editora;
	}

	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}


	public int getComprar() {
		return comprar;
	}
	public void setComprar(int comprar) {
		this.comprar = comprar;
	}
	
	public int getVendidos(){
		return vendidos;
	}
	public void setVendidos(int vendidos){
		this.vendidos = vendidos;
	}

	public double getPreco(){
		return this.preco;
	}
	public void setPreco(double preco){
		this.preco = preco;
	}
}
