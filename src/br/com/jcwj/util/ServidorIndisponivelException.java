package br.com.jcwj.util;

/**
 *
 * @author julio
 */
public class ServidorIndisponivelException extends Exception {

	private static final long serialVersionUID = 2903971963942255892L;

	public ServidorIndisponivelException(String url, Exception e) {
		super("Erro ao fazer requisição ao servidor na url " + url, e);
	}
}
