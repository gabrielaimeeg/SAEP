package br.ufg.inf.es.saep.sandbox.persistencia;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;

public class MongoPersistencia implements IPersistencia {
    String nomeBanco = "saep";
    MongoDatabase mongoDatabase;

    private static MongoPersistencia instance;

    public static synchronized MongoPersistencia getInstance() {
        if (instance == null)
            instance = new MongoPersistencia();

        return instance;
    }

    @Override
    public void iniciaConexaoBD() {
        MongoClient mongoClient = new MongoClient();
        mongoDatabase = mongoClient.getDatabase(nomeBanco);
    }

    public void limpaBase(String collection) {
        mongoDatabase.getCollection(collection).drop();
    }


    @Override
    public void persiste(String collection, String json) {
        Document document = Document.parse(json);
        mongoDatabase.getCollection(collection).insertOne(document);
    }

    @Override
    public Document busca(String collection, String tipoIdentificador, String valorIdentificador) {
        return mongoDatabase.getCollection(collection).find(eq(tipoIdentificador, valorIdentificador)).first();
    }

    @Override
    public void atualiza(String collection, String identificadorObjeto, String valorIdentificador, String json) {
        mongoDatabase.getCollection(collection).replaceOne(
                eq(identificadorObjeto, valorIdentificador),
                Document.parse(json));
    }

    @Override
    public void deleta(String collection, String tipoIdentificador, String valorIdentificador) {
        mongoDatabase.getCollection(collection).deleteOne(eq(tipoIdentificador, valorIdentificador));
    }

    @Override
    public void atualizaDocumentUsandoFiltro(String collection, Document parecer, Document filtro) {
        mongoDatabase.getCollection(collection).updateOne(parecer, filtro);
    }

    public List<String> listaValoresDeCollection(String collection, String valorIdentificador) {
        List<String> listaIds = new ArrayList<String>();

        for (Document resolucao : mongoDatabase.getCollection(collection).find()) {
            listaIds.add(resolucao.getString(valorIdentificador));
        }
        return listaIds;
    }

    @Override
    public Iterable<Document> encontraTodosDocumentsPorFiltro(String collection, String identificadorValor, String identificadorNome) {
        return mongoDatabase.getCollection(collection).find(new Document(identificadorNome, Pattern.compile(identificadorValor)));
    }

}
