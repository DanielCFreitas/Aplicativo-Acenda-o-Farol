package com.example.aluno.acenda_o_farol2;

import java.util.LinkedList;

/**
 * Classe que representa um Endereco
 */
public class Endereco {

    private String rua;
    private String cidade;

    // Construtores
    public Endereco(){

    }

    public Endereco(String rua, String cidade){
        this.rua = rua;
        this.cidade = cidade;
    }

    // GETTERS E SETTERS
    public String getEndereco(){
        return this.rua;
    }

    public void setRuaAtual(String rua){
        this.rua = rua;
    }

    public String getCidade(){
        return this.cidade;
    }

    public void setCidade(String cidade){
        this.cidade = cidade;
    }

    /** Método que verifica se o motorista está em uma rodovia     *
     * @param rodovias Lista de rodovias
     * @return Retorna True se for uma rodovia e False se não for rodovia
     */
    public boolean verificaRodovia(LinkedList<Endereco> rodovias){
        for (Endereco rodovia : rodovias){
            if (this.rua.equals(rodovia.getEndereco())){
                return true;
            }
        }
        return false;
    }
}
