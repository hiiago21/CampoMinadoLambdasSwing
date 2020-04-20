package model.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.entities.Tabuleiro;

public class TelaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public TelaPrincipal() {
		
		Tabuleiro tab = criarTabuleiro();

		PainelTabuleiro painelTab = new PainelTabuleiro(tab);	
		add(painelTab);
		
		setTitle("Campo Minado");
		setSize(700,450);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public void instanciarTela(Tabuleiro tab) {
		
	}
	public static Tabuleiro criarTabuleiro() {
		try{
			Integer linhas = Integer.parseInt(JOptionPane.showInputDialog("Informe a quantidade de Linhas"));
			Integer colunas = Integer.parseInt(JOptionPane.showInputDialog("Informe a quantidade de Colunas"));
			Integer minas = Integer.parseInt(JOptionPane.showInputDialog("Informe a quantidade de Minas"));	
			Tabuleiro tabuleiro = new Tabuleiro(linhas,colunas, minas);
			return tabuleiro;
		}
		catch(Exception e){
				JOptionPane.showMessageDialog(null, "Tabuleiro Padrão Criado");
		}
		return new Tabuleiro(10, 10, 11);
		
	}

	public static void main(String[] args) {
		 new TelaPrincipal();
	}
}
