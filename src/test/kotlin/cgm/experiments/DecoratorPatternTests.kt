package cgm.experiments

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class DecoratorPatternTests {

    @Test
    internal fun `should render the window`() {
        val window = MyWindow()
        window.render() shouldBe """
            ************
            *          *
            *          *
            *          *
            ************""".trimMargin()
    }

    @Test
    internal fun `should render the window with a status bar`() {
        val window = MyWindowWithStatus()
        window.render() shouldBe """
            ************
            *          *
            *          *
            *          *
            ************
            ............
            ............""".trimMargin()
    }

    @Test
    internal fun `should render the window with a status bar and scrollbar`() {
        val window = MyWindowWithStatusAndScrollBar()
        window.render() shouldBe """
            ************||
            *          *||
            *          *||
            *          *||
            ************||
            ............||
            ............||""".trimMargin()
    }
}

class MyWindowWithStatusAndScrollBar: MyWindowWithStatus() {
    override fun render(): String {
        return super.render()
            .split("\n")
            .joinToString("\n") { "$it||" }
    }
}

open class MyWindowWithStatus : MyWindow() {
    override fun render(): String {
        return """${super.render()}
            ............
            ............""".trimMargin()
    }
}

open class MyWindow {
    open fun render(): String = """
            ************
            *          *
            *          *
            *          *
            ************""".trimMargin()
}