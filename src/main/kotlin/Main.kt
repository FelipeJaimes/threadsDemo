import kotlinx.coroutines.*
import java.util.concurrent.atomic.*
import kotlin.system.measureTimeMillis

/*fun main(): Unit = runBlocking {
    val threadHandler = ThreadHandler()
    threadHandler.excute()
}*/

val result = AtomicInteger()

fun main(): Unit = runBlocking {
    val totalTime = measureTimeMillis {
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        val job1 = coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    result.addAndGet(1)
                    println("Result thread 1 = ${result.get()}")
                    delay(2000)
                }
        }
        val job2 = coroutineScope.launch {
            withContext(Dispatchers.IO) {
                result.addAndGet(2)
                println("Result thread 2 = ${result.get()}")
                delay(1000)
            }
        }
        job1.join()
        job2.join()
        println("Result = $result")
    }
    println("Total time = $totalTime")
}




