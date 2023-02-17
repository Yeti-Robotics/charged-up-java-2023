package frc.robot.commands.rest;

import frc.robot.utils.rests.restAnnotations.REST;
import frc.robot.utils.rests.restUtils.RESTContainer;

import javax.inject.Inject;
import java.security.cert.TrustAnchor;

public class ExampleREST extends RESTContainer {
    @Inject
    public ExampleREST () {
    }

    @REST
    public void exampleTest() {
        init(() -> {});
        execute(() -> {});
        isFinished(() -> {return true;});
        end(() -> {});
    }
}
