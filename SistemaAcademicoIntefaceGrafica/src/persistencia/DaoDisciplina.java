package persistencia;

import java.util.ArrayList;
import modelo.Disciplina;
import java.sql.*;

public class DaoDisciplina<T> extends DAO{
    
    DaoCurso daoCurso = new DaoCurso();

    public ArrayList<Disciplina> carregarDisciplina() {
        ArrayList<Disciplina> disciplinas = new ArrayList<>();
        try {
            String sql = "select * from disciplina";
            ResultSet rs = consultaSQL(sql);
            while (rs.next()) {
                Disciplina d = new Disciplina();
                d.setIdDisciplina(rs.getInt("iddisciplina"));
                d.setNome(rs.getString("nomeD"));
                d.setSemestre(rs.getInt("semestre"));
                d.setCrgHoraria(rs.getInt("crgHoraria"));

                disciplinas.add(d);
            }
        } catch (SQLException ex) {
            System.out.println("Falha ao carregar disciplinas!\n" + ex.getMessage());
        }
        return disciplinas;
    }
    
    public boolean salvar(Disciplina d) {
        try {
            String sql = """
                         INSERT INTO disciplina(
                          iddisciplina, nomeD, semestre, crgHoraria, idcursoDis)
                          VALUES (?, ?, ?, ?, ?);""";

            PreparedStatement ps = criarPreparedStatement(sql);
            d.setIdDisciplina(gerarProximoId("disciplina", "iddisciplina"));
            ps.setInt(1, d.getIdDisciplina());
            ps.setString(2, d.getNome());
            ps.setInt(3, d.getSemestre());
            ps.setInt(4, d.getCrgHoraria());

            if (d.getCurso() != null) {
                if (d.getCurso().getIdCurso()== null || d.getCurso().getIdCurso()== 0) {
                    daoCurso.salvar(d.getCurso());
                }
                ps.setInt(5, d.getCurso().getIdCurso());
            } else {
                ps.setObject(5, null);
            }

            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Falha ao salvar curso\n" + ex.getMessage());
            return false;
        }
    }
    
    public Disciplina carregarDisciplinaPorId(int id) {
        Disciplina d = new Disciplina();
        try {
            String sql = "select * from disciplina where iddisciplina = " + id;
            ResultSet rs = consultaSQL(sql);
            while (rs.next()) {
                d.setIdDisciplina(rs.getInt("iddisciplina"));
                d.setNome(rs.getString("nomeD"));
                d.setSemestre(rs.getInt("semestre"));
                d.setCrgHoraria(rs.getInt("crgHoraria"));

            }
        } catch (SQLException ex) {
            System.out.println("Falha ao carregar disciplina!\n" + ex.getMessage());
        }
        return d;
    }
    
    public boolean removerDisciplina(int id) {
        try {
            String removerFk = "SET foreign_key_checks = 0;";
            PreparedStatement st = criarPreparedStatement(removerFk);

            String sql = """
                         DELETE
                         FROM disciplina 
                         WHERE iddisciplina = ?;                      
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setInt(1, id);

            int l = stmt.executeUpdate();

            if (l > 0) {
                System.out.println("disciplina removida com sucesso!! ");
            }
            stmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro disciplina remvoer\n" + e.getMessage());
            return false;
        }
    }
    
    public void editarDisciplina(Disciplina d) {
        try {
            String sql = """
                            UPDATE disciplina d
                            SET nomeD = ?, semestre = ?, crgHoraria = ?, idcursoDis = ?
                            WHERE iddisciplina = ?;
                         
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setString(1, d.getNome());
            stmt.setInt(2, d.getSemestre());
            stmt.setInt(3, d.getCrgHoraria());
            stmt.setInt(4, d.getCurso().getIdCurso());
            stmt.setInt(5, d.getIdDisciplina());

            int l = stmt.executeUpdate();

            if (l > 0) {
                System.out.println("disciplina editado");
            }
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("Erro editar disciplina\n" + ex.getMessage());
        }
    }
}
