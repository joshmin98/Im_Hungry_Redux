package ImHungry;

import ro.pippo.core.Pippo;
import ro.pippo.core.route.Route;

/**
 * Run application from here.
 */
public class PippoLauncher {
    public static void main(String[] args) {
        Pippo pippo = new Pippo(new PippoApplication());
        pippo.addRoute(new Route("OPTIONS", "/.*", r -> {}));
        pippo.start();
    }
}
