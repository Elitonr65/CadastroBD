package cadastrobd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import cadastrobd.model.util.ConectorBD;

public class PessoaFisica extends Pessoa {

    private String cpf;

    public PessoaFisica(String nome, String logradouro, String cidade, String estado, String telefone, String email, String cpf) {
        super(nome, logradouro, cidade, estado, telefone, email);
        this.cpf = cpf;
    }

    public PessoaFisica() {
    super();
}

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public void exibir() {
        super.exibir();
        System.out.println("CPF: " + cpf);
    }

    public void salvar() {
        String sqlPessoa = "INSERT INTO Pessoa (nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlFisica = "INSERT INTO PessoaFisica (idPessoa, cpf) VALUES (?, ?)";
        
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

            try (PreparedStatement stmtFisica = conn.prepareStatement(sqlFisica)) {
                stmtFisica.setInt(1, idPessoa);
                stmtFisica.setString(2, this.cpf);
                stmtFisica.executeUpdate();
                System.out.println("Pessoa Física salva com sucesso!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar Pessoa Física: " + e.getMessage());
        }
    }

    public void atualizar() {
        String sql = "UPDATE Pessoa SET nome = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE idPessoa = ?";
        String sqlFisica = "UPDATE PessoaFisica SET cpf = ? WHERE idPessoa = ?";
        
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             PreparedStatement stmtFisica = conn.prepareStatement(sqlFisica)) {
            
            stmt.setString(1, this.getNome());
            stmt.setString(2, this.getLogradouro());
            stmt.setString(3, this.getCidade());
            stmt.setString(4, this.getEstado());
            stmt.setString(5, this.getTelefone());
            stmt.setString(6, this.getEmail());
            stmt.setInt(7, this.getIdPessoa());
            stmt.executeUpdate();

            stmtFisica.setString(1, this.cpf);
            stmtFisica.setInt(2, this.getIdPessoa());
            stmtFisica.executeUpdate();

            System.out.println("Pessoa Física atualizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar Pessoa Física: " + e.getMessage());
        }
    }

    public void excluir() {
        String sql = "DELETE FROM Pessoa WHERE idPessoa = ?";
        String sqlFisica = "DELETE FROM PessoaFisica WHERE idPessoa = ?";
        
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             PreparedStatement stmtFisica = conn.prepareStatement(sqlFisica)) {
            
            stmt.setInt(1, this.getIdPessoa());
            stmt.executeUpdate();

            stmtFisica.setInt(1, this.getIdPessoa());
            stmtFisica.executeUpdate();

            System.out.println("Pessoa Física excluída com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir Pessoa Física: " + e.getMessage());
        }
    }

    public static List<PessoaFisica> listarTodos() {
        List<PessoaFisica> lista = new ArrayList<>();
        String sql = "SELECT p.idPessoa, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, pf.cpf "
                   + "FROM Pessoa p INNER JOIN PessoaFisica pf ON p.idPessoa = pf.idPessoa";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
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
                lista.add(pessoa);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar pessoas físicas: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public int getIdPessoa() {
        return super.getIdPessoa();
    }
    @Override
    public String toString() {
        return """
               ==============================
               Id: """ + getIdPessoa() + "\n" +
               "Nome: " + getNome() + "\n" +
               "CPF: " + getCpf() + "\n" +
               "Logradouro: " + getLogradouro() + "\n";
    }
}
