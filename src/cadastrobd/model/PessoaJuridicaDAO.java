package cadastrobd.model;

import cadastrobd.model.util.ConectorBD;
import cadastrobd.model.util.SequenceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaJuridicaDAO {

    // 🔹 Recupera uma pessoa jurídica pelo ID
    public PessoaJuridica getPessoa(int idPessoa) {
        PessoaJuridica pessoa = null;
        String sql = "SELECT * FROM Pessoa p INNER JOIN PessoaJuridica pj ON p.idPessoa = pj.idPessoa WHERE p.idPessoa = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPessoa);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pessoa = mapPessoaJuridica(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pessoa;
    }

    // 🔹 Lista todas as pessoas jurídicas
    public List<PessoaJuridica> getPessoas() {
        List<PessoaJuridica> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pessoa p INNER JOIN PessoaJuridica pj ON p.idPessoa = pj.idPessoa";

        try (Connection conn = ConectorBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapPessoaJuridica(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 🔹 Insere uma nova pessoa jurídica
    public void incluir(PessoaJuridica pessoa) {
        String sqlPessoa = "INSERT INTO Pessoa (idPessoa, nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlJuridica = "INSERT INTO PessoaJuridica (idPessoa, cnpj) VALUES (?, ?)";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa);
             PreparedStatement stmtJuridica = conn.prepareStatement(sqlJuridica)) {

            int idPessoa = SequenceManager.getValue("seq_pessoa");
            pessoa.setIdPessoa(idPessoa);

            stmtPessoa.setInt(1, idPessoa);
            stmtPessoa.setString(2, pessoa.getNome());
            stmtPessoa.setString(3, pessoa.getLogradouro());
            stmtPessoa.setString(4, pessoa.getCidade());
            stmtPessoa.setString(5, pessoa.getEstado());
            stmtPessoa.setString(6, pessoa.getTelefone());
            stmtPessoa.setString(7, pessoa.getEmail());
            stmtPessoa.executeUpdate();

            stmtJuridica.setInt(1, idPessoa);
            stmtJuridica.setString(2, pessoa.getCnpj());
            stmtJuridica.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Atualiza os dados de uma pessoa jurídica
    public void alterar(PessoaJuridica pessoa) {
        String sqlPessoa = "UPDATE Pessoa SET nome=?, logradouro=?, cidade=?, estado=?, telefone=?, email=? WHERE idPessoa=?";
        String sqlJuridica = "UPDATE PessoaJuridica SET cnpj=? WHERE idPessoa=?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa);
             PreparedStatement stmtJuridica = conn.prepareStatement(sqlJuridica)) {

            stmtPessoa.setString(1, pessoa.getNome());
            stmtPessoa.setString(2, pessoa.getLogradouro());
            stmtPessoa.setString(3, pessoa.getCidade());
            stmtPessoa.setString(4, pessoa.getEstado());
            stmtPessoa.setString(5, pessoa.getTelefone());
            stmtPessoa.setString(6, pessoa.getEmail());
            stmtPessoa.setInt(7, pessoa.getIdPessoa());
            stmtPessoa.executeUpdate();

            stmtJuridica.setString(1, pessoa.getCnpj());
            stmtJuridica.setInt(2, pessoa.getIdPessoa());
            stmtJuridica.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Exclui uma pessoa jurídica pelo ID
    public void excluir(int idPessoa) {
        String sqlJuridica = "DELETE FROM PessoaJuridica WHERE idPessoa=?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE idPessoa=?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtJuridica = conn.prepareStatement(sqlJuridica);
             PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa)) {

            stmtJuridica.setInt(1, idPessoa);
            stmtJuridica.executeUpdate();

            stmtPessoa.setInt(1, idPessoa);
            stmtPessoa.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Utilitário para mapear os dados do ResultSet em um objeto PessoaJuridica
    private PessoaJuridica mapPessoaJuridica(ResultSet rs) throws SQLException {
        PessoaJuridica pessoa = new PessoaJuridica(
            rs.getString("nome"),
            rs.getString("logradouro"),
            rs.getString("cidade"),
            rs.getString("estado"),
            rs.getString("telefone"),
            rs.getString("email"),
            rs.getString("cnpj")
        );
        pessoa.setIdPessoa(rs.getInt("idPessoa"));
        return pessoa;
    }
}
