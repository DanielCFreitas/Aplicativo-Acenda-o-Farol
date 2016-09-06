package com.example.aluno.acenda_o_farol2;

import java.util.LinkedList;

/**
 * Classe que representa um Endereco
 */
public class Endereco {

    private String endereco;

    // Construtores
    public Endereco(){

    }

    public Endereco (String endereco){
        this.endereco = endereco;
    }


    // GETTERS E SETTERS
    public String getEndereco(){
        return this.endereco;
    }

    public void setEndereco(String endereco){
        this.endereco = endereco;
    }

    /** Método que verifica se o motorista está em uma rodovia     *
     * @param rodovias Lista de rodovias
     * @return Retorna True se for uma rodovia e False se não for rodovia
     */
    public boolean verificaRodovia(LinkedList<Endereco> rodovias){
        for (Endereco rodovia : rodovias){
            if (this.endereco.equals(rodovia.getEndereco())){
                return true;
            }
        }
        return false;
    }
}
