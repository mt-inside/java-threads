import java.util.concurrent.*;

import rx.*;
import rx.schedulers.*;

public class Main
{
    public static void main (String args[])
    {
        System.out.println("lol");

        ExecutorService e = Executors.newSingleThreadExecutor();
        ExecutorService e2 = Executors.newSingleThreadExecutor();
        e.submit( () -> System.out.println(idThread()) );
        e2.submit( () -> System.out.println(idThread()) );

        Observable<String> o = Observable.<String>create(
                sub -> sub.onNext(idThread())
                )
            .<String>subscribeOn(Schedulers.from(e))
            .<String>observeOn(Schedulers.from(e2));

        o.subscribe( s ->
                {
                    System.out.println(s); //subscribeon
                    System.out.println(idThread()); // subscribeon, observeOn
                }
                );


        try { Thread.sleep(1000); } catch (Exception ex) { }

        e.shutdown();
    }

    private static String idThread ()
    {
        return Thread.currentThread().getName() +
            "|" + Thread.currentThread().getId();
    }
}
