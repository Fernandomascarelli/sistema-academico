package controle;

import visao.TelaPrincipal;

/**
 *
 * @author Andre
 */
public class ControlePrincipal {

    private ControleLogin controleLogin;
    private ControleFuncionario controleFuncionario;
    private ControleAluno controleAluno;
    private ControleDocente controleDocente;
    private ControleCurso controleCurso;
    private ControleDisciplina controleDisciplina;
    

    private TelaPrincipal telaPrincipal;

    public ControlePrincipal() {
        this.controleLogin = new ControleLogin(this);
        this.controleFuncionario = new ControleFuncionario();
        this.controleDocente = new ControleDocente();
        this.controleCurso = new ControleCurso(controleDocente);
        this.controleAluno = new ControleAluno(controleCurso);
        this.controleDisciplina = new ControleDisciplina(controleCurso);
        

//        controleCurso.getRegistros().add(new Curso("Tads",100,6,null));
//        controleCurso.getRegistros().add(new Curso("Direito",100,6,null));
    }

    public ControleDisciplina getControleDisciplina(){
        return controleDisciplina;
    }
    
    public ControleLogin getControleLogin() {
        return controleLogin;
    }

    public ControleCurso getControleCurso() {
        return controleCurso;
    }

    public ControleFuncionario getControleFuncionario() {
        return controleFuncionario;
    }

    public ControleAluno getControleAluno() {
        return controleAluno;
    }

    public ControleDocente getControleDocente() {
        return controleDocente;
    }

    public void abrirTelaPrincipal() {
        telaPrincipal = new TelaPrincipal(this);
        telaPrincipal.setVisible(true);
    }

}
