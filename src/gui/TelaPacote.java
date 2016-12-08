package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import bd.OperacoesLivros;
import bd.OperacoesPacotes;
import principais.AnoEscolar;
import principais.ClienteManager;
import principais.EditoraManager;
import principais.Escola;
import principais.EscolaManager;
import principais.EstoqueManager;
import principais.Livro;
import principais.Pacote;
import principais.PacoteManager;
import utilidades.Acao;
import utilidades.Screen;
import utilidades.ServicoDeDigito;

public class TelaPacote extends JPanel implements IPrepararComponentes {
	
	private GUIManager guiManager;
	private ServicoDeDigito servicoDeDigito;
	JTable table;
	JComboBox comboBox;
	private Escola escolaSelecionada;
	private AnoEscolar anoEscolarSelecionado;
	
	public TelaPacote(GUIManager guiManager) {
		this.guiManager = guiManager;
		this.servicoDeDigito = new ServicoDeDigito();
		
		this.setLayout(new GridBagLayout());
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		 GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        
        for(int i = 0; i < 6; i ++){
        	for(int j = 0; j < 10; j++){
        		c.gridx = i;
        		c.gridy = j;
        		c.fill = GridBagConstraints.BOTH;
        		this.add(new JLabel(""), c);
        	}
        }
        
        JTextField[] textFields = new JTextField[2];
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridy = 7;
		c.fill = GridBagConstraints.HORIZONTAL;
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new JTextField();
			c.gridx = i + 2;
			this.add(textFields[i],c);
		}
		
		String[] columnNames = {"ID", "NOME"};
		JLabel[] labels = new JLabel[2];
		c.gridy = 6;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		for(int i = 0; i < textFields.length; i++){
			labels[i] = new JLabel(columnNames[i]);
			c.gridx = i + 2;
			this.add(labels[i],c);
		}
        
        JLabel txt_Title = new JLabel("PACOTE", SwingConstants.CENTER);
        txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 6;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
		
		String[] todasEscolas = EscolaManager.getInstance().getTodosNomesEscolas();
		comboBox = new JComboBox(todasEscolas);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		this.add(comboBox,c);
		
		String[] todosAnos = AnoEscolar.getTodosNomesAnosEscolares();
		JComboBox comboBoxAno = new JComboBox(todosAnos);
		c.gridx = 5;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		this.add(comboBoxAno,c);
		
		this.escolaSelecionada = new Escola(comboBox.getSelectedItem().toString());
		this.anoEscolarSelecionado = AnoEscolar.getAnoEscolarPeloNome(comboBoxAno.getSelectedItem().toString());
		
		this.table = new JTable(new MyTableModelPacote(escolaSelecionada, anoEscolarSelecionado));
		table.getColumnModel().getColumn(0).setMinWidth(30);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		JScrollPane scrollPane  = new JScrollPane(this.table);
		table.setFillsViewportHeight(true);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 6;
		c.gridheight = 5;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		this.add(scrollPane, c);
		
		
		String[] acoes = new String[Acao.values().length - 2];
		for(int i = 0; i < acoes.length; i++)
			acoes[i] = Acao.values()[i].name();
		JComboBox comboBoxAcoes = new JComboBox(acoes);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 2;
		c.gridy = 8;
		this.add(comboBoxAcoes, c);
		
		JButton btn_fazerAcao = new JButton("Fazer Ação!");
		c.fill = GridBagConstraints.HORIZONTAL;;
		c.gridx = 3;
		c.gridy = 8;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_fazerAcao, c);
		
		JButton btn_Salvar = new JButton("Salvar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 5;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Salvar, c);
		
		JButton btn_Voltar = new JButton("Voltar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Voltar, c);
		
	
		btn_Voltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.mudarParaTela("telaInicial");
			}
		});
		
		comboBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(comboBox != null)
					if(comboBox.getItemCount() > 0)
						escolaSelecionada = new Escola(comboBox.getSelectedItem().toString());
				
				repintarTabela();
			}
		});
		comboBoxAno.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				anoEscolarSelecionado = AnoEscolar.getAnoEscolarPeloNome(comboBoxAno.getSelectedItem().toString());
				repintarTabela();
			}
		});
		
		btn_fazerAcao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Acao acao = Acao.NENHUMA;
				acao = Acao.valueOf(comboBoxAcoes.getSelectedItem().toString());
				fazerAcao(textFields, table, acao);
			}
		});
		
		btn_Salvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				OperacoesPacotes operacoesPacotes = new OperacoesPacotes();
				operacoesPacotes.INSERT_TODOSPACOTES(PacoteManager.getInstance().getPacotes());
			}
		});

		//table.setDefaultRenderer(Boolean.class, new CustomCellRenderer());		
		
		this.guiManager.getCards().add(this, "telaRegistrarLivro");
	}
	
	@Override
	public void prepararComponentes(){
		comboBox.removeAllItems();
		String[] todasEscolas = new String[EscolaManager.getInstance().getEscolas().size()];
		for(int i = 0; i < EscolaManager.getInstance().getEscolas().size(); i++){
			todasEscolas[i] = EscolaManager.getInstance().getEscolas().get(i).getNome();
			comboBox.addItem(todasEscolas[i]);
		}

		this.repintarTabela();
	}
	
	private void fazerAcao(JTextField[] textFields, JTable table, Acao acao){
		String[] camposEmTexto = servicoDeDigito.transformarCamposEmTexto(textFields);
		
		if(acao == Acao.ADICIONAR){
			Livro livro = EstoqueManager.getInstance().getLivroPeloNome(camposEmTexto[1]);
			if(livro.getNome().equals("LivroInexistente"))
			{
				JOptionPane.showMessageDialog(this, "Esse livro não consta na tabela de livros.","Erro ao adicionar", JOptionPane.OK_CANCEL_OPTION);
			}
			else if(PacoteManager.getInstance().getPacote(escolaSelecionada, anoEscolarSelecionado).getLivros().contains(livro))
			{
				JOptionPane.showMessageDialog(this, "Esse livro já está inserido nesse pacote.","Erro ao adicionar", JOptionPane.OK_CANCEL_OPTION);
			}
			else
			{
				JOptionPane.showConfirmDialog(this, "Confirmar a adição do livro: " + livro.getNome(), "Confirmar Adição", JOptionPane.OK_CANCEL_OPTION);
				PacoteManager.getInstance().getPacote(escolaSelecionada, anoEscolarSelecionado).adicionarLivro(livro);
				this.repintarTabela();
			}
		}
		else if(acao == Acao.REMOVER){
			String idSelecionado = camposEmTexto[0];
			int id = -1;
			id = servicoDeDigito.transformarStringEmInt(idSelecionado);
			if(id >= 0){
				Pacote pacoteAtual = PacoteManager.getInstance().getPacote(escolaSelecionada, anoEscolarSelecionado);
				Livro livro = pacoteAtual.getLivroPeloId(id);
				if(livro.getNome().equals("LivroInexistente")){
					JOptionPane.showMessageDialog(this, "Esse livro não pertence a esse pacote.","Erro ao remover", JOptionPane.OK_CANCEL_OPTION);
				}
				else{
					JOptionPane.showConfirmDialog(this, "Remover livro: " + livro.getNome(), "Confirmar Remoção", JOptionPane.OK_CANCEL_OPTION);
					pacoteAtual.removerLivro(livro);
					this.repintarTabela();
				}
			}
			else{
				JOptionPane.showMessageDialog(this, "Insira um id válido.","Erro ao remover", JOptionPane.OK_CANCEL_OPTION);
			}
		}
	}
	
	private void repintarTabela(){
		if(this.table != null){
			((MyTableModelPacote)this.table.getModel()).updateData(escolaSelecionada, anoEscolarSelecionado);
			this.table.repaint();
		}
	}
	
}



class MyTableModelPacote extends AbstractTableModel {
	
	private static Boolean DEBUG;
	
    private String[] columnNames = {"ID",
                                    "NOME",
                                    "EDITORA",
                                    "PREÇO"};
   
    private Object[][] data;
    
    public MyTableModelPacote(Escola escola, AnoEscolar anoEscolar){
    	updateData(escola, anoEscolar);
    }
    
    public void updateData(Escola escola, AnoEscolar anoEscolar){
    	data = new Object[PacoteManager.getInstance().getPacote(escola, anoEscolar).getLivros().size()][];
    	for(int i = 0; i < data.length; i++){
    		data[i] = PacoteManager.getInstance().getPacote(escola, anoEscolar).getLivros().get(i).pegarParametrosDePacote();
    	}	
    }
    
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 2) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                               + " to " + value
                               + " (an instance of "
                               + value.getClass() + ")");
        }

        data[row][col] = value;
        fireTableCellUpdated(row, col);

        if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + data[i][j]);
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}

