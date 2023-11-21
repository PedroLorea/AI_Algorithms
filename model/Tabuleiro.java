package model;
public class Tabuleiro {

    private final int[][] tabuleiro;

    
    public Tabuleiro(int[][] tabuleiro){
        this.tabuleiro = tabuleiro;
    }

    public int[][] getTabuleiro1(){        
        return tabuleiro;
    }

    @Override
    public String toString() {
        int[][] tabuleiro = getTabuleiro1();
        StringBuilder result = new StringBuilder();
        result.append("\n---------\n");
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                result.append(tabuleiro[i][j]);

                if (j < tabuleiro[i].length - 1) {
                    result.append(" | ");
                }
            }

            if (i < tabuleiro.length - 1) {
                result.append("\n---------\n");
            }
        }
        result.append("\n---------\n");
        return result.toString();
    }

    public void desenhaTabuleiroComSetas(int zeroRow,int zeroCol,int newZeroRow,int newZeroCol){
        StringBuilder resultado = new StringBuilder();

        //calcula direção da seta
        String direcao;
        if(zeroRow == newZeroRow){
            if(zeroCol < newZeroCol){
                direcao = "←";
            }else{
                direcao = "→";
            }
        }else{
            if(zeroRow < newZeroRow){
                direcao = "↑";
            }else{
                direcao = "↓";
            }
        }


        resultado.append("----------------\n");
        // Desenha o tabuleiro
        for (int[] ints : tabuleiro) {
            for (int anInt : ints) {
                if (anInt == 0) {
                    resultado.append("| ").append(" ").append(direcao).append(" ");
                } else {
                    resultado.append("| ").append(" ").append(anInt).append(" ");
                }
            }
            resultado.append("|\n");
        }
        resultado.append("----------------");
        System.out.println(resultado);
    }

    public void desenhaTabuleiro() {
        StringBuilder resultado = new StringBuilder();

        resultado.append("----------------\n");
        // Desenha o tabuleiro
        for (int[] ints : tabuleiro) {
            for (int anInt : ints) {
                resultado.append("| ").append(anInt < 10 ? " " : "").append(anInt).append(" ");
            }
            resultado.append("|\n");
        }
        resultado.append("----------------");

        System.out.println(resultado);
    }

    public int getZeroRow() {
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                if (tabuleiro[i][j] == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int getZeroCol() {
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                if (tabuleiro[i][j] == 0) {
                    return j;
                }
            }
        }
        return -1;
    }
}
