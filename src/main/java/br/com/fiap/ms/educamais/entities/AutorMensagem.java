package br.com.fiap.ms.educamais.entities;

public enum AutorMensagem {

    ALUNO("Aluno"),
    EDIA("EdIA");

    private final String rotulo;

    AutorMensagem(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }
}
