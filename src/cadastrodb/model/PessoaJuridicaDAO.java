/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrodb.model;

/**
 *
 * @author rbgor
 */
import cadastro.model.util.ConectorBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaJuridicaDAO {
    public void incluir(PessoaJuridica pessoa) throws SQLException {
        String sqlPessoa = "INSERT INTO Pessoa (Nome, Logradouro, Cidade, Estado, Telefone, Email) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlPessoaJuridica = "INSERT INTO PessoaJuridica (PessoaID, Pessoa_PessoaID, CNPJ) VALUES (?, ?, ?)";

        try (Connection conn = ConectorBD.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS)) {
                stmtPessoa.setString(1, pessoa.getNome());
                stmtPessoa.setString(2, pessoa.getLogradouro());
                stmtPessoa.setString(3, pessoa.getCidade());
                stmtPessoa.setString(4, pessoa.getEstado());
                stmtPessoa.setString(5, pessoa.getTelefone());
                stmtPessoa.setString(6, pessoa.getEmail());
                stmtPessoa.executeUpdate();

                ResultSet rs = stmtPessoa.getGeneratedKeys();
                if (rs.next()) {
                    int pessoaId = rs.getInt(1);
                    pessoa.setId(pessoaId);

                    try (PreparedStatement stmtPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica)) {
                        stmtPessoaJuridica.setInt(1, pessoaId);
                        stmtPessoaJuridica.setInt(2, pessoaId);
                        stmtPessoaJuridica.setString(3, pessoa.getCnpj());
                        stmtPessoaJuridica.executeUpdate();
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void alterar(PessoaJuridica pessoa) throws SQLException {
        String sqlPessoa = "UPDATE Pessoa SET Nome = ?, Logradouro = ?, Cidade = ?, Estado = ?, Telefone = ?, Email = ? WHERE PessoaID = ?";
        String sqlPessoaJuridica = "UPDATE PessoaJuridica SET CNPJ = ? WHERE PessoaID = ?";

        try (Connection conn = ConectorBD.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa)) {
                stmtPessoa.setString(1, pessoa.getNome());
                stmtPessoa.setString(2, pessoa.getLogradouro());
                stmtPessoa.setString(3, pessoa.getCidade());
                stmtPessoa.setString(4, pessoa.getEstado());
                stmtPessoa.setString(5, pessoa.getTelefone());
                stmtPessoa.setString(6, pessoa.getEmail());
                stmtPessoa.setInt(7, pessoa.getId());
                stmtPessoa.executeUpdate();

                try (PreparedStatement stmtPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica)) {
                    stmtPessoaJuridica.setString(1, pessoa.getCnpj());
                    stmtPessoaJuridica.setInt(2, pessoa.getId());
                    stmtPessoaJuridica.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public List<PessoaJuridica> getPessoas() throws SQLException {
        List<PessoaJuridica> pessoas = new ArrayList<>();
        String sql = "SELECT p.PessoaID, p.Nome, p.Logradouro, p.Cidade, p.Estado, p.Telefone, p.Email, pj.CNPJ " +
                     "FROM Pessoa p JOIN PessoaJuridica pj ON p.PessoaID = pj.PessoaID";

        try (Connection conn = ConectorBD.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PessoaJuridica pessoa = new PessoaJuridica();
                pessoa.setId(rs.getInt("PessoaID"));
                pessoa.setNome(rs.getString("Nome"));
                pessoa.setLogradouro(rs.getString("Logradouro"));
                pessoa.setCidade(rs.getString("Cidade"));
                pessoa.setEstado(rs.getString("Estado"));
                pessoa.setTelefone(rs.getString("Telefone"));
                pessoa.setEmail(rs.getString("Email"));
                pessoa.setCnpj(rs.getString("CNPJ"));
                pessoas.add(pessoa);
            }
        }
        return pessoas;
    }

    public void excluir(int id) throws SQLException {
        String sqlPessoaJuridica = "DELETE FROM PessoaJuridica WHERE PessoaID = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE PessoaID = ?";

        try (Connection conn = ConectorBD.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica)) {
                stmtPessoaJuridica.setInt(1, id);
                stmtPessoaJuridica.executeUpdate();

                try (PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa)) {
                    stmtPessoa.setInt(1, id);
                    stmtPessoa.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}