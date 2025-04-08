package cadastrobd.model;

import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
            PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
            int opcao;

            do {
                System.out.println("\n=== Menu Principal ===");
                System.out.println("1 - Incluir Pessoa");
                System.out.println("2 - Alterar Pessoa");
                System.out.println("3 - Excluir Pessoa");
                System.out.println("4 - Exibir Pessoa pelo ID");
                System.out.println("5 - Exibir Todas as Pessoas");
                System.out.println("0 - Sair");
                System.out.print("Opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> {
                        System.out.println("Tipo de pessoa: F - Física | J - Jurídica");
                        String tipo = scanner.nextLine().toUpperCase();

                        switch (tipo) {
                            case "F" -> {
                                PessoaFisica pf = new PessoaFisica();
                                System.out.print("Nome: ");
                                pf.setNome(scanner.nextLine());
                                System.out.print("Logradouro: ");
                                pf.setLogradouro(scanner.nextLine());
                                System.out.print("Cidade: ");
                                pf.setCidade(scanner.nextLine());
                                System.out.print("Estado: ");
                                pf.setEstado(scanner.nextLine());
                                System.out.print("Telefone: ");
                                pf.setTelefone(scanner.nextLine());
                                System.out.print("Email: ");
                                pf.setEmail(scanner.nextLine());
                                System.out.print("CPF: ");
                                pf.setCpf(scanner.nextLine());
                                pessoaFisicaDAO.incluir(pf);
                            }
                            case "J" -> {
                                PessoaJuridica pj = new PessoaJuridica();
                                System.out.print("Nome: ");
                                pj.setNome(scanner.nextLine());
                                System.out.print("Logradouro: ");
                                pj.setLogradouro(scanner.nextLine());
                                System.out.print("Cidade: ");
                                pj.setCidade(scanner.nextLine());
                                System.out.print("Estado: ");
                                pj.setEstado(scanner.nextLine());
                                System.out.print("Telefone: ");
                                pj.setTelefone(scanner.nextLine());
                                System.out.print("Email: ");
                                pj.setEmail(scanner.nextLine());
                                System.out.print("CNPJ: ");
                                pj.setCnpj(scanner.nextLine());
                                pessoaJuridicaDAO.incluir(pj);
                            }
                            default -> System.out.println("Tipo inválido.");
                        }
                    }
                    case 2 -> {
                        System.out.println("Tipo de pessoa: F - Física | J - Jurídica");
                        String tipo = scanner.nextLine().toUpperCase();

                        System.out.print("ID da pessoa a alterar: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        if (tipo.equals("F")) {
                            PessoaFisica pf = pessoaFisicaDAO.getPessoa(id);
                            if (pf != null) {
                                System.out.print("Novo CPF: ");
                                pf.setCpf(scanner.nextLine());
                                pessoaFisicaDAO.alterar(pf);
                            } else {
                                System.out.println("Pessoa não encontrada.");
                            }
                        } else if (tipo.equals("J")) {
                            PessoaJuridica pj = pessoaJuridicaDAO.getPessoa(id);
                            if (pj != null) {
                                System.out.print("Novo CNPJ: ");
                                pj.setCnpj(scanner.nextLine());
                                pessoaJuridicaDAO.alterar(pj);
                            } else {
                                System.out.println("Pessoa não encontrada.");
                            }
                        } else {
                            System.out.println("Tipo inválido.");
                        }
                    }
                    case 3 -> {
                        System.out.println("Tipo de pessoa: F - Física | J - Jurídica");
                        String tipo = scanner.nextLine().toUpperCase();

                        System.out.print("ID da pessoa a excluir: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        if (tipo.equals("F")) {
                            pessoaFisicaDAO.excluir(id);
                        } else if (tipo.equals("J")) {
                            pessoaJuridicaDAO.excluir(id);
                        } else {
                            System.out.println("Tipo inválido.");
                        }
                    }
                    case 4 -> {
                        System.out.println("Tipo de pessoa: F - Física | J - Jurídica");
                        String tipo = scanner.nextLine().toUpperCase();

                        System.out.print("ID da pessoa a exibir: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        if (tipo.equals("F")) {
                            PessoaFisica pf = pessoaFisicaDAO.getPessoa(id);
                            if (pf != null) {
                                System.out.println(pf);
                            } else {
                                System.out.println("Pessoa não encontrada.");
                            }
                        } else if (tipo.equals("J")) {
                            PessoaJuridica pj = pessoaJuridicaDAO.getPessoa(id);
                            if (pj != null) {
                                System.out.println(pj);
                            } else {
                                System.out.println("Pessoa não encontrada.");
                            }
                        } else {
                            System.out.println("Tipo inválido.");
                        }
                    }
                    case 5 -> {
                        System.out.println("\n--- Pessoas Físicas ---");
                        List<PessoaFisica> listaPF = pessoaFisicaDAO.getPessoas();
                        for (PessoaFisica pf : listaPF) {
                            System.out.println(pf);
                        }

                        System.out.println("\n--- Pessoas Jurídicas ---");
                        List<PessoaJuridica> listaPJ = pessoaJuridicaDAO.getPessoas();
                        for (PessoaJuridica pj : listaPJ) {
                            System.out.println(pj);
                        }
                    }
                    case 0 -> System.out.println("Saindo do programa...");
                    default -> System.out.println("Opção inválida.");
                }

            } while (opcao != 0);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
