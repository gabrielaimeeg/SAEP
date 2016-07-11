package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.Document;

import java.util.List;

public class ParecerPersistencia implements ParecerRepository {
    IPersistencia mongoPersistencia = MongoPersistencia.getInstance();
    GsonBuilder gsonBuilder;
    static Gson gson;
    String parecerCollection = "parecerCollection";
    String radocCollection = "radocCollection";

    public ParecerPersistencia() {
        mongoPersistencia.iniciaConexaoBD();
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Nota.class, new SerializadorNota());
        gson = gsonBuilder.create();
    }

    private void limpaBase(String collection) {
        mongoPersistencia.limpaBase(collection);
    }

    @Override
    public void adicionaNota(String parecer, Nota nota) {
        Document document = mongoPersistencia.buscaJSON(parecerCollection, "id", parecer);
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

        mongoPersistencia.atualizaJSON(parecerCollection, "id", parecer, json);
    }

    @Override
    public void removeNota(String id, Avaliavel original) {
        Document parecer = mongoPersistencia.buscaJSON(parecerCollection, "id", id);
        Document filtro = Document.parse(gson.toJson(new Document("$pull", new Document("notas", new Document("original", original)))));

        mongoPersistencia.atualizaDocumentUsandoFiltro(parecerCollection, parecer, filtro);
    }

    @Override
    public void persisteParecer(Parecer parecer) {
        String json = gson.toJson(parecer);
        mongoPersistencia.persisteJSON(parecerCollection, json);
    }

    @Override
    public void atualizaFundamentacao(String parecer, String fundamentacao) {
        Document document = mongoPersistencia.buscaJSON(parecerCollection, "id", parecer);
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

        mongoPersistencia.atualizaJSON(parecerCollection, "id", parecer, json);
    }

    @Override
    public Parecer byId(String id) {
        Document document = mongoPersistencia.buscaJSON(parecerCollection, "id", id);
        return gson.fromJson(gson.toJson(document), Parecer.class);
    }

    @Override
    public void removeParecer(String id) {
        mongoPersistencia.deletaJSON(parecerCollection, "id", id);
    }

    @Override
    public Radoc radocById(String identificador) {
        Document document = mongoPersistencia.buscaJSON(radocCollection, "id", identificador);
        return gson.fromJson(document.toJson(), Radoc.class);
    }

    @Override
    public String persisteRadoc(Radoc radoc) {
        String json = gson.toJson(radoc);
        mongoPersistencia.persisteJSON(radocCollection, json);
        return null;
    }

    @Override
    public void removeRadoc(String identificador) {
        mongoPersistencia.deletaJSON(radocCollection, "id", identificador);
    }
}
