package br.com.fiap.ms.educamais.entities;

public enum TipoAula {

    VIDEO("Vídeo"),
    LEITURA("Leitura"),
    QUIZ("Quiz"),
    AO_VIVO("Ao vivo");

    private final String rotulo;

    TipoAula(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }
}
