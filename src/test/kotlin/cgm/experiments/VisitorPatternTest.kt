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
            is ItaArticle -> initialValue + article.minsan.toInt()
            is DeuArticle -> initialValue + article.code
            else -> throw Exception("booooom")
        }
    }

    fun sumCode2(article: Article): Int {
        var sum = initialValue

        article.readBy(object : ArticleReader{
            override fun readIta(article: ItaArticle) {
                sum += article.minsan.toInt()
            }

            override fun readDeu(article: DeuArticle) {
                sum += article.code
            }

            override fun readEsp(espArticle: EspArticle) {
                sum += espArticle.code.toInt()
            }
        })

        return sum
    }

}

class UseArticle {
    fun printName(article: Article) {
        when(article){
            is ItaArticle -> println(article.description)
            is DeuArticle -> println(article.name)
            else -> println("unknown")
        }
    }

    fun printName2(article: Article) {
        article.readBy(object : ArticleReader{
            override fun readIta(article: ItaArticle) {
                println(article.description)
            }

            override fun readDeu(article: DeuArticle) {
                println(article.name)
            }

            override fun readEsp(espArticle: EspArticle) {
                println("esp article non ha nome")
            }
        })
    }
}

class InternationalSearch(){

    fun search(articleName: String): Article = TODO("")

}

interface Article {
    fun readBy(reader: ArticleReader)
}

interface ArticleReader {

    fun readIta(article: ItaArticle)
    fun readDeu(article: DeuArticle)
    fun readEsp(espArticle: EspArticle)

}

data class ItaArticle(val minsan: String, val description: String, val ean: Int): Article {
    override fun readBy(reader: ArticleReader) {
        reader.readIta(this)
    }
}

data class DeuArticle(val code: Int, val name: String, val pzn: Double): Article{
    override fun readBy(reader: ArticleReader) {
        reader.readDeu(this)
    }
}

data class EspArticle(val code: Double): Article{
    override fun readBy(reader: ArticleReader) {
        reader.readEsp(this)
    }
}

