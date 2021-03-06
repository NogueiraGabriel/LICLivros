package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import principais.Cliente;
import principais.ClienteManager;
import principais.Pedido;
import principais.TipoPedido;
import utilidades.AutoSuggestor;
import utilidades.Screen;
import utilidades.ServicoDeDigito;

public class TelaPedidoCliente extends JPanel implements IPrepararComponentes {

	private GUIManager guiManager;
	AutoSuggestor autoSuggestor;
	private Boolean clienteValido = false;
	private Cliente cliente;
	JTextField[] textFields;
	ServicoDeDigito servicoDeDigito;
	
	public TelaPedidoCliente(GUIManager guiManager){
		this.guiManager = guiManager;
		
		this.setLayout(new GridBagLayout());
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        GridBagConstraints c = new GridBagConstraints();
        
        c.weightx = 1;
        c.weighty = 1;
        
       for(int i = 0; i < 24; i ++){
        	for(int j = 0; j < 20; j++){
        		c.gridx = i;
        		c.gridy = j;
        		c.fill = GridBagConstraints.BOTH;
        		this.add(new JLabel(""), c);
        	}
        }
        
        
        JLabel txt_Title = new JLabel("Pedido - Passo 1", SwingConstants.CENTER);
  		txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
  		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
  		c.gridx = 0;
  		c.gridy = 0;
  		c.gridwidth = 24;
  		c.gridheight = 5;
  		c.anchor = GridBagConstraints.PAGE_START;
  		this.add(txt_Title, c);
  		
  		textFields = new JTextField[6];
  		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 6;
		c.gridwidth = 10;
		c.fill = GridBagConstraints.HORIZONTAL;
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new JTextField();
			c.gridy = i * 2 + 2 ;
			if(i > 0){
				textFields[i].setEditable(false);
				textFields[i].setBackground(Color.LIGHT_GRAY);
			}
			this.add(textFields[i],c);
		}
  		
  		 String[] columnNames = {"NOME",
                 "BAIRRO",
                 "RUA",
                 "COMPLEMENTO",
                 "TELEFONE",
                 "CELULAR"};
		JLabel[] labels = new JLabel[6];
		c.gridx = 2;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_END;
		for(int i = 0; i < textFields.length; i++){
			labels[i] = new JLabel(columnNames[i]);
			c.gridy = i  * 2 + 2;
			labels[i].setFont(labels[i].getFont().deriveFont(15F));
			this.add(labels[i],c);
		}
		
		JButton btn_Avancar = new JButton("Avançar");
		c.gridx = 23;
		c.fill = GridBagConstraints.BOTH;
		c.gridy = 19;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Avancar, c);
		
		JButton btn_Voltar = new JButton("Voltar");
		c.gridx = 0;
		c.gridy = 19;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Voltar, c);
        
		autoSuggestor = new AutoSuggestor(textFields[0], guiManager.getJanela(), ClienteManager.getInstance().getTodosNomesClientes(), 
				Color.white.brighter(), Color.blue, Color.red, 0.75f);
		
		
		textFields[0].getDocument().addDocumentListener(new DocumentListener() {	
			@Override
			public void removeUpdate(DocumentEvent e) {
				checarNome(textFields[0].getText(), textFields);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				checarNome(textFields[0].getText(), textFields);
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				checarNome(textFields[0].getText(), textFields);
			}
		});
		
		btn_Avancar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				avancar();
			}
		});
		
		btn_Voltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limparCampos();
				guiManager.mudarParaTela("telaInicial");
			}
		});
		
	}
	
	private void checarNome(String nome, JTextField[] textFields){
		if(nome.length() == 0)
			return;
		
		List<String> todosNomesClientes = ClienteManager.getInstance().getTodosNomesClientes();
		if(todosNomesClientes.contains(nome))
		{
			atualizarCampos(textFields, nome);
		}
		else if(todosNomesClientes.contains(nome.substring(0, nome.length()-1)))
		{
			atualizarCampos(textFields,nome.substring(0, nome.length()-1));
		}
		else
			limparCampos(textFields);
	}
	
	private void atualizarCampos(JTextField[] campos, String nomeCliente){
		this.clienteValido = true;
		cliente = ClienteManager.getInstance().getClientePeloNome(nomeCliente);
		Object[] parametros = cliente.pegarTodosParametros();
		
		for(int i = 1; i < campos.length; i++){
			campos[i].setText(parametros[i+1].toString());
		}
	}
	
	private void limparCampos(JTextField[] campos){
		this.clienteValido = false;
		for(int i = 1; i < campos.length; i++)
			campos[i].setText("");
	}
	
	private void avancar(){
		if(clienteValido == true && this.cliente != null){
			System.out.println("Cliente Válido. Podemos avançar!");
			atualizarPedido();
			if(Pedido.tipoProximoPedido == TipoPedido.NORMAL)
				guiManager.mudarParaTela("telaPedidoPacote");
			else if(Pedido.tipoProximoPedido == TipoPedido.AVULSO){
				guiManager.mudarParaTela("telaPedidoPacoteAvulso");
			}
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Insira um cliente","Cliente Inválido", JOptionPane.OK_CANCEL_OPTION);
		}
	}
	
	private void atualizarPedido(){
		Pedido.pedidoAtual = new Pedido(this.cliente); 
	}
	
	@Override
	public void prepararComponentes(){
		autoSuggestor = new AutoSuggestor(autoSuggestor.getTextField(), guiManager.getJanela(), ClienteManager.getInstance().getTodosNomesClientes(), 
										  Color.white.brighter(), Color.blue, Color.red, 0.75f);
		
	}
	
	public void limparCampos(){
		if(textFields.length > 0){
			servicoDeDigito = new ServicoDeDigito();
			servicoDeDigito.limparCampos(textFields);
		}
	}
}
