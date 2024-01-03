package persistencia;

import modelo.Aluno;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DaoAluno extends DAO {

    DaoCurso daoCurso = new DaoCurso();
    DaoEndereco daoE = new DaoEndereco();

    public Aluno carregarAlunoPorId(int id) {
        Aluno a = new Aluno();
        try {
            String sql = """
                         SELECT * FROM aluno 
                         LEFT JOIN curso ON idcursoA = curso.idcurso
                         LEFT JOIN endereco ON idendA = endereco.idendereco
                         where idaluno = 
                         """ + id;
            ResultSet rs = consultaSQL(sql);
            while (rs.next()) {
                a.setIdPessoa(rs.getInt("idaluno"));

                a.setNome(rs.getString("nomeA"));
                a.setCpf(rs.getString("cpfA"));
                a.setEmail(rs.getString("emailA"));
                a.setGenero(rs.getString("generoA"));
                java.sql.Date dataNascimentoSQL = rs.getDate("dataNascimentoA");
                if (dataNascimentoSQL != null) {
                    a.setDataNascimento(dataNascimentoSQL.toLocalDate());
                }

                a.setRa(rs.getString("ra"));
                a.setSituacao(rs.getString("situacao"));

                java.sql.Date dataMatriculaSQL = rs.getDate("dataMatricula");
                if (dataMatriculaSQL != null) {
                    a.setDataMatricula(dataMatriculaSQL.toLocalDate());
                }

                if (rs.getObject("idendA", Integer.class) != null) {
                    a.getEndereco().setIdEndereco(rs.getInt("idendereco"));
                    a.getEndereco().setCidade(rs.getString("cidade"));
                    a.getEndereco().setRua(rs.getString("rua"));
                    a.getEndereco().setNumero(rs.getString("numero"));
                }

                if (rs.getObject("idcursoA", Integer.class) != null) {
                    a.getCurso().setIdCurso(rs.getInt("idcurso"));
                    a.getCurso().setNome(rs.getString("nomeC"));
                    a.getCurso().setCargaHoraria(rs.getInt("cargaHoraria"));
                    a.getCurso().setQtdSemestres(rs.getInt("qtdSemestre"));
                    a.getCurso().getCoordenador().setIdPessoa(rs.getInt("iddocenteC"));
                }

                System.out.println(a.toString());

            }

        } catch (SQLException e) {
            System.out.println("Falha ao carregar aluno!\n" + e.getMessage());
        }
        return a;
    }

    public void editarAluno(Aluno a) {
        try {
            String sql = """
                            UPDATE aluno a
                            INNER JOIN endereco e on a.idendA = e.idendereco
                            SET nomeA = ?, cpfA = ?, emailA = ?, generoA = ?, dataNascimentoA = ?, ra = ?, 
                            dataMatricula = ?, situacao = ?, 
                            e.cidade = ?, e.rua = ?, e.numero = ?,
                            idcursoA = ?
                            WHERE idaluno = ?;
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getCpf());
            stmt.setString(3, a.getEmail());
            stmt.setString(4, a.getGenero());

            LocalDate dataNascimento = a.getDataNascimento();
             if (dataNascimento != null) {
                DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dataNascimentoFormatada = dataNascimento.format(formatacao);
                java.sql.Date dataNascimentoSQL = java.sql.Date.valueOf(dataNascimentoFormatada);
                stmt.setDate(5, dataNascimentoSQL);
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
             
            stmt.setString(6, a.getRa());
            
            LocalDate dataMatricula = a.getDataMatricula();
             if (dataMatricula != null) {
                DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dataMatriculaFormatada = dataMatricula.format(formatacao);
                java.sql.Date dataMatriculaSQL = java.sql.Date.valueOf(dataMatriculaFormatada);
                stmt.setDate(7, dataMatriculaSQL);
            } else {
                stmt.setNull(7, java.sql.Types.DATE);
            }
            
            stmt.setString(8, a.getSituacao());
            stmt.setString(9, a.getEndereco().getCidade());
            stmt.setString(10, a.getEndereco().getRua());
            stmt.setString(11, a.getEndereco().getNumero());
            stmt.setInt(12, a.getCurso().getIdCurso());
            stmt.setInt(13, a.getIdPessoa());

            int l = stmt.executeUpdate();

            if (l > 0) {
                System.out.println("Aluno editado");
            }
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("Erro editar aluno\n" + ex.getMessage());
        }
    }
    
    public boolean removerAluno(int id) {
        try {
            String removerFk = "SET foreign_key_checks = 0;";
            PreparedStatement st = criarPreparedStatement(removerFk);

            String sql = """
                         DELETE a, e
                         FROM aluno a
                         INNER JOIN endereco e on a.idendA = e.idendereco
                         WHERE a.idaluno = ? and a.idendA = e.idendereco;                      
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("alnuo removido com sucesso!! ");
            }
            stmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro remover aluno\n" + e.getMessage());
            return false;
        }
    }

    public ArrayList<Aluno> carregarAlunos() {
        ArrayList<Aluno> listaAlunos = new ArrayList<>();
        try {
            String sql = """
                         SELECT * FROM aluno 
                         LEFT JOIN curso ON idcursoA = curso.idcurso
                         LEFT JOIN endereco ON idendA = endereco.idendereco""";
            ResultSet rs = consultaSQL(sql);
            while (rs.next()) {
                Aluno a = new Aluno();
                a.setIdPessoa(rs.getInt("idaluno"));

                a.setNome(rs.getString("nomeA"));
                a.setCpf(rs.getString("cpfA"));
                a.setEmail(rs.getString("emailA"));
                a.setGenero(rs.getString("generoA"));
                java.sql.Date dataNascimentoSQL = rs.getDate("dataNascimentoA");
                if (dataNascimentoSQL != null) {
                    a.setDataNascimento(dataNascimentoSQL.toLocalDate());
                }

                a.setRa(rs.getString("ra"));
                a.setSituacao(rs.getString("situacao"));

                java.sql.Date dataMatriculaSQL = rs.getDate("dataMatricula");
                if (dataMatriculaSQL != null) {
                    a.setDataMatricula(dataMatriculaSQL.toLocalDate());
                }

                if (rs.getObject("idendA", Integer.class) != null) {
                    a.getEndereco().setIdEndereco(rs.getInt("idendereco"));
                    a.getEndereco().setCidade(rs.getString("cidade"));
                    a.getEndereco().setRua(rs.getString("rua"));
                    a.getEndereco().setNumero(rs.getString("numero"));
                }

                if (rs.getObject("idcursoA", Integer.class) != null) {
                    a.getCurso().setIdCurso(rs.getInt("idcurso"));
                    a.getCurso().setNome(rs.getString("nomeC"));
                    a.getCurso().setCargaHoraria(rs.getInt("cargaHoraria"));
                    a.getCurso().setQtdSemestres(rs.getInt("qtdSemestre"));
                    a.getCurso().getCoordenador().setIdPessoa(rs.getInt("iddocenteC"));
                }

                System.out.println(a.toString());

                listaAlunos.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Falha ao carregar cursos!\n" + e.getMessage());
        }
        return listaAlunos;
    }

    public boolean salvar(Aluno aluno) {
        try {
            String sql = "INSERT INTO aluno(\n"
                    + " idaluno, nomeA, cpfA, emailA, generoA, dataNascimentoA, ra, dataMatricula, situacao, idendA, idcursoA)\n"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = criarPreparedStatement(sql);
            aluno.setIdPessoa(gerarProximoId("aluno", "idaluno"));
            ps.setInt(1, aluno.getIdPessoa());
            ps.setString(2, aluno.getNome());
            ps.setString(3, aluno.getCpf());
            ps.setString(4, aluno.getEmail());
            ps.setString(5, aluno.getGenero());

            LocalDate dataNascimento = aluno.getDataNascimento();
            if (dataNascimento != null) {
                DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dataNascimentoFormatada = dataNascimento.format(formatacao);
                java.sql.Date dataNascimentoSQL = java.sql.Date.valueOf(dataNascimentoFormatada);
                ps.setDate(6, dataNascimentoSQL);
            } else {
                ps.setNull(6, java.sql.Types.DATE);
            }

            ps.setString(7, aluno.getRa());
            ps.setString(9, aluno.getSituacao());

            LocalDate dataMatricula = aluno.getDataMatricula();
            if (dataMatricula != null) {
                DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dataMatriculaFormatada = dataMatricula.format(formatacao);
                java.sql.Date dataMatriculaSQL = java.sql.Date.valueOf(dataMatriculaFormatada);
                ps.setDate(8, dataMatriculaSQL);
            } else {
                ps.setNull(8, java.sql.Types.DATE);
            }

            if (aluno.getEndereco() != null) {
                if (aluno.getEndereco().getIdEndereco() == null || aluno.getEndereco().getIdEndereco() == 0) {
                    daoE.salvar(aluno.getEndereco());
                }
                ps.setInt(10, aluno.getEndereco().getIdEndereco());
            } else {
                ps.setObject(10, null);
            }

            if (aluno.getCurso() != null) {
                if (aluno.getCurso().getIdCurso() == null || aluno.getCurso().getIdCurso() == 0) {
                    daoCurso.salvar(aluno.getCurso());
                }
                ps.setInt(11, aluno.getCurso().getIdCurso());
            } else {
                ps.setObject(11, null);
            }

            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            try {
                getConexao().rollback();
            } catch (SQLException ex1) {
                System.out.println("Falhar ao realizar RollBack");
            }
            System.out.println("Falha ao salvar Aluno\n" + ex.getMessage());
            return false;
        }
    }
}
