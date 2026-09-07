package br.com.fiap.ms.educamais.entities;

public enum RiscoEvasao {

    BAIXO("Em dia"),
    MEDIO("Atenção"),
    ALTO("Risco alto");

    private final String rotulo;

    RiscoEvasao(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }
}
