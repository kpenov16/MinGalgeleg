package dk.kaloyan.async;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
//EpicPandaForce showed this example here https://stackoverflow.com/questions/58767733/android-asynctask-api-deprecating-in-android-11-what-are-the-alternatives
//as an example of couping with that Android AsyncTask API deprecating in Android 11
//I've modified it a bit
public class TaskRunner {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public interface Callback<R>{
        void onComplete(R result);
    }

    public <R> void executeAsync(Callable<R> callable, Callback<R> callback){
        executor.execute(()->{
            R result = null;
            try {
                result = callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            R finalResult = result;
            handler.post(()->{
                callback.onComplete(finalResult);
            });
        });

    }
}
