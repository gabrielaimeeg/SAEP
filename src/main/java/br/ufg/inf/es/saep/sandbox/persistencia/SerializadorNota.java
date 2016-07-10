package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import br.ufg.inf.es.saep.sandbox.dominio.Pontuacao;
import br.ufg.inf.es.saep.sandbox.dominio.Relato;
import com.google.gson.*;

import java.lang.reflect.Type;

public class SerializadorNota implements JsonSerializer<Nota>, JsonDeserializer<Nota> {
    static Gson gson = new Gson();

    @Override
    public JsonElement serialize(Nota nota, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("justificativa", nota.getJustificativa());
        adicionaNomeDaClasseDoAvaliavel(jsonObject, "classeAvaliavelOriginal", nota.getItemOriginal());
        jsonObject.add("original", gson.toJsonTree(nota.getItemOriginal()));
        adicionaNomeDaClasseDoAvaliavel(jsonObject, "classeAvaliavelNovo", nota.getItemNovo());
        jsonObject.add("novo", gson.toJsonTree(nota.getItemNovo()));

        return jsonObject;
    }

    private void adicionaNomeDaClasseDoAvaliavel(JsonObject jsonObject, String chave, Avaliavel avaliavel) {
        if (avaliavel instanceof Pontuacao) {
            jsonObject.addProperty(chave, "Pontuacao");
        } else {
            jsonObject.addProperty(chave, "Relato");
        }

    }

    @Override
    public Nota deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Avaliavel original = recuperaAvaliavelPorTipo(jsonObject, "classeAvaliavelOriginal", "original");
        Avaliavel novo = recuperaAvaliavelPorTipo(jsonObject, "classeAvaliavelNovo", "novo");
        String justificativa = jsonObject.get("justificativa").getAsString();
        Nota nota = new Nota(original, novo, justificativa);

        return nota;
    }

    private Avaliavel recuperaAvaliavelPorTipo(JsonObject jsonObject, String tipoClasse, String chave) {
        if (jsonObject.get(tipoClasse).getAsString().equals("Relato")) {
            return gson.fromJson(jsonObject.get(chave).getAsJsonObject(), Relato.class);
        } else {
            return gson.fromJson(jsonObject.get(chave).getAsJsonObject(), Pontuacao.class);
        }
    }
}
