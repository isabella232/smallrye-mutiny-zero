package docsamples;

import static mutiny.zero.ZeroPublisher.create;

import org.reactivestreams.Publisher;

import mutiny.zero.BackpressureStrategy;

public class FromTube {

    public static void main(String[] args) {

        SampleAsyncSource source = new SampleAsyncSource();

        Publisher<String> pub = create(BackpressureStrategy.BUFFER, 256, tube -> {

            // Start
            source.start();

            // Allow items to be received
            tube.whenRequested(n -> source.resume());

            // Termination cases
            tube.whenCancelled(source::close);
            tube.whenTerminates(() -> System.out.println("Done"));

            // Emit items, pause the source if need be
            source.onItem(str -> {
                tube.send(str);
                if (tube.outstandingRequests() == 0L) {
                    source.pause();
                }
            });

            // Error
            source.onError(tube::fail);

            // Completion
            source.onEnd(tube::complete);

        });
    }
}
