package principais;

public enum AnoEscolar {
	JARDIM_1("Jardim 1"), JARDIM_2("Jardim 2"), JARDIM_3("Jardim 3"), ANO_1("1º Ano"), ANO_2("2º Ano"), ANO_3("3º Ano"),
	ANO_4("4º Ano"), ANO_5("5º Ano"), ANO_6("6º Ano"), ANO_7("7º Ano"), ANO_8("8º Ano"), ANO_9("9º Ano");
	
	private String nome;
	
	AnoEscolar(String nome){
		this.setNome(nome);
	}
	
	private void setNome(String nome){
		this.nome = nome;
	}
	public String getNome(){
		return this.nome;
	}
	
	public static AnoEscolar getAnoEscolarDeUmaString(String valor){
		AnoEscolar anoEscolar = AnoEscolar.JARDIM_1;
		for(int i = 0; i < AnoEscolar.values().length; i++){
			if(valor == AnoEscolar.values()[i].getNome()){
				anoEscolar = AnoEscolar.values()[i];
			}
		}
		
		return anoEscolar;
	}
}