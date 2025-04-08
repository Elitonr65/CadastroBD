package cadastrobd.model;

public class Pessoa {
    protected int idPessoa;
    protected String nome;
    protected String logradouro;
    protected String cidade;
    protected String estado;
    protected String telefone;
    protected String email;

    // Construtor padrão
    public Pessoa() {}

    // Construtor usado ao criar uma nova pessoa (sem ID)
    public Pessoa(String nome, String logradouro, String cidade, String estado, String telefone, String email) {
        this.nome = nome;
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.estado = estado;
        this.telefone = telefone;
        this.email = email;
    }

    // Construtor usado para preencher uma pessoa já existente (com ID)
    public Pessoa(int idPessoa, String nome, String logradouro, String cidade, String estado, String telefone, String email) {
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.estado = estado;
        this.telefone = telefone;
        this.email = email;
    }

    public int getIdPessoa() { return idPessoa; }
    public String getNome() { return nome; }
    public String getLogradouro() { return logradouro; }
    public String getCidade() { return cidade; }
    public String getEstado() { return estado; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }

    public void setIdPessoa(int idPessoa) { this.idPessoa = idPessoa; }
    public void setNome(String nome) { this.nome = nome; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEmail(String email) { this.email = email; }

    public void exibir() {
        System.out.println("ID: " + idPessoa);
        System.out.println("Nome: " + nome);
        System.out.println("Endereço: " + logradouro + ", " + cidade + " - " + estado);
        System.out.println("Telefone: " + telefone);
        System.out.println("Email: " + email);
    }
}
