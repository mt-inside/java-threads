import java.util.concurrent.*;

public class Main
{
    public static void main (String args[])
    {
        System.out.println("lol");

        ThreadGroup tg = new MyGroup("group");

        Thread t = new Thread(tg, new Crasher());

        /* t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Uncaught in specific. Running on thread " + idThread());
            }
        }); */
        t.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Uncaught in default. Running on thread " + idThread());
            }
        });
        t.start();

        System.out.println("main still going");
    }

    private static String idThread ()
    {
        return Thread.currentThread().getName() +
            "|" + Thread.currentThread().getId();
    }

    private static class Crasher implements Runnable
    {
        @Override
        public void run ()
        {
            System.out.println(idThread());
            throw new RuntimeException();
        }
    }

    private static class MyGroup extends ThreadGroup
    {
        MyGroup (String name)
        {
            super(name);
        }

        @Override public void uncaughtException (Thread t, Throwable e)
        {
            System.out.println("Uncaught in group. Running on " + idThread());
        }
    }
}
