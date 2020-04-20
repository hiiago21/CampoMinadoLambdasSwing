package model.view;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import model.entities.Campo;
import model.entities.CampoEvento;
import model.entities.CampoObservador;

public class BotaoCampo extends JButton implements CampoObservador, AcoesMouse {
	private static final long serialVersionUID = 1L;
	
	private final Color BG_PADRAO =  new Color(184,184,184);
	private final Color BG_MARCAR =  new Color(8,179,247);
	private final Color BG_EXPLODIR =  new Color(189,66,68);
	private final Color TEXTO_VERDE =  new Color(0,100,0);
	
	
	private Campo campo;
	
	public BotaoCampo(Campo campo) {
		this.campo = campo;
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(1));
		
		addMouseListener(this);
		campo.incluirObservador(this);
	}

	@Override
	public void eventoOcorreu(Campo c, CampoEvento e) {
		switch(e) {
		case ABRIR:
			aplicarEstiloAbrir();
			break;
		
		case MARCAR:
			aplicarEstiloMarcar();
			break;
			
		case DESMARCAR:
			aplicarEstiloDesmarcar();
			break;
			
		case EXPLODIR:
			aplicarEstiloExplodir();
			break;
			
		default:
			aplicarEstiloPadrao();
			setBorder(BorderFactory.createBevelBorder(1));

		}
	}

	private void aplicarEstiloPadrao() {
		setBackground(BG_PADRAO);
		setText("");
	}

	private void aplicarEstiloExplodir() {
		setBackground(BG_EXPLODIR);
		setText("X");		
	}

	private void aplicarEstiloDesmarcar() {
		setBackground(BG_PADRAO);
		setText("");		
	}

	private void aplicarEstiloMarcar() {
		setBackground(BG_MARCAR);
		setText("M");
	}

	private void aplicarEstiloAbrir() {

		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		if(campo.isMinado()) {
			setBackground(BG_EXPLODIR);
			return;
		}
		
		setBackground(BG_PADRAO);
		switch (campo.minasNaVizinhanca()) {
		case 1:
			setForeground(TEXTO_VERDE);
			break;
			
		case 2:
			setForeground(Color.BLUE);
			break;
			
		case 3:
			setForeground(Color.YELLOW);
			break;
			
		case 4:
			setForeground(Color.ORANGE);
			break;
			
		case 5:
		case 6:
			setForeground(Color.RED);
			break;

		default:
			setForeground(Color.PINK);
			break;
		}
		
		String valor = !campo.vizinhoSeguro() ? campo.minasNaVizinhanca()+"" : "";
		
		setText(valor);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			campo.abrir();
		}
		else {
			campo.alternarMarcacao();;
		}
	}
	
	
}
