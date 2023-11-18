public class Tabuleiro {

    private int[][] tabuleiro;
    
    Tabuleiro(int[][] tabuleiro){
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
}
