package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;
import org.bson.Document;

import java.util.List;

public class ParecerPersistencia implements ParecerRepository {
    IPersistencia iPersistencia = new MongoPersistencia();
    static Gson gson = new Gson();
    String parecerCollection = "parecerCollection";
    String radocCollection = "radocCollection";

    public ParecerPersistencia() {
        iPersistencia.iniciaConexaoBD();
    }

    public void limpaBase(String collection) {
        iPersistencia.limpaBase(collection);
    }

    @Override
    public void adicionaNota(String parecer, Nota nota) {
        Document document = iPersistencia.buscaJSON(parecerCollection, parecer);
        Parecer parecerAntigaInstacia = gson.fromJson(document.toJson(), Parecer.class);

        List<Nota> listaNotas = parecerAntigaInstacia.getNotas();
        listaNotas.add(nota);

        Parecer parecerNovaInstancia = new Parecer(
                parecerAntigaInstacia.getId(),
                parecerAntigaInstacia.getResolucao(),
                parecerAntigaInstacia.getRadocs(),
                parecerAntigaInstacia.getPontuacoes(),
                parecerAntigaInstacia.getFundamentacao(),
                listaNotas
        );

        String json = gson.toJson(parecerNovaInstancia);

        iPersistencia.atualizaJSON(parecerCollection, "id", parecer, json);
    }

    @Override
    public void removeNota(Avaliavel original) {
        // TODO: 03/07/2016
    }

    @Override
    public void persisteParecer(Parecer parecer) {
        String json = gson.toJson(parecer);
        iPersistencia.persisteJSON(parecerCollection, json);
    }

    @Override
    public void atualizaFundamentacao(String parecer, String fundamentacao) {
        Document document = iPersistencia.buscaJSON(parecerCollection, parecer);
        Parecer parecerAntigaInstacia = gson.fromJson(document.toJson(), Parecer.class);

        Parecer parecerNovaInstancia = new Parecer(
                parecerAntigaInstacia.getId(),
                parecerAntigaInstacia.getResolucao(),
                parecerAntigaInstacia.getRadocs(),
                parecerAntigaInstacia.getPontuacoes(),
                fundamentacao,
                parecerAntigaInstacia.getNotas()
        );

        String json = gson.toJson(parecerNovaInstancia);

        iPersistencia.atualizaJSON(parecerCollection, "id", parecer, json);
    }

    @Override
    public Parecer byId(String id) {
        Document document = iPersistencia.buscaJSON(parecerCollection, id);
        return gson.fromJson(document.toJson(), Parecer.class);
    }

    @Override
    public void removeParecer(String id) {
        iPersistencia.deletaJSON(parecerCollection, id);
    }

    @Override
    public Radoc radocById(String identificador) {
        Document document = iPersistencia.buscaJSON(radocCollection, identificador);
        return gson.fromJson(document.toJson(), Radoc.class);
    }

    @Override
    public String persisteRadoc(Radoc radoc) {
        String json = gson.toJson(radoc);
        iPersistencia.persisteJSON(radocCollection, json);
        return null;
    }

    @Override
    public void removeRadoc(String identificador) {
        iPersistencia.deletaJSON(radocCollection, identificador);
    }
}
