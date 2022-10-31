package org.magix;


import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.sse.Event;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import io.reactivex.rxjava3.processors.MulticastProcessor;

@Controller("/magix")
public class Magix {

    private final MulticastProcessor<Event<String>> eventFlowableProcessor = MulticastProcessor.create();

    {
        eventFlowableProcessor.start();
    }

    @Get("/subscribe")
    @Produces(MediaType.TEXT_EVENT_STREAM)
    public Flowable<Event<String>> subscribe(){
        return eventFlowableProcessor;
    }


    @Post("/broadcast")
    @Consumes(MediaType.TEXT_PLAIN)
    public void broadcast(String message){
        eventFlowableProcessor.onNext(
                Event.of(message)
        );
    }
}
