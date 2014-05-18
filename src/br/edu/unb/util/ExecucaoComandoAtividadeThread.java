/**
 * 
 */
package br.edu.unb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

/**
 * @author Wallace
 *
 */
public class ExecucaoComandoAtividadeThread extends Thread {
	
	private String linhaComando;
	private Writer consoleSaida;
	
	public ExecucaoComandoAtividadeThread(final String comando, final Writer console){
		this.linhaComando = comando;
		this.consoleSaida = console;
	}
	
	 @Override
	public void run() {
		try {
			Process processo = Runtime.getRuntime().exec(linhaComando);
			InputStream is = processo.getInputStream();
			InputStreamReader isreader = new InputStreamReader(is);
			BufferedReader input = new BufferedReader(isreader);			
			String linha = "";
			while ((linha = input.readLine()) != null) {
				consoleSaida.write(linha);
			}			
		} catch (IOException e) {
			try {
				consoleSaida.write("Erro:" + e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
