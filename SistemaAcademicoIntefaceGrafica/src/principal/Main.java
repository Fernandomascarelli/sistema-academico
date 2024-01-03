package principal;

import controle.ControleLogin;
import controle.ControlePrincipal;
import modelo.Dados;
import modelo.Disciplina;


public class Main {

    private static ControlePrincipal controle;

    public static void main(String[] args) {
        Dados d = new Dados();
        d.dadosBase();
        
        controle = new ControlePrincipal();
        ControleLogin controleLogin = new ControleLogin(controle);

        controle.getControleLogin().abrirTelaLogin();
    }

    public static ControlePrincipal getControle() {
        return controle;
    }

}
