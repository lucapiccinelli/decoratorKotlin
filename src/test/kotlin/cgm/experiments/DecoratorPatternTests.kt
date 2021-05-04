package cgm.experiments

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class DecoratorPatternTests {

    @Test
    internal fun `should render the window`() {
        val window = MyWindow2()
        window.render() shouldBe """
            ************
            *          *
            *          *
            *          *
            ************""".trimMargin()
    }

    @Test
    internal fun `should render the window with a scrollbar`() {
        val window = MyScrollBar(MyWindow2())
        window.render() shouldBe """
            ************||
            *          *||
            *          *||
            *          *||
            ************||""".trimMargin()
    }

    @Test
    internal fun `should render the window with a status bar`() {
        val window = MyStatusBar(MyWindow2())
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
        val window = MyScrollBar(MyStatusBar(MyWindow2()))
        window.render() shouldBe """
            ************||
            *          *||
            *          *||
            *          *||
            ************||
            ............||
            ............||""".trimMargin()
    }

    @Test
    internal fun `should render the window with a scrollbar and a status bar`() {
        val window = MyStatusBar(MyScrollBar(MyWindow2()))
        window.render() shouldBe """
            ************||
            *          *||
            *          *||
            *          *||
            ************||
            ............
            ............""".trimMargin()
    }
}

class MyScrollBar(private val window: Window): Window by window{
    override fun render(): String {
        return window.render()
            .split("\n")
            .joinToString("\n") { "$it||" }
    }
}

class MyStatusBar(private val window: Window): Window {
    override fun render(): String {
        return """${window.render()}
            ............
            ............""".trimMargin()
    }

    override fun add(window: Window) = window.add(window)
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

interface Window {
    fun render(): String
    fun add(window: Window)
}

class MyWindow2 : Window {
    val w: MutableList<Window> = mutableListOf()

    override fun render(): String = """
            ************
            *          *
            *          *
            *          *
            ************""".trimMargin()

    override fun add(window: Window) {
        w.add(window)
    }
}