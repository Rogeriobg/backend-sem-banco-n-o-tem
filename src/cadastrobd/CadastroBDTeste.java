package cadastrobd;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rbgor
 */

import cadastrodb.model.PessoaFisica;
import cadastrodb.model.PessoaFisicaDAO;
import cadastrodb.model.PessoaJuridica;
import cadastrodb.model.PessoaJuridicaDAO;

import java.sql.SQLException;
import java.util.List;

public class CadastroBDTeste {
    public static void main(String[] args) {
        
        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setNome("Fulano");
        pessoaFisica.setLogradouro("Rua Teste, 123");
        pessoaFisica.setCidade("Cidade Teste");
        pessoaFisica.setEstado("TE");
        pessoaFisica.setTelefone("(00) 1234-5678");
        pessoaFisica.setEmail("fulano@teste.com");
        pessoaFisica.setCpf("12345678910");

        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();

        try {
            
            pessoaFisicaDAO.incluir(pessoaFisica);

            
            pessoaFisica.setNome("Fulano Alterado");
            pessoaFisicaDAO.alterar(pessoaFisica);

            
            List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.getPessoas();
            System.out.println("Pessoas Físicas:");
            for (PessoaFisica pf : pessoasFisicas) {
                pf.exibir();
                System.out.println();
            }

            
            pessoaFisicaDAO.excluir(pessoaFisica.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setNome("Empresa Teste");
        pessoaJuridica.setLogradouro("Av. Teste, 456");
        pessoaJuridica.setCidade("Cidade Teste");
        pessoaJuridica.setEstado("TE");
        pessoaJuridica.setTelefone("(00) 9876-5432");
        pessoaJuridica.setEmail("empresa@teste.com");
        pessoaJuridica.setCnpj("12345678000190");

        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();

        try {
            
            pessoaJuridicaDAO.incluir(pessoaJuridica);

           
            pessoaJuridica.setNome("Empresa Alterada");
            pessoaJuridicaDAO.alterar(pessoaJuridica);

            
            List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.getPessoas();
            System.out.println("\nPessoas Jurídicas:");
            for (PessoaJuridica pj : pessoasJuridicas) {
                pj.exibir();
                System.out.println();
            }

            
            pessoaJuridicaDAO.excluir(pessoaJuridica.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}