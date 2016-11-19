package com.example.daniel.acendaofarol.model;

import com.example.daniel.acendaofarol.api.Connection;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe que representa as Rodovias
 */
public class Rodovias {
    HashMap<String, ArrayList<String>> todasAsRodoviasDoBrasil;
    ArrayList<String> rodoviasDaCidadeAtual;

    //Método Construtor
    public Rodovias(){
        try {
            todasAsRodoviasDoBrasil = Connection.sendGet();
        } catch (Exception e) {

        }
        rodoviasDaCidadeAtual = new ArrayList<String>();
    }

    //Getters e Setters
    public ArrayList<String> getRodoviasDaCidadeAtual(){
        return this.rodoviasDaCidadeAtual;
    }

    /**
     * Método que atualiza a lista rodoviasDaCidadeAtual com uma lista das rodovias da cidade em que o motorista se encontra
     * @param cidadeAtual parametro para verificar se existe esta cidade na lista
     */
    public void atualizarListaDeRodovias(String cidadeAtual){
        if (this.todasAsRodoviasDoBrasil.containsKey(cidadeAtual)){
            this.rodoviasDaCidadeAtual = this.todasAsRodoviasDoBrasil.get(cidadeAtual);
        }
    }
}