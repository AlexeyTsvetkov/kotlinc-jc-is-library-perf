import java.io.File
import org.jetbrains.kotlin.utils.LibraryUtils.isKotlinJavascriptLibrary

inline fun Int.timesRun(title: String, fn: ()->Unit) {
    var runTime = 0L

    println("Running '$title' $this times:")

    for (i in 0..this) {
        val start = System.nanoTime()
        fn()
        val end = System.nanoTime()
        runTime += end - start
    }

    val runTimeSeconds = runTime.toDouble() / 1000000000
    val averageRunTimeSecond = runTimeSeconds / this
    println(String.format("Time: %.02f", runTimeSeconds))
    println(String.format("Avg. time: %.02f", averageRunTimeSecond))
}

fun main(args: Array<String>) {
    val jsStdlibJar = File("build/js-stdlib")
            .listFiles()
            .single { it.isFile && it.extension == "jar" }

    Thread.sleep(3000)

    1000.timesRun("isKotlinJavascriptLibrary") {
        isKotlinJavascriptLibrary(jsStdlibJar)
    }

    var size = 0
    1000.timesRun("readFile") {
        size += jsStdlibJar.readBytes().size
    }
}