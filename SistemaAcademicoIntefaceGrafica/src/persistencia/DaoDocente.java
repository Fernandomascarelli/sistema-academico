/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import modelo.Docente;

public class DaoDocente<T> extends DAO {

    public Docente carregarDocentePorId(int id) {
        Docente d = new Docente();
        try {
            String sql = """
                         SELECT * FROM funcionario 
                         LEFT JOIN endereco ON idendF = endereco.idendereco
                         where idfuncionario = """ + id;
            ResultSet rs = consultaSQL(sql);
            while (rs.next()) {

                d.setIdPessoa(rs.getInt("idfuncionario"));
                d.setCtps(rs.getString("ctpsF"));
                d.setSalario(rs.getDouble("salarioF"));

                d.setNome(rs.getString("nomeF"));
                d.setCpf(rs.getString("cpfF"));
                d.setEmail(rs.getString("emailF"));
                d.setGenero(rs.getString("generoF"));
                java.sql.Date dataNascimentoSQL = rs.getDate("dataNascimentoF");
                if (dataNascimentoSQL != null) {
                    d.setDataNascimento(dataNascimentoSQL.toLocalDate());
                }

                if (rs.getObject("idendF", Integer.class) != null) {
                    d.getEndereco().setIdEndereco(rs.getInt("idendereco"));
                    d.getEndereco().setCidade(rs.getString("cidade"));
                    d.getEndereco().setRua(rs.getString("rua"));
                    d.getEndereco().setNumero(rs.getString("numero"));
                }
                d.setFormacao(rs.getString("formacao"));
                
                System.out.println(d.toString());

            }

        } catch (SQLException e) {
            System.out.println("Falha ao carregar docente!\n" + e.getMessage());
        }
        return d;
    }
    
    public void editarDocente(Docente d) {
        try {
            String sql = """
                            UPDATE docente d
                            INNER JOIN endereco e on d.idendD = e.idendereco
                            SET nomeD = ?, cpfD = ?, emailD = ?, generoD = ?, dataNascimentoD = ?, ctpsD = ?, salarioD = ?,
                            e.cidade = ?, e.rua = ?, e.numero = ?
                            WHERE iddocente = ?;
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setString(1, d.getNome());
            stmt.setString(2, d.getCpf());
            stmt.setString(3, d.getEmail());
            stmt.setString(4, d.getGenero());
            LocalDate dataNascimento = d.getDataNascimento();
            if (dataNascimento != null) {
                DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dataNascimentoFormatada = dataNascimento.format(formatacao);
                java.sql.Date dataNascimentoSQL = java.sql.Date.valueOf(dataNascimentoFormatada);
                stmt.setDate(5, dataNascimentoSQL);
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
            stmt.setString(6, d.getCtps());
            stmt.setDouble(7, d.getSalario());
            stmt.setString(8, d.getEndereco().getCidade());
            stmt.setString(9, d.getEndereco().getRua());
            stmt.setString(10, d.getEndereco().getNumero());
            stmt.setInt(11, d.getIdPessoa());

            int l = stmt.executeUpdate();

            if (l > 0) {
                System.out.println("Docente editado");
            }
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("Erro editar docente\n" + ex.getMessage());
        }
    }
    
    public boolean removerDocente(int id) {
        try {
            String removerFk = "SET foreign_key_checks = 0;";
            PreparedStatement st = criarPreparedStatement(removerFk);
            
            /*String sql = """
            delete from docente where iddocente = ?;
            """;*/
            
            String sql = """
                         DELETE d, e
                         FROM docente d
                         INNER JOIN endereco e on d.idendD = e.idendereco
                         WHERE d.iddocente = ? and d.idendD = e.idendereco;                      
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);
            
            stmt.setInt(1, id);
         
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("docente removido com sucesso!! ");
            }
            stmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro docente remvoer\n" + e.getMessage());
            return false;
        }
    }
    
    public ArrayList<Docente> carregarDocentes() {
        ArrayList<Docente> listaDocentes = new ArrayList<>();
        try {
            String sql = """
                         SELECT * FROM docente 
                         LEFT JOIN endereco ON idendD = endereco.idendereco""";
            ResultSet rs = consultaSQL(sql);
            while (rs.next()) {
                Docente d = new Docente();
                d.setIdPessoa(rs.getInt("iddocente"));
                d.setCtps(rs.getString("ctpsD"));
                d.setSalario(rs.getDouble("salarioD"));
                
                d.setNome(rs.getString("nomeD"));
                d.setCpf(rs.getString("cpfD"));
                d.setEmail(rs.getString("emailD"));
                d.setGenero(rs.getString("generoD"));
                java.sql.Date dataNascimentoSQL = rs.getDate("dataNascimentoD");
                if (dataNascimentoSQL != null) {
                    d.setDataNascimento(dataNascimentoSQL.toLocalDate());
                }
                
                d.setFormacao(rs.getString("formacao"));
                
                if (rs.getObject("idendD", Integer.class) != null) {
                    d.getEndereco().setIdEndereco(rs.getInt("idendereco"));
                    d.getEndereco().setCidade(rs.getString("cidade"));
                    d.getEndereco().setRua(rs.getString("rua"));
                    d.getEndereco().setNumero(rs.getString("numero"));
                }
                
                System.out.println(d.toString());

                listaDocentes.add(d);
            }

        } catch (SQLException e) {
            System.out.println("Falha ao carregar docentes!\n" + e.getMessage());
        }
        return listaDocentes;
    }
    
    DaoEndereco daoE = new DaoEndereco();
    
    public boolean salvar(Docente d) {
        try {
            String sql = "INSERT INTO docente(\n"
                    + "iddocente, nomeD, cpfD, emailD, generoD, dataNascimentoD, ctpsD, salarioD, formacao, idendD)\n"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = criarPreparedStatement(sql);
            d.setIdPessoa(gerarProximoId("docente", "iddocente"));
            ps.setInt(1, d.getIdPessoa());
            ps.setString(2, d.getNome());
            ps.setString(3, d.getCpf());
            ps.setString(4, d.getEmail());
            ps.setString(5, d.getGenero());
            
            LocalDate dataNascimento = d.getDataNascimento();
            if (dataNascimento != null) {
                DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dataNascimentoFormatada = dataNascimento.format(formatacao);
                java.sql.Date dataNascimentoSQL = java.sql.Date.valueOf(dataNascimentoFormatada);
                ps.setDate(6, dataNascimentoSQL);
            } else {
                ps.setNull(6, java.sql.Types.DATE);
            }        
            
            ps.setString(7, d.getCtps());
            ps.setDouble(8, d.getSalario());
            
            ps.setString(9, d.getFormacao());

            if (d.getEndereco()!= null) {
                if (d.getEndereco().getIdEndereco()== null || d.getEndereco().getIdEndereco() == 0) {
                    daoE.salvar(d.getEndereco());
                }
                ps.setInt(10, d.getEndereco().getIdEndereco());
            } else {
                ps.setObject(10, null);
            }

            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            try {
                getConexao().rollback();
            } catch (SQLException ex1) {
                System.out.println("Falhar ao realizar RollBack");
            }
            System.out.println("Falha ao salvar docente\n" + ex.getMessage());
            return false;
        }
    }
}
