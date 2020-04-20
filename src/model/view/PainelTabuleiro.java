package model.view;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.entities.Tabuleiro;

public class PainelTabuleiro extends JPanel{
	private static final long serialVersionUID = 1L;

	public PainelTabuleiro(Tabuleiro tabuleiro) {
		
		setLayout(new GridLayout(tabuleiro.getLinhas(), tabuleiro.getColunas()));
		
		tabuleiro.paraCada(c -> add(new BotaoCampo(c)));
		
		tabuleiro.incluirObservador(e -> {
			
			SwingUtilities.invokeLater(() -> {
				if(e.booleanValue()) {
					JOptionPane.showMessageDialog(null, "Você Ganhou");
				}
				else {
					JOptionPane.showMessageDialog(null, "Você Perdeu");
				}
				
				tabuleiro.reiniciar();
			});			
		});
	}		
}
