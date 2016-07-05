package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import com.google.gson.Gson;
import org.bson.Document;

import java.util.List;

public class ResolucaoPersistencia implements ResolucaoRepository {
    IPersistencia iPersistencia = new MongoPersistencia();
    static Gson gson = new Gson();
    String resolucaoCollection = "resolucaoCollection";
    String tipoCollection = "tipoCollection";

    private void limpaBase(String collection) {
        iPersistencia.limpaBase(collection);
    }

    @Override
    public Resolucao byId(String id) {
        Document document = iPersistencia.buscaJSON(resolucaoCollection, id);
        return gson.fromJson(document.toJson(), Resolucao.class);
    }

    @Override
    public String persiste(Resolucao resolucao) {
        String json = gson.toJson(resolucao);
        iPersistencia.persisteJSON(resolucaoCollection, json);
        return resolucao.getId();
    }

    @Override
    public boolean remove(String identificador) {
        boolean result = false;
        try {
            iPersistencia.deletaJSON(tipoCollection, identificador);
            result = true;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return result;
    }

    @Override
    public List<String> resolucoes() {
        return iPersistencia.pegaIdsCollection(resolucaoCollection, "id");
    }

    @Override
    public void persisteTipo(Tipo tipo) {
        String json = gson.toJson(tipo);
        iPersistencia.persisteJSON(tipoCollection, json);
    }

    @Override
    public void removeTipo(String codigo) {
        iPersistencia.deletaJSON(tipoCollection, codigo);
    }

    @Override
    public Tipo tipoPeloCodigo(String codigo) {
        Document document = iPersistencia.buscaJSON(tipoCollection, codigo);
        return gson.fromJson(document.toJson(), Tipo.class);
    }

    @Override
    public List<Tipo> tiposPeloNome(String nome) {
        return null;
    }
}
