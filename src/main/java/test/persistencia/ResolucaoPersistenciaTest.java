package test.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.Atributo;
import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.persistencia.IPersistencia;
import br.ufg.inf.es.saep.sandbox.persistencia.MongoPersistencia;
import br.ufg.inf.es.saep.sandbox.persistencia.ResolucaoPersistencia;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class ResolucaoPersistenciaTest {
    IPersistencia banco = new MongoPersistencia();
    ResolucaoPersistencia resolucaoPersistencia = new ResolucaoPersistencia();
    String nomeCollectionResolucao = "resolucaoCollection";
    String nomeCollectionTipo = "tipoCollection";

    public ResolucaoPersistenciaTest() {
        banco.iniciaConexaoBD();
    }

    @Before
    public void limpaBase() {
        banco.limpaBase(nomeCollectionResolucao);
        banco.limpaBase(nomeCollectionTipo);
    }

    @Test
    public void testaById() {
        Resolucao resolucaoDesejada = criaResolucao("1");
        Resolucao resolucaoBuscada;
        resolucaoPersistencia.persiste(resolucaoDesejada);
        resolucaoBuscada = resolucaoPersistencia.byId("1");

        Assert.assertEquals(resolucaoDesejada.getId(), resolucaoBuscada.getId());

    }

    @Test
    public void testaPersiste() {
        Resolucao resolucao = criaResolucao("1");
        resolucaoPersistencia.persiste(resolucao);

        Assert.assertTrue(banco.busca(nomeCollectionResolucao, "id", "1").get("id").equals("1"));
    }

    @Test
    public void testaRemove() {
        resolucaoPersistencia.persiste(criaResolucao("1"));
        resolucaoPersistencia.remove("1");

        Assert.assertNull(resolucaoPersistencia.byId("1"));
    }

    @Test
    public void testaListaDeResolucoes() {
        resolucaoPersistencia.persiste(criaResolucao("1"));
        resolucaoPersistencia.persiste(criaResolucao("4"));
        resolucaoPersistencia.persiste(criaResolucao("6"));

        List<String> listaIDs = new ArrayList<>();
        listaIDs.add("1");
        listaIDs.add("4");
        listaIDs.add("6");

        Assert.assertEquals(listaIDs.get(0), resolucaoPersistencia.resolucoes().get(0));
        Assert.assertEquals(listaIDs.get(1), resolucaoPersistencia.resolucoes().get(1));
        Assert.assertEquals(listaIDs.get(2), resolucaoPersistencia.resolucoes().get(2));

    }

    @Test
    public void testaPersisteTipo() {
        resolucaoPersistencia.persisteTipo(criaTipo("2", "nome"));

        Assert.assertNotNull(resolucaoPersistencia.tipoPeloCodigo("2"));

    }

    @Test
    public void testaRemoveTipo() {
        resolucaoPersistencia.persisteTipo(criaTipo("8", "nome"));
        resolucaoPersistencia.removeTipo("8");

        Assert.assertNull(resolucaoPersistencia.tipoPeloCodigo("1"));
    }

    @Test
    public void testaTipoPeloCodigo() {
        Tipo tipoDesejado = criaTipo("1", "nome");
        Tipo tipoBuscado;
        resolucaoPersistencia.persisteTipo(tipoDesejado);
        tipoBuscado = resolucaoPersistencia.tipoPeloCodigo("1");

        Assert.assertEquals(tipoDesejado.getId(), tipoBuscado.getId());
    }

    @Test
    public void testaTiposPeloNome() {

        resolucaoPersistencia.persisteTipo(criaTipo("1", "nome1"));
        resolucaoPersistencia.persisteTipo(criaTipo("2", "nome2"));
        resolucaoPersistencia.persisteTipo(criaTipo("3", "nome3"));

        resolucaoPersistencia.tiposPeloNome("nome");

        List<Tipo> listaTipo = new ArrayList<>();
        listaTipo.add(criaTipo("1", "nome1"));
        listaTipo.add(criaTipo("2", "nome2"));
        listaTipo.add(criaTipo("3", "nome3"));

        Assert.assertEquals(listaTipo.get(0), resolucaoPersistencia.tiposPeloNome("nome").get(0));
        Assert.assertEquals(listaTipo.get(1), resolucaoPersistencia.tiposPeloNome("nome").get(1));
        Assert.assertEquals(listaTipo.get(2), resolucaoPersistencia.tiposPeloNome("nome").get(2));
    }

    private Resolucao criaResolucao(String id) {

        ArrayList<Regra> regras = new ArrayList<Regra>();
        ArrayList<String> dependeDe = new ArrayList<String>();
        Regra regra1 = new Regra("variavel1", 1, "regra1", 10, 10, "expressao", "entao", "senao", "alguma outra coisa", 10.0f, dependeDe);
        Regra regra2 = new Regra("variavel2", 2, "regra2", 10, 10, "expressao", "entao", "senao", "algum outro teste", 10.0f, dependeDe);

        regras.add(regra1);
        regras.add(regra2);
        return new Resolucao(id, "CONSUNI", "descricao", new Date(), regras);
    }

    private Tipo criaTipo(String id, String nome) {
        Set<Atributo> atributos = new LinkedHashSet<>();
        atributos.add(criaAtributo());
        return new Tipo(id, nome, "descricao", atributos);
    }

    private Atributo criaAtributo() {
        return new Atributo("nomeAtributo", "descricaoAtributo", 1);
    }

}
