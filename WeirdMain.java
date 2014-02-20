import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

class WeirdHelloWorld {
    public static void main(String... args) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        final Integer x = 42;
        Future<HashSet<String>> future = exec.submit(new Callable<HashSet<String>>() {
            String result = new String[] {"Hello world!", "Something else"} [x - x];
            @Override
            public HashSet<String> call() {
                HashSet<String> toPrint = new HashSet<>();
                Integer y = 41;
                if (++y == x) {
                    toPrint.add(result);
                    return toPrint;
                }
                throw new IllegalStateException("Can't get here");
            }
        });
        Set<?> toPrint = Collections.<String>emptySet();
        try {
            toPrint = future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            if (e.getCause() instanceof IllegalStateException)
                System.out.println("Oops!");
        }
        try {
            exec.shutdownNow();
            exec.awaitTermination(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        ((new WeirdMain()).new Printer()).print(toPrint);
    }

    private class Printer {
        private void print(Set<? extends Object> toPrint) {
            for (Object str : toPrint) {
                System.out.println(str.toString());
            }
        }
    }
}
