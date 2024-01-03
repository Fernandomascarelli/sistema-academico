package persistencia;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import modelo.Endereco;
import modelo.Funcionario;
import util.Input;

public class DaoFuncionario<T> extends DAO {

    DaoEndereco daoE = new DaoEndereco();

    public ArrayList<Funcionario> carregarFuncionarios() {
        ArrayList<Funcionario> listaFuncionarios = new ArrayList<>();
        try {
            String sql = """
                         SELECT * FROM funcionario 
                         LEFT JOIN endereco ON idendF = endereco.idendereco""";
            ResultSet rs = consultaSQL(sql);
            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setIdPessoa(rs.getInt("idfuncionario"));
                f.setCtps(rs.getString("ctpsF"));
                f.setSalario(rs.getDouble("salarioF"));

                f.setNome(rs.getString("nomeF"));
                f.setCpf(rs.getString("cpfF"));
                f.setEmail(rs.getString("emailF"));
                f.setGenero(rs.getString("generoF"));
                java.sql.Date dataNascimentoSQL = rs.getDate("dataNascimentoF");
                if (dataNascimentoSQL != null) {
                    f.setDataNascimento(dataNascimentoSQL.toLocalDate());
                }

                if (rs.getObject("idendF", Integer.class) != null) {
                    f.getEndereco().setIdEndereco(rs.getInt("idendereco"));
                    f.getEndereco().setCidade(rs.getString("cidade"));
                    f.getEndereco().setRua(rs.getString("rua"));
                    f.getEndereco().setNumero(rs.getString("numero"));
                }

                System.out.println(f.toString());

                listaFuncionarios.add(f);
            }

        } catch (SQLException e) {
            System.out.println("Falha ao carregar funcionarios!\n" + e.getMessage());
        }
        return listaFuncionarios;
    }
    
    public void editarFuncionario(Funcionario f) {
        try {
            String sql = """
                            UPDATE funcionario f
                            INNER JOIN endereco e on f.idendF = e.idendereco
                            SET nomeF = ?, cpfF = ?, emailF = ?, generoF = ?, dataNascimentoF = ?, ctpsF = ?, salarioF = ?, e.cidade = ?, e.rua = ?, e.numero = ?
                            WHERE f.idfuncionario = ?;
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCpf());
            stmt.setString(3, f.getEmail());
            stmt.setString(4, f.getGenero());
            LocalDate dataNascimento = f.getDataNascimento();
            if (dataNascimento != null) {
                DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dataNascimentoFormatada = dataNascimento.format(formatacao);
                java.sql.Date dataNascimentoSQL = java.sql.Date.valueOf(dataNascimentoFormatada);
                stmt.setDate(5, dataNascimentoSQL);
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
            stmt.setString(6, f.getCtps());
            stmt.setDouble(7, f.getSalario());
            stmt.setString(8, f.getEndereco().getCidade());
            stmt.setString(9, f.getEndereco().getRua());
            stmt.setString(10, f.getEndereco().getNumero());
            stmt.setInt(11, f.getIdPessoa());

            int l = stmt.executeUpdate();

            if (l > 0) {
                System.out.println("funcionario editado");
            }
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("erro editar func\n" + ex.getMessage());
        }
    }
    
    public Funcionario carregarFuncionarioPorId(int id) {
        Funcionario f = new Funcionario();
        try {
            String sql = """
                         SELECT * FROM funcionario 
                         LEFT JOIN endereco ON idendF = endereco.idendereco
                         where idfuncionario = """ + id;
            ResultSet rs = consultaSQL(sql);
            while (rs.next()) {

                f.setIdPessoa(rs.getInt("idfuncionario"));
                f.setCtps(rs.getString("ctpsF"));
                f.setSalario(rs.getDouble("salarioF"));

                f.setNome(rs.getString("nomeF"));
                f.setCpf(rs.getString("cpfF"));
                f.setEmail(rs.getString("emailF"));
                f.setGenero(rs.getString("generoF"));
                java.sql.Date dataNascimentoSQL = rs.getDate("dataNascimentoF");
                if (dataNascimentoSQL != null) {
                    f.setDataNascimento(dataNascimentoSQL.toLocalDate());
                }

                if (rs.getObject("idendF", Integer.class) != null) {
                    f.getEndereco().setIdEndereco(rs.getInt("idendereco"));
                    f.getEndereco().setCidade(rs.getString("cidade"));
                    f.getEndereco().setRua(rs.getString("rua"));
                    f.getEndereco().setNumero(rs.getString("numero"));
                }

                System.out.println(f.toString());

            }

        } catch (SQLException e) {
            System.out.println("Falha ao carregar funcionario!\n" + e.getMessage());
        }
        return f;
    }

    public boolean salvar(Funcionario func) {
        try {
            String sql = "INSERT INTO funcionario(\n"
                    + " idfuncionario, nomeF, cpfF, emailF, generoF, dataNascimentoF, ctpsF, salarioF, idendF)\n"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = criarPreparedStatement(sql);
            func.setIdPessoa(gerarProximoId("funcionario", "idfuncionario"));
            ps.setInt(1, func.getIdPessoa());
            ps.setString(2, func.getNome());
            ps.setString(3, func.getCpf());
            ps.setString(4, func.getEmail());
            ps.setString(5, func.getGenero());

            LocalDate dataNascimento = func.getDataNascimento();
            if (dataNascimento != null) {
                DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dataNascimentoFormatada = dataNascimento.format(formatacao);
                java.sql.Date dataNascimentoSQL = java.sql.Date.valueOf(dataNascimentoFormatada);
                ps.setDate(6, dataNascimentoSQL);
            } else {
                ps.setNull(6, java.sql.Types.DATE);
            }

            ps.setString(7, func.getCtps());
            ps.setDouble(8, func.getSalario());

            if (func.getEndereco() != null) {
                if (func.getEndereco().getIdEndereco() == null || func.getEndereco().getIdEndereco() == 0) {
                    daoE.salvar(func.getEndereco());
                }
                ps.setInt(9, func.getEndereco().getIdEndereco());
            } else {
                ps.setObject(9, null);
            }

            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            try {
                getConexao().rollback();
            } catch (SQLException ex1) {
                System.out.println("Falhar ao realizar RollBack");
            }
            System.out.println("Falha ao salvar funcionario\n" + ex.getMessage());
            return false;
        }
    }

    public void remocaocomentada() {
        /*public boolean remocaoAluno(int id) {
    Funcionario f = carregarFuncionarioPorId(id);
    
    if (f != null) {
    if (removerFuncionario(f)) {
    System.out.println("Funcionario removido com sucesso!");
    return true;
    } else {
    System.out.println("Falha ao remover funcionario.");
    return false;
    }
    } else {
    System.out.println("Funcionario nao encontrado.");
    return false;
    }
    
    }
    
    public boolean removerFuncionario(Funcionario f) {
    try {
    String sql = "DELETE FROM funcionario WHERE idfuncionario =" + f.getIdPessoa();
    executeSql(sql);
    
    Endereco e = f.getEndereco();
    if (e != null) {
    
    String enderecoSql = daoE.comandoSqlRemover(e);
    executeSql(enderecoSql);
    }
    
    ArrayList<Funcionario> funcionarios = carregarFuncionarios();
    
    ArrayList<Endereco> enderecos = daoE.carregarEnderecos();
    if (e != null) {
    enderecos.remove(e);
    }
    
    return true;
    } catch (SQLException e) {
    System.out.println("Falha ao remover funcionario!\n" + e.getMessage());
    return false;
    }
    }*/
    }

    public boolean removerFuncionario(int id) {
        try {
            String removerFk = "SET foreign_key_checks = 0;";
            PreparedStatement st = criarPreparedStatement(removerFk);

            String sql = """
                         DELETE f, e
                         FROM funcionario f
                         INNER JOIN endereco e on f.idendF = e.idendereco
                         WHERE f.idfuncionario = ? and f.idendF = e.idendereco;                      
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("funcioanrios removido com sucesso!! ");
            }
            stmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro remover funcioanrio\n" + e.getMessage());
            return false;
        }
    }
}
