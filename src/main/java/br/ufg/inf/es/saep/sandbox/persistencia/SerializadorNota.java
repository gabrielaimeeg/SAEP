package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import br.ufg.inf.es.saep.sandbox.dominio.Pontuacao;
import br.ufg.inf.es.saep.sandbox.dominio.Relato;
import com.google.gson.*;
import com.mongodb.util.JSON;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SerializadorNota implements JsonDeserializer<Nota> {
    static Gson gson = new Gson();

    @Override
    public Nota deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        String justificativa = jsonElement.getAsJsonObject().get("justificativa").getAsString();

        JsonObject novoJsonObject = jsonElement.getAsJsonObject().get("novo").getAsJsonObject();
        JsonObject originalJsonObject = jsonElement.getAsJsonObject().get("original").getAsJsonObject();

        Avaliavel original = verificaInstaciaAvaliavel(novoJsonObject);
        Avaliavel novo = verificaInstaciaAvaliavel(originalJsonObject);

        return new Nota(
                original,
                novo,
                justificativa
        );
    }

    private Avaliavel verificaInstaciaAvaliavel(JsonObject jsonObject){
        if(jsonObject.has("valor")){
            return gson.fromJson(jsonObject, Pontuacao.class);
        } else {
            return gson.fromJson(jsonObject, Relato.class);
        }
    }

}
