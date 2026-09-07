package br.com.fiap.ms.educamais.exceptions;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String mensagem) {
        super(mensagem);
    }
}
