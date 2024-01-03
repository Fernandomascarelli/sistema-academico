/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import persistencia.DaoAluno;
import persistencia.DaoCurso;
import persistencia.DaoDisciplina;
import persistencia.DaoDocente;
import persistencia.DaoEndereco;
import persistencia.DaoFuncionario;

/**
 *
 * @author Administrador
 */
public class Dados {

    public void dadosBase() {
        Endereco e1 = new Endereco(1, "Cascavel", "Manaus", "2121");
        Endereco e2 = new Endereco(2, "Cascavel", "Mato Grosso", "1333");
        Endereco e3 = new Endereco(3, "Cascavel", "Barão do Cerro Azul", "129");
        Endereco e4 = new Endereco(4, "Cascavel", "Afonso Pena", "1200");
        Endereco e5 = new Endereco(5, "Cascavel", "Parana", "1752");
        DaoEndereco daoE = new DaoEndereco();
        daoE.salvar(e1);
        daoE.salvar(e2);
        daoE.salvar(e3);
        daoE.salvar(e4);
        daoE.salvar(e5);

        String dataNasciment1 = "1992-09-03";
        LocalDate dataNascimento1 = LocalDate.parse(dataNasciment1);
        String dataNasciment2 = "1982-10-21";
        LocalDate dataNascimento2 = LocalDate.parse(dataNasciment2);
        String dataNasciment3 = "2001-02-27";
        LocalDate dataNascimento3 = LocalDate.parse(dataNasciment3);
        String dataNasciment4 = "1999-05-17";
        LocalDate dataNascimento4 = LocalDate.parse(dataNasciment4);
        String dataNasciment5 = "1994-12-03";
        LocalDate dataNascimento5 = LocalDate.parse(dataNasciment5);

        String dataMatricul1 = "2023-03-05";
        LocalDate dataMatricula1 = LocalDate.parse(dataMatricul1);
        String dataMatricul2 = "2021-07-03";
        LocalDate dataMatricula2 = LocalDate.parse(dataMatricul2);
        String dataMatricul3 = "2017-04-10";
        LocalDate dataMatricula3 = LocalDate.parse(dataMatricul3);

        Funcionario f1 = new Funcionario("13453", 3200, 1, "Antonio Fagundes", "93873610084", "antoniofagundes@gmail.com",
                "Masculino", dataNascimento1, e1);
        DaoFuncionario daoF = new DaoFuncionario();
        daoF.salvar(f1);

        Docente d1 = new Docente("Educação Física", "67130", 4100, 1, "Mirela Santos", "00987155640", "mirelasts@hotmail.com",
                "Feminino", dataNascimento2, e2);
        DaoDocente daoD = new DaoDocente();
        daoD.salvar(d1);

        Curso c1 = new Curso(1, "Educação Física", 3600, 16, d1);
        DaoCurso daoC = new DaoCurso();
        daoC.salvar(c1);

        Aluno a1 = new Aluno("23957", c1, dataMatricula1, "Em Andamento", e3, 1, "Miguel Casagrande",
                "88214900033", "miguelcasagrande01@gmail.com", "Masculino", dataNascimento3);
        Aluno a2 = new Aluno("23517", c1, dataMatricula2, "Em Transferencia", e4, 2, "Leonardo Goretks",
                "17852901100", "leo10@gmail.com", "Masculino", dataNascimento4);
        Aluno a3 = new Aluno("22119", c1, dataMatricula3, "Concluido", e5, 3, "Larrisa Portes",
                "00055077101", "laportes@gmail.com", "Feminino", dataNascimento5);
        DaoAluno daoA = new DaoAluno();
        daoA.salvar(a1);
        daoA.salvar(a2);
        daoA.salvar(a3);
        
        Disciplina dd1 = new Disciplina(1, "Anatomia Humana", 3, 120, c1);
        Disciplina dd2 = new Disciplina(2, "Fundamentos", 1, 80, c1);
        Disciplina dd3 = new Disciplina(3, "Técnicas", 2, 120, c1);
        DaoDisciplina daoDd = new DaoDisciplina();
        daoDd.salvar(dd1);
        daoDd.salvar(dd2);
        daoDd.salvar(dd3);
        
    }
}
