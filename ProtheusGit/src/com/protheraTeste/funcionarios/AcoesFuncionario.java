package com.protheraTeste.funcionarios;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.math.MathContext;
import java.util.Map;
import java.math.RoundingMode;
import java.util.stream.Collectors;

public class AcoesFuncionario {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        // Inserção de funcionários
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // Remover João
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        // Imprimir todos os funcionários
        System.out.println("Todos os funcionários:");
        for (Funcionario f : funcionarios) {
            System.out.printf("Nome: %s, Data Nascimento: %s, Salário: %,.2f, Função: %s%n",
                    f.getNome(), f.getDataNascimento().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    f.getSalario(), f.getFuncao());
        }

        // Aumentar salário em 10%
        funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(new BigDecimal("1.10"))));

        // Agrupar por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // Imprimir funcionários agrupados por função
        System.out.println("\nFuncionários agrupados por função:");
        funcionariosPorFuncao.forEach((funcao, funcionario) -> {
            //System.out.println(funcao + ":");
            //funcionario.forEach(System.out::println);
            String funcionariosFormatados = funcionario.stream()
                    .map(f -> String.format("\n%s | Data Nascimento: %s, Salário: %,.2f",
                            f.getNome(),
                            f.getDataNascimento().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            f.getSalario()))
                    .collect(Collectors.joining(""));
            System.out.printf("%s: { %s \n}%n", funcao, funcionariosFormatados);
        });

        // Funcionários que fazem aniversário no mês 10 e 12
        System.out.println("\nFuncionários que fazem aniversário nos meses 10 e 12:");
        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .forEach(f -> System.out.printf("Nome: %s, Data Nascimento: %s, Salário: %,.2f, Função: %s%n",
                        f.getNome(), f.getDataNascimento().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        f.getSalario(), f.getFuncao())
                );

        // Funcionário com maior idade
        Funcionario maisVelho = funcionarios.stream()
                .min((f1, f2) -> f1.getDataNascimento().compareTo(f2.getDataNascimento()))
                .orElse(null);
        if (maisVelho != null) {
            System.out.printf("\nFuncionário com maior idade: %s com %d anos %n",
                    maisVelho.getNome(), LocalDate.now().getYear() - maisVelho.getDataNascimento().getYear());
        }

        // Imprimir lista de funcionários por ordem alfabética
        System.out.println("\nFuncionários em ordem alfabética:");
        funcionarios.stream()
                .sorted((f1, f2) -> f1.getNome().compareTo(f2.getNome()))
                .forEach(System.out::println);

        // Imprimir total dos salários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("\nTotal dos salários: %,.2f%n", totalSalarios);

        // Imprimir quantos salários mínimos cada funcionário ganha
        final BigDecimal salarioMinimo = new BigDecimal("1212.00");
        MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
        System.out.println("\nSalários em múltiplos do salário mínimo:");
        funcionarios.forEach(f -> {
            BigDecimal multiplos = f.getSalario().divide(salarioMinimo, mc);
            System.out.printf("Nome: %s, Salários mínimos: %,.2f%n", f.getNome(), multiplos);
        });
    }
}
