package cgm.experiments

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class EventTests{

    @Test
    internal fun name() {
        var myArgsTestArgs: TestArgs? = null

        val event = MyEvent<TestArgs>()

        event + { args: TestArgs ->
            myArgsTestArgs = args
        }

        val actualArgs = TestArgs(0)
        event(actualArgs)

        myArgsTestArgs shouldBe actualArgs
    }
}

data class TestArgs(val x: Int)

class MyEvent<T> {
    val listeners = mutableListOf<(T) -> Unit>()

    infix fun addListener(listener: (T) -> Unit) {
        listeners.add(listener)
    }

    operator fun plus(listener: (T) -> Unit){
        addListener(listener)
    }

    operator fun invoke(args: T) {
        listeners.forEach { it(args) }
    }
}

class Configurazione(
    private val vendita: VenditaAlBanco,
    private val magazzino: Magazzino,
    private val statistiche: Statistiche
    ){

    fun exec(){
        vendita.vendutoArticolo.addListener { venduto ->
            magazzino.giacenza(venduto.article, venduto.qta)
        }

        vendita.vendutoArticolo.addListener { venduto ->
            statistiche.aggiorna(venduto.article, venduto.qta)
        }
    }
}

class VenditaAlBancoGUI(private val vendita: VenditaAlBanco){
    fun vendi(){
        vendita.sell("banane")
    }
}

class VenditaAlBanco(){
    val vendutoArticolo = MyEvent<VendutoArticoloArgs>()

    fun sell(article: String){
        vendo()
        vendutoArticolo.invoke(VendutoArticoloArgs(article, 1))
    }

    private fun vendo(): Unit = TODO("vendo")
}

data class VendutoArticoloArgs(val article: String, val qta: Int)

class Magazzino {
    fun giacenza(article: String, qta: Int): Unit = TODO("magazzino")
}

class Statistiche(){
    fun aggiorna(article: String, qta: Int): Unit = TODO("statistiche")
}