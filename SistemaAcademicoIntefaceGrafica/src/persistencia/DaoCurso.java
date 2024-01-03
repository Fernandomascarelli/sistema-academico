/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.*;
import java.util.ArrayList;
import modelo.Curso;

/**
 *
 * @author Administrador
 */
public class DaoCurso<T> extends DAO {

    DaoDocente daoC = new DaoDocente();

    public ArrayList<Curso> carregarCursos() {
        ArrayList<Curso> cursos = new ArrayList<>();
        try {
            String sql = "select * from curso";
            ResultSet rs = consultaSQL(sql);
            while (rs.next()) {
                Curso c = new Curso();
                c.setIdCurso(rs.getInt("idcurso"));
                c.setNome(rs.getString("nomeC"));
                c.setCargaHoraria(rs.getInt("cargaHoraria"));
                c.setQtdSemestres(rs.getInt("qtdSemestre"));

                /*if (rs.getObject("iddocenteC", Integer.class) != null) {
                c.getCoordenador().setIdPessoa(rs.getInt("iddocente"));
                c.getCoordenador().setCtps(rs.getString("ctpsD"));
                c.getCoordenador().setSalario(rs.getDouble("salarioD"));
                
                c.getCoordenador().setNome(rs.getString("nomeD"));
                c.getCoordenador().setCpf(rs.getString("cpfD"));
                c.getCoordenador().setEmail(rs.getString("emailD"));
                c.getCoordenador().setGenero(rs.getString("generoD"));
                java.sql.Date dataNascimentoSQL = rs.getDate("dataNascimentoD");
                if (dataNascimentoSQL != null) {
                c.getCoordenador().setDataNascimento(dataNascimentoSQL.toLocalDate());
                }
                
                c.getCoordenador().setFormacao(rs.getString("formacao"));
                
                }*/
                cursos.add(c);
            }
        } catch (SQLException ex) {
            System.out.println("Falha ao carregar cursos!\n" + ex.getMessage());
        }
        return cursos;
    }

    public Curso carregarCursoPorId(int id) {
        Curso c = new Curso();
        try {
            String sql = "select * from curso where idcurso = " + id;
            ResultSet rs = consultaSQL(sql);
            while (rs.next()) {
                c.setIdCurso(rs.getInt("idcurso"));
                c.setNome(rs.getString("nomeC"));
                c.setCargaHoraria(rs.getInt("cargaHoraria"));
                c.setQtdSemestres(rs.getInt("qtdSemestre"));

            }
        } catch (SQLException ex) {
            System.out.println("Falha ao carregar curso!\n" + ex.getMessage());
        }
        return c;
    }

    public boolean removerCurso(int id) {
        try {
            String removerFk = "SET foreign_key_checks = 0;";
            PreparedStatement st = criarPreparedStatement(removerFk);

            String sql = """
                         DELETE c
                         FROM curso c
                         WHERE c.idcurso = ?;                      
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("curso removido com sucesso!! ");
            }
            stmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro curso remvoer\n" + e.getMessage());
            return false;
        }
    }
    
    public void editarCurso(Curso c) {
        try {
            String sql = """
                            UPDATE curso c
                            SET nomeC = ?, cargaHoraria = ?, qtdSemestre = ?, iddocenteC = ?
                            WHERE idcurso = ?;
                         
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setString(1, c.getNome());
            stmt.setInt(2, c.getCargaHoraria());
            stmt.setInt(3, c.getQtdSemestres());
            stmt.setInt(4, c.getCoordenador().getIdPessoa());
            stmt.setInt(5, c.getIdCurso());

            int l = stmt.executeUpdate();

            if (l > 0) {
                System.out.println("Curso editado");
            }
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("Erro editar curso\n" + ex.getMessage());
        }
    }
    
    public boolean salvar(Curso c) {
        try {
            String sql = "INSERT INTO curso(\n"
                    + " idcurso, nomeC, cargaHoraria, qtdSemestre, iddocenteC)\n"
                    + " VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = criarPreparedStatement(sql);
            c.setIdCurso(gerarProximoId("curso", "idcurso"));
            ps.setInt(1, c.getIdCurso());
            ps.setString(2, c.getNome());
            ps.setInt(3, c.getCargaHoraria());
            ps.setInt(4, c.getQtdSemestres());

            if (c.getCoordenador() != null) {
                if (c.getCoordenador().getIdPessoa() == null || c.getCoordenador().getIdPessoa() == 0) {
                    daoC.salvar(c.getCoordenador());
                }
                ps.setInt(5, c.getCoordenador().getIdPessoa());
            } else {
                ps.setObject(5, null);
            }

            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            try {
                getConexao().rollback();
            } catch (SQLException ex1) {
                System.out.println("Falhar ao realizar RollBack");
            }
            System.out.println("Falha ao salvar curso\n" + ex.getMessage());
            return false;
        }
    }
}
