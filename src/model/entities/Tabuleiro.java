package model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Tabuleiro implements CampoObservador{

	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Campo> campos = new ArrayList<>();
	private final List<Consumer<Boolean>> observadores = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampo();
		associarOsVizinhos();
		sortearMinas();
	}
	
	 public void incluirObservador(Consumer<Boolean> obervador) {
		 observadores.add(obervador);
	 }
	 
	 private void notificarObservadores(boolean resultado) {
		 observadores.stream().forEach(observador -> observador.accept(resultado));
	 }
	
	public void abrir(int linhas, int colunas) {
			campos.stream()
			.filter(c -> c.getLinha()==linhas && c.getColuna()==colunas)
			.findFirst()
			.ifPresent(c -> c.abrir());				
	}

	public void marcar(int linhas, int colunas) {
		campos.stream()
		.filter(c -> c.getLinha()==linhas && c.getColuna()==colunas)
		.findFirst().ifPresentOrElse(c -> c.alternarMarcacao(), null);
	}
	
	private void sortearMinas() {
		long minasArmadas = 0;
		
		do {
			int aleatorio = (int)(Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(c -> c.isMinado()).count();
		}while(minasArmadas < minas);
	}

	private void associarOsVizinhos() {
		for(Campo c1: campos) {
			for (Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}

	private void gerarCampo() {
		for (int i = 0; i <linhas; i++) {
			for (int j = 0; j < colunas; j++) {
				Campo campo = new Campo(i, j);
				campo.incluirObservador(this);
				campos.add(campo);
			}
			
		}
	}
	
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciar() {
		 campos.stream().forEach(c -> c.reiniciar());
		 sortearMinas();
	}

	@Override
	public void eventoOcorreu(Campo c, CampoEvento e) {
		
		if(e == CampoEvento.EXPLODIR) {
			mostrarMinas();
			notificarObservadores(false);
		}
		else if(objetivoAlcancado()) {
			notificarObservadores(true);
		}
	}
	
	private void mostrarMinas() {
		campos
		.stream()
		.filter(c -> c.isMinado())
		.filter(c -> !c.isMinado())
		.forEach(c -> c.setAberto(true));
	}

	
	
	
	public int getLinhas() {
		return linhas;
	}



	public int getColunas() {
		return colunas;
	}


	public void paraCada(Consumer<Campo> funcao) {
		campos.forEach(funcao);
	}
}
