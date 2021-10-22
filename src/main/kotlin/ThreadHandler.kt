import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class ThreadHandler(var mainDispatcher: CoroutineDispatcher = Dispatchers.Default, var t1Dispatcher: CoroutineDispatcher = Dispatchers.IO, var t2Dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private var result = Result()
    private var printer = UIprinter()

    suspend fun excute(){
        val totalTime = measureTimeMillis {
            val coroutineScope = CoroutineScope(mainDispatcher)
            val job1 = coroutineScope.launch {
                withContext(t1Dispatcher) {
                    result.atomicInteger.addAndGet(1)
                    printer.printAction("Result thread 1 = ${result.atomicInteger.get()}")
                    delay(2000)
                }
            }
            val job2 = coroutineScope.launch {
                withContext(t2Dispatcher) {
                    result.atomicInteger.addAndGet(2)
                    printer.printAction("Result thread 2 = ${result.atomicInteger.get()}")
                    delay(1000)
                }
            }
            job1.join()
            job2.join()

            printer.printResult(result.atomicInteger.toString())
        }
        printer.printTotalTime(totalTime)
    }

}