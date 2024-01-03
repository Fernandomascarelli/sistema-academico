package controle;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import modelo.Curso;
import modelo.Disciplina;
import modelo.Docente;
import persistencia.DaoDisciplina;
import visao.TelaCadastro;
import visao.TelaCadastroDisciplina;

public class ControleDisciplina extends ControleCadastroGenerico<Disciplina> {

    private ControleCurso controleCurso;

    public ControleDisciplina(ControleCurso controleCurso) {
        super(Disciplina.class);
        this.controleCurso = controleCurso;
        setTelaCadastro(new TelaCadastroDisciplina(this));
    }

    public ControleCurso getControleCurso() {
        return controleCurso;
    }

    DaoDisciplina daoD = new DaoDisciplina();

    public void setarDadosObjeto(Disciplina d, HashMap<String, Object> dados) {
        if (d == null) {
            JOptionPane.showMessageDialog(null, "Falha ao Setar Dados!", "Falha ao Setar Dados", JOptionPane.ERROR_MESSAGE);
            return;
        }

        d.setNome((String) dados.getOrDefault("disciplina", ""));
        d.setCrgHoraria((int) dados.getOrDefault("crghoraria", 0));
        d.setSemestre((int) dados.getOrDefault("semestre", 0));
        d.setCurso((Curso) dados.getOrDefault("curso", null));

    }

    public HashMap<String, Object> gerarVetorDados(Disciplina d) {
        HashMap<String, Object> dados = new HashMap<>();
        dados.put("disciplina", d.getNome());
        dados.put("crghoraria", d.getCrgHoraria());
        dados.put("semestre", d.getSemestre());
        dados.put("curso", d.getCurso());

        return dados;
    }

    public List<String> getNomesDisciplinas() {
        registros = daoD.carregarDisciplina();
        return registros.stream().map(x -> x.getNome()).collect(Collectors.toList());
    }

    public Disciplina getDisciplinaSelecionada(int index) {
        if (index >= 0 && index < registros.size()) {
            return registros.get(index);
        }
        return null;
    }

    public boolean removerCadastro(int index) {
        if (daoD.removerDisciplina(index)) {
            return true;
        };
        return false;
    }

    @Override
    public void abrirTelaCadastroParaEdicao(int index) {
        registroSelecionado = daoD.carregarDisciplinaPorId(index);
        if (registroSelecionado == null) {
            JOptionPane.showMessageDialog(null, "Falha ao Editar \nRegistro não encontrado!", "Falha ao Editar", JOptionPane.ERROR_MESSAGE);
            return;
        }

        telaCadastro.setarDadosTela(gerarVetorDados(registroSelecionado));
        telaCadastro.setEditarDados(true);
        telaCadastro.setVisible(true);
    }

    @Override
    public void editar(HashMap<String, Object> dados) {
        if (registroSelecionado != null) {
            setarDadosObjeto(registroSelecionado, dados);
            daoD.editarDisciplina((Disciplina) registroSelecionado);
        }
    }

    @Override
    public void salvar(HashMap<String, Object> dados) {
        Disciplina d = new Disciplina();
        setarDadosObjeto(d, dados);
        registros.add(d);
        daoD.salvar(d);
    }

}
