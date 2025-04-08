package cadastrobd.model;

import cadastrobd.model.util.ConectorBD;
import cadastrobd.model.util.SequenceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {

    // 🔹 Busca uma pessoa física pelo ID
    public PessoaFisica getPessoa(int idPessoa) {
        PessoaFisica pessoa = null;
        String sql = "SELECT * FROM Pessoa p INNER JOIN PessoaFisica pf ON p.idPessoa = pf.idPessoa WHERE p.idPessoa = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPessoa);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pessoa = mapPessoaFisica(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pessoa;
    }

    // 🔹 Lista todas as pessoas físicas
    public List<PessoaFisica> getPessoas() {
        List<PessoaFisica> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pessoa p INNER JOIN PessoaFisica pf ON p.idPessoa = pf.idPessoa";

        try (Connection conn = ConectorBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapPessoaFisica(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 🔹 Insere uma nova pessoa física
    public void incluir(PessoaFisica pessoa) {
        String sqlPessoa = "INSERT INTO Pessoa (idPessoa, nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlFisica = "INSERT INTO PessoaFisica (idPessoa, cpf) VALUES (?, ?)";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa);
             PreparedStatement stmtFisica = conn.prepareStatement(sqlFisica)) {

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

            stmtFisica.setInt(1, idPessoa);
            stmtFisica.setString(2, pessoa.getCpf());
            stmtFisica.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Atualiza uma pessoa física existente
    public void alterar(PessoaFisica pessoa) {
        String sqlPessoa = "UPDATE Pessoa SET nome=?, logradouro=?, cidade=?, estado=?, telefone=?, email=? WHERE idPessoa=?";
        String sqlFisica = "UPDATE PessoaFisica SET cpf=? WHERE idPessoa=?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa);
             PreparedStatement stmtFisica = conn.prepareStatement(sqlFisica)) {

            stmtPessoa.setString(1, pessoa.getNome());
            stmtPessoa.setString(2, pessoa.getLogradouro());
            stmtPessoa.setString(3, pessoa.getCidade());
            stmtPessoa.setString(4, pessoa.getEstado());
            stmtPessoa.setString(5, pessoa.getTelefone());
            stmtPessoa.setString(6, pessoa.getEmail());
            stmtPessoa.setInt(7, pessoa.getIdPessoa());
            stmtPessoa.executeUpdate();

            stmtFisica.setString(1, pessoa.getCpf());
            stmtFisica.setInt(2, pessoa.getIdPessoa());
            stmtFisica.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Remove uma pessoa física pelo ID
    public void excluir(int idPessoa) {
        String sqlFisica = "DELETE FROM PessoaFisica WHERE idPessoa=?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE idPessoa=?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtFisica = conn.prepareStatement(sqlFisica);
             PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa)) {

            stmtFisica.setInt(1, idPessoa);
            stmtFisica.executeUpdate();

            stmtPessoa.setInt(1, idPessoa);
            stmtPessoa.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Método utilitário para mapear dados do ResultSet para um objeto PessoaFisica
    private PessoaFisica mapPessoaFisica(ResultSet rs) throws SQLException {
        PessoaFisica pessoa = new PessoaFisica(
            rs.getString("nome"),
            rs.getString("logradouro"),
            rs.getString("cidade"),
            rs.getString("estado"),
            rs.getString("telefone"),
            rs.getString("email"),
            rs.getString("cpf")
        );
        pessoa.setIdPessoa(rs.getInt("idPessoa"));
        return pessoa;
    }
}
