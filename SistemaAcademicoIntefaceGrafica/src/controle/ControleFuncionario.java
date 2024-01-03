/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import modelo.Funcionario;
import modelo.Pessoa;
import persistencia.DaoFuncionario;
import util.NumberUtils;
import visao.TelaCadastroPessoa;

/**
 *
 * @author Andre
 */
public class ControleFuncionario extends ControlePessoa<Funcionario> {

    DaoFuncionario daoF = new DaoFuncionario();
    private ArrayList<Funcionario> funcionarios = new ArrayList<>();

    public ControleFuncionario() {
        super(Funcionario.class);
        setTelaCadastro(new TelaCadastroPessoa(this));
    }

    public ControleFuncionario(Class classeModelo) {
        super(classeModelo);
    }

    public void setarDadosObjeto(Funcionario func, HashMap<String, Object> dados) {
        try {
            super.setarDadosObjeto(func, dados);

            func.setCtps((String) dados.getOrDefault("ctps", ""));
            func.setSalario(NumberUtils.parseFloat((String) dados.getOrDefault("salario", 0)));
            
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro de conversao ao salvar o funcionario:\n" + numberFormatException.getMessage(), "Erro de Conversao", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Falha ao Setar Dados do Funcionario:\n" + e.getMessage(), "Falha ao Setar Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    public HashMap<String, Object> getDadosObjeto(Funcionario func) {
        HashMap<String, Object> dados = super.getDadosObjeto(func);
        dados.put("ctps", func.getCtps());
        dados.put("salario", func.getSalario());
        return dados;
    }

    @Override
    public void abrirTelaCadastroParaEdicao(int index) {
        registroSelecionado = daoF.carregarFuncionarioPorId(index);
        if (registroSelecionado == null) {
            JOptionPane.showMessageDialog(null, "Falha ao Editar \nRegistro não encontrado!", "Falha ao Editar", JOptionPane.ERROR_MESSAGE);
            return;
        }

        telaCadastro.setarDadosTela(getDadosObjeto(registroSelecionado));
        telaCadastro.setEditarDados(true);
        telaCadastro.setVisible(true);
    }

    @Override
    public void editar(HashMap<String, Object> dados) {
        if (registroSelecionado != null) {
            setarDadosObjeto(registroSelecionado, dados);
            daoF.editarFuncionario((Funcionario) registroSelecionado);
        }
    }

    public boolean removerCadastro(int index){
       if( daoF.removerFuncionario(index)){
           return true;
       };
        return false;
    }

    
    @Override
    public void salvar(HashMap<String, Object> dados) {
        Funcionario funcionario = new Funcionario();
        setarDadosObjeto(funcionario, dados);
        registros.add(funcionario);
        daoF.salvar(funcionario);
        System.out.println(funcionario);
    }

}
