public class Timer <T> {
    private long timeWhenWasStarted;
    public Timer(){
        timeWhenWasStarted = System.nanoTime();
    }
    public void startCounting(){
        timeWhenWasStarted = System.nanoTime();
    }
    public String stopTimer(String functionName, T result){
        final long timeWhenWasStopped = System.nanoTime();
        if(timeWhenWasStarted == -1){
            return "Таймер не було попередньо запущено, тому він не може бути зупинений!";
        }
        final long duration = timeWhenWasStopped - timeWhenWasStarted;
        return String.format("Виконання функції %26s зайняло: %2.5f секунд; Результат виконання: %s", functionName, duration / 1_000_000_000., result);
    }
}
