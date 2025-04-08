package cadastrobd.model;

import java.sql.Connection;
import cadastrobd.model.util.ConectorBD;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class PessoaJuridica extends Pessoa {

    private String cnpj;

    // Construtor completo
    public PessoaJuridica(int idPessoa, String nome, String logradouro, String cidade,
                          String estado, String telefone, String email, String cnpj) {
        super(nome, logradouro, cidade, estado, telefone, email);
        this.setIdPessoa(idPessoa);
        this.cnpj = cnpj;
    }

    // Construtor para novos cadastros
    public PessoaJuridica(String nome, String logradouro, String cidade,
                          String estado, String telefone, String email, String cnpj) {
        super(nome, logradouro, cidade, estado, telefone, email);
        this.cnpj = cnpj;
    }

    // Construtor vazio
    public PessoaJuridica() {
        super();
    }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    @Override
    public void exibir() {
        super.exibir();
        System.out.println("CNPJ: " + cnpj);
    }

    public void salvar() {
        String sqlPessoa = "INSERT INTO Pessoa (nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlJuridica = "INSERT INTO PessoaJuridica (idPessoa, cnpj) VALUES (?, ?)";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmtPessoa.setString(1, this.getNome());
            stmtPessoa.setString(2, this.getLogradouro());
            stmtPessoa.setString(3, this.getCidade());
            stmtPessoa.setString(4, this.getEstado());
            stmtPessoa.setString(5, this.getTelefone());
            stmtPessoa.setString(6, this.getEmail());
            stmtPessoa.executeUpdate();

            ResultSet generatedKeys = stmtPessoa.getGeneratedKeys();
            int idPessoa = 0;
            if (generatedKeys.next()) {
                idPessoa = generatedKeys.getInt(1);
                this.setIdPessoa(idPessoa);
            }

            try (PreparedStatement stmtJuridica = conn.prepareStatement(sqlJuridica)) {
                stmtJuridica.setInt(1, idPessoa);
                stmtJuridica.setString(2, this.cnpj);
                stmtJuridica.executeUpdate();
                System.out.println("Pessoa Jurídica salva com sucesso!");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao salvar Pessoa Jurídica: " + e.getMessage());
        }
    }

    public void atualizar() {
        String sqlPessoa = "UPDATE Pessoa SET nome = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE idPessoa = ?";
        String sqlJuridica = "UPDATE PessoaJuridica SET cnpj = ? WHERE idPessoa = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa);
             PreparedStatement stmtJuridica = conn.prepareStatement(sqlJuridica)) {

            stmtPessoa.setString(1, this.getNome());
            stmtPessoa.setString(2, this.getLogradouro());
            stmtPessoa.setString(3, this.getCidade());
            stmtPessoa.setString(4, this.getEstado());
            stmtPessoa.setString(5, this.getTelefone());
            stmtPessoa.setString(6, this.getEmail());
            stmtPessoa.setInt(7, this.getIdPessoa());
            stmtPessoa.executeUpdate();

            stmtJuridica.setString(1, this.cnpj);
            stmtJuridica.setInt(2, this.getIdPessoa());
            stmtJuridica.executeUpdate();

            System.out.println("Pessoa Jurídica atualizada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar Pessoa Jurídica: " + e.getMessage());
        }
    }

    public void excluir() {
        String sqlJuridica = "DELETE FROM PessoaJuridica WHERE idPessoa = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE idPessoa = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtJuridica = conn.prepareStatement(sqlJuridica);
             PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa)) {

            stmtJuridica.setInt(1, this.getIdPessoa());
            stmtJuridica.executeUpdate();

            stmtPessoa.setInt(1, this.getIdPessoa());
            stmtPessoa.executeUpdate();

            System.out.println("Pessoa Jurídica excluída com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao excluir Pessoa Jurídica: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return """
               ==============================
               Id: """ + getIdPessoa() + "\n" +
               "Nome: " + getNome() + "\n" +
               "CNPJ: " + getCnpj() + "\n" +
               "Logradouro: " + getLogradouro() + "\n";
    }

    // Método para listar todas as pessoas jurídicas do banco
    public static List<PessoaJuridica> listarTodos() {
        List<PessoaJuridica> lista = new ArrayList<>();
        String sql = "SELECT p.idPessoa, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, pj.cnpj " +
                     "FROM Pessoa p INNER JOIN PessoaJuridica pj ON p.idPessoa = pj.idPessoa";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PessoaJuridica pj = new PessoaJuridica(
                    rs.getInt("idPessoa"),
                    rs.getString("nome"),
                    rs.getString("logradouro"),
                    rs.getString("cidade"),
                    rs.getString("estado"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cnpj")
                );
                lista.add(pj);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar Pessoas Jurídicas: " + e.getMessage());
        }

        return lista;
    }
}
