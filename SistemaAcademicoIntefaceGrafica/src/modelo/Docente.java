package modelo;

import java.time.LocalDate;

public class Docente extends Funcionario {

    protected String formacao;

    public Docente(){
        
    }
    
    public Docente(String formacao, String ctps, double salario) {
        super(ctps, salario);
        this.formacao = formacao;
    }

    public Docente(String formacao, String ctps, double salario, Integer idPessoa, String nome, String cpf, String email, String genero, LocalDate dataNascimento, Endereco endereco) {
        super(ctps, salario, idPessoa, nome, cpf, email, genero, dataNascimento, endereco);
        this.formacao = formacao;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println(nome + " | Cpf: " + cpf + " | Idade: " + calcularIdade() + " anos "
                + " | Cidade: " + endereco.getCidade() + " | Rua : " + endereco.getRua() + " | Número: " + endereco.getNumero() + " | "
                + "Ctps: " + ctps + " |  Salario: " + salario + " | Formação: " + formacao
        );
    }
}
