package sample;

public class ProgramController {

    private static Scheduler currentScheduler;

    public static Scheduler getCurrentScheduler() {
        return currentScheduler;
    }

    public static void setCurrentScheduler(Scheduler currentScheduler) {
        ProgramController.currentScheduler = currentScheduler;
    }
}
