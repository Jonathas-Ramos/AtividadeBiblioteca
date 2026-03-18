package modelo;

public class Multa {
    private double valor;
    private int diasAtraso;

    public Multa(int diasAtraso) {
        this.diasAtraso = diasAtraso;
        this.valor = calcularValor(diasAtraso);
    }

    public double calcularValor(int dias) {
        return dias * 2.50; // Exemplo: R$ 2,50 por dia de atraso
    }

    public double getValor() { return valor; }
    public int getDiasAtraso() { return diasAtraso; }
}