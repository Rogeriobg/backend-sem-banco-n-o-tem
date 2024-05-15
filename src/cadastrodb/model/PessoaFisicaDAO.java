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

public class PessoaFisicaDAO {
    public void incluir(PessoaFisica pessoa) throws SQLException {
        String sqlPessoa = "INSERT INTO Pessoa (Nome, Logradouro, Cidade, Estado, Telefone, Email) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlPessoaFisica = "INSERT INTO PessoaFisica (PessoaID, Pessoa_PessoaID, CPF) VALUES (?, ?, ?)";

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

                    try (PreparedStatement stmtPessoaFisica = conn.prepareStatement(sqlPessoaFisica)) {
                        stmtPessoaFisica.setInt(1, pessoaId);
                        stmtPessoaFisica.setInt(2, pessoaId);
                        stmtPessoaFisica.setString(3, pessoa.getCpf());
                        stmtPessoaFisica.executeUpdate();
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void alterar(PessoaFisica pessoa) throws SQLException {
        String sqlPessoa = "UPDATE Pessoa SET Nome = ?, Logradouro = ?, Cidade = ?, Estado = ?, Telefone = ?, Email = ? WHERE PessoaID = ?";
        String sqlPessoaFisica = "UPDATE PessoaFisica SET CPF = ? WHERE PessoaID = ?";

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

                try (PreparedStatement stmtPessoaFisica = conn.prepareStatement(sqlPessoaFisica)) {
                    stmtPessoaFisica.setString(1, pessoa.getCpf());
                    stmtPessoaFisica.setInt(2, pessoa.getId());
                    stmtPessoaFisica.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public List<PessoaFisica> getPessoas() throws SQLException {
        List<PessoaFisica> pessoas = new ArrayList<>();
        String sql = "SELECT p.PessoaID, p.Nome, p.Logradouro, p.Cidade, p.Estado, p.Telefone, p.Email, pf.CPF " +
                     "FROM Pessoa p JOIN PessoaFisica pf ON p.PessoaID = pf.PessoaID";

        try (Connection conn = ConectorBD.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PessoaFisica pessoa = new PessoaFisica();
                pessoa.setId(rs.getInt("PessoaID"));
                pessoa.setNome(rs.getString("Nome"));
                pessoa.setLogradouro(rs.getString("Logradouro"));
                pessoa.setCidade(rs.getString("Cidade"));
                pessoa.setEstado(rs.getString("Estado"));
                pessoa.setTelefone(rs.getString("Telefone"));
                pessoa.setEmail(rs.getString("Email"));
                pessoa.setCpf(rs.getString("CPF"));
                pessoas.add(pessoa);
            }
        }
        return pessoas;
    }

    public void excluir(int id) throws SQLException {
        String sqlPessoaFisica = "DELETE FROM PessoaFisica WHERE PessoaID = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE PessoaID = ?";

        try (Connection conn = ConectorBD.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtPessoaFisica = conn.prepareStatement(sqlPessoaFisica)) {
                stmtPessoaFisica.setInt(1, id);
                stmtPessoaFisica.executeUpdate();

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
