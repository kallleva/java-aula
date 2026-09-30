package aula.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * ============================================================================
 * CONCEITO 1: GENERICS (<T>)
 * ============================================================================
 * Generics (Generosidade de Tipos) permite criar classes, interfaces e métodos
 * que funcionam com QUALQUER tipo de objeto especificado entre colchetes angulares <T>.
 *
 * 'T' é um "parâmetro de tipo" (Type Parameter) que representa o tipo que será
 * definido quando o repositório for instanciado.
 * Exemplo:
 *    Repository<Cliente> repoCliente = new Repository<>(); // T será Cliente
 *    Repository<Conta> repoConta = new Repository<>();     // T será Conta
 *
 * Benefícios:
 * 1. Reutilização de código: A mesma classe gerencia Cliente, Conta ou Historico.
 * 2. Segurançao de tipo (Type Safety): O compilador garante que você não colocará uma Conta
 *    dentro de um Repository<Cliente>.
 * 3. Elimina a necessidade de Casts explícitos como (Cliente) obj.
 *
 * ============================================================================
 * CONCEITO 2: LIST (LISTA DA INTERFACE java.util.List)
 * ============================================================================
 * List<T> é uma coleção ordenada de elementos do tipo T que permite duplicatas.
 * ArrayList<T> é a implementação baseada em array dinâmico da interface List.
 */
public class Repository<T> {

    // A lista interna que armazena elementos do tipo genérico T
    protected final List<T> dados;

    public Repository() {
        this.dados = new ArrayList<>();
    }

    /**
     * Adiciona um elemento do tipo T à lista.
     * @param dado Objeto do tipo T
     */
    public void adicionar(T dado) {
        if (dado != null) {
            dados.add(dado);
        }
    }

    /**
     * Retorna uma cópia imutável da lista contendo todos os elementos do tipo T.
     * Retornar List<T> é uma boa prática para não expor a lista interna diretamente.
     */
    public List<T> listar() {
        return Collections.unmodifiableList(dados);
    }

    /**
     * ============================================================================
     * CONCEITO 3: OPTIONAL (java.util.Optional<T>)
     * ============================================================================
     * Optional<T> é um container (caixa) que pode ou não conter um valor não-nulo do tipo T.
     *
     * Por que usar Optional em vez de retornar null?
     * 1. Evita o famoso erro NullPointerException (NPE).
     * 2. Deixa explícito na assinatura do método que o resultado pode NÃO existir.
     * 3. Força o chamador a tratar a ausência do valor (usando .orElseThrow(), .isPresent(), etc).
     */
    public Optional<T> buscar(Predicate<T> criterio) {
        return dados.stream()
                .filter(criterio)
                .findFirst();
    }

    /**
     * Retorna a quantidade de elementos armazenados no repositório.
     */
    public int tamanho() {
        return dados.size();
    }
}