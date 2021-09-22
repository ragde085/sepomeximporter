package mx.silverwolfdev.sepomeximporter.subscriber;

import lombok.extern.log4j.Log4j2;
import mx.silverwolfdev.sepomeximporter.model.SepomexXmlEntry;

import java.util.concurrent.Flow;

@Log4j2
public class TraceSubscriber implements Flow.Subscriber<SepomexXmlEntry> {

    private Flow.Subscription subscription;
    private int counter = 0;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1); //  Ask for initial one data object.
    }

    @Override
    public void onNext(SepomexXmlEntry item) {
        log.trace(item);
        counter++;
        subscription.request(1); // Ask for one more.
    }

    @Override
    public void onError(Throwable throwable) {
        log.error(throwable);
    }

    @Override
    public void onComplete() {
        log.debug("DONE processed {} items", counter);
    }
}