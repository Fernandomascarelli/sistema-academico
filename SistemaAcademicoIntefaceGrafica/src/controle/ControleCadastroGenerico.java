package controle;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import modelo.*;
import persistencia.DAO;
import persistencia.DaoAluno;
import persistencia.DaoCurso;
import persistencia.DaoDisciplina;
import persistencia.DaoDocente;
import util.DialogBoxUtils;
import visao.TelaCadastro;
import visao.TelaListaCadastro;
import persistencia.DaoFuncionario;

/**
 *
 * @author Andre
 * @param <T>
 */
public abstract class ControleCadastroGenerico<T> extends DAO implements IControleCadastro {

    private Class classeModelo;
    protected TelaCadastro telaCadastro;
    protected TelaListaCadastro telaListaCadastro;
    protected List<T> registros = new ArrayList<>();
    protected T registroSelecionado;

    

    public ControleCadastroGenerico(Class classeModelo) {
        this.classeModelo = classeModelo;
    }

    public ControleCadastroGenerico(Class classeModelo, TelaCadastro telaCadastro) {
        this.classeModelo = classeModelo;
        this.telaCadastro = telaCadastro;
    }

    public TelaCadastro getTelaCadastro() {
        return telaCadastro;
    }

    public void setTelaCadastro(TelaCadastro telaCadastro) {
        this.telaCadastro = telaCadastro;
    }

    public TelaListaCadastro getTelaListaCadastro() {
        return telaListaCadastro;
    }

    public List<T> getRegistros() {
        return registros;
    }

    public T getRegistroSelecionado() {
        return registroSelecionado;
    }

    @Override
    public String[] gerarColunasTabela() {
        if (classeModelo.equals(Aluno.class)) {
            return new String[]{"ID","Nome", "CPF", "E-mail", "Curso"};
        } else if (classeModelo.getSuperclass() == Pessoa.class || classeModelo.equals(Docente.class) || classeModelo.equals(Funcionario.class)) {
            return new String[]{"ID","Nome", "CPF", "E-mail"};
        } else if (classeModelo.equals(Curso.class)) {
            return new String[]{"ID","Curso", "Semestres"};
        } else if (classeModelo.equals(Disciplina.class)){
            return new String[]{"ID", "Disciplina", "Semestre"};
        } else {
            DialogBoxUtils.exibirMensagemDeErro("Erro", "Erro ao gerar colunas para Tabela");
            return null;
        }
    }

    
    private DaoFuncionario daoFuncionario = new DaoFuncionario();
    public DaoDocente daoDocente = new DaoDocente();
    private DaoAluno daoAluno = new DaoAluno();
    private DaoCurso daoCurso = new DaoCurso();
    private DaoDisciplina daoD = new DaoDisciplina();
    
    @Override
    public String[][] gerarDadosTabela(int qtdColunas, IControleCadastro controleCadastro) {

        List<T> teste = new ArrayList<>();
        
        if (controleCadastro instanceof ControleAluno) {
            teste.addAll((Collection<? extends T>) daoAluno.carregarAlunos());
        } else if (controleCadastro instanceof ControleFuncionario) {
            if (controleCadastro instanceof ControleDocente) {
                teste.addAll((Collection<? extends T>) daoDocente.carregarDocentes());
            } else {
                teste.addAll(daoFuncionario.carregarFuncionarios());
            }
        } else if (controleCadastro instanceof ControleCurso) {
            teste.addAll((Collection<? extends T>) daoCurso.carregarCursos());

        } else if (controleCadastro instanceof ControleDisciplina){
            teste.addAll((Collection<? extends T>) daoD.carregarDisciplina());
        }
        
        String[][] dados = new String[teste.size()][qtdColunas];
        

        int linha = 0;
        for (T reg : teste) {
            
            dados[linha] = getDadosEntidadeModeloParaTabela(reg);
            linha++;
        }

        return dados;
    }

    public String[] getDadosEntidadeModeloParaTabela(T entidade) {

        if (entidade instanceof Aluno) {
            Aluno al = (Aluno) entidade;
            return new String[]{"" + al.getIdPessoa(), al.getNome(), al.getCpf(), al.getEmail(), al.getCurso() != null ? al.getCurso().getNome() : ""};
        } else if (entidade.getClass().getSuperclass() == Pessoa.class || entidade instanceof Docente || entidade instanceof Funcionario) {
            Pessoa p = (Pessoa) entidade;
            System.out.println(p.getNome());
            return new String[]{"" + p.getIdPessoa(), p.getNome(), p.getCpf(), p.getEmail()};
        } else if (entidade instanceof Curso) {
            Curso cur = (Curso) entidade;
            return new String[]{"" + cur.getIdCurso(), cur.getNome(), "" + cur.getQtdSemestres()}; 
        } else if (entidade instanceof Disciplina) {
            Disciplina dis = (Disciplina) entidade;
            return new String[]{"" + dis.getIdDisciplina(), dis.getNome(), "" + dis.getSemestre()};
        } else {
            DialogBoxUtils.exibirMensagemDeErro("Erro", "Erro ao gerar Dados para Tabela");
            return new String[10];
        }
    }

    public List<String> getDescricaoRegistros() {
        if (classeModelo.isAnnotationPresent(IDescricao.class)) {
            return registros.stream().map(x -> ((IDescricao) x).getDescricao()).collect(Collectors.toList());
        } else {
            return registros.stream().map(x -> x.toString()).collect(Collectors.toList());
        }

    }

    @Override
    public void atualizarTabelaTelaListagem() {
        telaListaCadastro.atualizarTabela();
    }

    @Override
    public void abrirTelaListagem() {
        telaListaCadastro = new TelaListaCadastro(this);
        telaListaCadastro.setVisible(true);
    }

    @Override
    public void abrirTelaCadastro() {
        telaCadastro.inicializarComponentesTela();
        telaCadastro.setVisible(true);
        telaCadastro.setEditarDados(false);
    }

    @Override
    public abstract void abrirTelaCadastroParaEdicao(int index);

    @Override
    public boolean removerCadastro(int index) {
        try {
            registros.remove(index);
            return true;
        } catch (Exception e) {
            DialogBoxUtils.exibirMensagemDeErro("Falha ao Remover Cadastro", "Falha ao Remover Cadastro\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public abstract void editar(HashMap<String, Object> dados);

    @Override
    public abstract void salvar(HashMap<String, Object> dados);
}
