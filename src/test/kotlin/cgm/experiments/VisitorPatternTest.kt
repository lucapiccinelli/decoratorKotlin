package cgm.experiments

import org.junit.jupiter.api.Test


class VisitorPatternTest {

    @Test
    internal fun name() {
        val search = InternationalSearch()
        val article: Article = search.search("banana")

        val useArticle = UseArticle()
        useArticle.printName(article)

        val useArticle2 = UseArticle2(0)
        useArticle2.sumCode(article)
    }
}

class UseArticle2(private val initialValue: Int) {
    fun sumCode(article: Article): Int {
        return when(article){
            is Article.ItaArticle -> initialValue + article.minsan.toInt()
            is Article.DeuArticle -> initialValue + article.code
            is Article.EspArticle -> TODO()
        }
    }
}

class UseArticle {
    fun printName(article: Article) {
        when (article) {
            is Article.ItaArticle -> println(article.description)
            is Article.DeuArticle -> println(article.name)
            is Article.EspArticle -> TODO()
        }.exhaustive
    }
}

val Any.exhaustive: Unit get() = Unit

class InternationalSearch(){

    fun search(articleName: String): Article = TODO("")

}

sealed class Article {
    data class ItaArticle(val minsan: String, val description: String, val ean: Int): Article()
    data class DeuArticle(val code: Int, val name: String, val pzn: Double): Article()
    data class EspArticle(val code: Double): Article()
}

