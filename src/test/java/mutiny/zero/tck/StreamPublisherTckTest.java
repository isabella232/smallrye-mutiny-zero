package mutiny.zero.tck;

import java.util.function.Supplier;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;

import mutiny.zero.ZeroPublisher;

public class StreamPublisherTckTest extends PublisherVerification<Long> {

    public StreamPublisherTckTest() {
        super(new TestEnvironment());
    }

    @Override
    public Publisher<Long> createPublisher(long elements) {
        Supplier<Stream<Long>> supplier = () -> LongStream.rangeClosed(1, elements).boxed();
        return ZeroPublisher.fromStream(supplier);
    }

    @Override
    public Publisher<Long> createFailedPublisher() {
        return null;
    }

    @Override
    public long maxElementsFromPublisher() {
        return 1024L;
    }
}
